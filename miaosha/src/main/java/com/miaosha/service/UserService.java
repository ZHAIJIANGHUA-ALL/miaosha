package com.miaosha.service;

import com.miaosha.error.BusinessException;
import com.miaosha.service.model.UserModel;

/**
 * @date: 2019\11\22 0022
 * @author: zhaijh
 * @description:
 */
public interface UserService {
    public UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;

    /**
     *
     * @param phone 用户手机号
     * @param encryptPassword  用户加密密码
     * @throws BusinessException
     */
    UserModel validateLogin(String phone,String encryptPassword) throws BusinessException;
}
