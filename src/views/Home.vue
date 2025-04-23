<template>
    <div>
        <div>
            <el-form :model="searchFormData" label-width="80px" @submit.prevent>
                <el-row :gutter="5">
                    <el-col :span="5">
                        <el-form-item label="手机号">
                            <el-input
                                clearable
                                placeholder="支持模糊搜索"
                                v-model.trim="searchFormData.phoneFuzzy"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5">
                        <el-form-item label="姓名">
                            <el-input
                                clearable
                                placeholder="支持模糊搜索"
                                v-model.trim="searchFormData.nameFuzzy"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5">
                        <el-button type="primary" @click="loadData">搜索</el-button>
                        <el-button type="primary" @click="showUserEdit()">新增职工</el-button>
                    </el-col>
                    <el-col :span="5">
                        <el-popconfirm
                            title="确认退出登录？"
                            placement="right"
                            @confirm="logout"
                        >
                            <template #reference>
                                <el-button type="primary">退出登录</el-button>
                            </template>
                        </el-popconfirm>
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <el-table :data="tableData" style="width: 100%" height="250">
            <el-table-column fixed prop="userId" label="职工号"/>
            <el-table-column prop="name" label="姓名"/>
            <el-table-column prop="phone" label="手机号"/>
            <el-table-column prop="birthday" label="生日"/>
            <el-table-column prop="sex" label="性别">
                <template #default="scope">
                    {{ SEX_MAP[scope.row.sex] }}
                </template>
            </el-table-column>
            <el-table-column prop="department" label="部门">
                <template #default="scope">
                    {{ DEPARTMENT_MAP[scope.row.department] }}
                </template>
            </el-table-column>
            <el-table-column prop="position" label="职位">
                <template #default="scope">
                    {{ formatPosition(scope.row.position) }}
                </template>
            </el-table-column>
            <el-table-column prop="joinTime" label="入职时间"/>
            //操作
            <el-table-column fixed="right" label="操作" min-width="120">
              <template #default="scope">
                <el-button-group>
                    <el-button type="primary" @click="showUserEdit(scope.row)">
                        修改
                    </el-button>
                    <el-button type="primary" @click="showUpdatePassword(scope.row.userId)">
                        修改密码
                    </el-button>
                    <el-button type="danger" @click="delUser(scope.row.userId)">
                        删除
                    </el-button>
                </el-button-group>
              </template>
            </el-table-column>
        </el-table>
        <div :style="{ 'margin-top': '20px' }">
            <el-pagination
              background
              layout="prev, pager, next"
              :total="total"
              :page-size="pageSize"
              @current-change="pageChange"
            />
        </div>
        <UserEdit ref="userEditRef" @reload="loadData"></UserEdit>
        <UpdatePassword ref="updatePasswordRef"></UpdatePassword>
    </div>
</template>
<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { SEX_MAP, DEPARTMENT_MAP, POSITION_MAP, API_MAP } from "@/utils/constans";
import axios from "axios";
import { ElMessage, ElMessageBox  } from 'element-plus';
import UserEdit from "./UserEdit.vue";
import UpdatePassword from "./UpdatePassword.vue";
import VueCookies from 'vue-cookies';
import { useRouter, useRoute } from "vue-router";

const tableData = ref([]);
const searchFormData = ref({});
const total = ref(1);
const pageSize = ref(1);
const current = ref(1);
const userEditRef = ref();
const updatePasswordRef = ref();

const router = useRouter();
const route = useRoute();

const loadData = async () => {
    const params = Object.assign({}, searchFormData.value);
    params.current = current.value;
    let result = await axios.post(API_MAP.loadUserInfoList, params, {
        headers: {
            "Content-Type": "multipart/form-data",
        }
    });
    const data = result.data;
    if (data.status != "Success") {
        ElMessage({
            showClose: true,
            message: data.info,
            type: 'error',
        });
        return;
    }
    tableData.value = data.data.records;
    total.value = data.data.total;
    pageSize.value = data.data.size;
};

loadData();

const formatPosition = (position) => {
    let positionArr = position.split(",");
    let result = [];
    positionArr.forEach(element => {
        result.push(POSITION_MAP[element]);
    });
    return result.join(",");
};

const pageChange = (e) => {
    current.value = e;
    loadData();
};

const showUserEdit = (data) => {
    let param = null;
    if (data) {
        param = Object.assign({}, data);
    }
    userEditRef.value.show(param);
};

const showUpdatePassword = (userId) => {
    updatePasswordRef.value.show(userId);
};

const delUser = (userId) => {
    ElMessageBox.confirm(
        '删除该职工信息操作无法复原，要继续吗？',
        '请确认删除该职工信息',
    {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning',
        closeOnClickModal: false
    }
  )
    .then(async () => {
        await axios.post(API_MAP.delUser, {userId}, {
            headers: {
                "Content-Type": "multipart/form-data",
            }
        })
        loadData();
        ElMessage({
          type: 'success',
          message: '已删除',
        })
    })
    .catch(() => {
        ElMessage({
          type: 'info',
          message: '已取消操作',
        })
    })
};

const logout = async () => {
    let result = await axios.get(API_MAP.logout);
    let data = result.data;
    if (data.status != "Success") {
        ElMessage.error(data.info);
        return;
    }
    VueCookies.remove("loginUserInfo");
    router.push("/login");
};

</script>
<style>

</style>
