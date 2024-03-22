<template>
  <q-layout class="app-bg">
    <q-page-container>
      <q-page class="login-page">
        <div class="form-container card-glass">
          <div class="logo-container">欢迎回来!</div>
          <div class="social-buttons">
            <button class="social-button facebook" @click="loginToGitee">
              <svg
                t="1705411393600"
                class="icon"
                viewBox="0 0 1024 1024"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                p-id="8492"
                width="200"
                height="200"
              >
                <path
                  d="M773.9392 301.8752m-200.0384 0a200.0384 200.0384 0 1 0 400.0768 0 200.0384 200.0384 0 1 0-400.0768 0Z"
                  fill="#40ECCB"
                  p-id="8493"
                ></path>
                <path
                  d="M924.4672 706.2528a24.32 24.32 0 0 1-24.2688 24.2688h-145.7664a24.3712 24.3712 0 0 0-24.2688 24.32 24.2688 24.2688 0 0 0 24.2688 24.2688h48.5888a24.32 24.32 0 0 1 24.2688 24.32 24.32 24.32 0 0 1-24.2688 24.2688h-64.512A388.7616 388.7616 0 0 1 122.88 390.4512h-48.5888a24.32 24.32 0 0 1 0-48.5888h97.28a24.2688 24.2688 0 0 0 6.8096-47.5648l0.768-0.9728H122.88a24.32 24.32 0 1 1 0-48.5888h101.632a388.7616 388.7616 0 0 1 619.52 437.248h56.32a24.32 24.32 0 0 1 24.1152 24.2688z"
                  fill="#43CEED"
                  p-id="8494"
                ></path>
                <path
                  d="M294.5024 575.744c-15.36 38.0928-18.2784 74.0864-5.9392 80.5376 8.6016 4.8128 22.5792-5.888 35.4816-25.2416a106.1888 106.1888 0 0 0 35.4304 55.8592c-18.7904 6.9632-31.1808 18.7904-31.1808 31.6416 0 21.504 33.3312 39.2192 74.1376 39.2192 37.0176 0 67.6352-13.9776 73.5232-32.768h8.6016c5.9392 18.7904 36.5056 32.768 73.5744 32.768 41.3184 0 74.0864-17.2032 74.0864-39.2192 0-12.8512-12.3392-24.6784-31.1296-31.6416a106.1888 106.1888 0 0 0 35.4304-55.8592c12.9024 19.3536 26.3168 30.0544 35.4304 25.2416 12.9024-6.4512 10.24-42.9568-5.888-80.5376-12.3392-29.5424-28.9792-51.5584-41.8816-56.32 0-1.6384 0.512-3.7888 0.512-5.9392a58.368 58.368 0 0 0-8.5504-30.72v-2.1504a32.1024 32.1024 0 0 0-3.2256-14.5408C609.6896 389.12 560.2816 327.68 480.256 327.68S350.8736 389.12 347.648 466.176a32.1024 32.1024 0 0 0-3.2256 14.5408v2.1504a57.9584 57.9584 0 0 0-8.6016 30.72v5.9392c-11.776 4.6592-28.9792 26.1632-41.3184 56.2176z"
                  fill="#FFFFFF"
                  p-id="8495"
                ></path>
              </svg>
              <span>使用Gitee 登录</span>
            </button>
            <button class="social-button apple" @click="loginToGithub">
              <svg
                t="1705411506104"
                class="icon"
                viewBox="0 0 1024 1024"
                version="1.1"
                xmlns="http://www.w3.org/2000/svg"
                p-id="11508"
                width="200"
                height="200"
              >
                <path
                  d="M512 512m-512 0a512 512 0 1 0 1024 0 512 512 0 1 0-1024 0Z"
                  fill="#4186F5"
                  p-id="11509"
                ></path>
                <path
                  d="M611.944 302.056c0-15.701 2.75-30.802 7.816-44.917a384.238 384.238 0 0 0-186.11 2.956c-74.501-50.063-93.407-71.902-107.975-39.618a136.243 136.243 0 0 0-3.961 102.287 149.515 149.515 0 0 0-39.949 104.806c0 148.743 92.139 181.875 179.961 191.61a83.898 83.898 0 0 0-25.192 51.863c-40.708 22.518-91.94 8.261-115.181-32.058a83.117 83.117 0 0 0-60.466-39.98s-38.871-0.361-2.879 23.408a102.97 102.97 0 0 1 43.912 56.906s23.398 75.279 133.531 51.863v65.913c0 10.443 13.548 42.63 102.328 42.63 71.275 0 94.913-30.385 94.913-42.987V690.485a90.052 90.052 0 0 0-26.996-72.03c83.996-9.381 173.328-40.204 179.6-176.098a164.706 164.706 0 0 1-21.129 1.365c-84.07 0-152.223-63.426-152.223-141.666z"
                  fill="#FFFFFF"
                  p-id="11510"
                ></path>
                <path
                  d="M743.554 322.765a136.267 136.267 0 0 0-3.961-102.289s-32.396-10.445-107.979 39.618a385.536 385.536 0 0 0-11.853-2.956 132.623 132.623 0 0 0-7.816 44.917c0 78.24 68.152 141.667 152.222 141.667 7.171 0 14.222-0.472 21.129-1.365 0.231-5.03 0.363-10.187 0.363-15.509a149.534 149.534 0 0 0-42.105-104.083z"
                  fill="#FFFFFF"
                  opacity=".4"
                  p-id="11511"
                ></path>
              </svg>
              <span>使用 GitHub 登录</span>
            </button>
          </div>
          <div class="line"></div>
          <q-form class="form" @submit="loginHandel">
            <div class="form-group">
              <q-input filled required="" placeholder="输入您的账号" name="username" label="您的账号:"
                       id="email" type="text"
                       v-model="loginData.username"
                       :rules="[
                  // val=>isValidEmail(val),
                  val=>isNotEmpty(val)
                ]"
              />
            </div>

            <div class="form-group">
              <label for="password"></label>
              <q-input filled required="" name="password" placeholder="输入您的密码" id="password" type="password"
                       label="您的密码:"
                       v-model="loginData.password"
                       :rules="[
                  val=>isNotEmpty(val)
                ]"
              />
            </div>

            <button type="submit" class="form-submit-btn">登录</button>
          </q-form>

          <a class="forgot-password-link link" href="#">忘记密码?</a>

          <p class="signup-link">
            没有帐户?
            <a class="signup-link link" href="#">
              <q-btn @click="$router.push('/register')" flat>
                立即注册
              </q-btn>
            </a>
          </p>
        </div>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script setup lang="ts" name="login">
import {loginApi, LoginData, SysUserVo} from "@/api/login/login.data";
import {setToken} from "@/utils/auth";
import {LocalStorage, Notify} from "quasar";
import useUserStore from "@/stores/modules/user";
import {isNotEmpty} from "@/utils/verify";
import {useLoading} from "@/components/loading/loading.data";

const route = useRoute();
const router = useRouter()
const userStore = useUserStore()
const {showLoading, hideLoading} = useLoading({message: '正在登陆.....'})
const loginData = ref<LoginData>({
  clientId: import.meta.env.VITE_APP_CLIENT_ID,
  code: "",
  grantType: "password",
  password: "",
  rememberMe: true,
  tenantId: "",
  username: "",
  uuid: ""
})

function generateUUID() {
  const uuid = crypto.randomUUID();
  return uuid.replace(/\-/g, ''); // 移除UUID中的连字符
}

// 发起gitee登录
const loginToGitee = () => {
  showLoading()
  window.location.href = "https://gitee.com/oauth/authorize" +
    "?client_id=13401bcfc2cc6c91b11fb3b80cf86dcb9500ea19cd303b75b5939ac516430a96" +
    "&redirect_uri=http://localhost:8080/oauth/gitee/login" +
    "&response_type=code" +
    "&scope=user_info"

  hideLoading()
}

// 发起github登录
const loginToGithub = () => {
  showLoading()
  window.location.href = 'https://github.com/login/oauth/authorize' +
    '?scope=user,project,read:packages,repo:status&client_id=8883af5aedda289a2bf6&redirect_uri=http://localhost:8080/oauth/github/login&' +
    'state=' + new Date().getTime()
  hideLoading()
}

// github登录
async function githubLogin(loginUser: SysUserVo) {
  showLoading()
  const loginData: LoginData = {
    code: '',
    password: '123456',
    rememberMe: false,
    tenantId: loginUser.tenantId,
    username: loginUser.userName,
    uuid: generateUUID(),
    clientId: null,
    grantType: null,
  }
  let res = await loginApi(loginData)
  if (res.code === 200) {
    setToken(res.data.access_token)
    LocalStorage.set('client_id', res.data.client_id)
    await userStore.getUserinfo()
    await router.replace("/")
  } else {
    Notify.create({
      type: 'negative',
      message: "登录失败!!!",
      position: 'top',
      timeout: 3000
    })
  }
  hideLoading()
}


//正常登录
async function loginHandel() {
  showLoading()
  loginApi(loginData.value).then((res: any) => {
    if (res.code === 200) {
      setToken(res.data.access_token)
      LocalStorage.set('client_id', res.data.client_id)
      userStore.getUserinfo()
      router.replace("/")
    } else {
      Notify.create({
        type: 'negative',
        message: "登录失败!!!",
        position: 'top',
        timeout: 3000
      })
    }
  }).catch((err: any) => {
    console.log(err)
  }).finally(() => {
    hideLoading()
  })

}

onMounted(async () => {
  // github登录 回调处理
  const loginUser: SysUserVo = route.query.token ? JSON.parse(route.query.token) : {}
  if(loginUser.userType){
    await githubLogin(loginUser);
  }else{
    let username: string = route.query.username ? route.query.username as string : ''
    if (username) {
      loginData.value.username = username
    }
  }
  // if (loginUser && loginUser.userType === 'github' || loginUser.userType === 'gitee') {
  //   await githubLogin(loginUser);
  // } else {
  //   let username: string = route.query.username ? route.query.username as string : ''
  //   if (username) {
  //     loginData.value.username = username
  //   }
  // }

})

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
  background-color: #fff;
  padding: 32px 24px;
  font-size: 14px;
  font-family: inherit;
  color: #212121;
  display: flex;
  flex-direction: column;
  gap: 24px;
  box-sizing: border-box;
  border-radius: 10px;
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.084), 0px 2px 3px rgba(0, 0, 0, 0.168);
}

.form-container button:active {
  scale: 0.95;
}

.form-container .logo-container {
  margin-bottom: 12px;
  text-align: center;
  font-weight: 700;
  font-size: 20px;
}

.form-container .social-buttons {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  flex-direction: column;
  gap: 12px;
  align-items: center;
}

.form-container .social-button {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  font-family: inherit;
  color: #fff;
  border: none;
  padding: 12px 16px;
  gap: 8px;
  cursor: pointer;
  border-radius: 6px;
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.084), 0px 2px 3px rgba(0, 0, 0, 0.168);
}

.form-container .social-button svg {
  width: 22px;
  height: 22px;
  fill: white;
}

.form-container .social-button.facebook {
  background-color: #1778f2;
}

.form-container .social-button.apple {
  background-color: #212121;
}

.form-container .form {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.form-container .form-group {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.form-container .form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-container .form-group input[type="text"],
.form-container .form-group input[type="password"] {
  width: 100%;
  padding: 12px 16px;
  border-radius: 6px;
  font-family: inherit;
  border: 1px solid #ccc;
}

.form-container .form-group input::placeholder {
  opacity: 0.5;
}

.form-container .form-group input[type="text"]:focus,
.form-container .form-group input[type="password"]:focus {
  outline: none;
  border-color: #1778f2;
}

.form-container .form-submit-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: inherit;
  color: #fff;
  background-color: #212121;
  border: none;
  width: 100%;
  padding: 12px 16px;
  font-size: inherit;
  gap: 8px;
  margin: 12px 0;
  cursor: pointer;
  border-radius: 6px;
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.084), 0px 2px 3px rgba(0, 0, 0, 0.168);
}

.form-container .form-submit-btn:hover {
  background-color: #313131;
}

.form-container .link {
  color: #1778f2;
  text-decoration: none;
}

.form-container .forgot-password-link {
  align-self: flex-end;
  margin-top: -20px;
}

.form-container .signup-link {
  align-self: center;
  font-weight: 500;
}

.form-container .signup-link .link {
  font-weight: 400;
}

.form-container .link:hover {
  text-decoration: underline;
}

.form-container .line {
  width: 100%;
  height: 1px;
  background-color: #212121;
  opacity: 0.1;
}


</style>
