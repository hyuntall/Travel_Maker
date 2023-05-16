<template>
  <div class="wrap">
    <div class="board">
      <div class="page-title">
        <div class="container">
          <h3>자유게시판</h3>
        </div>
      </div>

      <!-- board seach area -->
      <div id="board-search">
        <div class="container">
          <div class="search-window">
            <form action="">
              <div class="search-wrap">
                <label for="search" class="blind">공지사항 내용 검색</label>
                <input id="search" type="search" name="" placeholder="검색어를 입력해주세요." value="" />
                <button type="submit" class="btn btn-dark">검색</button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- board list area -->
      <div id="board-list">
        <div class="container">
          <table class="board-table">
            <thead>
              <tr>
                <th scope="col" class="th-num">번호</th>
                <th scope="col" class="th-title">제목</th>
                <th scope="col" class="th-date">작성자</th>
                <th scope="col" class="th-date">등록일</th>
                <th scope="col" class="th-date">조회수</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="board in boards" :key="board.board_id">
                <td>{{ board.board_id }}</td>
                <td>
                  <a href="#!">{{ board.title }}</a>
                </td>
                <td>{{ board.user_id }}</td>
                <td>{{ board.written_date }}</td>
                <td>{{ board.view }}</td>
              </tr>
            </tbody>
          </table>
          <button class="btn btn-dark write" @click="moveWrite()">글쓰기</button>
        </div>
      </div>
      <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center pagination-lg">
          <li class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1" aria-disabled="true">&lt;&lt;</a>
          </li>
          <li class="page-item"><a class="page-link" href="#">1</a></li>
          <li class="page-item"><a class="page-link" href="#">2</a></li>
          <li class="page-item"><a class="page-link" href="#">3</a></li>
          <li class="page-item"><a class="page-link" href="#">4</a></li>
          <li class="page-item"><a class="page-link" href="#">5</a></li>
          <li class="page-item">
            <a class="page-link" href="#">>></a>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
export default {
  name: "BoardList",
  components: {},
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
  margin: 0 auto;
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
