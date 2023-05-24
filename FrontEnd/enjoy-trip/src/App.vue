<template>
  <div id="app">
    <header-nav :user="user" @logout="logout" />
    <router-view @login="login" class="body-content" />
  </div>
</template>

<script>
import HeaderNav from "./components/common/HeaderNav.vue";
import http from "@/util/http-common";
export default {
  components: { HeaderNav },
  data() {
    return {
      user: null,
    };
  },
  methods: {
    logout() {
      localStorage.removeItem("user");
      this.user = null;
      console.log("adsds");
      window.location.reload();
    },
    login(id, password) {
      http
        .post("/user/login", {
          id: id,
          password: password,
        })
        .then(({ data }) => {
          console.log(data);
          this.user = id;
          localStorage.setItem("user", id);
          alert("로그인 성공");
          this.$router.push("/");
        });
    },
  },
};
</script>

<style>
@import "./assets/css/reset.css";

#app {
  font-family: "SDGothic", serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
  background-image: url("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_-_q84Q4uvLsSnjHH2sj9J3LabQTs0982QA&usqp=CAU");
}

body {
  padding-top: -5px;
}

/* /* 가운데 영역 / */
.container {
  position: relative;
  width: 95%;
  margin: 0 auto;
  background: rgba(0, 0, 0, 0);
}

/* / 로우 영역 */
.row {
  padding: 0 28px;
}
.body-content {
  height: 100vh;
  padding-top: 80px;
}
</style>
