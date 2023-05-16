<template>
  <write-form :type="type" />
</template>

<script>
import WriteForm from "../common/WriteForm.vue";
export default {
  name: "UserWrite",
  data() {
    return {
      type: "write",
    };
  },
  components: { WriteForm },
  created() {
    if (this.$route.params.user_id) this.type = "modify";
  },
};
</script>

<style scoped></style>
