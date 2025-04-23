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
            <el-form-item label="姓名" prop="name">
              <el-input
                clearable
                placeholder="请输入姓名"
                v-model.trim="formData.name"
              ></el-input>
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input
                clearable
                placeholder="请输入手机号"
                v-model.trim="formData.phone"
              ></el-input>
            </el-form-item>
            <template v-if="!formData.userId">
                <el-form-item label="密码" prop="password">
                    <el-input
                      clearable
                      placeholder="请输入密码"
                      v-model.trim="formData.password"
                      type="password"
                      show-password
                    ></el-input>
                </el-form-item>
                <el-form-item label="重复密码" prop="rePassword">
                  <el-input
                    clearable
                    placeholder="请再次输入密码"
                    v-model.trim="formData.rePassword"
                    type="password"
                    show-password
                  ></el-input>
                </el-form-item>
                <el-form-item label="生日" prop="birthday">
                    <el-date-picker
                      :style="{ width: '100%' }"
                      v-model="formData.birthday"
                      type="date"
                      placeholder="请选择出生年月日"
                    />
                </el-form-item>
            </template>
            <el-form-item label="性别" prop="sex">
                <el-radio-group v-model="formData.sex">
                    <el-radio :value="item.key" v-for="item in sexArray">{{ item.value }}</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="部门" prop="department">
                <el-radio-group v-model="formData.department">
                  <el-radio :value="item.key" v-for="item in departmentArray">{{ item.value }}</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="职位" prop="position">
                <el-checkbox-group v-model="formData.position">
                  <el-checkbox :value="item.key + ''" v-for="item in positionArray">{{ item.value }}</el-checkbox>
                </el-checkbox-group>
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
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { SEX_MAP, DEPARTMENT_MAP, POSITION_MAP, API_MAP } from "@/utils/constans";
import axios from "axios";
import { ElMessage } from "element-plus";

const dialogVisible = ref(false);
const title = ref("title");
const formData = ref({});
const formDataRef = ref();

const converMap2Array = (dataMap) => {
  const array = [];
  for (let item in dataMap) {
    array.push({
      key: Number.parseInt(item),
      value: dataMap[item],
    });
  }
  return array;
};
const sexArray = ref(converMap2Array(SEX_MAP));
const departmentArray = ref(converMap2Array(DEPARTMENT_MAP));
const positionArray = ref(converMap2Array(POSITION_MAP));

//自定义校验
const regexs = {
  password: /^(?=.*\d)(?=.*[a-zA-Z])[\da-zA-Z~!@#$%^&*_]{8,18}$/,
  phone: /(1[0-9])\d{9}$/,
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
const checkPhone = (rule, value, callback) => {
    check(regexs.phone, rule, value, callback);
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
    name: [{required: true, message: '请输入姓名'}],
    phone: [{required: true, message: '请输入手机号码'}, {validator: checkPhone, message: '请输入正确的手机号码'}],
    password: [{required: true, message: '请输入密码'}, {validator: checkPassword, message: '密码只能是数字，字母，特殊字符8-18位'}],
    rePassword: [{required: true, message: '请再次输入密码'}, {validator: checkRePassword, message: '两次输入的密码不一致'}],
    birthday: [{required: true, message: '请选择生日'}],
    sex: [{required: true, message: '请选择性别'}],
    department: [{required: true, message: '请选择部门'}],
    position: [{required: true, message: '请选择职位'}]
};
//===自定义校验===

const emit = defineEmits(["reload"]);
const submitForm = () => {
  formDataRef.value.validate(async (valid) => {
    if(!valid){
      return;
    }
    let params = {};
    Object.assign(params, formData.value);
    params.position = params.position.join(",");
    let result = await axios.post(API_MAP.saveUser, params, {
      headers: {
        "Content-Type": "multipart/form-data"
      }
    });
    const data = result.data;
    if (data.status != "Success") {
      ElMessage.error(data.info);
      return;
    }
    ElMessage.success("保存成功");
    dialogVisible.value = false;
    emit("reload");
  });
};

const show = (data) => {
  dialogVisible.value = true;
  title.value = data ? "修改职工信息" : "新增职工信息";
  nextTick(() => {
    formDataRef.value.resetFields();
    if (data) {
      data.position = data.position.split(",");
    }
    formData.value = Object.assign({}, data || {});
  });
};

defineExpose({
  show,
});

</script>
<style>

</style>