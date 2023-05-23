<template>
    <div class="container">
      <div class="row">
        <div class="detail">
         

          <div class="map">
            <div class="map_wrap">
              <div id="map" style="width: 100%; height: 95vh; position: relative; overflow: hidden"></div>              
            </div>
          </div>

          <div class="tog">
            <b-button v-b-toggle.sidebar-1>여행 계획표</b-button>
            <b-sidebar id="sidebar-1" title="여행 계획" shadow>
              <div class="px-3 py-2">

                <div class="accordion" role="tablist">
                  <b-card no-body class="mb-1">
                    <b-card-header header-tag="header" class="p-1" role="tab">
                      <b-button block v-b-toggle.accordion-1 >1일차 일정</b-button>
                      <span style="font-size:14px;"> 2002.03.12 ~ 2005.12.31</span>
                    </b-card-header>
                    <b-collapse id="accordion-1" visible accordion="my-accordion" role="tabpanel">
                      <b-card-body>
                        <b-card-text>I start opened because <code>visible</code> is <code>true</code></b-card-text>
                        <b-card-text>{{ text }}</b-card-text>
                      </b-card-body>
                    </b-collapse>
                  </b-card>

                  <b-card no-body class="mb-1">
                    <b-card-header header-tag="header" class="p-1" role="tab">
                      <b-button block v-b-toggle.accordion-2>2일차 일정</b-button>
                      <span style="font-size:14px;"> 2002.03.12 ~ 2005.12.31</span>
                    </b-card-header>
                    <b-collapse id="accordion-2" accordion="my-accordion" role="tabpanel">
                      <b-card-body>
                        <b-card-text>{{ text }}</b-card-text>
                      </b-card-body>
                    </b-collapse>
                  </b-card>

                  <b-card no-body class="mb-1">
                    <b-card-header header-tag="header" class="p-1" role="tab">
                      <b-button block v-b-toggle.accordion-3 >3일차 일정</b-button>
                      <span style="font-size:14px;"> 2002.03.12 ~ 2005.12.31</span>
                    </b-card-header>
                    <b-collapse id="accordion-3" accordion="my-accordion" role="tabpanel">
                      <b-card-body>
                        <b-card-text>{{ text }}</b-card-text>
                      </b-card-body>
                    </b-collapse>
                  </b-card>
                </div>
              </div>
            </b-sidebar>
          </div>

        </div>
      </div>
    </div>
</template>

<script>
import http from "@/util/http-common";

export default {
  name: 'TripDetail',
  components: {},
  data() {
    return {
      map: null,
      text: `
          Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry
          richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor
          brunch. Food truck quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon
          tempor, sunt aliqua put a bird on it squid single-origin coffee nulla
          assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore
          wes anderson cred nesciunt sapiente ea proident. Ad vegan excepteur butcher
          vice lomo. Leggings occaecat craft beer farm-to-table, raw denim aesthetic
          synth nesciunt you probably haven't heard of them accusamus labore VHS.
        `,
      tripmembers: [],
      plan : null,
      dayCnt: null,
      places : [],
    };
  },
  created() {
    console.log(this.$route.params.plan_idx)
    http.get("/plan/detail/"+this.$route.params.plan_idx).then(({data})=>{
      console.log(data)
      this.plan = data.plan
      this.tripmembers = data.tripmember

      const sdate = new Date(this.plan.start_date)
      const edate = new Date(this.plan.end_date)
      this.dayCnt = (edate.getTime() - sdate.getTime()) / (1000 * 60 * 60 * 24) + 1;
      
      for(var i=0; i<this.dayCnt; i++) {
        var arr = []
        this.places.push(arr)
      }

      data.places.forEach(element => {
        //date_index : 몇일째인지(1base) , index : 해당 일차 몇번째인지(1base) 
        console.log(element)
        this.places[element.date_index-1].push(element)
      });

      for(var j=0; j<this.dayCnt; j++) {
        //정렬 
        this.places[j].sort(function(a,b){
          return a.index - b.index
        })
      }
    })
  },
   mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=915cffed372954b7b44804ed422b9cf0";
      document.head.appendChild(script);
    }    
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 6, // 지도의 확대 레벨
      };
      this.map = new kakao.maps.Map(container, options);
      
    },
  },
};
</script>

<style scoped>
.container {height:100vh}
.detail {margin-top: 80px; position: relative;}

.detail .tog {position: absolute; z-index: 9999; top:0}


/* kakao */
.map_wrap,
.map_wrap * {
  /* margin: 10px; */
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}

.map_wrap a,
.map_wrap a:hover,
.map_wrap a:active {
  color: #000;
  text-decoration: none;
}

.map_wrap {
  position: relative;
  width: 100%;
  height: 150px;
}




</style>