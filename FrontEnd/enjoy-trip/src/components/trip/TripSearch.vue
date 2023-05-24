<template>
  <div class="container">
    <div class="row">
      <div class="search">
        <h2 class="mt-5">어디로 여행을 떠나시나요?</h2>
        <b-form-input
          class="w-50 mt-4 mx-auto"
          v-model="text"
          placeholder="여행지를 검색해주세요"
          @keyup.enter="search1"
        ></b-form-input>
        <ul class="select">
          <li tabindex="1" @click="search2">서울</li>
          <li tabindex="1" @click="search2">경기</li>
          <li tabindex="1" @click="search2">강원</li>
          <li tabindex="1" @click="search2">충북</li>
          <li tabindex="1" @click="search2">충남</li>
          <li tabindex="1" @click="search2">전북</li>
          <li tabindex="1" @click="search2">전남</li>
          <li tabindex="1" @click="search2">경북</li>
          <li tabindex="1" @click="search2">경남</li>
          <li tabindex="1" @click="search2">제주</li>
        </ul>

        <div class="regions">
          <div v-for="region in regions" :key="region.idx" >
            <b-card
              :title="region.cate1_eng"
              :img-src="region.img"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width: 14em"
              class="mb-2 region"
            >
              <b-card-text>
                {{ region.cate2 }}
              </b-card-text>
              <div>
                <b-button @click="openModal" :id="region.idx">상세보기</b-button>

                <b-modal :id="region.idx" size="lg" centered title="BootstrapVue" class="modal" hide-footer hide-header>
                  <div class="body">
                    <div class="bg">
                      <img :src="region.img" alt="" />
                    </div>
                    <div class="desc">
                      <h2>{{ region.cate1 }}</h2>
                      <span>{{ region.cate2 }}</span>
                      <p>
                        {{ region.descr }}
                      </p>
                      <!-- <b-link :to="{name: 'TripMake', params: region}">플랜 만들기</b-link> -->
                      <b-link :to="`trip/make/${region.cate1}/${region.cate2}`">플랜 만들기</b-link>
                    </div>
                  </div>
                </b-modal>
              </div>
            </b-card>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
export default {
  name: "TripSearch",
  components: {},
  data() {
    return {
      text: "",
      message: "",
      num: "",
      regions: [],
    };
  },
  created() {
    http
      .get("/region/all")
      .then(({ data }) => {
        console.log(data);
        data.forEach((region) => {
          console.log(region);
          this.regions.push(region);
        });
      })
      .catch((error) => console.log(error));
  },
  methods: {
    search1(event) {
      console.log(event.target.value);
      http.get(`/region/keyword/${event.target.value}`).then(({ data }) => {
        console.log(data);
        this.regions = [];
        data.forEach((element) => {
          this.regions.push(element);
        });
      });
    },
    search2(event) {
      console.log(event.target.textContent);
      http.get(`/region/keyword/${event.target.textContent}`).then(({ data }) => {
        console.log(data);
        this.regions = [];
        data.forEach((element) => {
          this.regions.push(element);
        });
      });
    },
    openModal(event) {
      this.$bvModal.show(Number(event.target.id));
    },
  },
};
</script>

<style scoped>
.search {
  /* margin-top: 100px; */
  text-align: center;
}
.search h2 {
  font-weight: 700;
}
.search input {
  height: 60px;
  text-align: center;
  font-size: 16px;
}
.search .select {
  display: flex;
  justify-content: center;
  padding-right: 25px;
}
.search .select li {
  padding: 15px;
  font-size: 18px;
}
.search .select li:focus {
  background-color: #000;
  color: #fff;
}
.search .regions {
  display: flex;
  flex-wrap: wrap;
  padding-left: 40px;
}
.search .regions .region {
  box-shadow: 0 5px 5px -5px #333;
  margin: 30px;
  overflow: hidden;
}
.search .regions .region img {
  transition: all 0.3s linear;
}
.search .regions .region img:hover {
  transform: scale(1.1);
}
.search .regions .region button {
  background-color: #fff;
  color: #000;
  border-color: rgba(0, 0, 0, 0.2);
}
.body {
  display: flex;
}
.body .bg {
  width: 30%;
}
.body .desc {
  width: 65%;
  padding: 20px;
  margin-left: auto;
}
.body .desc p {
  margin-top: 3px;
  font-size: 8px;
}
.body .desc a {
  text-decoration: none;
  float: right;
  margin-top: 10px;
}
.card-img-top {
  width: 250px;
  height: 200px;
}
</style>
