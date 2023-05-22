<template>
  <div>
    <div class="plan">
      <div class="planner">
        <h4>{{ region.cate2 }} 여행</h4>
        <!-- 친구추가기능 -->
        <div class="friends">
          <span style="color: red">친구 추가 기능이 들어갈 예정입니다.</span>
        </div>
        <div class="cal">
          <label for="example-datepicker">출발날짜</label>
          <b-form-datepicker id="example-datepicker" v-model="start" class="mb-2" size="sm"></b-form-datepicker>
          <label for="example-datepicker2">도착날짜</label>
          <b-form-datepicker id="example-datepicker2" v-model="end" class="mb-2" size="sm"></b-form-datepicker>
          <a href="#" @click="makedays">일정 생성</a>
        </div>
        <!-- days -->
        <div class="days" v-if="cnt">
          <div class="cnt">
            <p>{{ start }} ~ {{ end }}</p>
            <p>{{ cnt }}일 일정</p>
          </div>
          <div class="plandays" v-for="idx in cnt" :key="idx">
            <h4>{{idx}}일차</h4>
            <hr>
            
            <draggable v-model="days[idx-1]" class="list-group places" :list="days[idx-1]" group="places">
              <div class="list-group-item" v-for="p in days[idx-1]" :key="p">
                {{p.place_name}}
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
        <h1>목록</h1>
        <ul id="placesList">
            <draggable v-model="results" class="list-group places" :list="results" :group="{ name: 'places', pull: 'clone', put: false }">
              <div class="list-group-item" :id="'r'+index" v-for="place, index in results" :key="index">
                    {{place.place_name}}
              </div>
            </draggable>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import draggable from "vuedraggable";
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
      region : null,
      cnt : null,
      results : [],
      days: [],
    };
  },
  created() {
    console.log("created!! " + this.$route.params.cate2);
    this.region = this.$route.params;
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
        });
    });
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
          data.documents.forEach(element => {
            this.results.push(element)
          });
          this.placesSearchCB(this.results);
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
        // var div = document.querySelector("#r"+i);
        // var div1 = document.getElementById('r'+i)
        // console.log(div1);
        
        ((marker, title) => {
          kakao.maps.event.addListener(marker, "mouseover", () => {
            this.displayInfowindow(marker, title);
          });

          kakao.maps.event.addListener(marker, "mouseout", () => {
            this.infowindow.close();
          });

          // div1.onmouseover = () => {
          //   this.displayInfowindow(marker, title);
          // };

          // div1.onmouseout = () => {
          //   this.infowindow.close();
          // };
        })(marker, places[i].place_name);

        //fragment.appendChild(itemEl);
      }

      // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
      //listEl.appendChild(fragment);
      //menuEl.scrollTop = 0;

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      this.map.setBounds(bounds);
    },
    getListItem(index, places) {
      var el = document.createElement("li"),
        itemStr =
          '<span class="markerbg marker_' +
          (index + 1) +
          '"></span>' +
          '<div class="info">' +
          "   <h5><a href='" +
          places.place_url +
          "' target='_blank'>" +
          places.place_name +
          "</a>" +
          "</h5>";

      if (places.road_address_name) {
        itemStr +=
          "    <span>" +
          places.road_address_name +
          "</span>" +
          '   <span class="jibun gray">' +
          places.address_name +
          "</span>";
      } else {
        itemStr += "    <span>" + places.address_name + "</span>";
      }

      itemStr += '  <span class="tel">' + places.phone + "</span>" + "</div>";

      el.innerHTML = itemStr;
      el.className = "item";

      return el;
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
      var content = '<div style="padding:5px;z-index:1;">' + title + "</div>";

      this.infowindow.setContent(content);
      this.infowindow.open(this.map, marker);
    },
    removeAllChildNods(el) {
      while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
      }
    },
    makedays() {
      if (this.start === null || this.end === null) {
        alert("날짜를 선택해주세요!");
      } else {
        this.days = [];
        const date1 = new Date(this.start)
        const date2 = new Date(this.end)
        this.cnt = (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24) +1 
        for(var i=1 ; i<=this.cnt ; i++){
          console.log(i)
          let arr = []
          this.days.push(arr)
        }
      }
    },
  },
};
</script>

<style scoped>
.plan {
  padding-top: 80px;
  display: flex;
  height: 100vh;
}

.plan .planner {
  width: 16%;
  /* background-color: blue; */
  text-align: center;
}

.plan .planner .friends {
  margin-top: 15px;
}
/* .plan .planner .days {overflow-y: scroll} */
.plan .planner .days .plandays {
  margin: 10px auto;
  border: 1px solid black;
  width: 95%;
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

.plan .map {
  width: 69%;
}

.plan .result {
  width: 15%;
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
</style>
