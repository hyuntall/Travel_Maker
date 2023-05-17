<template>
  <div class="wrap">
    <div class="form">
      <h2>Sign Up</h2>
      <div class="form_input user_id">
        <label for="id" class="label">ID</label>
        <input type="text" v-model="id" name="id" id="id" placeholder="id" />
      </div>
      <div class="form_input user_pw">
        <label for="password" class="label">Password</label>
        <input type="password" v-model="password" name="password" id="password" placeholder="Password" />
      </div>
      <div class="form_input user_name">
        <label for="name" class="label">Name</label>
        <input type="text" v-model="name" name="password" id="name" placeholder="name" />
      </div>
      <div class="form_input phone_number">
        <label for="phone_number" class="label">Phone Number</label>
        <input type="text" v-model="phone_number" name="phone_number" id="phone_number" placeholder="phone_number" />
      </div>
      <div class="form_input age">
        <label for="name" class="label">Age</label>
        <input type="number" v-model="age" name="password" id="age" placeholder="age" />
      </div>
      <div class="form_input gender">
        <label for="name" class="label">gender</label>
        <select>
          <option seleted value="true">남</option>
          <option value="false">여</option>
        </select>
      </div>
      <div class="submit row">
        <button class="col-5" variant="outline-success">SignUp</button>
        <button class="col-5" variant="outline-success">LogIn</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "WriteForm",
  props: {
    type: String,
  },
  data() {
    return {
      id: "",
      name: "",
      password: "",
      phone_number: "",
      gender: 0,
      age: 0,
    };
  },
  created() {
    if (this.type === "modify") {
      // url에서 파라미터정보 얻기.
      // http.get(`/user/${this.$route.params.user_id}`).then(({ data }) => {
      //   this.id = data.user_id;
      //   this.name = data.name;
      //   this.password = data.password;
      //   this.phone_number = data.phone_number;
      //   this.gender = data.gender;
      //   this.age = data.age;
      // });
    }
  },
  methods: {
    checkValue() {
      // 사용자 입력값 체크하기
      // isbn, 제목, 저자, 가격, 설명이 없을 경우 각 항목에 맞는 메세지를 출력
      let err = true;
      let msg = "";
      !this.id && ((msg = "ID를 입력해주세요"), (err = false), this.$refs.id.focus());
      err && !this.name && ((msg = "이름을 입력해주세요"), (err = false), this.$refs.name.focus());
      err && !this.password && ((msg = "패스워드를 입력해주세요"), (err = false), this.$refs.password.focus());
      err &&
        !this.phone_number &&
        ((msg = "휴대폰 번호를 입력해주세요"), (err = false), this.$refs.phone_number.focus());
      if (!err) alert(msg);
      // 만약, 내용이 다 입력되어 있다면 modifyBook 호출
      else this.type === "regist" ? this.registBook() : this.modifyBook();
    },
    registUser() {},
    modifyUser() {},
  },
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
