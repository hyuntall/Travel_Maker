<template>
  <div class="row plan-row">
    <font-awesome-icon icon="fa-solid fa-plane" fade class="col-2" size="lg" />
    <div class="title col-4">
      <a href="#" class="link">
        {{ plan.title }}
      </a>
    </div>
    <div class="date col-4">{{ plan.start_date | dateFormat }} ~ {{ plan.end_date | dateFormat }}</div>
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
  border-top: 1px solid rgb(218, 217, 217);
  align-items: center;
  justify-content: center;
}
.title {
  text-align: center;
}
.date {
  text-align: center;
}
.link {
  text-decoration: none;
  color: rgb(77, 77, 77);
}
</style>
