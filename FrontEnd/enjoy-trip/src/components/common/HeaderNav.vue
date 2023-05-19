<template>
  <header id="header">
    <div class="container">
      <div class="row">
        <div class="header clearfix">
          <div>
            <router-link to="/"> <b>Travel Maker</b>&nbsp;&nbsp;<span>YOUR TRIP PLAN MANAGER</span> </router-link>
          </div>
          <nav class="nav">
            <ul class="main-menu">
              <li>
                <router-link to="/trip">여행지</router-link>
              </li>
              <li><router-link to="/">MyPlan</router-link></li>
              <li>
                <router-link to="/board">자유게시판</router-link>
              </li>
              <li>
                <router-link to="/qna">QnA</router-link>
              </li>
              <li v-if="userInfo">
                <a href="#" @click.prevent="onClickLogout">로그아웃</a>
              </li>
              <li v-else-if="!userInfo">
                <router-link to="/login">로그인</router-link>
              </li>
              <li v-if="!userInfo">
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
import { mapState, mapGetters, mapActions } from "vuex";

export default {
  name: "HeaderNav",
  computed: {
    ...mapState(["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(["userLogout"]),
    // ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      // this.SET_IS_LOGIN(false);
      // this.SET_USER_INFO(null);
      // sessionStorage.removeItem("access-token");
      // if (this.$route.path != "/") this.$router.push({ name: "main" });
      console.log(this.userInfo.id + " ");
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.userInfo.id);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "Home" });
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
  padding-top: 10px;
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

a {
  text-decoration: none;
  color: #000;
}
</style>
