<template>
  <div>
    <SearchBarComponent class="w-full mb-6" @searchKeyup="onSearchKeyup" />

    <ul role="list" class="flex flex-col justify-center items-center">
      <UserListItemComponent
          v-for="user in filteredUsers"
          :key="user.id"
          :user="user"
          @click="select(user)"
      />
      <li class="text-sm" v-if="filteredUsers.length < 1">
        {{ usersMessage }}
      </li>
    </ul>
  </div>
</template>

<script>
import SearchBarComponent from "@/components/overview-components/SearchBarComponent";
import UserListItemComponent from "@/components/overview-components/UserListItemComponent";

export default {
  name: "UsersListComponent",
  props: {
    'users': Array
  },
  components: {
    UserListItemComponent,
    SearchBarComponent
  },
  methods: {
    select(user) {
      if (this.selectedUser && this.selectedUser.id === user.id) {
        this.selectedUser = null;
      } else this.selectedUser = user;
    },
    onSearchKeyup(input) {
      this.selectedUser = null;
      if (input) {
        this.searchInput = input;
      } else {
        this.searchInput = null;
      }
    },
  },
  data() {
    return {
      selectedUser: null,
      searchInput: null,
    }
  },
  computed: {
    filteredUsers() {
      if (!this.searchInput) return this.users;
      return this.users.filter((user) =>
          user.firstName ? user.firstName.toLowerCase().includes(this.searchInput.toLowerCase()) : null,
      );
    },
    usersMessage() {
      return this.users.length < 1
          ? "It's a little quiet here..."
          : this.filteredUsers.length < 1
              ? "Couldn't find any users with that name."
              : '';
    },
  },
}
</script>

<style scoped>

</style>