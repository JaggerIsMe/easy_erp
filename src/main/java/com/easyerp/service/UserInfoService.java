package com.easyerp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easyerp.entity.po.UserInfo;
import com.easyerp.entity.query.UserInfoQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Jagger
 * @since 2025-04-11 12:52:27
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 加载数据 根据条件分页查询
     * @param query
     * @return
     */
    Page<UserInfo> loadDataByParamAndPage(UserInfoQuery query);

    /**
     * 新增 或 修改 用户
     * @param userInfo
     * @return
     */
    void saveUserInfo(UserInfo userInfo);

    /**
     * 修改密码
     * @param password
     * @param userId
     */
    void updatePassword(String password, String userId);

    /**
     * 删除用户
     * @param userId
     */
    void delUser(String userId);

    UserInfo login(UserInfo userInfo);
}
