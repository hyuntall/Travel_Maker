<template>
  <header id="header">
    <div class="container">
      <div class="row">
        <div class="header clearfix">
          <div>
            <router-link to="/"> <b>MYRO</b>&nbsp;&nbsp;<span>MAKE YOUR ROUTE OPTIMIZED</span> </router-link>
          </div>
          <nav class="nav">
            <ul class="main-menu">
              <li>
                <router-link to="/trip">여행지</router-link>
              </li>
              <li v-if="getUser"><router-link to="/">MyPlan</router-link></li>
              <li>
                <router-link to="/">이용방법???</router-link>
              </li>
              <li>
                <a href="#" v-if="getUser" @click="logout">로그아웃</a>
                <router-link to="/login" v-else>로그인</router-link>
              </li>
              <li v-if="!getUser">
                <router-link to="/user/regist">회원가입</router-link>
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "HeaderNav",
  props: {
    user: null,
  },
  methods: {
    logout() {
      this.$emit("logout");
    },
  },
  computed: {
    getUser() {
      if (this.user) return true;
      return false;
    },
  },
};
</script>

<style scoped>
/* header */
#header {
  z-index: 200;
  position: fixed;
  top: 0;
  width: 100%;
  border-bottom: 1px solid #c6c6c6;
  background-color: #fff;
  box-shadow: 0 5px 5px -5px #333;
}
.header {
  display: flex;
}
.header div {
  padding: 1em;
}
.header div a b {
  font-size: 30px;
}
.header div a span {
  font-size: 8px;
}
.header nav {
  margin-left: auto;
}
.header nav .main-menu {
  display: flex;
  justify-content: space-around;
}
.header nav .main-menu li {
  text-align: center;
  padding: 10px;
}
.header nav .main-menu li:hover {
  background-color: #dad7d7;
}
.header nav .main-menu li a {
  display: block;
  padding: 14px 10px;
  font-size: 15px;
  font-weight: 700;
  color: #444;
}
.header nav .main-menu li a:hover {
  display: inline-block;
  color: #039be5;
}
</style>
