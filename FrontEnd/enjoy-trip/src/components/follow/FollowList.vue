<template>
  <div class="wrap">
    <div class="container">
      <div v-if="type==='follow'" id="board-search">
        <div class="search-window">
          <form action="">
            <div class="search-wrap">
              <h2>사용자 검색</h2>
              <b-form-input
                class="w-70 mt-4 mx-auto"
                placeholder="사용자를 검색해주세요"
                @focus="flist"
                @keyup="flist"
              ></b-form-input>
              <div class="user-list">
                <div class="user" v-for="(user, index) in friendList" :key="index">
                  <div class="profile row">
                    <div class="user-img col-2">
                      <b-avatar
                        variant="info"
                        :src="
                          user.image
                            ? require(`C:/EnjoyTrip/user/${user.image}`)
                            : require('../../assets/tmp_profile2.jpg')
                        "
                      ></b-avatar>
                    </div>
                    <div class="info col-2">
                      <div>
                        <p class="name">{{ user.name }}</p>
                        <p class="id">{{ user.id }}</p>
                      </div>
                    </div>
                    <div class="icon col-8" @click="follow(index)">
                      <font-awesome-icon icon="fa-solid fa-user-plus" size="2x" style="color: #999999" />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
      <div id="board-search" style="margin-bottom: 20px">
        <div class="search-window first">
          <h2>{{this.type}}</h2>
          <div v-if="followed.length === 0">현재 팔로잉하고 있는 유저가 없습니다</div>
          <div class="profile-list">
            <div class="followed_profile profile row" v-for="(follo, index) in followed" :key="index">
              <div class="col-3">
                <b-avatar
                  variant="info"
                  :src="
                    follo.image ? require(`C:/EnjoyTrip/user/${follo.image}`) : require('../../assets/tmp_profile2.jpg')
                  "
                ></b-avatar>
              </div>
              <div class="followed-id col-3">{{ follo.id }}</div>
              <div class="followed-id col-3">{{ follo.name }}</div>
              <div v-if="type==='follow'" class="followed-id col-3" @click="unFollow(follo.id)">
                <font-awesome-icon icon="fa-solid fa-user-slash" />
                팔로우 취소
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from "vuex";
import swal from "sweetalert";

export default {
  name: "FollowList",
  components: {},
  props: {
    user_id: String,
  },
  data() {
    return {
      friendList: [],
      followed: [],
      type: "follow"
    };
  },
  created() {
    console.log("sadasdadads");
    this.type = this.$route.params.type;
    this.getFollowings();
    console.log(this.type)
  },
  computed: {
    ...mapState(["userInfo"]),
  },
  watch: {
    followed() {},
  },
  methods: {
    getFollowings() {
      console.log("getfollowing");
      http.get(`user/${this.type}/${this.userInfo.id}`).then(({ data }) => {
        this.followed = [];
        data.forEach((element) => {
          this.followed.push(element);
          console.log(element);
        });
      });
    },
    flist(event) {
      console.log(event.target.value);
      this.userList = [];
      this.friendList = [];
      if (event.target.value) {
        http.get("/user/searchBykeyword/" + event.target.value + "/" + this.userInfo.id).then(({ data }) => {
          data.forEach((element) => {
            if (element.id !== this.userInfo.id) {
              this.friendList.push(element);
            }
            console.log(this.friendList);
          });
        });
      }
    },
    clr() {
      this.friendList = [];
    },
    follow(index) {
      console.log("follow");
      console.log(index);
      http
        .post("/user/follow", {
          following: this.friendList[index].id,
          follower: this.userInfo.id,
        })
        .then(({ data }) => {
          swal({
            title: "Success",
            text: data,
            icon: "success",
          });
          // this.friendList.splice(index, 1);
          this.getFollowings();
        });
    },
    unFollow(following){
      if(!confirm("팔로우를 취소하시겠습니까?"))return;
      http.post(`/user/unfollow`, {
          following,
          follower: this.userInfo.id,
        }).then(({data})=>{
          swal({
            title: "Success",
            text: data,
            icon: "success",
          });
          this.getFollowings();
        })
    }
  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "SDGothic", sans-serif;
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
  text-align: center;
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

.user-list {
  background-color: #fff;
  position: absolute;
  width: 100%;
  z-index: 200;
}
.user-list .user {
  padding: 5px;
  position: relative;
}

.user .profile {
  display: flex;
}

.user .profile > div {
  display: flex;
  align-items: center;
  justify-content: center;
}

.user .profile .icon {
  justify-content: flex-end;
  padding-right: 5px;
}

.user-list .user:hover {
  background-color: rgb(118, 206, 233);
}
#board-search .first {
  align-content: center;
}

.profile-list {
  height: 60vh;
  overflow-y: scroll;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
.profile-list::-webkit-scrollbar {
  display: none;
}

.followed_profile {
  margin-top: 15px;
  display: flex;
}

.profile-list :hover {
  background-color: hsl(65, 80%, 65%);
}

.followed-id {
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
