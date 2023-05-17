<template>
  <div class="col-9 statuses">
    <div class="card">
      <div class="top">
        <div class="userDetails">
          <div class="profilepic">
            <div class="profile_img">
              <div class="image">
                <img src="https://media.geeksforgeeks.org/wp-content/uploads/20220609093229/g-200x200.png" alt="img8" />
              </div>
            </div>
          </div>
          <h3>작성자</h3>
        </div>
        <div>작성일</div>
      </div>
      <div class="title">글 제목</div>
      <div class="swiper">
        <div class="swiper-wrapper">
          <div class="imgBx swiper-slide">
            <router-link to="detail">
              <img src="../../assets/main_page.jpg" alt="post-1" class="cover" />
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BoardOne",
  props: {
    board: null,
  },
  components: {},
  data() {
    return {
      message: "",
    };
  },
  created() {},
  methods: {},
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, system-ui, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue",
    sans-serif;
  background: #fafafa;
}

.col-9 {
  width: 100%;
  margin-top: 30px;
}

.statuses {
  margin-bottom: 30px;
  width: 100%;
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: start;
  padding: 20px 0;
  border-radius: 2px;
  border: 1px solid rgb(218, 217, 217);
  overflow: auto;
}

.statuses .status {
  display: inline-block;
  border-radius: 50%;
  overflow: auto;
  width: 70px;
  height: 70px;
  min-width: 70px;
  min-height: 70px;
  background: linear-gradient(to right, red, orange);
  padding: 2px;
  margin-left: 15px;
  cursor: pointer;
}

.statuses .status .image {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  overflow: hidden;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px solid white;
}

.statuses .status img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.col-3 {
  width: 33%;
  position: -webkit-sticky;
  position: sticky;
  top: 90px;
}

.col-3 h4 {
  color: rgb(100, 100, 100);
}

.col-3 .card {
  margin-bottom: 20px;
  position: relative;
  width: 100%;
  /* min-height: 400px; */
  display: inline-block;
}

.col-3 .card .top {
  padding: 10px 0px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.col-3 .card .top a {
  color: #1d92ff;
}

.col-3 .card .bottom {
  padding: 10px 20px;
}

.col-3 .card .top .userDetails {
  width: 100%;
  display: flex;
  align-items: center;
}

.col-3 .card .top .userDetails h3 {
  font-size: 16px;
  color: #4d4d4f;
  font-weight: 500;
  line-height: 1em;
}

.col-3 .card .top .userDetails h3 span {
  font-size: 0.75em;
}

.col-3 .card .top .userDetails h3 span {
  font-size: 0.75em;
}

.title {
  margin-left: 15px;
  font-weight: bold;
}

.col-9 .card {
  margin-bottom: 20px;
  position: relative;
  width: 100%;
  /* min-height: 400px; */
  display: inline-block;
  border: 1px solid rgba(7, 7, 7, 0.24);
}

.col-9 .card .top {
  padding: 10px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.col-9 .card .top .userDetails {
  width: 100%;
  display: flex;
  align-items: center;
}

.profilepic {
  display: inline-block;
  cursor: pointer;
}

.profilepic .profile_img {
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  overflow: hidden;
  width: 30px;
  height: 30px;
  background: linear-gradient(to right, red, orange);
  padding: 2px;
  margin-right: 8px;
  cursor: pointer;
}

.profilepic .profile_img .image {
  position: relative;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  overflow: hidden;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px solid white;
}

.profilepic .profile_img img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.cover {
  /* position: absolute;
  top: 0;
  left: 0; */
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.col-9 .card .top .userDetails h3 {
  /* width: 100%; */
  font-size: 16px;
  color: #4d4d4f;
  font-weight: 500;
  line-height: 1em;
}

.col-9 .card .top .userDetails h3 span {
  font-size: 0.75em;
}

.col-9 .card .top .userDetails h3 span {
  font-size: 0.75em;
}

.dot {
  transform: scale(0.6);
  cursor: pointer;
}

.imgBx {
  position: relative;
  width: 100%;
  min-height: 600px;
  margin: 10px 0 15px;
}

.postTime {
  margin-top: 8px;
  font-weight: lighter;
  color: rgb(163, 163, 163);
  font-size: 12px;
  text-transform: uppercase;
}

a {
  text-decoration: none;
}

.hidden {
  display: none;
}

@media screen and (min-width: 1000px) {
  .col-9 {
    width: 100%;
    background: #fafafa;
  }

  .col-3 {
    display: none;
  }
}

@media screen and (max-width: 600px) {
  .container {
    width: 100% !important;
  }
}

.navbar .container {
  padding: 15px 10px !important;
}

.col-9 {
  margin-top: 4px;
  min-width: 100%;
}

.statuses {
  margin-bottom: 0px;
}

.col-9 .card {
  margin-bottom: 0px;
  width: 100%;
  border: none;
}

.imgBx {
  position: relative;
  width: 100%;
  min-height: 400px;
  margin: 10px 0 15px;
}
</style>
