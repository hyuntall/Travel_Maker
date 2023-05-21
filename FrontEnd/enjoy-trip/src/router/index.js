import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import UserView from "../views/UserView";
import TripView from "../views/TripView";
import BoardView from "../views/BoardView";
import MypageView from "../views/MyPageView";
import store from "@/store";

Vue.use(VueRouter);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["checkUserInfo"];
  const checkToken = store.getters["checkToken"];
  let token = sessionStorage.getItem("access-token");
  console.log("로그인 처리 전", checkUserInfo, token);

  if (checkUserInfo != null && token) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "login" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/login",
    name: "login",
    component: () => import(/* webpackChunkName: "user" */ "@/components/LoginForm"),
  },
  {
    path: "/mypage",
    name: "MyPage",
    component: MypageView,
  },
  {
    path: "/user",
    component: UserView,
    children: [
      {
        path: "regist",
        name: "UserRegist",
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserWrite"),
      },
      {
        path: "modify",
        name: "UserModify",
        component:() => import(/* webpackChunkName: "user" */ "@/components/user/UserWrite"),
      }
    ],
  },
  {
    path: "/trip",
    component: TripView,
    children: [
      {
        path: "",
        name: "TripSearch",
        component: () => import(/* webpackChunkName: "trip" */ "@/components/trip/TripSearch"),
      },
      {
        path: "make",
        name: "TripMake",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "trip" */ "@/components/trip/TripMake"),
      },
    ],
  },
  {
    path: "/board",
    name: "BoardView",
    component: BoardView,
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "BoardList",
        beforeEnter: onlyAuthUser,
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardList"),
      },
      {
        path: "regist",
        name: "BoardRegist",
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardWrite"),
      },
      {
        path: "detail/:idx",
        name: "BoardDetail",
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardDetail"),
      },
      {
        path: "modify",
        name: "BoardModify",
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardWrite"),
      },
    ],
  },
  {
    path: "/qna",
    name: "QnAView",
    component: BoardView,
    redirect: "/qna/list",
    children: [
      {
        path: "list",
        name: "QnAList",
        component: () => import(/* webpackChunkName: "qna" */ "@/components/qna/QnAList"),
      },
      {
        path: "regist",
        name: "QnARegist",
        component: () => import(/* webpackChunkName: "qna" */ "@/components/qna/QnAWrite"),
      },
      {
        path: "detail/:id",
        name: "QnADetail",
        component: () => import(/* webpackChunkName: "qna" */ "@/components/qna/QnAWrite"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
