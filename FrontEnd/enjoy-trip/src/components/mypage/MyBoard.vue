<template>
  <div class="wrapper">
    <img class="boardImg" :src="imgUrl" />
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
  },
  data() {
    return {
      imgUrl: "",
    };
  },
  created() {
    console.log(this.board);
    this.imgUrl = require(`C:/EnjoyTrip/board/${this.board.image}`);
  },
  methods: {},
  filters: {
    textLengthOverCut(txt) {
      const lastTxt = "...";
      if (txt.length > 5) {
        txt = txt.substr(0, 5) + lastTxt;
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
  font-size: 12px;
}
</style>
