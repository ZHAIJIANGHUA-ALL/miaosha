package com.miaosha.service.impl;

import com.miaosha.dao.UserDOMapper;
import com.miaosha.dao.UserPasswordDOMapper;
import com.miaosha.entity.UserDO;
import com.miaosha.entity.UserPasswordDO;
import com.miaosha.error.BusinessException;
import com.miaosha.error.EmBusinessError;
import com.miaosha.service.UserService;
import com.miaosha.service.model.UserModel;
import com.miaosha.validator.ValidationResult;
import com.miaosha.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * @date: 2019\11\22 0022
 * @author: zhaijh
 * @description:
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper passwordDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);
        UserPasswordDO userPasswordDO = passwordDOMapper.selectByUserId(userDO.getId());
        return convertFromDataObject(userDO, userPasswordDO);
    }

    @Override
    public void register(UserModel userModel) throws BusinessException {
        if(userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        /*if(StringUtils.isEmpty(userModel.getName())
        ||userModel.getGender()==null
        ||userModel.getAge()==null
        ||StringUtils.isEmpty(userModel.getTelphone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }*/
        ValidationResult validate = validator.validate(userModel);
        if(validate.isHasError()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,validate.getErrMsg());
        }
        UserDO userDO=convertFromDataObject(userModel);

        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"该手机号已注册!");
        }
        userModel.setId(userDO.getId());
        UserPasswordDO passwordDO=convertPasswordFromDataObject(userModel);
        passwordDOMapper.insertSelective(passwordDO);
        return;
    }

    @Override
    public UserModel validateLogin(String phone, String encryptPassword) throws BusinessException {
        //通过用户的手机获取用户的信息
        UserDO userDO = userDOMapper.selectByTelephone(phone);
        if(userDO==null){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FILE);
        }
        UserPasswordDO passwordDO = passwordDOMapper.selectByUserId(userDO.getId());
        UserModel userModel = convertFromDataObject(userDO, passwordDO);
        //比较用户信息内加密的面是否和传输进来的密码箱匹配
        if(!StringUtils.equals(encryptPassword,passwordDO.getPassword())){
            throw new BusinessException(EmBusinessError.USER_LOGIN_FILE);
        }
        return userModel;
    }

    public UserPasswordDO convertPasswordFromDataObject(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserPasswordDO passwordDO=new UserPasswordDO();
        passwordDO.setPassword(userModel.getPassword());
        passwordDO.setUserId(userModel.getId());
        return passwordDO;
    }

    public UserDO convertFromDataObject(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserDO userDO=new UserDO();
        BeanUtils.copyProperties(userModel,userDO);
        return userDO;
    }

    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO) {
        UserModel userModel = new UserModel();
        if(userDO==null){
            return null;
        }
        BeanUtils.copyProperties(userDO, userModel);
        if(userPasswordDO!=null){
            userModel.setPassword(userPasswordDO.getPassword());
        }
        return userModel;
    }
}
