<template>
  <div class="main">
    <div class="plan">
      <div class="planner">
        <h4 style="margin-top: 10px">{{ region.cate2 }}여행</h4>
        <!-- 친구추가기능 -->
        <div class="friends">
          <div class="added">
            <div class="profile" v-for="f in friends" :key="f.id">
              <b-avatar
                variant="info"
                :src="f.image ? require(`C:/EnjoyTrip/user/${f.image}`) : require('../../assets/tmp_profile2.jpg')"
              ></b-avatar>
            </div>
          </div>
          <input type="text" @keyup="flist" placeholder="친구 검색" style="text-align: center; border-radius: 5px" />
          <div id="userlist" v-for="(friend, index) in friendList" :key="index">
            <div>
              <b-avatar
                variant="info"
                :src="
                  friend.image ? require(`C:/EnjoyTrip/user/${friend.image}`) : require('../../assets/tmp_profile2.jpg')
                "
              ></b-avatar
              >{{ friend.name }} ({{ friend.id }}) <a href="#" @click="addF(index)">추가</a>
            </div>
          </div>
        </div>
        <div class="cal">
          <label for="example-datepicker">출발날짜</label>
          <b-form-datepicker
            id="example-datepicker"
            v-model="start"
            class="mb-2"
            size="sm"
            @input="makedays"
          ></b-form-datepicker>
          <label for="example-datepicker2">도착날짜</label>
          <b-form-datepicker
            id="example-datepicker2"
            v-model="end"
            class="mb-2"
            size="sm"
            @input="makedays"
          ></b-form-datepicker>
        </div>
        <!-- days -->
        <div class="days" v-if="cnt">
          <div class="cnt">
            <p>{{ start }}</p>
            <p>~</p>
            <p>
              {{ end }}
            </p>
            <p>{{ cnt }}일 일정</p>
            <a href="#" @click="makePlan">일정 생성</a>
          </div>
          <div class="plandays" v-for="idx in cnt" :key="idx">
            <h4>{{ idx }}일차 <a href="#" @click="setline(idx)">경로보기</a></h4>
            <hr />

            <draggable v-model="days[idx - 1]" class="list-group places" :list="days[idx - 1]" group="places">
              <div class="list-group-item" v-for="(p, index) in days[idx - 1]" :key="index">
                {{ p.place_name }} <a href="#" @click="memo(idx, index)">메모</a>
                <a href="#" @click="removeP(idx, index)">제거</a>
                <div :id="'memo' + idx + '-' + index" style="display: none; border: 1px solid #ebe5e5">
                  <div :id="'content' + idx + '-' + index" contenteditable="true">
                    {{ p.comment }}
                  </div>
                  <a href="#" @click="memoSave(idx, index)" @keyup.13="memoSave(idx, index)">완료</a>
                </div>
              </div>
            </draggable>
          </div>
        </div>
      </div>

      <div class="map">
        <div class="map_wrap">
          <div id="map" style="width: 100%; height: 95vh; position: relative; overflow: hidden"></div>
          <div id="menu_wrap" class="bg_white">
            <div class="option">
              <div>
                여행 지역 <input type="text" value="" id="keyword" size="10" @keyup.13="moveMap()" />

                <div style="margin-top: 15px">
                  <button @click="categorySearch('AT4')">관광지</button>
                  <button @click="categorySearch('AD5')">숙소</button>
                  <button @click="categorySearch('FD6')">식당</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="result">
        <h3 style="margin-top: 10px">여행지 목록</h3>
        <ul id="placesList">
          <draggable
            v-model="results"
            class="list-group places"
            :list="results"
            :group="{ name: 'places', pull: 'clone', put: false }"
          >
            <div class="list-group-item" :id="'r' + index" v-for="(place, index) in results" :key="index">
              <h6>{{ place.place_name }}</h6>
              <p>{{ place.address_name }}</p>
              <!-- <a :href="place.place_url" target="_blank">상세 보기</a> -->
              <a @click="showPopUp(place.place_url)">상세 보기</a>
            </div>
          </draggable>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import draggable from "vuedraggable";
import { mapState } from "vuex";
import swal from "sweetalert";

export default {
  name: "TripMake",
  components: {
    draggable,
  },
  data() {
    return {
      message: "",
      map: null,
      ps: null,
      infowindow: null,
      markers: [],
      start: null,
      end: null,
      region: null,
      cnt: null,
      results: [],
      days: [],
      friendList: [],
      friends: [],
    };
  },
  computed: {
    ...mapState(["userInfo"]),
  },
  created() {
    console.log("created!! " + this.$route.params.cate1);
    this.region = this.$route.params;
  },
  updated() {
    if (this.results.length > 0) this.placesSearchCB(this.results);
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
    console.log(this.$route.params);
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

      var url = `https://dapi.kakao.com/v2/local/search/address.json?query=${this.$route.params.cate1} ${this.$route.params.cate2}`;
      var key = "KakaoAK 8e2e5c55f8a455204cc6d497c99b6c00";
      console.log(url);
      window.kakao.maps.load(() => {
        fetch(url, {
          headers: {
            Authorization: key,
          },
        })
          .then((response) => response.json())
          .then((data) => {
            console.log(data.documents);
            console.log(data.documents[0].y);
            var y = data.documents[0].y;
            var x = data.documents[0].x;
            this.map.setCenter(new kakao.maps.LatLng(y, x));
          })
          .then(() => {
            this.categorySearch("AT4");
          });
      });
    },

    async moveMap() {
      console.log("함수실행");
      var keyword = document.getElementById("keyword").value;

      if (!keyword.replace(/^\s+|\s+$/g, "")) {
        alert("키워드를 입력해주세요!");
        return false;
      }
      //fetch
      var url = `https://dapi.kakao.com/v2/local/search/address.json?query=${keyword}`;
      var key = "KakaoAK 8e2e5c55f8a455204cc6d497c99b6c00";
      console.log(url);
      await fetch(url, {
        headers: {
          Authorization: key,
        },
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data.documents);
          console.log(data.documents[0].y);
          var y = data.documents[0].y;
          var x = data.documents[0].x;
          this.map.setCenter(new kakao.maps.LatLng(y, x));
        });
    },
    categorySearch(category) {
      //지도 중심 좌표, category fetch
      var position = this.map.getCenter();
      console.log(position);

      let url = `https://dapi.kakao.com/v2/local/search/category.json?category_group_code=${category}&x=${position.La}&y=${position.Ma}&radius=20000`;
      let key = "06c33ac07fc44b677e02f424b096640b";
      fetch(url, {
        headers: {
          "Content-Type": "application/json",
          Authorization: "KakaoAK " + key,
        },
      })
        .then((response) => response.json())
        .then((data) => {
          console.log(data);
          this.results = [];
          data.documents.forEach((element) => {
            // this.results.push(element)
            this.results.push({
              address_name: element.address_name,
              category_group_code: element.category_group_code,
              category_group_name: element.category_group_name,
              category_name: element.category_name,
              distance: element.distance,
              id: element.id,
              phone: element.phone,
              place_name: element.place_name,
              place_url: element.place_url,
              road_address_name: element.road_address_name,
              x: element.x,
              y: element.y,
              comment: "",
            });
          });
          // this.placesSearchCB(this.results);
        });
    },

    placesSearchCB(data) {
      this.displayPlaces(data);
    },
    displayPlaces(places) {
      //var listEl = document.getElementById("placesList"),
      //menuEl = document.getElementById("menu_wrap"),
      //fragment = document.createDocumentFragment(),
      var bounds = new kakao.maps.LatLngBounds();
      // listStr = "";

      // 검색 결과 목록에 추가된 항목들을 제거합니다
      //this.removeAllChildNods(listEl);

      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();

      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
        var marker = this.addMarker(placePosition, i);
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

          div.onmouseover = () => {
            this.displayInfowindow(marker, title);
          };

          div.onmouseout = () => {
            this.infowindow.close();
          };
        })(marker, places[i].place_name);
      }
      this.map.setBounds(bounds);
    },
    addMarker(position, idx, title) {
      console.log("title " + title);
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

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
    makedays() {
      if (this.start !== null && this.end !== null) {
        if (this.start >= this.end) {
          alert("올바르지 않은 날짜입니다.");
          return;
        }
        this.days = [];
        const date1 = new Date(this.start);
        const date2 = new Date(this.end);
        this.cnt = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24) + 1;
        for (var i = 1; i <= this.cnt; i++) {
          console.log(i);
          let arr = [];
          this.days.push(arr);
        }
      }
    },
    flist(event) {
      console.log(event.target.value);
      this.friendList = [];
      if (event.target.value) {
        http.get("/user/friend/" + this.userInfo.id + "/" + event.target.value).then(({ data }) => {
          data.forEach((element) => {
            this.friendList.push(element);
          });
        });
      }
    },
    addF(index) {
      this.friends.push(this.friendList[index]);
      this.friendList.splice(index, 1);
      console.log(this.friends);
    },
    removeP(idx, index) {
      console.log(idx + " " + index);
      this.days[idx - 1].splice(index, 1);
    },
    makePlan() {
      let users = [this.userInfo.id];
      for (i = 0; i < this.friends.length; i++) {
        users.push(this.friends[i].id);
      }
      const plan = {
        user_id: users,
        title: this.region.cate2 + " 여행",
        start_date: new Date(this.start),
        end_date: new Date(this.end),
        content: "없는 내용",
        places: [],
      };
      for (var i = 0; i < this.days.length; i++) {
        for (var j = 0; j < this.days[i].length; j++) {
          console.log(this.days[i][j]);
          const place = this.days[i][j];
          plan.places.push({
            name: place.place_name,
            url: place.place_url,
            latitude: place.x,
            longitude: place.y,
            address: place.address_name,
            category_code: place.category_group_code,
            date_index: i + 1,
            index: j + 1,
            comment: place.comment,
          });
        }
      }
      http
        .post("/plan/insert", plan)
        .then(({ data }) => {
          console.log(data);
          swal({
            title: "Success",
            text: "여행 계획이 등록되었습니다.",
            icon: "success",
          });
          this.$router.push("/");
        })
        .catch(() => {
          swal({
            title: "Error",
            text: "잘못된 계획 정보입니다.",
            icon: "error",
          });
        });
      console.log(plan);
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
    memo(idx, index) {
      console.log(this.days[idx - 1][index]);
      var memo = document.querySelector("#memo" + idx + "-" + index);
      console.log(memo);
      memo.style.display = "block";
    },
    memoSave(idx, index) {
      var content = document.querySelector("#content" + idx + "-" + index);
      console.log(content.textContent);
      this.days[idx - 1][index].comment = content.textContent;
      var memo = document.querySelector("#memo" + idx + "-" + index);
      memo.style.display = "none";
    },
    setline(idx) {
      this.displayPlaces(this.days[idx - 1]);
      // 127.046048046817 37.5261262382238
      // TripMake.vue:459 127.042202512988 37.5276413582416
      console.log("setline idx: " + idx);
      var linePath = [];
      this.days[idx - 1].forEach((element) => {
        var x = element.x;
        var y = element.y;
        console.log(x + " " + y);
        linePath.push(new kakao.maps.LatLng(x, y));
      });
      console.log(linePath);
      // 지도에 표시할 선을 생성합니다
      var polyline = new kakao.maps.Polyline({
        path: linePath, // 선을 구성하는 좌표배열 입니다
        strokeWeight: 5, // 선의 두께 입니다
        strokeColor: "red", // 선의 색깔입니다
        strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: "solid", // 선의 스타일입니다
      });
      // 지도에 선을 표시합니다
      polyline.setMap(this.map);
    },
  },
};
</script>

<style scoped>
.main {
  height: 100vh;
}

.plan {
  padding-top: 80px;
  display: flex;
  height: 100vh;
}

.plan .planner {
  width: 18%;
  height: 100%;
  /* background-color: blue; */
  text-align: center;
  overflow-y: scroll;
}

.plan .planner .friends {
  margin-top: 15px;
}
.plan .planner .friends .added {
  display: flex;
  margin-bottom: 5px;
}
.plan .planner .friends .added img {
  width: 20px;
  height: 20px;
  border-radius: 50%;
}

.plan .planner .days {
  height: 67.8%;
  /* overflow-y: scroll;
  scrollbar-width: 0px; */
}

/* .plan .planner .days {overflow-y: scroll} */
.plan .planner .days .plandays {
  margin: 10px auto;
  border: 1px solid black;
  width: 95%;
}

.plan .planner .days .plandays a {
  font-size: 14px;
}
.plan .planner .days .plandays .places {
  min-height: 60px;
}

.plan .planner .cal {
  width: 95%;
  margin: 10px auto;
}
.plan .planner .cal label {
  font-size: 12px;
}
.plan .planner .cal input {
}

.plan .planner .days {
  margin-top: 15px;
}

.plan .planner .days .plandays {
  border: 1px solid #ebe5e5;
  border-radius: 15px;
}
.plan .planner .days .plandays h4 {
  margin: 5px 0 auto;
}
.plan .planner .days .plandays hr {
  margin: 0;
}
.plan .planner .days .cnt p {
  margin-bottom: 2px;
}

.plan .map {
  width: 64%;
}

.plan .result {
  width: 18%;
  text-align: center;
  /* height: 100vh; */
  overflow-y: scroll;
}

.plan .result #placesList .list-group-item {
}
.plan .result #placesList .list-group-item p {
  font-size: 8px;
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

#menu_wrap {
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  width: 200px;
  margin: 10px 0 30px 10px;
  padding: 5px;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.7);
  z-index: 1;
  font-size: 12px;
  border-radius: 10px;
  height: 70px;
}

.bg_white {
  background: #fff;
}

#menu_wrap hr {
  display: block;
  height: 1px;
  border: 0;
  border-top: 2px solid #5f5f5f;
  margin: 3px 0;
}

#menu_wrap .option {
  text-align: center;
}

#menu_wrap .option p {
  margin: 10px 0;
}

#menu_wrap .option button {
  margin-left: 5px;
}

#placesList {
  padding: 20px;
}

#placesList li {
  list-style: none;
}

#placesList .item {
  position: relative;
  border-bottom: 1px solid #888;
  overflow: hidden;
  cursor: pointer;
  min-height: 65px;
}

#placesList .item span {
  display: block;
  margin-top: 4px;
}

#placesList .item h5,
#placesList .item .info {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

#placesList .item .info {
  padding: 10px 0 10px 55px;
}

#placesList .info .gray {
  color: #8a8a8a;
}

#placesList .info .jibun {
  padding-left: 26px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_jibun.png) no-repeat;
}

#placesList .info .tel {
  color: #009900;
}

#placesList .item .markerbg {
  float: left;
  position: absolute;
  width: 36px;
  height: 37px;
  margin: 10px 0 0 10px;
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png) no-repeat;
}

#placesList .item .marker_1 {
  background-position: 0 -10px;
}

#placesList .item .marker_2 {
  background-position: 0 -56px;
}

#placesList .item .marker_3 {
  background-position: 0 -102px;
}

#placesList .item .marker_4 {
  background-position: 0 -148px;
}

#placesList .item .marker_5 {
  background-position: 0 -194px;
}

#placesList .item .marker_6 {
  background-position: 0 -240px;
}

#placesList .item .marker_7 {
  background-position: 0 -286px;
}

#placesList .item .marker_8 {
  background-position: 0 -332px;
}

#placesList .item .marker_9 {
  background-position: 0 -378px;
}

#placesList .item .marker_10 {
  background-position: 0 -423px;
}

#placesList .item .marker_11 {
  background-position: 0 -470px;
}

#placesList .item .marker_12 {
  background-position: 0 -516px;
}

#placesList .item .marker_13 {
  background-position: 0 -562px;
}

#placesList .item .marker_14 {
  background-position: 0 -608px;
}

#placesList .item .marker_15 {
  background-position: 0 -654px;
}

#pagination {
  margin: 10px auto;
  text-align: center;
}

#pagination a {
  display: inline-block;
  margin-right: 10px;
}

#pagination .on {
  font-weight: bold;
  cursor: default;
  color: #777;
}

.infowindow {
  background-color: red;
}
</style>
