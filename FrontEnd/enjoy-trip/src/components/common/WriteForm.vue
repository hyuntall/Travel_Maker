<template>
  <div class="wrap">
    <div class="form">
      <h2>Sign Up</h2>
      <div class="form_input user_id">
        <label for="id" class="label" >ID</label>
        <input type="text" v-model="user.id" name="id" id="id" placeholder="id" v-bind:readonly="readonly"/>
      </div>
      <div class="form_input user_pw">
        <label for="password" class="label">Password</label>
        <input type="password" v-model="user.password" name="password" id="password" placeholder="Password" />
      </div>
      <div class="form_input user_name">
        <label for="name" class="label">Name</label>
        <input type="text" v-model="user.name" name="password" id="name" placeholder="name" />
      </div>
      <div class="form_input phone_number">
        <label for="phone_number" class="label">Phone Number</label>
        <input
          type="text"
          v-model="user.phone_number"
          name="phone_number"
          id="phone_number"
          placeholder="phone_number"
        />
      </div>
      <div class="form_input age">
        <label for="name" class="label">Age</label>
        <input type="number" v-model="user.age" name="password" id="age" placeholder="age" />
      </div>
      <div class="form_input gender">
        <label for="name" class="label">gender</label>
        <select>
          <option seleted value="true">남</option>
          <option value="false">여</option>
        </select>
      </div>
      <div class="submit row" v-if="type!=='modify'">
        <button class="col-5" variant="outline-success" @click="checkValue">SignUp</button>
        <button class="col-5" variant="outline-success">LogIn</button>
      </div>
      <div class="submit row" v-else>
        <button variant="outline-success" @click="checkValue">Modify</button>
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common";
import { mapState } from "vuex";

export default {
  name: "WriteForm",
  props: {
    type: String,
  },
  data() {
    return {
      user: {
        id: "",
        name: "",
        password: "",
        phone_number: "",
        gender: "M",
        age: 0,
      },
      readonly: false,
    };
  },
  created() {
    console.log(this.type)
    if (this.type === "modify") {
      // url에서 파라미터정보 얻기.
      console.log(this.userInfo)
      this.user.id = this.userInfo.id;
      this.user.name = this.userInfo.name;
      this.user.password = this.userInfo.password;
      this.user.phone_number = this.userInfo.phone_number;
      this.user.gender = this.userInfo.gender;
      this.user.age = this.userInfo.age;
      this.readonly = true;
    }
  },
  methods: {
    checkValue() {
      // 사용자 입력값 체크하기
      // isbn, 제목, 저자, 가격, 설명이 없을 경우 각 항목에 맞는 메세지를 출력
      let err = true;
      let msg = "";
      !this.user.id && ((msg = "ID를 입력해주세요"), (err = false));
      err && !this.user.name && ((msg = "이름을 입력해주세요"), (err = false));
      err && !this.user.password && ((msg = "패스워드를 입력해주세요"), (err = false));
      err && !this.user.phone_number && ((msg = "휴대폰 번호를 입력해주세요"), (err = false));
      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 modifyBook 호출
      else this.type === "modify" ? this.modifyUser() : this.registUser();
    },
    registUser() {
      http
        .post("/user/regist", this.user)
        .then(({ data }) => {
          alert(data);
          this.$router.push({ name: "home" });
        })
        .catch((data) => {
          alert("error! " + data.response.data);
        });
    },
    modifyUser() {
      //Todo
    },
  },
  computed: {
    ...mapState(["userInfo"]),
  }
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Noto Sans KR", sans-serif;
}

a {
  text-decoration: none;
  color: black;
}

li {
  list-style: none;
}

.wrap {
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.1);
}

.form {
  width: 40%;
  min-width: 600px;
  height: 750px;
  background: white;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

h2 {
  color: black;
  font-size: em;
}

.form_input {
  margin-top: 20px;
  width: 80%;
}

.form_input label {
  float: left;
}

.form_input input {
  width: 100%;
  height: 35px;
  border-radius: 5px;
  margin-top: 10px;
  padding: 0px 20px;
  border: 1px solid lightgray;
  outline: none;
}

.form_input select {
  width: 100%;
  height: 35px;
  border-radius: 5px;
  margin-top: 10px;
  padding: 0px 20px;
  border: 1px solid lightgray;
  outline: none;
}

.submit {
  margin-top: 50px;
  width: 80%;
}
.submit button {
  height: 50px;
  border: 0;
  outline: none;
  border-radius: 10px;
  background: skyblue;
  color: white;
  font-size: 1.2em;
  letter-spacing: 2px;
  margin: 4.125%;
}
</style>
