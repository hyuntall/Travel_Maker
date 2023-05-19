<template>
  <div class="home row">
    <div class="col-5 align-self-center main-text">
      <h2>Travel Maker</h2>
      <p>Travel Maker 프로젝트 설명</p>
      <button class="">시작하기</button>
    </div>
    <img class="col-7" alt="Vue logo" src="../assets/main_page.jpg" />
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: "HomeView",
  components: {},
};
</script>

<style scoped>
.home {
  height: 100vh;
}
.main-text {
  text-align: center;
  
}

.main-text h2 {font-weight: 800px; font-size: 50px;}

.main-text button {border: 2px solid #706565; padding: 8px; background-color: #fff; font-weight: 700; color:#706565;}
.main-text button:hover {background-color: #706565; color:#fff}
</style>
