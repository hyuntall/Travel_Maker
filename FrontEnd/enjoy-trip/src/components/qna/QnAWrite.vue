<template>
  <div class="wrap">
    <div class="board">
      <h2>문의하기</h2>
      <div class="board_input user_id">
        <label for="id" class="label">작성자</label>
        <br />
        <input type="text" v-model="user_id" name="user_id" id="user_id" />
      </div>
      <div class="board_input title">
        <label for="title" class="label">제목</label>
        <input type="text" v-model="title" name="title" id="title" />
      </div>
      <div class="board_input content">
        <label for="content" class="label">내용</label>
        <textarea v-model="content" name="title" id="title"> </textarea>
      </div>
      <div class="submit">
        <button v-if="id" class="btn btn-dark">수정</button>
        <button v-else class="btn btn-dark" @click="writeQnA">작성하기</button>

        <button v-if="id" class="btn btn-dark">삭제</button>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";

export default {
  name: "qnaWrite",
  components: {},
  data() {
    return {
      id: 0,
      user_id: "",
      title: "",
      content: "",
    };
  },
  computed: {
    renderedContent() {
      return this.content.replace(/!\[(.*?)\]\((.*?)\)/g, '<img src="$2" alt="$1">');
    },
  },
  created() {
    this.id = this.$route.params.id;
    console.log(this.id);
    if (this.id) {
      http.get(`/qna/detail/${this.id}`).then(({ data }) => {
        this.id = data.idx;
        this.user_id = data.userId;
        this.title = data.title;
        this.content = data.content;
      });
    }
  },
  methods: {
    writeQnA() {
      http.post(`/qna/write`, {
        title: this.title,
        content: this.content,
        user_id: this.user_id,
      });
    },
    updateQnA() {
      http
        .put(`/qna/update`, {
          title: this.title,
          content: this.content,
          user_id: this.user_id,
        })
        .then(({ data }) => {
          console.log(data);
        });
    },
    deleteQnA() {
      http.delete(`/qna/delete/${this.id}`).then(({ data }) => {
        console.log(data);
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
</style>
