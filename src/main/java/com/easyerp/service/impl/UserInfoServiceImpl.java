package com.easyerp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyerp.config.AppConfig;
import com.easyerp.entity.enums.DateTimePatternEnum;
import com.easyerp.entity.enums.PageSize;
import com.easyerp.entity.enums.VerifyRegexEnum;
import com.easyerp.entity.po.UserInfo;
import com.easyerp.entity.query.UserInfoQuery;
import com.easyerp.exception.BusinessException;
import com.easyerp.mappers.UserInfoMapper;
import com.easyerp.service.UserInfoService;
import com.easyerp.utils.DateUtil;
import com.easyerp.utils.StringTools;
import com.easyerp.utils.VerifyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Jagger
 * @since 2025-04-11 12:52:27
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    UserInfoMapper userInfoMapper;

    @Resource
    AppConfig appConfig;

    /**
     * 加载数据 根据条件分页查询
     * @param query
     * @return
     */
    @Override
    public Page<UserInfo> loadDataByParamAndPage(UserInfoQuery query) {
        // 模糊查询
        String phoneFuzzy = query.getPhoneFuzzy();
        String nameFuzzy = query.getNameFuzzy();
        QueryWrapper<UserInfo> qw = new QueryWrapper<>();
        if (!StringTools.isEmpty(phoneFuzzy)){
            qw.like("phone", phoneFuzzy);
        }
        if (!StringTools.isEmpty(nameFuzzy)){
            qw.like("name", nameFuzzy);
        }

        // 分页信息
        Page<UserInfo> page = new Page<>();
        long size = query.getSize() == null ? PageSize.SIZE15.getSize() : query.getSize();
        long current = query.getCurrent() == null ? 1L : query.getCurrent();
        page.setSize(size);
        page.setCurrent(current);
        // 排序
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("join_time"); // 按什么字段排序
        orderItem.setAsc(false); // 是否升序
        page.setOrders(new ArrayList<>(Arrays.asList(orderItem)));

        return this.userInfoMapper.selectPage(page, qw);
    }

    /**
     * 新增 或 修改 用户
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserInfo(UserInfo userInfo) {
        if (StringTools.isEmpty(userInfo.getUserId()) && (StringTools.isEmpty(userInfo.getPassword()) || !VerifyUtils.verify(VerifyRegexEnum.PASSWORD, userInfo.getPassword()))) {
            throw new BusinessException("密码不能为空，只能是数字，字母，特殊字符，8-18位");
        }
        if (StringTools.isEmpty(userInfo.getName()) || userInfo.getName().length() > 10) {
            throw new BusinessException("姓名不能为空，且长度不能超过10");
        }
        if (StringTools.isEmpty(userInfo.getPhone()) || !VerifyUtils.verify(VerifyRegexEnum.PHONE, userInfo.getPhone())) {
            throw new BusinessException("手机号不能为空，且格式要正确");
        }
        if (userInfo.getBirthday() == null) {
            throw new BusinessException("出生日期不能为空");
        }
        if (userInfo.getSex() == null) {
            throw new BusinessException("性别不能为空");
        }
        if (userInfo.getDepartment() == null) {
            throw new BusinessException("部门不能为空");
        }
        if (StringTools.isEmpty(userInfo.getPosition())) {
            throw new BusinessException("职位不能为空");
        }

        // id为空，则是新增操作
        if (null == userInfo.getUserId()){
            UserInfo userInfo4MemberCounts = new UserInfo();
            userInfo4MemberCounts.setDepartment(userInfo.getDepartment());
            QueryWrapper<UserInfo> memberCountsQuery = new QueryWrapper<>(userInfo4MemberCounts);
            int memberCounts = userInfoMapper.selectCount(memberCountsQuery).intValue();
            userInfo.setUserId(createUserId(new Date(), userInfo.getDepartment(), memberCounts));
            userInfo.setPassword(StringTools.encodeByMd5(userInfo.getPassword()));
            userInfo.setJoinTime(new Date());
        } else {
            //防止 Password JoinTime 被恶意更新
            userInfo.setPassword(null);
            userInfo.setJoinTime(null);
        }
        saveOrUpdate(userInfo);
    }

    /**
     * 修改密码
     * @param password
     * @param userId
     */
    @Override
    public void updatePassword(String password, String userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (null == userInfo){
            throw new BusinessException("用户信息不存在");
        }
        UserInfo updatePasswordUser = new UserInfo();
        updatePasswordUser.setPassword(StringTools.encodeByMd5(password));
        updatePasswordUser.setUserId(userId);
        userInfoMapper.updateById(updatePasswordUser);
    }

    /**
     * 删除用户
     * @param userId
     */
    @Override
    public void delUser(String userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        if (null == userInfo){
            throw new BusinessException("用户信息不存在");
        }
        userInfoMapper.deleteById(userId);
    }

    @Override
    public UserInfo login(UserInfo userInfo) {
        userInfo.setPassword(StringTools.encodeByMd5(userInfo.getPassword()));
        QueryWrapper<UserInfo> qw = new QueryWrapper<>(userInfo);
        UserInfo userInfoLogin = userInfoMapper.selectOne(qw);
        if (null == userInfoLogin) {
            throw new BusinessException("账号或密码错误");
        }
        return userInfoLogin;
    }


    /**
     * 生成 UserId
     */
    public String createUserId(Date joinTime, int department, int memberCounts){
        return appConfig.getCompanyCode() + DateUtil.format(joinTime, DateTimePatternEnum.YYYYMM.getPattern()) + department + String.format("%03d", memberCounts + 1);
    }

}
