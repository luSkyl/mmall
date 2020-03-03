package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author lcy
 * @Date 2020/2/24
 * @Description
 */
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {
    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("添加品类参数错误");
        }
        Category category = Category.builder().name(categoryName).parentId(parentId).status(true).build();
        int resultCount = categoryMapper.insertSelective(category);
        if (resultCount > 0) {
            return ServerResponse.createBySuccess("添加种类成功");
        }
        return ServerResponse.createByErrorMessage("添加种类失败");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("更新品类参数错误");
        }
        Category category = Category.builder().id(categoryId).name(categoryName).build();
        int resultCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (resultCount > 0) {
            return ServerResponse.createBySuccess("更新种类名称成功");
        }
        return ServerResponse.createByErrorMessage("更新种类名称失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildAndParallelCategory(Integer categoryId) {
        List<Category> categoryList = categoryMapper.selectChildAndParallelByCategoryId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> selectCategoryAndDeepChildCategory(Integer categoryId) {
        HashSet<Category> categorySet = Sets.newHashSet();
        Set<Category> categories = findChildCategory(categorySet, categoryId);
        List<Integer> categoryList = Lists.newArrayList();
        if (categoryId != null) {
            if (categories.size() > 0) {
                categories.forEach(e -> categoryList.add(e.getId()));
            }
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归算法
     *
     * @param categorySet
     * @param categoryId
     * @return
     */
    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectChildAndParallelByCategoryId(categoryId);
        if (categoryList.size() > 0) {
            categoryList.forEach(e -> findChildCategory(categorySet, e.getId()));
        }
        return categorySet;
    }
}
