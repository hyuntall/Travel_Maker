<template>
  <div class="wrap">
    <h4>comments</h4>
    <comment-vue v-for="comment in comments" :key="comment.idx" :comment="comment" />
  </div>
</template>

<script>
import http from "@/util/http-common";
import CommentVue from "./Comment.vue";
import swal from "sweetalert";

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
    this.getComments();
  },
  methods: {
    getComments() {
      http
        .get(`/comment/board/${this.board_idx}`)
        .then(({ data }) => {
          console.log(data);
          this.comments = data;
        })
        .catch(() => {
          swal({
            title: "Error",
            text: "댓글 목록을 불러오지 못했습니다.",
            icon: "error",
          });
        });
    },
  },
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
