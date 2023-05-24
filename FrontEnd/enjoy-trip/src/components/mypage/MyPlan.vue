<template>
  <div class="row plan-row">
    <font-awesome-icon icon="fa-solid fa-plane" fade class="col-1" size="lg" />
    <div class="title col-4">
      <router-link :to="'/trip/detail/' + plan.idx" class="link">{{ plan.title }}</router-link>
    </div>
    <div class="date col-4">{{ plan.start_date | dateFormat }} ~ {{ plan.end_date | dateFormat }}</div>
    <button class="remove-button col-1" @click="leavePlan">
      <font-awesome-icon icon="fa-solid fa-xmark" />
    </button>
  </div>
</template>

<script>
import http from "@/util/http-common";
import swal from "sweetalert";

export default {
  name: "MyPlan",
  components: {},
  props: {
    plan: null,
    user_id: String,
  },
  data() {
    return {
      message: "",
    };
  },
  created() {
    console.log(this.plan);
  },
  methods: {
    leavePlan() {
      swal({
        title: "계획을 떠나시겠습니까?",
        type: "warning",
        icon: "warning",
        buttons: ["No", "Yes"],
      }).then((Yes) => {
        if (!Yes) return;
        http
          .post("/plan/leave", {
            plan_idx: this.plan.idx,
            user_id: this.user_id,
          })
          .then(({ data }) => {
            console.log(data);
            swal({
              title: "Success",
              text: "여행 계획에서 떠나셨습니다.",
              icon: "success",
            });
            this.$emit("getPlans");
          });
      });
    },
  },
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

.remove-button {
  background-color: white;
}
</style>
