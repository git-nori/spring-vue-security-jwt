<template>
  <v-container>
    <h3>{{content}}</h3>
  </v-container>
</template>

<script>
import UserService from "../services/user.service";

export default {
  name: "admin",
  data() {
    return {
      content: ""
    };
  },
  mounted() {
    UserService.getAdminBoard().then(
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
