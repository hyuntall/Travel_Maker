<template>
  <div class="wrap">
    <div class="container">
      <div class="my-profile row">
        <div class="profile-img col-4">
          <label for="file" class="file-label">
            <img :src="url" />
          </label>
          <div class="file-label">
            <div class="btn-upload" @click="updateImage()">이미지 변경</div>
          </div>
          <input type="file" name="file" id="file" @change="uploadImage" />
        </div>
        <div class="profile col-6">
          <h3>{{ this.userInfo.id }}({{ this.userInfo.name }})님</h3>
          <p>
            {{ this.userInfo.phone_number }}
          </p>
          <div class="friends row">
            <div class="col-4">
              <font-awesome-icon icon="fa-solid fa-pen-to-square" size="2x" style="color: #999999" />
              <p>게시글 수</p>
            </div>
            <div class="col-4">
              <font-awesome-icon icon="fa-solid fa-users" size="2x" style="color: #999999" />
              <p>팔로워</p>
            </div>
            <div class="col-4">
              <router-link :to="{ name: 'FollowList', params: { type: 'following' } }">
                <font-awesome-icon icon="fa-solid fa-user-plus" size="2x" style="color: #999999" />
                <p>팔로우</p>
              </router-link>
            </div>
          </div>
        </div>
        <div class="setup col-2">
          <div class="notice">
            <router-link :to="{ name: 'UserModify', params: { user_id: userInfo.id } }">
              <button class="modify-button">
                <font-awesome-icon icon="fa-solid fa-gear" size="2x" style="color: #999999" />
              </button>
            </router-link>
          </div>
        </div>
      </div>
      <p class="plan-header">내 계획 목록</p>
      <div class="my-plan row">
        <my-plan v-for="myPlan in myPlans" :key="myPlan.idx" :plan="myPlan"></my-plan>
      </div>
      <p class="board-header">내가 작성한 글</p>
      <div class="my-boards row">
        <div v-if="myBoards.length > 0" class="my-boards-content row">
          <my-board
            class="my-board"
            v-for="myBoard in myBoards"
            :key="myBoard.idx"
            :board="myBoard"
            :url="originUrl"
          ></my-board>
        </div>
        <div v-else>작성한 글이 없습니다.</div>
        <div></div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapState, mapMutations } from "vuex";
import MyBoard from "@/components/mypage/MyBoard.vue";
import MyPlan from "@/components/mypage/MyPlan.vue";

export default {
  name: "MyPageView",
  components: { MyBoard, MyPlan },
  data() {
    return {
      image: null,
      url: require("../assets/tmp_profile2.jpg"),
      originUrl: "",
      myBoards: [],
      myPlans: [],
    };
  },
  created() {
    console.log(this.userInfo);
    if (this.userInfo.image) this.url = require(`C:/EnjoyTrip/user/${this.userInfo.image}`);
    this.originUrl = this.userInfo.image;
    http
      .get(`/board/list/${this.userInfo.id}`)
      .then(({ data }) => {
        console.log(data);
        data.sort((a, b) => {
          return -(a.idx - b.idx);
        });
        this.myBoards = data;
      })
      .catch(() => {
        alert("게시글 목록 요청 실패");
      });

    http
      .get(`/plan/list/${this.userInfo.id}`)
      .then(({ data }) => {
        console.log(data);
        console.log(data);
        data.sort((a, b) => {
          return -(a.idx - b.idx);
        });
        this.myPlans = data;
      })
      .catch(() => {
        console.log("계획 목록 요청 실패");
      });
  },
  methods: {
    ...mapMutations(["SET_USER_INFO"]),
    uploadImage(event) {
      this.image = event.target.files[0];
      // 이미지 업로드 로직
      // 서버로 이미지 업로드 요청을 보내고, 응답으로 받은 이미지 경로를 post.image에 저장
      console.log(this.image);
      this.url = URL.createObjectURL(this.image);
      // this.renderedContent();
    },
    updateImage() {
      if (this.image == null) return;
      const formData = new FormData();
      formData.append("user_id", this.userInfo.id);
      formData.append("image", this.image);
      http
        .post("/user/image", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        })
        .then(({ data }) => {
          console.log(data);
          alert("이미지 변경 성공");
          this.userInfo.image = data;
          this.SET_USER_INFO(this.userInfo);
          window.location.reload();
        })
        .catch(() => {
          alert("이미지 변경 실패");
        });
    },
  },
  computed: {
    ...mapState(["userInfo"]),
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
.my-profile {
  background-color: white;
  border: 1px solid rgb(218, 217, 217);
  height: 40%;
  align-items: center;
  justify-content: center;
}

.profile {
  padding: 30px;
}

.profile .friends {
  height: 150px;
  background-color: white;
  border: 1px solid black;
  align-items: center;
  justify-content: center;
}

.profile-img {
  width: 200px;
  height: 200px;
}

.profile-img img {
  margin-top: 10%;
  height: 150px;
  object-fit: fill;
  border: 5px solid lightgray;
  border-radius: 50%;
}

.file-label {
  width: 100%;
}
.btn-upload {
  margin: auto;
  width: 150px;
  height: 30px;
  background: #fff;
  border: 1px solid rgb(77, 77, 77);
  border-radius: 10px;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}
.btn-upload:hover {
  background: rgb(77, 77, 77);
  color: #fff;
}

#file {
  display: none;
}

.setup {
  display: flex;
  align-items: center;
}

.setup .modify-button {
  background-color: rgb(200, 200, 200);
  width: 65px;
  height: 65px;
  border-radius: 50%;
}

.plan-header,
.board-header {
  text-align: center;
  background-color: rgb(218, 217, 217);
}
.my-plan {
  height: 30%;
  background-color: white;
  border: 1px solid rgb(218, 217, 217);
  width: 720px;
  overflow-y: scroll;
}
.my-boards {
  height: 30%;
  background-color: white;
  border: 1px solid rgb(218, 217, 217);
  width: 720px;
  overflow-y: scroll;
}
.my-boards-content {
  padding-top: 15px;
}
.my-board {
  margin-left: 5%;
}
</style>
