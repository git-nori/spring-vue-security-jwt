<template>
  <v-container>
    <h3>{{content}}</h3>
  </v-container>
</template>

<script>
import UserService from "../services/user.service";

export default {
  name: "user",
  data() {
    return {
      content: ""
    };
  },
  mounted() {
    UserService.getUserBoard().then(
      res => {
        this.content = res.data;
      },
      error => {
        this.content = error.response.data.message;
      }
    );
  }
};
</script>
