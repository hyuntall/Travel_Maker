<template>
  <div class="wrap">
    <div class="container">

      <div id="board-search" style="margin-bottom:20px">
        <div class="search-window first">
          <h2>팔로잉</h2>
          <div v-if="followed.length === 0">현재 팔로잉하고 있는 유저가 없습니다</div>
          <div class="profile">
            <div v-for="follo, index in followed" :key="index">
              <b-avatar variant="info" :src="follo.image? require(`C:/EnjoyTrip/user/${follo.image}`):require('../../assets/tmp_profile2.jpg')"></b-avatar>
            </div>
          </div>
        </div>
      </div>

      <div id="board-search">

        <div class="search-window">
          <form action="">
            <div class="search-wrap">
              <h2>사용자 검색</h2>
              <b-form-input class="w-70 mt-4 mx-auto" placeholder="사용자를 검색해주세요" @focus="flist" @keyup="flist" ></b-form-input>
              <!-- <input id="search" type="search" name="" placeholder="검색어를 입력해주세요." value="" @keyup="flist" /> -->
              <!-- <button type="submit" class="btn btn-dark">검색</button> -->
              <div class="user-list">
                <div class="user" v-for="(user, index) in friendList" :key="index" >
                  <div class="profile">
                    <b-avatar variant="info" :src="user.image? require(`C:/EnjoyTrip/user/${user.image}`):require('../../assets/tmp_profile2.jpg')"></b-avatar>
                  </div>
                  <div class="info">
                    <p class="name">{{user.name}} </p>
                    <p class="id">{{user.id}}</p>
                  </div>
                  <div class="icon" @click="follow(index)">
                    <font-awesome-icon icon="fa-solid fa-user-plus" size="2x" style="color: #999999" />
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from 'vuex';

export default {
  name: "FollowList",
  components: {},
  props: {
    user_id: String,
  },
  data() {
    return {
      friendList: [],
      followed: []
    };
  },
  created() {
    console.log("sadasdadads");
    this.getFollowings()
  },
  computed: {
    ...mapState(["userInfo"]),
    
  },
  watch: {
    followed() {

    }
  },
  methods: {
    getFollowings() {
      console.log("getfollowing")
      http.get("user/follow/"+this.userInfo.id).then(({data})=> {
      data.forEach(element => {
        this.followed.push(element)
        // console.log(element)
      });
    })
    },
    flist(event) {

      console.log(event.target.value);
      this.friendList = [];
      if (event.target.value) {
        http.get("/user/searchBykeyword/" + event.target.value + "/" + this.userInfo.id).then(({ data }) => {
          data.forEach((element) => {
            if(element.id !== this.userInfo.id) {
              this.friendList.push(element);
            }
            console.log(this.friendList);
          });
        });
      }
    },
    clr() {
      this.friendList = []
    },
    follow(index) {
      console.log("follow")
      console.log(index)
      http.post("/user/follow",{
        following : this.friendList[index].id,
        follower : this.userInfo.id
      }).then(({data})=>{
        alert(data)
        this.friendList.splice(index,1)
        this.getFollowings()
      })
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

.user-list {background-color: #fff;}
.user-list .user {display: flex; padding: 5px; position:relative}
.user-list .user:hover {background-color: rgb(118, 206, 233);}
.user-list .user .profile {margin-left: 20px;}
.user-list .user .info {margin-left: 50px;}
.user-list .user .info .name {font-weight: 600;font-size: 16px;}
.user-list .user .info .id {color:#625e5e; font-size: 14px;}
.user-list .user .icon {position: absolute; left: 500px; padding-top: 5px;}

#board-search .first {align-content: center;}
#board-search .first .profile {display: flex;}

</style>
