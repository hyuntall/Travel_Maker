<template>
  <div class="container row" >
    <div class="float" @click="moveWrite()">
      <span class="float-button" > + </span>
    </div>
    <div class="list">
      <board-one v-for="board in boards" :key="board.board_id" :board="board" />
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import BoardOne from "./BoardOne.vue";
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
    http.get("/board/all/cnt").then(({data})=>{
      this.limit = data;
    }).catch(()=> {
      console.log("게시글 전체 갯수 요청 실패")
    })
  },
  mounted(){
    window.addEventListener('scroll', this.scrollDown);
  },
  methods: {
    scrollDown(){
      if (this.page * 5 <= this.limit && window.innerHeight + window.scrollY >= document.body.offsetHeight){
        // console.log("무한스크롤 발생!")
        this.getBoardList();
      }
    },
    getBoardList(){
      http
      .get(`/board/list/page/${this.page++}`)
      .then(({ data }) => {
        // console.log(data);
        // data.sort((a, b) => {
        //   return -(a.idx - b.idx);
        // });
        this.boards = this.boards.concat(data);
        // console.log(this.boards);
        // console.log(this.page)
      })
      .catch(() => {
        alert("게시판 요청 실패");
      });
    },
    moveWrite() {
      this.$router.push({ name: "BoardRegist" });
    },
  },
};
</script>

<style scoped>
/* reset */

* {
  list-style: none;
  text-decoration: none;
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

.container {
  margin-top: 100px !important;
  width: 800px;
  margin: auto;
}

.float {
  position: fixed;
  left: 90%;
  bottom: 5%;
  z-index: 999;
  font-size: 1.8em;
  padding: 0;
}
.float-button {
  color: #fff;
  padding: 0;
  width: 55.5px;
  height: 55.5px;
  padding: 5px;
  display: inline-block;
  outline: 0;
  border: none;
  text-decoration: none;
  background: #2196f3;
  text-align: center;
  border-radius: 50%;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0 rgba(0, 0, 0, 0.12);
}
.float :hover {
  background: #42a5f5;
}
</style>
