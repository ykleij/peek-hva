<template>
<!--  <RouterLink :to="'/expert/expertProject/' + project.id">-->
    <div class="hover:-translate-y-1 shadow transition-all duration-200 rounded-xl bg-white h-60 p-10 grid grid-cols-3">
      <div class="w-full h-full flex items-center justify-center col-span-1">
        <img class="w-[100px] h-[100px] rounded object-contain" :src="getImageBLOBUrlProject()" alt=""/>
      </div>
      <div class="w-full p-5 col-span-2">
        <div class="text-neutral-900 flex flex-row justify-between text-sm">
          <div>
            <div class="flex flex-row space-x-3 truncate">
              <p class="font-semibold">{{ project.name }}</p>
              <p class="text-[10px] text-neutral-400 truncate">{{ getDate() }}</p>
            </div>
            <p class="text-xs font-light">{{ project.description }}</p>
          </div>
          <button class="min-w-[20px] min-h-[20px] h-5 w-5 hover:-translate-y-0.5 transition-all duration-200" @click.prevent="likeButton()"
                  :disabled="likeButtonDisabled">
            <img alt="" v-if="like" :src="liked"/>
            <img alt="" v-else :src="unLiked"/>
          </button>
        </div>

        <div class="flex flex-row space-x-1 justify-center h-full items-center">
          <div v-for="projectRole in project.projectRoles" :key="projectRole.id" class="flex flex-row space-x-3">
            <div class="text-xs w-fit h-fit bg-neutral-300 text-neutral-700 rounded-full px-2 py-0.5 truncate">
              {{ projectRole.sector.sectorName }}
            </div>
            <div class="text-xs w-fit h-fit bg-neutral-300 text-neutral-700 rounded-full px-2 py-0.5 truncate">
              {{ projectRole.roles[0].roleName }}
            </div>
            <div class="text-xs w-fit h-fit bg-neutral-300 text-neutral-700 rounded-full px-2 py-0.5 truncate">
              {{ projectRole.tools[0].toolName }}
            </div>
          </div>
        </div>
      </div>
    </div>
<!--  </RouterLink>-->
</template>

<script>
import {getImageBLOBUrlProject} from "@/utils/imageDecoder";
import {formatJavaDate} from "@/utils/helperUtils";

export default {
  name: "ExpertProjectComponent",
  inject: {
    likingService: {from: 'likingService'}
  },
  props: ['project', 'showAllProjects', 'likes', 'userId'],
  created() {
    if (this.likes.includes(this.project.id)) {
      this.like = true;
    }
  },
  emits: ['reloadLikes', 'likeProject', 'dislikeProject'],
  data() {
    return {
      imageNotFound: 'https://care.ntbprov.go.id/img/noimage.png',
      liked: 'data:image/png;base64,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',
      unLiked: 'https://icons.veryicon.com/png/128/miscellaneous/beautiful-trip/follow-63.png',
      like: false,
      reloadLikes: true,
      likeButtonDisabled: false,
    }
  },
  methods: {
    getDate() {
      return formatJavaDate(this.project.dateCreated).date;
    },
    getImageBLOBUrlProject() {
      return getImageBLOBUrlProject(this.project);
    },
    async likeButton() {
      try {
        this.likeButtonDisabled = true;

        if (!this.like) {
          this.$emit("likeProject", this.project.id)
          this.like = true;
          await this.likingService.likeProject(this.project.id, this.userId);
        } else {
          this.$emit("dislikeProject", this.project.id)
          this.like = false;
          await this.likingService.unlikeProject(this.project.id, this.userId);
        }
      } catch (error) {
        // Handle errors, e.g., log the error or show a user-friendly message
        console.error('Error during like/unlike operation', error);
      } finally {
        // Delay for 500ms before re-enabling the button
        await new Promise(resolve => setTimeout(resolve, 500));
        this.likeButtonDisabled = false;
      }
    }
  }
}
</script>

<style scoped>

</style>
