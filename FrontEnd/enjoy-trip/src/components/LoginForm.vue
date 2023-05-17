<template>
  <div class="wrap">
    <div class="login">
      <h2>Log-in</h2>
      <div class="login_input login_id">
        <label for="id" class="label">ID</label>
        <input type="text" v-model="id" name="id" id="id" placeholder="id" />
      </div>
      <div class="login_input login_pw">
        <label for="password" class="label">Password</label>
        <input type="password" v-model="password" name="password" id="password" placeholder="Password" />
      </div>
      <div class="submit">
        <button variant="outline-success" @click="login">submit</button>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "LoginForm",
  data() {
    return {
      id: "",
      password: "",
    };
  },
  methods: {
    login() {
      let user = {
        id: this.id,
        password: this.password,
      };

      this.$emit("login", user);
    },
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Noto Sans KR", sans-serif;
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
  background: rgba(0, 0, 0, 0.1);
}

.login {
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
  border-radius: 10px;
  background: skyblue;
  /* background: linear-gradient(to left, rgb(255, 77, 46), rgb(255, 155, 47)); */
  color: white;
  font-size: 1.2em;
  letter-spacing: 2px;
}
</style>
