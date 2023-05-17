<template>
  <div id="app">
    <header-nav :user="user" @logout="logout" />
    <router-view @login="login" />
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
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  /* text-align: center; */
  color: #2c3e50;
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
</style>
