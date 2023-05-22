<template>
  <div class="wrap">
    <div class="login">
      <h2>Log-in</h2>
      <div class="login_input login_id">
        <b-alert show variant="danger" v-if="isLoginError">아이디 또는 비밀번호를 확인하세요.</b-alert>
        <label for="id" class="label">ID</label>
        <input type="text" v-model="user.id" name="id" id="id" placeholder="id" @keyup.enter="confirm" required />
      </div>
      <div class="login_input login_pw">
        <label for="password" class="label">Password</label>
        <input type="password" v-model="user.password" name="password" id="password" placeholder="Password" required @keyup.enter="confirm" />
      </div>
      <div class="submit">
        <button variant="outline-success" @click="confirm">로그인</button>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState, mapActions } from "vuex";
export default {
  name: "LoginForm",
  data() {
    return {
      user: {
        id: null,
        password: null
      },
    };
  },
  computed: {
    ...mapState(["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
   ...mapActions(["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      // console.log("1. confirm() token >> " + token);
      if (this.isLogin) {
        await this.getUserInfo(token);
        // console.log("4. confirm() userInfo :: ", this.userInfo);
        this.$router.push({ name: "home" });
      }
    },
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "SDGothic";
}

a {
  text-decoration: none;
  color: black;
}

li {
  list-style: none;
}

.wrap {
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  /* background: rgba(0, 0, 0, 0.1); */
}

.login {
  border-radius: 2px;
  border: 1px solid rgb(218, 217, 217);
  width: 40%;
  min-width: 600px;
  height: 600px;
  background: white;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

h2 {
  color: black;
  font-size: 2em;
}

.login_input {
  margin-top: 20px;
  width: 80%;
}
.login_input label {
  float: left;
}
.login_input input {
  width: 100%;
  height: 50px;
  border-radius: 5px;
  margin-top: 10px;
  padding: 0px 20px;
  border: 1px solid lightgray;
  outline: none;
}

.login_etc {
  padding: 10px;
  width: 80%;
  font-size: 14px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-weight: bold;
}

.submit {
  margin-top: 50px;
  width: 80%;
}
.submit button {
  width: 100%;
  height: 50px;
  border: 0;
  outline: none;
  border-radius: 2px;
  background: lightgray;
  /* background: linear-gradient(to left, rgb(255, 77, 46), rgb(255, 155, 47)); */
  color: white;
  font-size: 1.2em;
  letter-spacing: 2px;
}
</style>
