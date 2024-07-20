<template>
  <div class="p-5 w-full h-full flex flex-col items-center">
    <div v-if="this.isLoading === true" class="absolute top-[50%]">
      <SpinnerComponent/>
    </div>
    <div v-else
         class="flex flex-col md:flex-row items-start text-start gap-8 w-full min-w-[30rem] h-[20rem]">

      <div v-for="item in items" :key="item.name"
           class="w-1/2 h-full bg-white rounded-xl shadow-sm p-10 flex flex-row justify-between">
        <div>
          <p class="text-xs font-semibold">Total</p>
          <h1>{{ item.name }}</h1>
          <span v-if="item.name === 'Active Projects'" class="text-[4rem] font-semibold"
          >{{ this.projectsAmount }}
          </span>
          <span v-if="item.name === 'Users'" class="text-[4rem] font-semibold"
          >{{ this.userAmount }}
          </span>

        </div>
      </div>
    </div>

    <div v-if="isLoading === false" class="flex md:flex-row w-full mx-5 items-start justify-start">
      <div class="w-1/2">
        <ProjectChart/>
      </div>

      <div class="w-1/2">
        <UserChart/>
      </div>

    </div>


  </div>


</template>

<script>
import SpinnerComponent from "@/components/SpinnerComponent";
import ProjectChart from "@/components/ProjectChart";
import UserChart from "@/components/UserChart";

export default {
  name: "DashboardView",
  inject: {
    projectsService: {from: 'projectsService'},
    usersService: {from: 'usersService'},
  },
  async mounted() {
    try {
      this.isLoading = true;
      if (this.usersService.entities.length > 0 && this.projectsService.entities.length > 0) {
        this.userAmount = this.usersService.entities.length;
        this.projectsAmount = this.projectsService.entities.slice().filter((project) => project.status === 1).length;

        this.isLoading = false;
      } else {
        await this.usersService.asyncFindAll();
        this.userAmount = this.usersService.entities.length;
        await this.projectsService.asyncFindAll();
        this.projectsAmount = this.projectsService.entities.slice().filter((project) => project.status === 1).length;
        this.isLoading = false;
      }

    } catch (e) {
      console.error(e)
    }
  },
  components: {
    UserChart,
    ProjectChart,
    SpinnerComponent,
  },

  data() {
    return {
      isLoading: true,
      selectedProject: null,
      searchInput: null,
      projectsAmount: null,
      userAmount: null,
      matchesAmount: null,
      items: [
        {
          name: "Active Projects",
          stat: 0,
          progression: 0,
        },
        {
          name: "Users",
          stat: 0,
          progression: 0,
        },
        // {
        //   name: "Matches",
        //   stat: 0,
        //   progression: 0,
        // }
      ]
    }
  }
}
</script>

<style scoped>

</style>