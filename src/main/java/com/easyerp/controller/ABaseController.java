package com.easyerp.controller;

import com.easyerp.entity.enums.ResponseCodeEnum;
import com.easyerp.entity.vo.ResponseVo;
import com.easyerp.exception.BusinessException;

public class ABaseController {

    protected static final String STATUS_SUCCESS = "Success";
    protected static final String STATUS_ERROR = "Error";

    protected <T> ResponseVo getSuccessResponseVo(T t){
        ResponseVo<T> successResponseVo = new ResponseVo<>();
        successResponseVo.setStatus(STATUS_SUCCESS);
        successResponseVo.setCode(ResponseCodeEnum.CODE_200.getCode());
        successResponseVo.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        successResponseVo.setData(t);
        return successResponseVo;
    }

}
