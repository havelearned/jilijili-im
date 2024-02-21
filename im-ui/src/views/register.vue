<template>
  <q-layout class="app-bg">
    <q-page-container>
      <q-page class="login-page">
        <div class="form-container card-glass">
          <p class="title">创建账号</p>
          <!-- <p class="sub-title">Let's get statred with your 30 days free trial</p> -->
          <q-form class="form" @submit="registerHandel">
            <q-input type="text" filled placeholder="您的账号信息" v-model="registerData.username"
                     label="创建账号:"
                     :rules="[
                       val=> isNotEmpty(val)
                     ]"
            />
            <q-input :type="toggleShowPassword ?'text' :'password'"
                     filled placeholder="您的密码" v-model="registerData.password" label="密码:"
                     :rules="[
                       val=> isNotEmpty(val)
                     ]"
            >
              <template v-slot:append>
                <q-icon name="visibility_off" v-if="toggleShowPassword" class="cursor-pointer"
                        @click="toggleShowPassword = !toggleShowPassword"/>
                <q-icon name="visibility" class="cursor-pointer" v-else
                        @click="toggleShowPassword = !toggleShowPassword"/>
              </template>
            </q-input>
            <q-input type="password" filled placeholder="确定您的密码" v-model="configPassword" label="确认密码:"
                     :rules="[
                       val=> isNotEmpty(val),
                       val=> val === registerData.password || '密码不一致!!!'
                     ]"
            />

            <button class="form-btn" type="submit">创建账号</button>
          </q-form>
          <p class="sign-up-label">
            已经有一个帐户?<span class="sign-up-link">
            <q-btn flat @click="$router.push('/login')">回到登录</q-btn>
            </span>
          </p>

        </div>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script setup lang="ts">
import {isNotEmpty} from "@/utils/verify";
import {ref} from "vue";
import {register, RegisterData} from "@/api/login/login.data";
import {useRouter} from "vue-router";
import {Notify} from "quasar";


const router = useRouter()

const registerData = ref<RegisterData>({
  uuid: "",
  clientId: import.meta.env.VITE_APP_CLIENT_ID,
  code: "",
  grantType: "password",
  password: "",
  rememberMe: false,
  tenantId: "",
  userType: "sys_user",
  username: ""
});
const configPassword = ref("")
const toggleShowPassword = ref(false)

async function registerHandel() {
  let res = await register(registerData.value)
  console.log("res=>", res)
  if (res && res.code == 200) {
    Notify.create({
      type: "positive",
      message: res.msg,
      position: "top",
      timeout: 10000
    })
    await router.push("/login?username=" + registerData.value.username)
  } else {
    Notify.create({
      type: "negative",
      message: res.msg,
      position: "top",
      timeout: 10000
    })
  }
}
</script>

<style scoped lang="scss">
@import '@/css/app.scss';

.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.form-container {
  width: 350px;
  height: 550px;
  background-color: #fff;
  box-shadow: rgba(0, 0, 0, 0.35) 0px 5px 15px;
  border-radius: 10px;
  box-sizing: border-box;
  padding: 20px 30px;
}

.title {
  text-align: center;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
  "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
  margin: 10px 0 30px 0;
  font-size: 28px;
  font-weight: 800;
}

.sub-title {
  margin: 0;
  margin-bottom: 5px;
  font-size: 9px;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
  "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}

.form {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 18px;
  margin-bottom: 15px;
}

.input {
  border-radius: 20px;
  border: 1px solid #c0c0c0;
  outline: 0 !important;
  box-sizing: border-box;
  padding: 12px 15px;
}

.form-btn {
  padding: 10px 15px;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
  "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
  border-radius: 20px;
  border: 0 !important;
  outline: 0 !important;
  background: teal;
  color: white;
  cursor: pointer;
  box-shadow: rgba(0, 0, 0, 0.24) 0px 3px 8px;
}

.form-btn:active {
  box-shadow: none;
}

.sign-up-label {
  margin: 0;
  font-size: 10px;
  color: #747474;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
  "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}

.sign-up-link {
  margin-left: 1px;
  font-size: 11px;
  text-decoration: underline;
  text-decoration-color: teal;
  color: teal;
  cursor: pointer;
  font-weight: 800;
}

.buttons-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  margin-top: 20px;
  gap: 15px;
}

.apple-login-button,
.google-login-button {
  border-radius: 20px;
  box-sizing: border-box;
  padding: 10px 15px;
  box-shadow: rgba(0, 0, 0, 0.16) 0px 10px 36px 0px, rgba(0, 0, 0, 0.06) 0px 0px 0px 1px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
  "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
  font-size: 11px;
  gap: 5px;
}

.apple-login-button {
  background-color: #000;
  color: #fff;
  border: 2px solid #000;
}

.google-login-button {
  border: 2px solid #747474;
}

.apple-icon,
.google-icon {
  font-size: 18px;
  margin-bottom: 1px;
}
</style>
