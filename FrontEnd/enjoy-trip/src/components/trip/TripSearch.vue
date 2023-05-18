<template>
    <div class="container">
      <div class="row">
        <div class="search">
          <h2 class="mt-5">어디로 여행을 떠나시나요?</h2>
          <b-form-input class="w-50 mt-4 mx-auto" v-model="text" placeholder="여행지를 검색해주세요" @keyup.enter="search1"></b-form-input>
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
            <div v-for="region in regions" :key="region.idx">

              <b-card
              :title="region.cate1_eng"
              :img-src="region.img"
              img-alt="Image"
              img-top
              tag="article"
              style="max-width:14em;"
              class="mb-2 region"
            >
              <b-card-text>
                {{region.cate2}}
              </b-card-text>
              <div>
                <b-button v-b-modal.modal-center>상세보기</b-button>

                <b-modal id="modal-center" size="lg" centered title="BootstrapVue" class="modal" hide-footer hide-header>
                  <div class="body">
                    <div class="bg">
                      <img :src="region.img" alt="">
                    </div>
                    <div class="desc">
                      <h2>JEJU</h2>
                      <span>대한민국 제주도</span>
                      <p>
                      섬 전체가 하나의 거대한 관광자원인 제주도. 에메랄드빛 물빛이 인상적인 협재 해수욕장은 제주 대표 여행지며, 파도가 넘보는 주상절리와 바다 위 산책로인 용머리 해안은 제주에서만 볼 수 있는 천혜의 자연경관으로 손꼽힌다. 드라마 촬영지로 알려진 섭지코스는 꾸준한 사랑을 받고 있으며 한라봉과 흑돼지, 은갈치 등은 제주를 대표하는 음식이다.
                      </p>
                      <b-link to="/trip/make">플랜 만들기</b-link>
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
import http from "@/util/http-common.js"
export default {
  name: 'TripSearch',
  components: {},
  data() {
    return {
      text: '',
      message: '',
      num:'',
      regions:[]
    };
  },
  created() {
    http.get("/region/all").then(({data})=>{
      console.log(data)
      data.forEach((region) => {
        console.log(region)
        this.regions.push(region)
      });
    })
  },
  methods: {
    search1(event) {
      console.log(event.target.value)
    },
    search2(event) {
      console.log(event.target.textContent)
    }
  },
};
</script>

<style scoped>
.search {margin-top: 100px; text-align: center;}
.search h2 {font-weight: 700;}
.search input {height: 60px; text-align: center; font-size: 16px;}
.search .select {display: flex; justify-content: center; padding-right: 25px;}
.search .select li {padding: 15px;font-size: 18px;}
.search .select li:focus {background-color: #000; color: #fff;}
.search .regions {display: flex; flex-wrap: wrap; padding-left: 40px;}
.search .regions .region {box-shadow: 0 5px 5px -5px #333; margin: 30px;overflow: hidden; }
.search .regions .region img {  transition: all 0.3s linear;}
.search .regions .region img:hover {transform: scale(1.1);}
.search .regions .region button {background-color: #fff; color:#000; border-color: rgba(0,0,0,0.2)}
.body {display: flex;}
.body .bg {width: 30%;}
.body .desc {width: 65%; padding: 20px; margin-left: auto;}
.body .desc p {margin-top: 3px; font-size: 8px;}
.body .desc a {text-decoration: none; float: right ; margin-top: 10px;}
.card-img-top {width: 250px; height: 200px}
</style>