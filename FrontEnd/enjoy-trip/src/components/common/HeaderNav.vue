<template>
  <header id="header">
    <div class="">
      <div class="row">
        <div class="header clearfix">
          <div class="title">
            <router-link to="/"> <b>Travel Maker</b>&nbsp;&nbsp; <span>YOUR TRIP PLAN MANAGER</span> </router-link>
          </div>
          <nav class="nav">
            <ul class="main-menu">
              <li>
                <router-link to="/trip">여행지</router-link>
              </li>

              <li>
                <router-link to="/board">여행 게시판</router-link>
              </li>
              <li>
                <router-link to="/qna">QnA</router-link>
              </li>
              <li v-if="userInfo">
                <router-link to="/mypage">MyPage</router-link>
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
          <div class="dropdown">
            <b-dropdown right text="menu" class="drop-button">
              <b-dropdown-item>
                <router-link to="/trip"> 여행지 </router-link>
              </b-dropdown-item>
              <b-dropdown-item>
                <router-link to="/board">여행 게시판</router-link>
              </b-dropdown-item>
              <b-dropdown-item>
                <router-link to="/qna">QnA</router-link>
              </b-dropdown-item>
              <b-dropdown-item v-if="userInfo">
                <router-link to="/mypage">MyPage</router-link>
              </b-dropdown-item>
              <b-dropdown-item v-if="userInfo">
                <a href="#" @click.prevent="onClickLogout">로그아웃</a>
              </b-dropdown-item>
              <b-dropdown-item v-else-if="!userInfo">
                <router-link to="/login">로그인</router-link>
              </b-dropdown-item>
              <b-dropdown-item v-if="!userInfo">
                <router-link to="/user/regist">회원가입</router-link>
              </b-dropdown-item>
            </b-dropdown>
          </div>
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
    onClickLogout() {
      console.log(this.userInfo.id + " ");
      this.userLogout(this.userInfo.id);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      if (this.$route.path != "/") this.$router.push({ name: "home" });
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
  height: 80px;
  border-bottom: 1px solid #c6c6c6;
  background-color: #fff;
  box-shadow: 0 5px 5px -5px #333;
}
.row {
  width: 100%;
}

.header {
  display: flex;
  align-items: center;
  justify-content: center;
}
.header div {
  padding: 1em;
  margin-left: 200px;
}
.header div a b {
  font-size: 30px;
}
.header div a span {
  font-size: 8px;
}
.header nav {
  margin-right: 200px;
  margin-left: auto;
}
.header nav .main-menu {
  display: flex;
  justify-content: space-around;
  margin: 0;
}
.header nav .main-menu li {
  text-align: center;
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
.drop-button {
  border-radius: 2px;
}

@media screen and (min-width: 993px) {
  .dropdown {
    display: none;
  }
}

@media screen and (max-width: 992px) {
  .container {
    width: 100% !important;
  }
  .nav {
    display: none;
  }
  .header .title {
    margin-left: 0;
  }
}
</style>
