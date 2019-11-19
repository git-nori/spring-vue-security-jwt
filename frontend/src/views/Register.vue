<template>
  <div class="col-md-12">
    <div class="card card-container">
      <img
        id="profile-img"
        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
        class="profile-img-card"
      />
      <form name="form" @submit.prevent="handleRegister">
        <div v-if="!successful">
          <div class="form-group">
            <label for="username">Username</label>
            <input
              type="text"
              class="form-control"
              name="username"
              v-model="user.username"
              v-validate="'required|min:3|max:20'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('username')"
            >{{errors.first('username')}}</div>
          </div>
          <div class="form-group">
            <label for="email">Email</label>
            <input
              type="email"
              class="form-control"
              name="email"
              v-model="user.email"
              v-validate="'required|email|max:50'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('email')"
            >{{errors.first('email')}}</div>
          </div>
          <div class="form-group">
            <label for="password">Password</label>
            <input
              type="password"
              class="form-control"
              name="password"
              v-model="user.password"
              v-validate="'required|min:6|max:40'"
            />
            <div
              class="alert-danger"
              v-if="submitted && errors.has('password')"
            >{{errors.first('password')}}</div>
          </div>
          <div class="form-group">
            <button class="btn btn-primary btn-block">Sign Up</button>
          </div>
        </div>
      </form>

      <div
        class="alert"
        :class="successful ? 'alert-success' : 'alert-danger'"
        v-if="message"
      >{{message}}</div>
    </div>
  </div>
</template>

<script>
import User from "../models/user";

export default {
  name: "register",
  data() {
    return {
      user: new User("", "", ""),
      submitted: false,
      successful: false,
      message: ""
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    }
  },
  mounted() {
    if (this.loggedIn) {
      this.$router.push("/profile");
    }
  },
  methods: {
    handleRegister() {
      this.message = "";
      this.submitted = true;
      this.$validator.validate().then(valid => {
        if (valid) {
          this.$store.dispatch("auth/register", this.user).then(data => {
            this.message = data.message;
            this.successful = data.successful;
          }),
            error => {
              this.message = error.message;
              this.successful = false;
            };
        }
      });
    }
  }
};
</script>