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
          <b-button v-b-toggle.sidebar-1 aria-controls="sidebar-1" aria-expanded="true">여행 계획표</b-button>
          <b-sidebar id="sidebar-1" :title="plan.title" shadow visible>
            <div class="px-3 py-2">
              <!--  -->
              <div class="added">
                <div class="profile" v-for="f in tripmembers" :key="f.id">
                  <b-avatar
                    v-b-tooltip.hover
                    :title="f.name + ' ' + f.id"
                    variant="info"
                    :src="f.image ? require(`C:/EnjoyTrip/user/${f.image}`) : require('../../assets/tmp_profile2.jpg')"
                  ></b-avatar>
                </div>
              </div>
              <div class="accordion" role="tablist">
                <b-card no-body class="mb-1" v-for="(cnt, idx) in dayCnt" :key="idx">
                  <b-card-header header-tag="header" class="p-1" role="tab">
                    <!-- <b-button block v-b-toggle.accordion-1 @click="displayOnMap(idx)">{{cnt}}일차 일정</b-button> -->
                    <b-button block @click="displayOnMap(idx)">{{ cnt }}일차 일정</b-button>
                    <!-- <span style="font-size:14px;"> 2002.03.12 ~ 2005.12.31</span> -->
                  </b-card-header>
                  <!-- <b-collapse id="accordion-1" accordion="my-accordion" role="tabpanel" style="display:none"> -->
                  <div :id="'d' + idx" accordion="my-accordion" role="tabpanel" style="display: none">
                    <b-card-body>
                      <b-card-text class="mb-3" v-for="(place, index) in places[idx]" :key="index">
                        <p>{{ index + 1 }}. {{ place.name }}</p>
                        <p>({{ place.address }})</p>
                        <a href="#" @click="showPopUp(place.url)"> 상세 </a>
                        <a href="#" @click="showComment(idx, index)"> 메모 </a>
                        <div
                          class="memo"
                          :id="'memo' + idx + '-' + index"
                          style="display: none; border: 1px solid #ebe5e5"
                        >
                          {{ place.comment }}
                          <a href="#" @click="hideComment(idx, index)">접기</a>
                        </div>
                        <div :id="'pl' + idx + '-' + index" style="color: orange; margin-top: 20px"></div>
                      </b-card-text>
                    </b-card-body>
                  </div>
                </b-card>
              </div>
            </div>
          </b-sidebar>
        </div>
        <div class="comment">
          <b-button v-b-toggle.sidebar-right aria-controls="sidebar-right" aria-expanded="true">상세 내용</b-button>
          <b-sidebar id="sidebar-right" title="상세 내용" right shadow>
            <div class="comment-list">
              <div v-for="(planComment, index) in planComments" :key="index" class="planComment">
                <div class="comment-user_id">{{ planComment.user_id }}님의 메모</div>
                <div class="comment-content">
                  {{ planComment.content }}
                </div>
              </div>
              <form class="input-form" @submit="sendComment">
                <input type="text" v-model="comment" />
                <img src="@/assets/send.png" class="send-button" />
              </form>
            </div>
          </b-sidebar>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from "vuex";

export default {
  name: "TripDetail",
  components: {},
  data() {
    return {
      map: null,
      infowindow: null,
      markers: [],
      tripmembers: [],
      plan: { title: "" },
      dayCnt: null,
      places: [],
      polyline: null,
      linePath: [],
      comment: "",
      planComments: [],
    };
  },
  created() {
    console.log(this.$route.params.plan_idx);
    http.get("/plan/detail/" + this.$route.params.plan_idx).then(({ data }) => {
      console.log(data);
      this.plan = data.plan;
      this.tripmembers = data.tripmember;

      const sdate = new Date(this.plan.start_date);
      console.log(sdate + 1);
      const edate = new Date(this.plan.end_date);
      this.dayCnt = (edate.getTime() - sdate.getTime()) / (1000 * 60 * 60 * 24) + 1;

      for (var i = 0; i < this.dayCnt; i++) {
        var arr = [];
        this.places.push(arr);
      }

      data.places.forEach((element) => {
        //date_index : 몇일째인지(1base) , index : 해당 일차 몇번째인지(1base)
        console.log(element);
        this.places[element.date_index - 1].push(element);
      });

      for (var j = 0; j < this.dayCnt; j++) {
        //정렬
        this.places[j].sort(function (a, b) {
          return a.index - b.index;
        });
      }
      this.getComments();
    });
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
      this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      this.polyline = new kakao.maps.Polyline({
        path: [], // 선을 구성하는 좌표배열 입니다
        strokeWeight: 5, // 선의 두께 입니다
        strokeColor: "red", // 선의 색깔입니다
        strokeOpacity: 0.6, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: "solid", // 선의 스타일입니다
      });
    },
    showPopUp(place_url) {
      console.log(place_url);
      //창 크기 지정
      var width = 900;
      var height = 500;

      //pc화면기준 가운데 정렬
      var left = window.screen.width / 2 - width / 2;
      var top = window.screen.height / 4;

      //윈도우 속성 지정
      var windowStatus =
        "width=" +
        width +
        ", height=" +
        height +
        ", left=" +
        left +
        ", top=" +
        top +
        ", scrollbars=no, toolbar=no, location=no, status=no, resizable=yes, titlebar=yes";

      //연결하고싶은url
      // const url = "https://www.naver.com/";
      const url = place_url;

      //등록된 url 및 window 속성 기준으로 팝업창을 연다.
      window.open(url, "hello popup", windowStatus);
    },
    showComment(idx, index) {
      var memo = document.querySelector("#memo" + idx + "-" + index);
      memo.style.display = "block";
    },
    hideComment(idx, index) {
      var memo = document.querySelector("#memo" + idx + "-" + index);
      memo.style.display = "none";
    },
    displayPlaces(places) {
      var bounds = new kakao.maps.LatLngBounds();
      // listStr = "";

      // 검색 결과 목록에 추가된 항목들을 제거합니다
      //this.removeAllChildNods(listEl);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].longitude, places[i].latitude);

        var marker = this.addMarker(placePosition, i, places[i].category_code);
        //var itemEl = this.getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
        // marker.place_name = places[i].place_name;
        console.log("marker: " + marker);
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);
        // console.log("dddddddddddd"+places[i].place_name)
        let div = this.$el.querySelector("#r" + i);
        console.log(div);
        // var div1 = document.getElementById('r'+i)
        // console.log("#r"+i);

        ((marker, title) => {
          kakao.maps.event.addListener(marker, "mouseover", () => {
            this.displayInfowindow(marker, title);
          });

          kakao.maps.event.addListener(marker, "mouseout", () => {
            this.infowindow.close();
          });

          // div.onmouseover = () => {
          //   this.displayInfowindow(marker, title);
          // };

          // div.onmouseout = () => {
          //   this.infowindow.close();
          // };
        })(marker, places[i].name);
      }
      this.map.setBounds(bounds);
    },
    addMarker(position, idx, code) {
      console.log("addMarker: " + code);
      // console.log("title " + title);
      var imageSrc, imageSize, markerImage, marker, imgOptions;
      // if(code === 'AT4') {
      //   imageSrc = require("C:/SSAFY/ssafy_pjt/final관통/enjoy_trip/FrontEnd/enjoy-trip/src/assets/img/관광지.png")
      //   imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
      //   markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
      //   marker = new kakao.maps.Marker({
      //     position: position, // 마커의 위치
      //     image: markerImage,
      //   });
      // }
      // else
      {
        (imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png"), // 마커 이미지 url, 스프라이트 이미지를 씁니다
          (imageSize = new kakao.maps.Size(36, 37)); // 마커 이미지의 크기
        (imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        }),
          (markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions)),
          (marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage,
          }));
      }

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.markers.push(marker); // 배열에 생성된 마커를 추가합니다
      console.log("addmarker: " + marker);
      return marker;
    },
    removeMarker() {
      for (var i = 0; i < this.markers.length; i++) {
        this.markers[i].setMap(null);
      }
      this.markers = [];
    },
    displayInfowindow(marker, title) {
      var content = '<div style="padding:5px;z-index:1;" class="infowindow">' + title + "</div>";

      this.infowindow.setContent(content);
      this.infowindow.open(this.map, marker);
    },
    displayOnMap(idx) {
      var div = document.querySelector("#d" + idx);
      console.log(div);
      if (div.style.display === "block") div.style.display = "none";
      else div.style.display = "block";

      for (var i = 0; i < this.dayCnt; i++) {
        if (i !== idx) {
          var no = document.querySelector("#d" + i);
          no.style.display = "none";
        }
      }
      if (this.places[idx].length >= 1) {
        this.displayPlaces(this.places[idx]);
      }

      // https://apis-navi.kakaomobility.com/v1/directions
      if (this.places[idx].length > 1) {
        let url = `https://apis-navi.kakaomobility.com/v1/waypoints/directions?`;
        //8e2e5c55f8a455204cc6d497c99b6c00  06c33ac07fc44b677e02f424b096640b
        let key = "8e2e5c55f8a455204cc6d497c99b6c00";

        //body 객체에 origin destination waypoints
        let body = {};
        body.origin = { x: this.places[idx][0].latitude, y: this.places[idx][0].longitude };
        body.destination = {
          x: this.places[idx][this.places[idx].length - 1].latitude,
          y: this.places[idx][this.places[idx].length - 1].longitude,
        };
        var waypoints = [];
        for (var j = 1; j < this.places[idx].length - 1; j++) {
          waypoints.push({ x: this.places[idx][j].latitude, y: this.places[idx][j].longitude });
        }
        body.waypoints = waypoints;

        fetch(url, {
          method: "post",
          headers: {
            "Content-Type": "	application/json;",
            Authorization: "KakaoAK " + key,
          },
          body: JSON.stringify(body),
        })
          .then((response) => response.json())
          .then((data) => {
            console.log(data.routes[0].result_code);

            //길찾기가 성공했을 때
            this.linePath = [];
            if (data.routes[0].result_code === 0) {
              let count = 1;
              data.routes[0].sections.forEach((element) => {
                let { distance, duration } = element;
                // console.log(distance, duration, guides)

                console.log(
                  this.places[idx][count - 1].name +
                    "에서 " +
                    this.places[idx][count].name +
                    "까지 거리: " +
                    Math.ceil(distance / 1000) +
                    "km 시간: " +
                    Math.ceil(duration / 60) +
                    "분"
                );
                var sel = document.querySelector("#pl" + idx + "-" + (count - 1));
                sel.innerHTML = "<p>" +
                  this.places[idx][count - 1].name +
                  " -> " +
                  this.places[idx][count].name + "</p>"+
                  " 거리: " +
                  Math.ceil(distance / 1000) +
                  "km 시간: " +
                  Math.ceil(duration / 60) +
                  "분";

                count = count + 1;

                element.roads.forEach((guide) => {
                  var limit = 0;
                  // 10개 5좌표
                  while (guide.vertexes.length > limit) {
                    var placePosition = new kakao.maps.LatLng(guide.vertexes[limit + 1], guide.vertexes[limit]);
                    this.linePath.push(placePosition);
                    limit = limit + 2;
                  }
                });

                //  console.log("linepath: "+this.linePath)
                // 지도에 표시할 선을 생성합니다
                this.polyline.setPath(this.linePath);
                // 지도에 선을 표시합니다
                this.polyline.setMap(this.map);
              });
            } else {
              alert("길찾기 에러 발생");
            }
          });
      }
      //
    },
    sendComment(event) {
      event.preventDefault();
      console.log(this.plan.idx);
      console.log(this.userInfo.id);
      console.log(this.comment);
      http
        .post("/plan/comment/insert", {
          user_id: this.userInfo.id,
          plan_idx: this.plan.idx,
          content: this.comment,
        })
        .then(({ data }) => {
          console.log(data);
          this.comment = "";
          this.getComments();
        })
        .catch(() => {
          console.log("실패");
        });
    },
    getComments() {
      http
        .get(`/plan/comment/${this.plan.idx}`)
        .then(({ data }) => {
          console.log(data);
          this.planComments = data;
        })
        .catch(() => {
          console.log("실패");
        });
    },
  },
  computed: {
    ...mapState(["userInfo"]),
  },
};
</script>

<style scoped>
.container {
  height: 100vh;
}
.detail {
  position: relative;
}

.detail .tog {
  position: absolute;
  z-index: 9999;
  top: 15px;
  left: 20px;
}
.detail .tog p {
  margin: 2px 0;
}

.detail .comment {
  position: absolute;
  z-index: 9999;
  top: 15px;
  right: 20px;
}

.added {
  display: flex;
  margin-bottom: 15px;
  align-items: center;
  justify-content: center;
}
.added img {
  width: 20px;
  height: 20px;
  border-radius: 50%;
}
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

.input-form {
  position: absolute;
  padding: 10px;
  bottom: 0px;
  width: 90%;
  display: flex;
}

.input-form input {
  width: 100%;
  border: none;
  border-bottom: 1px solid black;
  outline: none;
  font-weight: 400;
  font-size: 14px;
  color: #262626;
  background: none;
}

.input-form .send-button {
  margin-left: 15px;
  width: 30px;
}
.comment-list {
  overflow-y: scroll;
}
.comment-list::-webkit-scrollbar {
  display: none;
}

.planComment {
  border: 1px solid lightgray;
  padding: 5px;
  border-radius: 5px;
  margin: 5px;
}

.comment-content {
  font-size: 15px;
}
</style>
