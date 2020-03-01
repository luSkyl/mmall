package com.mmall.dao;

import com.mmall.pojo.Category;

import java.util.List;

public interface CategoryMapper {
    /**
     * 根据Id删除品类
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增品类
     * @param record
     * @return
     */
    int insert(Category record);

    /**
     * 新增品类
     * @param record
     * @return
     */
    int insertSelective(Category record);

    /**
     * 根据Id查询品类
     * @param id
     * @return
     */
    Category selectByPrimaryKey(Integer id);

    /**
     * 更新品类
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Category record);

    /**
     * 更新品类
     * @param record
     * @return
     */
    int updateByPrimaryKey(Category record);

    /**
     * 根据parent_id查找同一类的品类(不递归)
     * @param parentId
     * @return
     */
    List<Category> selectChildAndParallelByCategoryId(Integer parentId);
}