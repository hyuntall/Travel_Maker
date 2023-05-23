<template>
  <div class="wrap">
    <div class="board">
      <h2>글쓰기</h2>
      <div class="board_input user_id">
        <label for="id" class="label">작성자</label>
        <br />
        <input type="text" v-model="userInfo.id" name="user_id" id="user_id" readonly />
      </div>
      <div class="board_input title">
        <label for="title" class="label">제목</label>
        <input type="text" v-model="title" name="title" id="title" />
      </div>
      <div class="board_input content">
        <label for="content" class="label">내용</label>
        <textarea v-model="content" name="title" id="title"> </textarea>
      </div>
      <div class="board_input image">
        <input type="file" name="image" id="image" @change="uploadImage" accept="image/*" />
      </div>
      <div v-if="image || url" class="board_input uploadImg">
        <img :src="url" alt="Uploaded Image" />
      </div>
      <div class="submit">
        <button v-if="$route.params.idx" class="btn btn-dark" @click="updateBoard">수정하기</button>
        <button v-else class="btn btn-dark" @click="writeBoard">작성하기</button>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from "vuex";
import swal from "sweetalert";

export default {
  name: "boardWrite",
  components: {},
  data() {
    return {
      title: "",
      content: "",
      image: null,
      url: "",
    };
  },
  created() {
    console.log(this.$route.params);
    if (this.$route.params.idx) {
      http.get(`/board/${this.$route.params.idx}`).then(({ data }) => {
        console.log(data);
        this.title = data.title;
        this.content = data.content;
        this.url = require(`C:/EnjoyTrip/board/${data.image}`);
      });
    }
  },
  computed: {
    ...mapState(["userInfo"]),
    renderedContent() {
      return this.content.replace(/!\[(.*?)\]\((.*?)\)/g, '<img src="$2" alt="$1">');
    },
  },
  methods: {
    uploadImage(event) {
      this.image = event.target.files[0];
      // 이미지 업로드 로직
      // 서버로 이미지 업로드 요청을 보내고, 응답으로 받은 이미지 경로를 post.image에 저장
      console.log(this.image);
      this.url = URL.createObjectURL(this.image);
      // this.renderedContent();
    },
    writeBoard() {
      if (this.image == null) {
        swal({
            title: "Warning",
            text: "이미지를 필수로 등록해야합니다.",
            icon: "warning",
          });
          return;
      }
      const formData = new FormData();
      formData.append("user_id", this.userInfo.id);
      formData.append("title", this.title);
      formData.append("content", this.content);
      formData.append("image", this.image);
      http
        .post("/board/write", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(({ data }) => {
          console.log(data);
          swal({
            title: "Success",
            text: "게시글이 등록되었습니다.",
            icon: "success",
          });
          this.$router.push("/board");
        })
        .catch(() => {
          swal({
            title: "Error",
            text: "게시글 등록에 실패했습니다.",
            icon: "error",
          });
        });
    },

    updateBoard() {
      const formData = new FormData();

      formData.append("user_id", this.userInfo.id);
      formData.append("title", this.title);
      formData.append("content", this.content);
      if (this.image != null) formData.append("image", this.image);
      http
        .put(`/board/update/${this.$route.params.idx}`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(({ data }) => {
          console.log(data);
          swal({
            title: "Success",
            text: "게시글이 수정 되었습니다.",
            icon: "success",
          });
          this.$router.push("/board");
        })
        .catch(() => {
          swal({
            title: "Error",
            text: "게시글을 수정에 오류가 발생했습니다.",
            icon: "error",
          });
          this.$router.push("/board");
        });
    },
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Noto Sans KR", sans-serif;
}

a {
  text-decoration: none;
  color: black;
}

li {
  list-style: none;
}

.wrap {
  margin-top: 30px;
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  /* background: rgba(0, 0, 0, 0.1); */
}

.board {
  border: #555 solid 1px;
  border-radius: 5px;
  width: 80%;
  background: white;
  display: flex;
  align-items: center;
  flex-direction: column;
}

h2 {
  width: 100%;
  height: 50px;
  color: black;
  font-size: 2em;
  background: rgba(0, 0, 0, 0.1);
  padding-left: 8%;
  padding-top: 5px;
}

.board_input {
  margin-top: 20px;
  width: 80%;
}
.board_input label {
  float: left;
}
.user_id input {
  width: 200px;
  height: 50px;
  border-radius: 5px;
  margin-top: 10px;
  padding: 0px 20px;
  border: 1px solid lightgray;
  outline: none;
}

.title input {
  width: 100%;
  height: 50px;
  border-radius: 5px;
  margin-top: 10px;
  padding: 0px 20px;
  border: 1px solid lightgray;
  outline: none;
}

.board_input textarea {
  width: 100%;
  height: 200px;
  border-radius: 5px;
  margin-top: 10px;
  padding: 0px 20px;
  border: 1px solid lightgray;
  outline: none;
}

.btn {
  display: inline-block;
  padding: 0 30px;
  font-size: 15px;
  font-weight: 400;
  background: transparent;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  -ms-touch-action: manipulation;
  touch-action: manipulation;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  border: 1px solid transparent;
  text-transform: uppercase;
  -webkit-border-radius: 0;
  -moz-border-radius: 0;
  border-radius: 0;
  -webkit-transition: all 0.3s;
  -moz-transition: all 0.3s;
  -ms-transition: all 0.3s;
  -o-transition: all 0.3s;
  transition: all 0.3s;
}

.submit {
  margin-top: 15px;
  width: 100%;
  margin-bottom: 15px;
}

.btn-dark {
  float: right;
  margin-right: 15px;
  background: #555;
  color: #fff;
  height: 30px;
}

.btn-dark:hover,
.btn-dark:focus {
  background: #373737;
  border-color: #373737;
  color: #fff;
}

.uploadImg img {
  width: 200px;
  border: 1px solid black;
  border-radius: 3px;
}
</style>
