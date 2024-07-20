<template>
  <div class="w-full sm:px-32 my-8 mb-12">
    <div class="divide-gray-100 w-full flex flex-col">
      <div class="w-full flex flex-col">
        <div class="mb-10 space-x-3 text-start text-sm mr-10 ml-10">
          <div>
            <button class="p-3 w-32" :class="!this.showUsers ? 'text-neutral-700' : 'text-neutral-400'"
                    @click="toggle('showUsers', true); clearSearchbar(); refreshLikes()">
              Projects
            </button>
            <button class="p-3 w-32 " :class="this.showUsers ? 'text-neutral-700' : 'text-neutral-400'"
                    @click="toggle('showUsers', false); clearSearchbar()">
              Users
            </button>
            <div class="bg-neutral-200 h-[2px]">
              <div class="bg-neutral-700 w-32 h-[2px] transition-all duration-200"
                   :class="!showUsers ? '' : 'translate-x-32'"></div>
            </div>
          </div>
        </div>


        <div v-if="!showUsers"
             class="space-x-2">
          <button class="transition-colors duration-300 text-sm w-fit rounded px-3 py-1"
                  :class="this.showAllProjects ? 'bg-black text-white' : 'border bg-white text-neutral-900 hover:bg-neutral-100'"
                  @click="toggle('showAllProjects', false)">
            All Projects
          </button>
          <button class="transition-colors duration-300 text-sm w-fit rounded px-3 py-1"
                  :class="!this.showAllProjects ? 'bg-black text-white' : 'border bg-white text-neutral-900 hover:bg-neutral-100'"
                  @click="toggle('showAllProjects', true)">
            Liked Projects
          </button>
        </div>
      </div>

      <SearchBarComponent
          class="my-10"
          :background-color=" 'white' "
          ref="clearInput"
          @searchKeyup="onSearchKeyup">
      </SearchBarComponent>

      <div v-if="this.isLoading" class="grid grid-cols-1 lg:grid-cols-2 2xl:grid-cols-3 gap-5">
        <div v-for="index in 9" :key="index"
             class="shadow transition-all duration-200 rounded-xl bg-white h-60 p-10 grid grid-cols-3 animate-pulse">
          <div class="w-full h-full flex items-center justify-center col-span-1">
            <div class="w-[100px] h-[100px] flex-none rounded bg-gray-100"/>
          </div>

          <div class="w-full p-5 col-span-2">
            <div class="w-full flex flex-row space-x-3">
              <div class="h-3 my-2 bg-gray-100 rounded-full w-3/5"></div>
              <div class="h-3 my-2 bg-gray-100 rounded-full w-2/5"></div>
            </div>
            <div class="h-3 my-2 bg-gray-100 rounded-full w-1/5"></div>
            <div class="flex flex-row space-x-1 justify-center h-full items-center">
              <div class="h-3 my-2 bg-gray-100 rounded-full w-1/5"></div>
              <div class="h-3 my-2 bg-gray-100 rounded-full w-1/5"></div>
              <div class="h-3 my-2 bg-gray-100 rounded-full w-1/5"></div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="!isLoading && !showUsers && showAllProjects"
           class="grid grid-cols-1 md:grid-cols-2 2xl:grid-cols-3 gap-5">
        <ExpertProjectComponent
            @likeProject="likeProject"
            @dislikeProject="dislikeProject"
            v-for="project in filteredProjects"
            :key="project.id"
            :project="project"
            :showAllProjects="showAllProjects"
            :likes="likes"
            :userId="userId"/>
        <p class="text-sm" v-if="filteredProjects.length < 1">
          {{ projectsMessage }}
        </p>
      </div>

      <div v-if="!isLoading && !showUsers && !showAllProjects"
           class="grid grid-cols-1 md:grid-cols-2 2xl:grid-cols-3 gap-5">
        <ExpertProjectComponent
            @likeProject="likeProject"
            @dislikeProject="dislikeProject"
            v-for="project in likedProjects"
            :key="project.id"
            :project="project"
            :showAllProjects="showAllProjects"
            :likes="likes"
            :userId="userId"/>
        <p class="text-sm" v-if="likedProjects.length < 1">
          {{ projectsMessage }}
        </p>
      </div>

      <div v-if="!isLoading && showUsers"
           class="flex flex-col self-center w-full">
        <ul role="list" class="mr-10 ml-10">
          <UserListItemComponent
              v-for="user in filteredUsers"
              :key="user.id"
              :user="user"
              class="mb-2"/>
        </ul>
        <p class="text-sm" v-if="filteredUsers.length < 1">
          {{ usersMessage }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import ExpertProjectComponent from "@/components/ExpertProjectComponent";
import SearchBarComponent from "@/components/overview-components/SearchBarComponent";
import UserListItemComponent from "@/components/overview-components/UserListItemComponent";

export default {
  name: "ExpertOverviewView",
  inject: {
    projectsService: {from: 'projectsService'},
    usersService: {from: 'usersService'},
    sessionService: {from: 'sessionService'},
    likingService: {from: 'likingService'},
  },
  components: {
    UserListItemComponent,
    SearchBarComponent,
    ExpertProjectComponent,
  },
  computed: {
    projects() {
      try {
        if (!this.projectsService.entities) return [];
        return this.projectsService.entities;
      } catch (e) {
        return [];
      }
    },
    users() {
      try {
        if (!this.usersService.entities) return [];
        return this.usersService.entities.slice().filter(user => user.userProfile);
      } catch (e) {
        return [];
      }
    },

    filteredProjects() {
      try {
        if (!this.searchInput) {
          return this.projects
              .filter(project => project.status === 1 && !this.likes.includes(project.projectid))
              .sort((p1, p2) => {
                if (p1.status !== p2.status) {
                  return p2.status - p1.status;
                }
                return new Date(p2.dateCreated) - new Date(p1.dateCreated);
              });
        }

        return this.projects
            .filter(project =>
                project.name
                    ? project.name.toLowerCase().includes(this.searchInput.toLowerCase())
                    : null
            )
            .filter(project => project.status === 1 && !this.likes.includes(project.projectid))
            .sort((p1, p2) => {
              if (p1.status !== p2.status) return p2.status - p1.status;

              return new Date(p2.dateCreated) - new Date(p1.dateCreated);
            });
      } catch (e) {
        console.error(e);
        return this.projects;
      }
    },
    likedProjects() {
      try {
        if (!this.searchInput) {
          return this.projects
              .filter(project => project.status === 1 && this.likes.includes(project.id))
              .sort((p1, p2) => {
                if (p1.status !== p2.status) {
                  return p2.status - p1.status;
                }
                return new Date(p2.dateCreated) - new Date(p1.dateCreated);
              });
        }

        return this.projects
            .filter(project =>
                this.likes.includes(project.id) &&
                (project.name ? project.name.toLowerCase().includes(this.searchInput.toLowerCase()) : true) &&
                project.status === 1
            )
            .sort((p1, p2) => {
              if (p1.status !== p2.status) {
                return p2.status - p1.status;
              }
              return new Date(p2.dateCreated) - new Date(p1.dateCreated);
            });
      } catch (e) {
        console.log(e);
        return this.projects;
      }
    },


    projectsMessage() {
      return this.projects.length < 1
          ? "It's a little quiet here..."
          : this.filteredProjects.length < 1
              ? "Couldn't find any projects with that name."
              : '';
    },
    filteredUsers() {
      try {
        if (!this.searchInput) return this.users.filter(user => user.id !== this.userId);
        return this.users.filter((user) =>
            user.userProfile.firstName ? user.userProfile.firstName.toLowerCase().includes(this.searchInput.toLowerCase()) && user.id !== this.userId : null,
        );
      } catch (e) {
        return [];
      }
    },
    usersMessage() {
      return this.users.length < 1
          ? "It's a little quiet here..."
          : this.filteredUsers.length < 1
              ? "Couldn't find any users with that name."
              : '';
    },
  },
  async mounted() {
    try {
      this.isLoading = true;

      // If the cache contains projects and users, these will be rendered because of quicker load times.
      if (this.projectsService.entities.length > 0 && this.usersService.entities.length > 0) {
        this.userId = this.sessionService.getUserIdFromBrowserStorage();
        this.likes = await this.likingService.getAllLikesFromUser(this.userId);
        return this.isLoading = false;
      }

      // If the projects and users aren't cached we fetch them from the database.
      this.userId = this.sessionService.getUserIdFromBrowserStorage();
      this.likes = await this.likingService.getAllLikesFromUser(this.userId);
      await this.projectsService.asyncFindAll();
      await this.usersService.asyncFindAll();

      this.isLoading = false;
    } catch (e) {
      console.error(e)
    }
  },
  data() {
    return {
      isLoading: true,
      showUsers: false,
      showAllProjects: true,
      userId: null,
      searchInput: null,
      likes: [],
    }
  },
  methods: {
    likeProject(id) {
      try {
        this.likes.push(id);
      } catch (e) {
        console.log(e)
      }
    },
    dislikeProject(id) {
      try {
        this.likes = this.likes.filter(projectId => projectId !== id);
      } catch (e) {
        console.log(e)
      }
    },
    clearSearchbar() {
      this.$refs.clearInput.clearInput();
      this.searchInput = null;
    },
    toggle(key, param) {
      if (this[key] === param) {
        this[key] = !this[key];
      }
    },
    onSearchKeyup(input) {
      if (input) {
        this.searchInput = input;
      } else {
        this.searchInput = null;
      }
    },
    async refreshLikes() {
      this.isLoading = true;
      await this.likingService.getAllLikesFromUser(this.userId);
      this.isLoading = false;
    }
  },

}
</script>

<style scoped>

:deep(.hide-role-expert-overview) {
  visibility: hidden;
}
</style>
