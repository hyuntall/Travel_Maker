<template>
  <div class="row plan-row">
    <div class="title col-6">
      <a href="#">
        {{ plan.title }}
      </a>
    </div>
    <div class="date col-6">{{ plan.start_date | dateFormat }} ~ {{ plan.end_date | dateFormat }}</div>
  </div>
</template>

<script>
export default {
  name: "MyPlan",
  components: {},
  props: {
    plan: null,
  },
  data() {
    return {
      message: "",
    };
  },
  created() {
    console.log(this.plan);
  },
  methods: {},
  filters: {
    dateFormat(value) {
      const date = new Date(value);
      return date.toLocaleDateString();
    },
  },
};
</script>

<style scoped>
.plan-row {
  height: 50px;
  border-top: 1px solid rgb(218, 217, 217);;
  align-items: center;
  justify-content: center;
}
.title {
  text-align: center;
}
.date {
  text-align: center;
}
</style>
