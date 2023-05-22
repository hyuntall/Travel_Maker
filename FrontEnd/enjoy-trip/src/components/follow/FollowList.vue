<template>
  <div class="wrap">
    <div class="container">
      <div id="board-search">
        <div class="container">
          <div class="search-window">
            <form action="">
              <div class="search-wrap">
                <label for="search" class="blind">사용자 검색</label>
                <input id="search" type="search" name="" placeholder="검색어를 입력해주세요." value="" @keyup="flist" />
                <button type="submit" class="btn btn-dark">검색</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";

export default {
  name: "FollowList",
  components: {},
  props: {
    user_id: String,
  },
  data() {
    return {
      friendList: [],
    };
  },
  created() {
    console.log("sadasdadads");
  },
  methods: {
    flist(event) {
      console.log(event.target.value);
      this.friendList = [];
      if (event.target.value) {
        http.get("/user/searchBykeyword/" + event.target.value).then(({ data }) => {
          data.forEach((element) => {
            this.friendList.push(element);
            console.log(this.friendList);
          });
        });
      }
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
.wrap {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.1);
}
.container {
  max-width: 720px;
  min-width: 720px;
  height: 100vh;
  min-height: 1000px;
  padding-top: 100px;
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
</style>
