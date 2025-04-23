package com.easyerp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyerp.annotation.GlobalInterceptor;
import com.easyerp.entity.constants.Constants;
import com.easyerp.entity.po.UserInfo;
import com.easyerp.entity.query.UserInfoQuery;
import com.easyerp.entity.vo.ResponseVo;
import com.easyerp.service.UserInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Jagger
 * @since 2025-04-11 12:52:27
 */
@RestController
@RequestMapping("/userInfo")
@Validated
public class UserInfoController extends ABaseController{

    @Resource
    UserInfoService userInfoService;


    /**
     * 加载数据 根据条件分页查询
     * @param query
     * @return
     */
    @RequestMapping("/loadUserInfoList")
    @GlobalInterceptor()
    public ResponseVo<Page<UserInfo>> loadUserInfoList(UserInfoQuery query){
        return getSuccessResponseVo(userInfoService.loadDataByParamAndPage(query));
    }


    /**
     * 新增 或 修改 用户
     * @param userInfo
     * @return
     */
    @RequestMapping("/saveUser")
    @GlobalInterceptor()
    public ResponseVo saveUser(UserInfo userInfo){
        userInfoService.saveUserInfo(userInfo);
        return getSuccessResponseVo(null);
    }

    /**
     * 修改密码
     * @param password
     * @param userId
     * @return
     */
    @RequestMapping("/updatePassword")
    @GlobalInterceptor()
    public ResponseVo updatePassword(@Valid @Pattern(regexp = Constants.REGEX_PASSWORD) @NotNull String password, @Valid @NotNull String userId){
        userInfoService.updatePassword(password, userId);
        return getSuccessResponseVo(null);
    }


    /**
     * 删除用户
     * @return
     */
    @RequestMapping("/delUser")
    @GlobalInterceptor()
    public ResponseVo delUser(@Valid @NotNull String userId){
        userInfoService.delUser(userId);
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/login")
    public ResponseVo login(HttpSession session, UserInfo userInfo) {
        UserInfo loginUserInfo = userInfoService.login(userInfo);
        session.setAttribute("loginUserInfo", loginUserInfo);
        return getSuccessResponseVo(loginUserInfo);
    }


    @RequestMapping("/logout")
    public ResponseVo logout(HttpSession session) {
        session.invalidate();
        return getSuccessResponseVo(null);
    }

}
