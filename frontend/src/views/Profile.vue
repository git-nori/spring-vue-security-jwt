<template>
  <v-container>
    <v-row>
      <v-col>
        <h3>
          Profile of
          <strong>"{{currentUser.username}}"</strong>
        </h3>
        <p>
          <strong>Token:</strong>
          {{currentUser.token.substring(0, 20)}} ... {{currentUser.token.substr(currentUser.token.length - 20)}}
        </p>
        <p>
          <strong>Id:</strong>
          {{currentUser.id}}
        </p>
        <p>
          <strong>Email:</strong>
          {{currentUser.email}}
        </p>
        <p>
          <strong>Authorities:</strong>
          <v-list-item>
            <v-list-item-content v-for="(role,index) in currentUser.roles" :key="index">
              <v-list-item-title>{{role}}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </p>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "profile",
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  mounted() {
    if (!this.currentUser) {
      this.$router.push("/login");
    }
  }
};
</script>