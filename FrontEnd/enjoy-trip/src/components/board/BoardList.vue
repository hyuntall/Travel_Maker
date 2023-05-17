<template>
  <div class="container row">
    <div>
      <div>
        <router-link to="regist">글쓰기</router-link>
      </div>
      <board-one v-for="board in boards" :key="board.board_id" board="board" />
    </div>
  </div>
</template>

<script>
// import BoardDetail from "./BoardDetail.vue";
import BoardOne from "./BoardOne.vue";
export default {
  name: "BoardList",
  components: { BoardOne },
  data() {
    return {
      boards: [
        {
          board_id: 1,
          title: "test1",
          user_id: "박현철",
          written_date: "2023-05-16",
          view: 11,
        },
        {
          board_id: 2,
          title: "test2",
          user_id: "박현철",
          written_date: "2023-05-17",
          view: 2,
        },
        {
          board_id: 3,
          title: "test1",
          user_id: "박현철",
          written_date: "2023-05-186",
          view: 1,
        },
        {
          board_id: 1,
          title: "test1",
          user_id: "박현철",
          written_date: "2023-05-16",
          view: 11,
        },
        {
          board_id: 2,
          title: "test2",
          user_id: "박현철",
          written_date: "2023-05-17",
          view: 2,
        },
        {
          board_id: 3,
          title: "test1",
          user_id: "박현철",
          written_date: "2023-05-186",
          view: 1,
        },
        {
          board_id: 1,
          title: "test1",
          user_id: "박현철",
          written_date: "2023-05-16",
          view: 11,
        },
        {
          board_id: 2,
          title: "test2",
          user_id: "박현철",
          written_date: "2023-05-17",
          view: 2,
        },
        {
          board_id: 3,
          title: "test1",
          user_id: "박현철",
          written_date: "2023-05-186",
          view: 1,
        },
        {
          board_id: 10,
          title: "test1",
          user_id: "박현철",
          written_date: "2023-05-16",
          view: 11,
        },
      ],
    };
  },
  created() {},
  methods: {
    moveWrite() {
      this.$router.push({ name: "BoardRegist" });
    },
  },
};
</script>

<style scoped>
.wrap {
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

table {
  border-collapse: collapse;
  border-spacing: 0;
}
.board {
  padding: 80px 0;
  height: 90vh;
  background: white;
}

.page-title {
  margin-bottom: 60px;
}
.page-title h3 {
  font-size: 28px;
  color: #333333;
  font-weight: 400;
  text-align: center;
}

#board-search .search-window {
  padding: 15px 0;
  background-color: #f9f7f9;
}
#board-search .search-window .search-wrap {
  position: relative;
  /*   padding-right: 124px; */
  margin: 0 auto;
  width: 80%;
  max-width: 564px;
}
#board-search .search-window .search-wrap input {
  height: 40px;
  width: 100%;
  font-size: 14px;
  padding: 7px 14px;
  border: 1px solid #ccc;
}
#board-search .search-window .search-wrap input:focus {
  border-color: #333;
  outline: 0;
  border-width: 1px;
}
#board-search .search-window .search-wrap .btn {
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
  width: 108px;
  padding: 0;
  font-size: 16px;
}

.board-table {
  font-size: 13px;
  width: 100%;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}

.board-table a {
  color: #333;
  display: inline-block;
  line-height: 1.4;
  word-break: break-all;
  vertical-align: middle;
}
.board-table a:hover {
  text-decoration: underline;
}
.board-table th {
  text-align: center;
}

.board-table .th-num {
  width: 100px;
  text-align: center;
}

.board-table .th-date {
  width: 200px;
}

.board-table th,
.board-table td {
  padding: 14px 0;
}

.board-table tbody td {
  border-top: 1px solid #e7e7e7;
  text-align: center;
}

.board-table tbody th {
  padding-left: 28px;
  padding-right: 14px;
  border-top: 1px solid #e7e7e7;
  text-align: left;
}

.board-table tbody th p {
  display: none;
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

.btn-dark {
  background: #555;
  color: #fff;
}

.btn-dark:hover,
.btn-dark:focus {
  background: #373737;
  border-color: #373737;
  color: #fff;
}

.write {
  float: right;
  height: 30px;
}

/* reset */

* {
  list-style: none;
  text-decoration: none;
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}
.clearfix:after {
  content: "";
  display: block;
  clear: both;
}
.container {
  width: 1100px;
  margin: 100px auto;
}
.blind {
  position: absolute;
  overflow: hidden;
  clip: rect(0 0 0 0);
  margin: -1px;
  width: 1px;
  height: 1px;
}

.pagination {
  margin-top: 30px;
  margin-bottom: 30px;
}
.page-item {
  width: 50px;
  text-align: center;
}
</style>
