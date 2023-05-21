<template>
  <div class="container">
    <div class="my-profile row">
      <div class="profile-img col-4">
        <label for="file" class="file-label">
          <img :src="url" />
        </label>
        <div class="file-label">
          <div class="btn-upload" @click="updateImage()">이미지 변경</div>
        </div>
        <input type="file" name="file" id="file" @change="uploadImage">
      </div>
      <div class="profile col-8">
        <h3>{{this.userInfo.id}}({{ this.userInfo.name }})님</h3>
        <h3>ID</h3>
        <div class="friends">친구 관련</div>
      </div>
    </div>
    <div class="my-plan">내 계획 목록</div>
    <div class="my-boards">내가 작성한 글</div>
  </div>
</template>

<script>
import http from '@/util/http-common';
import { mapState, mapMutations } from 'vuex';

export default {
  name: "MyPageView",
  components: {},
  data() {
    return {
      image: null,
      url: require("../assets/tmp_profile2.jpg")
    };
  },
  created() {
    console.log(this.userInfo);
    this.url = require(`C:/EnjoyTrip/user/${this.userInfo.image}`);
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
    updateImage(){
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
    }
  },
  computed: {
    ...mapState(["userInfo"]),
  }
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Noto Sans KR", sans-serif;
}

.container {
  max-width: 720px;
  background-color: bisque;
  height: 100vh;
  padding-top: 150px;
}
.my-profile {
  background-color: aqua;
  height: 40%;
}

.profile {
  padding: 30px;
}

.profile .friends {
  height: 30%;
  background-color: yellowgreen;
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
  border: 1px solid rgb(77,77,77);
  border-radius: 10px;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}
.btn-upload:hover {
  background: rgb(77,77,77);
  color: #fff;
}

#file {
  display: none;
}
.my-plan {
  height: 30%;
  background-color: pink;
}

.my-boards {
  height: 30%;
  background-color: cornflowerblue;
}
</style>
