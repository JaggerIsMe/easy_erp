<template>
    <el-dialog
        v-model="dialogVisible"
        :title="title"
        width="500"
        :close-on-click-modal="false"
    >
        <el-form
            ref="formDataRef"
            :model="formData"
            :rules="rules"
            label-width="80px"
            @submit.prevent
        >
            <el-form-item label="密码" prop="password">
                <el-input v-model="formData.password" type="password" show-password />
             </el-form-item>
            <el-form-item label="确认密码" prop="rePassword">
                <el-input v-model="formData.rePassword" type="password" show-password />
            </el-form-item>
        </el-form>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submitForm">
                    确认
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>
<script setup>
import { API_MAP } from "@/utils/constans";
import axios from "axios";
import { ref, reactive, getCurrentInstance, nextTick } from "vue"
import { ElMessage } from "element-plus";

const dialogVisible = ref(false);
const title = "修改密码";

const formDataRef = ref();
const formData = ref({});

//自定义校验
const regexs = {
  password: /^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z~!@#$%^&*_]{8,18}$/
};
const check = (regex, rule, value, callback) => {
  if (!value) {
    callback(new Error(rule.message));
  } else {
    if (regex.test(value)) {
      callback();
    } else {
      callback(new Error(rule.message));
    }
  }
};
const checkPassword = (rule, value, callback) => {
    check(regexs.password, rule, value, callback);
};
const checkRePassword = (rule, value, callback) => {
    if(value && formData.value.password!=value){
        callback(new Error(rule.message));
    } else {
        callback();
    }
};
const rules = {
    password: [{required: true, message: '请输入密码'}, {validator: checkPassword, message: '密码只能是数字，字母，特殊字符8-18位'}],
    rePassword: [{required: true, message: '请再次输入密码'}, {validator: checkRePassword, message: '两次输入的密码不一致'}]
};
//===自定义校验===

const submitForm = () => {
    formDataRef.value.validate(async (valid) => {
        if (!valid) {
            return;
        }
        let params = {};
        Object.assign(params, formData.value);
        let result =  await axios.post(API_MAP.updatePassword, params, {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        });
        const data = result.data;
        if (data.status!="Success") {
            ElMessage.error(data.info);
            return;
        }
        ElMessage.success("保存成功");
        dialogVisible.value = false;
    });
};

const show = (userId) => {
    dialogVisible.value = true;
    nextTick(() => {
        formDataRef.value.resetFields();
        formData.value = {userId};
    });
};

defineExpose({
  show,
});

</script>
<style>

</style>