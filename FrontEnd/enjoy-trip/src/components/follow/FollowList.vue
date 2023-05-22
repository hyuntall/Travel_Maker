<template>
  <div class="wrap">
    <div class="container">
      <div id="board-search">
          <div class="search-window">
            <form action="">
              <label for="search" class="search-label">사용자 검색</label>
              <div class="search-wrap">
                <input id="search" type="search" name="" placeholder="검색어를 입력해주세요." value="" @keyup="flist" />
                <button type="submit" class="btn btn-dark">검색</button>
              </div>
            </form>
          </div>
      </div>
      <div class="resultList">
        <div v-for="user in userList" :key="user.idx" class="result">
          <div class="user-image">
            <div class="user-img">
              <img :src="user.image? require(`C:/EnjoyTrip/user/${user.image}`):require('../../assets/tmp_profile2.jpg')"/>
            </div>
          </div>
          <div class="user-id">{{ user.id }}</div>
          <div class="user-name">{{ user.name }}</div>
          <div class="button-add">
            <button>친구 추가</button>
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
      userList: [],
    };
  },
  created() {
    console.log("sadasdadads");
  },
  methods: {
    flist(event) {
      console.log(event.target.value);
      this.userList = [];
      if (event.target.value) {
        http.get("/user/searchBykeyword/" + event.target.value).then(({ data }) => {
          data.forEach((element) => {
            this.userList.push(element);
            console.log(this.userList);
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
  margin-top: 15px;
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

.search-label {
  width: 80%;
  margin-left: 10%;
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

.resultList {
  background-color: white;
  align-items: center;
  justify-content: center;
  padding-bottom: 5px;
}
.resultList .result {
  display: flex;
  width: 90%;
  text-align: center;
  align-items: center;
  justify-content: center;
  margin-left: 10%;
  margin-bottom: 15px;
}

.resultList .user-img {
  width: 50px;
  height: 50px;
  overflow:hidden;
  border: 2px solid lightgray;
  border-radius: 50%;
}

.resultList .user-img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.resultList .user-id {
  width: 40%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.resultList .user-name {
  width: 30%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.resultList .button-add {
  width: 30%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.btn {
  height: 100%;
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
</style>
