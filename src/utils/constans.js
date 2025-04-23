const SEX_MAP = {
    0: '女',
    1: '男'
}
const DEPARTMENT_MAP = {
    0: '开发部',
    1: '测试部',
    2: '需求部'
}
const POSITION_MAP = {
    0: '部门经理',
    1: '小组组长',
    2: '普通成员',
    3: '优秀员工'
}
const API_MAP = {
    loadUserInfoList: "/api/userInfo/loadUserInfoList",
    saveUser: "/api/userInfo/saveUser",
    updatePassword: "/api/userInfo/updatePassword",
    delUser: "/api/userInfo/delUser",
    login: "/api/userInfo/login",
    logout: "/api/userInfo/logout"
  };

export {
    SEX_MAP,
    DEPARTMENT_MAP,
    POSITION_MAP,
    API_MAP
}