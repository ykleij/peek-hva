<template>
  <div class="h-full w-full flex flex-col items-center">

    <div class="w-full h-fit">
      <RouterLink :to="'/expert/expert-overview'">
        <ArrowLeftIcon class="self-start h-[40px] w-[40px] p-3 cursor-pointer text-black hover:bg-neutral-200 transition-colors duration-100 ease-in rounded-full"/>
      </RouterLink>
    </div>

    <div v-if="isLoading" class="h-full w-full flex flex-col items-center justify-center">
      <SpinnerComponent/>
    </div>

    <div v-if="project" class="h-full w-full flex flex-col items-center justify-center">
      <div class="flex flex-col md:flex-row items-center bg-white m-4">
          <div class="self-start p-4">
            <div class="w-96">
            <img v-if="project.image === undefined || project.image === null" class="w-full h-full object-cover"
                 :src="imageNotFound" alt=""/>
            <img :src="project.image" class="max-w-full md:max-h-full mx-auto md:mx-0"/>
          </div>
        </div>

        <div class="max-w-5xl min-w-80 p-4">
          <div class="md:ml-2">
            <h2 class="text-2xl font-bold mb-4">Information Title</h2>
            <div class="flex flex-wrap">
              <div v-for="projectRole in project.projectRoles" :key="projectRole.sector.sectorName"
                   class="mr-2 mb-2 text-[10px] w-fit h-fit bg-neutral-300 text-neutral-700 rounded-full px-2 py-0.5 truncate">
                {{ projectRole.sector.sectorName }}
              </div>
            </div>

            <div class="flex flex-col">
              <p>{{project.description}}</p>
              <p class="mt-14 self-end text-xs">{{getDateCreatedString()}}</p>
            </div>

          </div>
        </div>
      </div>

    </div>

</div>
</template>

<script>
import SpinnerComponent from "@/components/SpinnerComponent";
import {ArrowLeftIcon} from "@heroicons/vue/24/outline";

export default {
  name: "ExpertProjectView",
  inject: {
    projectsService: {from: 'projectsService'},

  },
  components: {
    SpinnerComponent,
    ArrowLeftIcon,

  },
  async mounted() {
    try {
      this.isLoading = true;
      this.projectId = this.$route.params.id;

      this.project = this.projectsService.entities
          .slice()
          .find((project) => project.id === parseInt(this.projectId));

      if (this.project) {
        return this.isLoading = false;
      }

      this.project = await this.projectsService.asyncFindById(this.projectId);

      if (!this.project) {
        return this.$router.push({name: 'expertOverview'});
      }

      this.isLoading = false;

    } catch (e) {
      console.error(e);
    }
  },
  data() {
    return{
      imageNotFound: 'https://care.ntbprov.go.id/img/noimage.png',
      isLoading: false,
      project: null,
      projectId: null,
    }
  },
  methods: {
    getDateCreatedString() {
      const seconds = Math.floor((new Date() - new Date(this.project.dateCreated)) / 1000);
      const minutes = Math.floor(seconds / 60);
      const hours = Math.floor(minutes / 60);
      const days = Math.floor(hours / 24);
      const weeks = Math.floor(days / 7);
      const months = Math.floor(days / 30);

      // If more than 60 minutes return the hours, if more than 24 hours return the days, if more than 30 days return the date.
      if (months > 0) {
        const date = new Date(this.project.dateCreated);

        const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        const monthAbbreviation = months[date.getMonth()];
        const day = date.getDate();
        const year = date.getFullYear();

        return `${monthAbbreviation} ${day}, ${year}`;
      } else if (weeks > 0) {
        return weeks + (weeks === 1 ? " week" : " weeks") + " ago";
      } else if (days > 0) {
        return days + (days === 1 ? " day" : " days") + " ago";
      } else if (hours > 0) {
        return hours + (hours === 1 ? " hour" : " hours") + " ago";
      } else if (minutes > 0) {
        return minutes + (minutes === 1 ? " minute" : " minutes") + " ago";
      } else {
        return seconds + (seconds === 1 ? " second" : " seconds") + " ago";
      }
    }
  }
}
</script>

<style scoped>

</style>
