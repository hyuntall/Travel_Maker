<template>
  <div>
    <div class="container">
      <div class="row">
        <div class="plan">
          <div class="planner"></div>
          <div class="map">
            <div class="map_wrap">
              <div id="map" style="width:96%;height:280px;position:relative;overflow:hidden;"></div>
              <div id="menu_wrap" class="bg_white">
                  <div class="option">
                      <div>
                          <form onsubmit="moveMap(); return false;">
                              여행 지역 <input type="text" value="경기도" id="keyword" size="10"> 
                              <button type="submit" style="display:none">검색</button>
                              <button onclick="categorySearch('AT4'); return false;">관광지</button>
                              <button onclick="categorySearch('AD5'); return false;">숙소</button>
                              <button onclick="categorySearch('FD6'); return false;">식당</button>
                          </form>
                      </div>
                  </div>                  
              </div>

            </div>
          </div>
          <div class="result"></div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'TripMake',
  components: {},
  data() {
    return {
      message: '',
    };
  },
  created() {},
  methods: {},
};
</script>

<style scoped></style>