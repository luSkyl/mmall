package com.mmall.controller.backend;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lcy
 * @Date 2020/2/24
 * @Description
 */
@RestController
@RequestMapping("/manage/category/")
public class CategoryManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    @PostMapping("addCategory.do")
    public ServerResponse addCategory(String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        return iCategoryService.addCategory(categoryName, parentId);
    }

    @PutMapping("set_category_name.do")
    public ServerResponse setCategoryName(Integer categoryId, String categoryName) {
        return iCategoryService.updateCategoryName(categoryId, categoryName);
    }

    @GetMapping("get_category.do")
    public ServerResponse getChildAndParallelCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        //查询子节点的category 信息，并且不递归，保持平级
        return iCategoryService.getChildAndParallelCategory(categoryId);
    }

    @GetMapping("get_deep_category.do")
    public ServerResponse getCategoryAndDeepChildCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        //查询当前节点的Id跟递归子节点的Id
        return iCategoryService.selectCategoryAndDeepChildCategory(categoryId);
    }
}
