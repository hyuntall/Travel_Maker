<template>
  <div class="wrapper">
    <router-link :to="{ name: 'BoardDetail', params: { idx: board.idx, originProfileUrl: url } }">
      <img class="boardImg" :src="imgUrl" />
    </router-link>
    <p>
      {{ board.title | textLengthOverCut }}
    </p>
  </div>
</template>

<script>
export default {
  name: "MyBoard",
  components: {},
  props: {
    board: null,
    url: String,
  },
  data() {
    return {
      imgUrl: "",
    };
  },
  created() {
    console.log(this.board);
    console.log(this.url);
    this.imgUrl = require(`C:/EnjoyTrip/board/${this.board.image}`);
  },
  methods: {},
  filters: {
    textLengthOverCut(txt) {
      const lastTxt = "...";
      if (txt.length > 4) {
        txt = txt.substr(0, 4) + lastTxt;
      }
      return txt;
    },
  },
};
</script>

<style scoped>
.wrapper {
  background-color: white;
  width: 100px;
  height: 100px;
  border: 3px solid lightgray;
  border-radius: 10%;
}
.boardImg {
  width: 100%;
  height: 100%;
  object-fit: fill;
}
p {
  margin-top: 5px;
  font-size: 15px;
  text-align: center;
}
</style>
