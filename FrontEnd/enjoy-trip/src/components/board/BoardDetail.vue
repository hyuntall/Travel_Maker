<template>
  <div class="container row">
    <div class="col-9 statuses">
      <div class="card">
        <div class="swiper">
          <div class="swiper-wrapper">
            <div class="imgBx swiper-slide">
              <img :src="img" alt="post-1" class="cover" />
            </div>
          </div>
        </div>

        <div class="top">
          <div class="userDetails">
            <div class="profilepic">
              <div class="profile_img">
                <div class="image">
                  <img :src="profileUrl" alt="img8" />
                </div>
              </div>
            </div>
            <h3>{{ board.user_id }}</h3>
          </div>
          <div class="date">{{ board.written_date | dateFormat }}</div>
        </div>
        <div v-if="board && board.user_id === userInfo.id" class="modify">
          <button class="btn btn-1" @click="deleteBoard">삭제</button>
          <router-link class="btn btn-1" :to="{ name: 'BoardModify', params: { idx: board.idx } }">수정</router-link>
        </div>
        <p class="likes">{{ board.view_count }} views</p>
        <div class="bottom">
          <div class="title">{{ board.title }}</div>
          <div class="message" v-html="enterToBr"></div>
          <form @submit="writeComment">
          <div class="addComments">
            <div class="reaction">
              <h3>
                <i class="far fa-smile"></i>
              </h3>
            </div>
              <input type="text" class="text" v-model="comment" placeholder="Add a comment..." />
              <a href="#" @click="writeComment">Post</a>
            </div>
          </form>
          </div>
        <comment-list :board_idx="board.idx" ref="commentList"></comment-list>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from "vuex";
import CommentList from "../comment/CommentList.vue";
import swal from "sweetalert";

export default {
  name: "BoardDetail",
  components: { CommentList },
  data() {
    return {
      message: "",
      comment: [],
      board: null,
      profileUrl: require("../../assets/tmp_profile.jpg"),
      img: "",
      content: "",
    };
  },
  created() {
    http
      .get(`/board/${this.$route.params.idx}`)
      .then(({ data }) => {
        console.log(data);
        this.board = data;
        this.img = require(`C:/EnjoyTrip/board/${this.board.image}`);
        this.getProfileImg();
      })
      .catch(() => {
        swal({
          title: "Error",
          text: "게시글을 불러오지 못했습니다.",
          icon: "error",
        });
        this.$router.push("/board");
      });
  },
  methods: {
    getProfileImg(){
      http.get(`/user/image/${this.board.user_id}`).then(({data})=>{
        this.profileUrl = require(`C:/EnjoyTrip/user/${data}`);
        }).catch(()=>{
          console.log("프로필 이미지 요청 오류");
      })
    },
    deleteBoard() {
      http
        .delete(`/board/delete/${this.board.idx}`)
        .then(({ data }) => {
          console.log(data);
          swal({
            title: "Success",
            text: "게시글이 성공적으로 삭제되었습니다.",
            icon: "success",
          });
          this.$router.push("/board");
        })
        .catch(() => {
          swal({
            title: "Error",
            text: "게시글을 삭제하지 못했습니다.",
            icon: "error",
          });
        });
    },
    writeComment() {
      console.log(this.board.idx);
      http
        .post("/comment/write", {
          user_id: this.userInfo.id,
          board_idx: this.board.idx,
          content: this.comment,
        })
        .then(({ data }) => {
          console.log(data);
          this.$refs.commentList.getComments();
          // window.location.reload();
        })
        .catch(() => {
          swal({
            title: "Error",
            text: "댓글을 작성할 수 없습니다.",
            icon: "error",
          });
        });
    },
  },
  computed: {
    ...mapState(["userInfo"]),
    enterToBr() {
      // 문자열에 enter값을 <br />로 변경.(html상에서 줄바꿈 처리)
      return String(this.board.content).replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
  },
  filters: {
    dateFormat(value) {
      const date = new Date(value);
      return date.toLocaleString();
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, system-ui, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue",
    sans-serif;
  background: #fafafa;
}

.container {
  width: 1100px;
  margin: 100px auto;
}

.col-9 {
  width: 100%;
  margin-top: 30px;
}

.statuses {
  margin-bottom: 30px;
  width: 100%;
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: start;
  padding: 20px 0;
  border-radius: 2px;
  border: 1px solid rgb(218, 217, 217);
  overflow: auto;
}

.statuses .status {
  display: inline-block;
  border-radius: 50%;
  overflow: auto;
  width: 70px;
  height: 70px;
  min-width: 70px;
  min-height: 70px;
  /* background: linear-gradient(to right, red, orange); */
  padding: 2px;
  margin-left: 15px;
  cursor: pointer;
}

.statuses .status .image {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  overflow: hidden;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px solid white;
}

.statuses .status img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.col-3 {
  width: 33%;
  position: -webkit-sticky;
  position: sticky;
  top: 90px;
}

.col-3 h4 {
  color: rgb(100, 100, 100);
}

.col-3 .card {
  margin-bottom: 20px;
  position: relative;
  width: 100%;
  /* min-height: 400px; */
  display: inline-block;
}

.col-3 .card .top {
  padding: 10px 0px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.col-3 .card .top a {
  color: #1d92ff;
}

.col-3 .card .bottom {
  padding: 10px 20px;
}

.col-3 .card .top .userDetails {
  width: 100%;
  display: flex;
  align-items: center;
}

.col-3 .card .top .userDetails h3 {
  font-size: 16px;
  color: #4d4d4f;
  font-weight: 500;
  line-height: 1em;
}

.col-3 .card .top .userDetails h3 span {
  font-size: 0.75em;
}

.col-3 .card .top .userDetails h3 span {
  font-size: 0.75em;
}

.col-9 .card {
  margin-bottom: 20px;
  position: relative;
  width: 100%;
  /* min-height: 400px; */
  display: inline-block;
  border: 1px solid rgba(7, 7, 7, 0.24);
}

.col-9 .card .top {
  padding: 10px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.col-9 .card .bottom {
  padding: 10px 20px;
}

.col-9 .card .top .userDetails {
  width: 100%;
  display: flex;
  align-items: center;
}

.profilepic {
  display: inline-block;
  cursor: pointer;
}

.profilepic .profile_img {
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  overflow: hidden;
  width: 50px;
  height: 50px;
  /* background: linear-gradient(to right, red, orange); */
  border: 1px solid rgb(150, 150, 150);
  padding: 2px;
  margin-right: 8px;
  cursor: pointer;
}

.profilepic .profile_img .image {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  overflow: hidden;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px solid white;
}

.profilepic .profile_img img {
  /* width: 100%; */
  height: 400px;
  object-fit: contain;
}

.cover {
  /* position: absolute;
  top: 0;
  left: 0; */
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.col-9 .card .top .userDetails h3 {
  /* width: 100%; */
  font-size: 16px;
  color: #4d4d4f;
  font-weight: 500;
  line-height: 1em;
}

.col-9 .card .top .userDetails h3 span {
  font-size: 0.75em;
}

.col-9 .card .top .userDetails h3 span {
  font-size: 0.75em;
}

.date {
  width: 100%;
  text-align: right;
  font-size: 15px;
}

.imgBx {
  position: relative;
  width: 100%;
  min-height: 600px;
  margin: 10px 0 15px;
}

.actionBtns {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.actionBtns svg {
  cursor: pointer;
}

.actionBtns .left svg {
  margin-right: 8px;
}

.likes {
  font-weight: 500;
  margin-top: 5px;
  font-size: 14px;
  color: #4d4d4f;
  float: right;
}

.message {
  font-weight: 400;
  margin-top: 5px;
  font-size: 14px;
  color: #777;
  line-height: 1.5em;
}

.message b {
  color: #262626;
}

.message span {
  cursor: pointer;
  color: #1d92ff;
}

.comments {
  margin-top: 10px;
  font-weight: 400;
  color: #999;
}

.addComments {
  display: flex;
  align-items: center;
  margin-top: 20px;
  border-top: 1px solid #ddd;
  padding: 10px 0;
}

.addComments a {
  color: #1d92ffcb;
  font-weight: 500;
}

.addComments .reaction {
  position: relative;
  font-size: 1.3rem;
  margin-right: 10px;
  color: rgb(88, 88, 88);
}

input.text {
  width: 100%;
  border: none;
  outline: none;
  font-weight: 400;
  font-size: 14px;
  color: #262626;
  background: none;
}

input.text::placeholder {
  color: #777;
}

.postTime {
  margin-top: 8px;
  font-weight: lighter;
  color: rgb(163, 163, 163);
  font-size: 12px;
  text-transform: uppercase;
}

a {
  text-decoration: none;
}

.hidden {
  display: none;
}

@media screen and (min-width: 1000px) {
  .col-9 {
    width: 100%;
    background: #fafafa;
  }

  .col-3 {
    display: none;
  }
}

@media screen and (max-width: 600px) {
  .container {
    width: 100% !important;
  }
}

.navbar .container {
  padding: 15px 10px !important;
}

.col-9 {
  margin-top: 4px;
  min-width: 100%;
}

.statuses {
  margin-bottom: 0px;
}

.col-9 .card {
  margin-bottom: 0px;
  width: 100%;
  border: none;
}

.imgBx {
  position: relative;
  width: 100%;
  min-height: 400px;
  margin: 10px 0 15px;
}
.likes {
  margin-right: 15px;
}

.modify {
  text-align: right;
  padding-right: 10px;
}

.btn {
  width: 100px;
  margin-left: 10px;
  height: 40px;
  color: #ddd;
  padding: 10px 25px;
  font-family: "Lato", sans-serif;
  font-weight: 500;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow: inset 2px 2px 2px 0px rgba(255, 255, 255, 0.5), 7px 7px 20px 0px rgba(0, 0, 0, 0.1),
    4px 4px 5px 0px rgba(0, 0, 0, 0.1);
  outline: none;
}

.btn-1 {
  background: rgb(150, 150, 150);
  /* background: linear-gradient(0deg, rgba(6, 14, 131, 1) 0%, rgba(12, 25, 180, 1) 100%); */
  border: none;
}
.btn-1:hover {
  background: rgb(200, 200, 200);
  /* background: linear-gradient(0deg, rgba(0, 3, 255, 1) 0%, rgba(2, 126, 251, 1) 100%); */
}
</style>
