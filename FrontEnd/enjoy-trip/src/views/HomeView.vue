<template>
  <div class="container">
    <div class="wrap">
      <b-carousel
        class="slide"
        id="carousel-1"
        v-model="slide"
        :interval="4000"
        controls
        indicators
        label-next=""
        label-prev=""
        style="text-shadow: 1px 1px 2px #333"
      >
        <b-carousel-slide class="imgbox">
          <template #img>
            <img class="img" :src="require(`@/assets/main_page.jpg`)" />
            <h3 class="board-title">Welcome to Travel Maker!!!</h3>
          </template>
        </b-carousel-slide>
        <b-carousel-slide class="imgbox" v-for="(board, index) in weekBoards" :key="index">
          <template #img>
            <img class="img" :src="require(`C:/EnjoyTrip/board/${board.image}`)" alt="image slot" />
            <h3 class="board-title">주간 {{ index + 1 }}위 게시글 : {{ board.title }}</h3>
          </template>
        </b-carousel-slide>
      </b-carousel>

      <div class="title">Travel Maker</div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";

export default {
  data() {
    return {
      slide: 0,
      sliding: null,
      weekBoards: [],
    };
  },
  methods: {},
  created() {
    http
      .get("/board/list/week/0")
      .then(({ data }) => {
        this.weekBoards = data;
        console.log(data);
      })
      .catch(() => {
        console.log("인기글 불러오기 실패");
      });
  },
};
</script>

<style scoped>
.wrap {
  height: 100vh;
  overflow: hidden;
}

.slide {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.img {
  width: 100%;
  height: 100%;
  max-height: 80vh;
  object-fit: cover;
}

.board-title {
  width: 100%;
  padding-left: 10px;
  background-color: #ababab;
}

.title {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 200;
  font-size: 5vw;
  /* color: black; */
  text-align: center;
  color: #ffffff;
  text-shadow: 2px 8px 6px rgba(0, 0, 0, 0.2), 0px -3px 20px rgba(255, 255, 255, 0.4);
}
@media screen and (min-width: 993px) {
  .slide {
    margin-top: 50px;
  }
}

@media screen and (max-width: 992px) {
}
</style>
