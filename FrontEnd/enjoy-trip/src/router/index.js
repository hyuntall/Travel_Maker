import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import UserView from "../views/UserView";
import TripView from "../views/TripView";
import BoardView from "../views/BoardView";

Vue.use(VueRouter);

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
    path: "/user",
    component: UserView,
    children: [
      {
        path: "regist",
        name: "UserRegist",
        component: () => import(/* webpackChunkName: "user" */ "@/components/user/UserWrite"),
      },
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
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardList"),
      },
      {
        path: "regist",
        name: "BoardRegist",
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardWrite"),
      },
      {
        path: "detail",
        name: "BoardDetail",
        component: () => import(/* webpackChunkName: "board" */ "@/components/board/BoardDetail"),
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
