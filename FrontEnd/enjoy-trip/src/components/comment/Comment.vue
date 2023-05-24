<template>
  <div class="wrap">
    <div class="top">
      <div class="userDetails">
        <div class="profilepic">
          <div class="profile_img">
            <div class="image">
              <img :src="img" alt="img8" />
            </div>
          </div>
        </div>
        <p>{{ comment.user_id }}</p>
      </div>
      <div class="date">
        {{ comment.written_date | dateFormat }}
      </div>
    </div>
    <div class="main">
      <textarea class="content" v-model="content" v-bind:readonly="readonly"></textarea>
    </div>
    <div class="bottom" v-if="comment.user_id === userInfo.id">
      <button v-if="!readonly" class="btn-1" @click="updateComment">수정</button>
      <button class="btn-1" @click="changeMode" v-html="mode"></button>
      <button class="btn-1" @click="deleteComment">삭제</button>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from "vuex";
import swal from "sweetalert";
export default {
  name: "CommentVue",
  props: {
    comment: null,
  },
  components: {},
  data() {
    return {
      message: "",
      content: "",
      readonly: true,
      mode: "수정",
      img: require("../../assets/tmp_profile.jpg"),
    };
  },
  created() {
    this.content = this.comment.content;
    http
      .get(`/user/image/${this.comment.user_id}`)
      .then(({ data }) => {
        this.img = require(`C:/EnjoyTrip/user/${data}`);
      })
      .catch(() => {
        console.log("프로필 이미지 요청 오류");
      });
  },
  methods: {
    changeMode() {
      if (this.readonly) this.mode = "취소";
      else this.mode = "수정";
      this.readonly = !this.readonly;
    },
    deleteComment() {
      http
        .delete(`comment/delete/${this.comment.idx}`)
        .then(({ data }) => {
          console.log(data);
          swal({
            title: "Success",
            text: "댓글이 성공적으로 삭제 되었습니다.",
            icon: "success",
          });
          window.location.reload();
        })
        .catch(() => {
          swal({
            title: "Error",
            text: "댓글 삭제에 오류가 발생했습니다.",
            icon: "error",
          });
        });
    },
    updateComment() {
      http
        .put("comment/update/", {
          idx: this.comment.idx,
          content: this.content,
        })
        .then(({ data }) => {
          console.log(data);
          swal({
            title: "Success",
            text: "댓글이 성공적으로 수정되었습니다.",
            icon: "success",
          });
          window.location.reload();
        })
        .catch(() => {
          swal({
            title: "Error",
            text: "댓글을 수정할 수 없습니다.",
            icon: "error",
          });
        });
    },
  },
  filters: {
    dateFormat(value) {
      const date = new Date(value);
      return date.toLocaleString();
    },
  },
  computed: {
    ...mapState(["userInfo"]),
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
.wrap {
  padding: 15px 15px !important;
  margin-top: 15px;
  border: 1px solid rgb(218, 217, 217);
  height: 180px;
  border-radius: 10px;
}

.content {
  width: 100%;
  border: 1px solid rgb(218, 217, 217);
  border-radius: 5px;
}

.date {
  font-size: 15px;
}

.top {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.top .userDetails {
  width: 80%;
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
  width: 40px;
  height: 40px;
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
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.main {
  margin-top: 5px;
  height: 60px;
}
.bottom {
  margin-top: 20px;
  text-align: right;
}
.bottom button {
  margin-left: 15px;
  padding: 2px;
  border-radius: 5px;
  color: #fafafa;
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

.content {
  padding: 10px;
}
</style>
