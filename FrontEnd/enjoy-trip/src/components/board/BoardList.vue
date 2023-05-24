<template>
  <div class="container row" ref="top">
    <div class="list" v-if="boards.length > 0">
      <board-one v-for="board in boards" :key="board.board_id" :board="board" />
    </div>
    <div v-else class="nothing">등록된 게시글이 없습니다.</div>
    <div class="float">
      <div class="float-button up" @click="moveUp()">
        <font-awesome-icon icon="fa-solid fa-arrow-up" size="xl" />
      </div>
      <div class="float-button write" @click="moveWrite()">
        <font-awesome-icon icon="fa-solid fa-plus" size="xl" />
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import BoardOne from "./BoardOne.vue";
import swal from "sweetalert";
export default {
  name: "BoardList",
  components: { BoardOne },
  data() {
    return {
      boards: [],
      page: 0,
      limit: 0,
    };
  },
  created() {
    this.getBoardList();
    http
      .get("/board/all/cnt")
      .then(({ data }) => {
        this.limit = data;
      })
      .catch(() => {
        console.log("게시글 전체 갯수 요청 실패");
        swal({
          title: "Error",
          text: "게시글 목록을 불러오지 못했습니다.",
          icon: "error",
        });
        this.$router.push("/");
      });
  },
  mounted() {
    // 이걸 윈도우 말고 div에 넣을 수는 없을까 생각해보자
    window.addEventListener("scroll", this.scrollDown);
  },
  methods: {
    scrollDown() {
      if (this.page * 5 <= this.limit && window.innerHeight + window.scrollY >= document.body.offsetHeight) {
        this.getBoardList();
      }
    },
    getBoardList() {
      http
        .get(`/board/list/page/${this.page++}`)
        .then(({ data }) => {
          this.boards = this.boards.concat(data);
        })
        .catch(() => {
          swal({
            title: "Error",
            text: "게시글 목록을 불러오지 못했습니다.",
            icon: "error",
          });
          this.$router.push("/");
        });
    },
    moveWrite() {
      this.$router.push({ name: "BoardRegist" });
    },
    moveUp() {
      this.$refs.top.scrollIntoView({ behavior: "smooth" });
    },
  },
};
</script>

<style scoped>
/* reset */

* {
  list-style: none;
  text-decoration: none;
  /* padding: 0; */
  margin: 0;
  box-sizing: border-box;
}

.container {
  /* margin-top: 100px !important; */
  min-width: 800px;
  max-width: 800px;
  margin: auto;
}

.list {
  z-index: 100;
}
.float {
  position: fixed;
  font-size: 1.8em;
  padding: 0;
  width: 870px;
  height: 90vh;
}
.float-button {
  z-index: 999;
  position: absolute;
  right: 0;
  align-items: center;
  justify-content: center;
  display: flex;
  color: #fff;
  width: 55.5px;
  height: 55.5px;
  outline: 0;
  border: none;
  text-decoration: none;
  background: lightgray;
  text-align: center;
  border-radius: 50%;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12);
}

.write {
  bottom: 10%;
}

.up {
  bottom: 20%;
}
.float :hover {
  background: gray;
}

.nothing {
  text-align: center;
}
</style>
