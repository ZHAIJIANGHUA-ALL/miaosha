package com.miaosha.error;

/**
 * @date: 2019\11\25 0025
 * @author: zhaijh
 * @description:
 */
public interface CommonError {
  public int getErrorCode();
  public String getErrMsg();
  public CommonError setErrMsg(String errMsg);
}
