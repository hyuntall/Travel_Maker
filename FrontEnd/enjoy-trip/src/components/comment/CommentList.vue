<template>
  <div class="wrap">
    <h4>comments</h4>
    <comment-vue v-for="comment in comments" :key="comment.idx" :comment="comment" />
  </div>
</template>

<script>
import http from "@/util/http-common";
import CommentVue from "./Comment.vue";

export default {
  name: "CommentList",
  props: ["board_idx"],
  components: { CommentVue },
  data() {
    return {
      comments: [],
    };
  },
  created() {
    console.log(this.board_idx);
    http
      .get(`/comment/board/${this.board_idx}`)
      .then(({ data }) => {
        console.log(data);
        this.comments = data;
      })
      .catch(() => {
        alert("댓글 요청 실패");
      });
  },
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
.wrap {
  padding: 10px 20px;
}
</style>
