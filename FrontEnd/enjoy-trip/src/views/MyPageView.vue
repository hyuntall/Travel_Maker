<template>
  <div class="container">
    <div class="my-profile row">
      <div class="profile-img col-4">
        <img src="../assets/tmp_profile2.jpg" />
        <input type="file" class="img-input" />
      </div>
      <div class="profile col-8">
        <h3>박현철님</h3>
        <h3>ID</h3>
        <div class="friends">친구 관련</div>
      </div>
    </div>
    <div class="my-plan">내 계획 목록</div>
    <div class="my-boards">내가 작성한 글</div>
  </div>
</template>

<script>
export default {
  name: "MyPageView",
  components: {},
  data() {
    return {
      message: "",
    };
  },
  created() {
    console.log("dsadadasasd");
  },
  methods: {},
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
  background-color: bisque;
  height: 100vh;
  padding-top: 150px;
}
.my-profile {
  background-color: aqua;
  height: 40%;
  overflow: hidden;
}

.profile {
  padding: 30px;
}

.profile .friends {
  height: 30%;
  background-color: yellowgreen;
}

.profile-img {
}

.profile-img img {
  margin-top: 10%;
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
