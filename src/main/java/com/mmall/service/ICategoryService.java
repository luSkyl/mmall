package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * @Author lcy
 * @Date 2020/2/24
 * @Description 种类 业务层
 */
public interface ICategoryService {
    /**
     * 增加种类
     * @param categoryName
     * @param parentId
     * @return
     */
    ServerResponse addCategory(String categoryName, Integer parentId);

    /**
     *更新品类名字
     * @param categoryId
     * @param categoryName
     * @return
     */
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    /**
     * 获取当前品类的同一级别的子品类(不递归)
     * @param categoryId
     * @return
     */
    ServerResponse<List<Category>> getChildAndParallelCategory(Integer categoryId);

    /**
     * 查询当前品类以及其子品类
     * @param categoryId
     * @return
     */
    ServerResponse<List<Integer>> selectCategoryAndDeepChildCategory(Integer categoryId);
}
