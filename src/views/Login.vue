<template>
    <div>
        <el-form
            ref="formDataRef"
            :model="formData"
            :rules="rules"
            label-width="80px"
            style="text-align: center"
            @submit.prevent
        >
            <el-form-item label="职工号" prop="userId">
                <el-input
                  clearable
                  placeholder="请输入职工号"
                  v-model.trim="formData.userId"
                ></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input
                  clearable
                  placeholder="请输入密码"
                  v-model.trim="formData.password"
                  type="password"
                  show-password
                ></el-input>
            </el-form-item>
            <el-button type="primary" @click="login">
                登录
            </el-button>
        </el-form>
    </div>
</template>
<script setup>
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import axios from "axios";
import { API_MAP } from "@/utils/constans";
import { ElMessage } from "element-plus";
import VueCookies from 'vue-cookies';
import { useRouter, useRoute } from "vue-router";

const { proxy } = getCurrentInstance();
const formDataRef = ref();
const formData = ref({});

const router = useRouter();
const route = useRoute();

const rules = {
    userId: [{required: true, message: ''}],
    password: [{required: true, message: ''}]
};

const login = () => {
    formDataRef.value.validate(async (valid) => {
        if (!valid) {
            return;
        }
        let param = Object.assign({}, formData.value);
        let result = await axios.post(API_MAP.login, param, {
            headers: {
              "Content-Type": "multipart/form-data"
            }
        });
        let data = result.data;
        if (data.status != "Success") {
            ElMessage.error(data.info);
            return;
        }
        VueCookies.set("loginUserInfo", data.data, 0);
        router.push("/");
    });
};

</script>
<style>

</style>