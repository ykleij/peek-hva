<template>

  <div class="flex flex-col items-center my-5 px-0 sm:px-5">
    <div v-if="this.isLoading === true" class="absolute top-[50%]">
      <SpinnerComponent/>
    </div>

    <div v-else class="w-full">
      <div class="mb-10 space-x-3 text-start text-sm px-3">
        <div>
          <button class="p-3 w-20" :class="this.showProjects ? 'text-neutral-700' : 'text-neutral-400'"
                  @click="toggleProjects">
            Projects
          </button>
          <button class="p-3 w-20" :class="!this.showProjects ? 'text-neutral-700' : 'text-neutral-400'"
                  @click="toggleUsers">
            Users
          </button>
          <div class="bg-neutral-200 h-[2px]">
            <div class="bg-neutral-700 w-20 h-[2px] transition-all duration-200"
                 :class="showProjects ? '' : 'translate-x-20'"></div>
          </div>
        </div>
      </div>


      <div class="gap-5 w-full">
        <div
            class="flex flex-col h-fit min-h-full bg-white p-12 shadow-sm border border-neutral-200 w-full"
        >
          <ProjectsListComponent
              v-if="showProjects"
              :projects="projects"
          />

          <UsersListComponent
              v-else
              :users="users"
          />
        </div>


      </div>
    </div>
  </div>
</template>

<script>
import {ref} from 'vue';
import SpinnerComponent from "@/components/SpinnerComponent";
import ProjectsListComponent from "@/components/overview-components/ProjectsListComponent";
import UsersListComponent from "@/components/overview-components/UsersListComponent";

export default {
  name: "OverviewView",
  inject: {
    projectsService: {from: 'projectsService'},
    usersService: {from: 'usersService'}
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
    }
  },
  async mounted() {
    try {
      this.isLoading = true;

      // If the cache contains projects and users, these will be rendered because of quicker load times.
      if (this.projectsService.entities && this.usersService.entities &&
          this.projectsService.entities.length > 0 && this.usersService.entities.length > 0) return this.isLoading = false;

      // If the projects and users aren't cached we fetch them from the database.
      await this.projectsService.asyncFindAll();
      await this.usersService.asyncFindAll();
      this.isLoading = false;
    } catch (e) {
      console.error(e)
    }
  },
  components: {
    UsersListComponent,
    ProjectsListComponent,
    SpinnerComponent,
  },
  methods: {
    toggleProjects() {
      this.showProjects = true;
    },
    toggleUsers() {
      this.showProjects = false;
    },
  },
  data() {
    return {
      isLoading: false,
      buttonClass: "w-28 py-3 rounded-full text-xs font-medium transition-all duration-200 ease",
      showProjects: ref(true),
    }
  }
}
</script>

<style scoped>

</style>