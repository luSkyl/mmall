package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author :lcy
 */
public interface UserMapper {
    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入用户
     * @param record
     * @return
     */
    int insert(User record);

    /**
     * 插入用户
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     * 查找用户
     * @param id
     * @return
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 更新用户
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 更新用户
     * @param record
     * @return
     */
    int updateByPrimaryKey(User record);

    /**
     * 检查用户是否存在
     * @param username
     * @return
     */
    int checkUsername(String username);

    /**
     * 检查用户密码是否正确
     * @param username
     * @param password
     * @return
     */
    User selectLogin(@Param("username")String username,@Param("password") String password);

    /**
     * 检查邮箱
     * @param email
     * @return
     */
    int checkEmaill(String email);

    /**
     * 通过用户名查找问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);

    /**
     * 检查回答是否正确
     * @param username
     * @param question
     * @param answer
     * @return
     */
    int checkAnswer(@Param("username") String username,@Param("question") String question,@Param("answer") String answer);

    /**
     * 根据用户名更新密码
     * @param username
     * @param passwordNew
     * @return
     */
    int updatePasswordByUsername(@Param("username")String username,@Param("passwordNew")String passwordNew);

    /**
     * 校验密码
     * @param password
     * @param userId
     * @return
     */
    int checkPassword(@Param("password")String password,@Param("userId")Integer userId);

    /**
     * 检验邮箱
     * @param email
     * @param userId
     * @return
     */
    int checkEmailByUserId(@Param("email") String email,@Param("userId") Integer userId);


}