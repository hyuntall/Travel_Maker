<template>
  <div id="app">
    <header-nav :user="user" @logout="logout" />
    <router-view @login="login" />
  </div>
</template>

<script>
import HeaderNav from "./components/common/HeaderNav.vue";
export default {
  components: { HeaderNav },
  data() {
    return {
      user: null,
    };
  },
  methods: {
    logout() {
      this.user = null;
    },
    login(user) {
      if (user.id === "admin" && user.password === "1234") {
        this.user = user;
        alert("로그인 성공");
        this.$router.push("/");
      }
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
