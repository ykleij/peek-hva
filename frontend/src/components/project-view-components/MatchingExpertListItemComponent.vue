<template>
  <li class="w-full flex flex-col">

    <RouterLink :to="'/profile/' + user.id"
                class="rounded-lg bg-white hover:bg-neutral-100 transition-all duration-200 flex flex-col space-y-2 py-8 px-8 m-1 items-center"
                :class="this.index === 1 ? 'hover:-translate-y-1 hover:border-b-4 hover:border-yellow-400' :
              this.index === 2 ? 'hover:-translate-y-1 hover:border-b-4 hover:border-neutral-300' :
              this.index === 3 ? 'hover:-translate-y-1 hover:border-b-4 hover:border-amber-600' : ''">

      <div class="w-full grid grid-cols-5">
        <div class="flex items-center min-w-0 gap-x-4 col-span-4">
          <span>{{ this.index }}.</span>
          <img class="self-center h-12 w-12 flex-none rounded" :src="getImageBLOBUrlProject()" alt=""/>

          <div class="min-w-0 flex-auto text-start space-y-2">

            <div class="space-x-1.5 truncate flex flex-row items-center">
              <span class="text-sm font-semibold leading-6 text-gray-900 w-fit">{{ user.userProfile.firstName }}</span>
              <p class="truncate text-[10px] leading-5 px-2 rounded-full h-fit w-fit font-normal"
                 :class="user.role === 'admin' ? 'bg-red-300 text-red-600' : ' bg-neutral-200 text-neutral-600'">
                {{ user.role }}</p>
            </div>
            <p class="mt-1 truncate text-xs leading-5 text-gray-500">{{ user.userProfile.bio }}</p>
          </div>

          <div v-for="projectRole in user.expertProfile.projectRoles" :key="projectRole.id" class="flex flex-row space-x-3">
            <div class="text-[10px] w-fit h-fit bg-neutral-300 text-neutral-700 rounded-full px-2 py-0.5 truncate">
              {{ projectRole.sector.sectorName }}
            </div>
            <div class="text-[10px] w-fit h-fit bg-neutral-300 text-neutral-700 rounded-full px-2 py-0.5 truncate">
              {{ projectRole.roles[0].roleName }}
            </div>
            <div class="text-[10px] w-fit h-fit bg-neutral-300 text-neutral-700 rounded-full px-2 py-0.5 truncate">
              {{ projectRole.tools[0].toolName }}
            </div>
          </div>
        </div>


        <div class="col-span-1 flex flex-col items-end space-y-2">

          <button
              @click.prevent="addCandidate"
              class="truncate transition-all duration-200 hover:border-neutral-800 border border-neutral-200 text-end text-xs px-3 py-1 rounded"
              :class="isCandidate ? 'bg-neutral-800 hover:bg-neutral-600 border-0 text-white' : 'bg-white'"
          >
            <span class="flex flex-row items-center space-x-1">
              <CheckIcon class="w-4 h-4" v-if="isCandidate"/>
              <span>{{ isCandidate ? 'Candidate' : 'Mark as candidate' }}</span>
            </span>
          </button>

          <div class="truncate text-xs px-3 py-1 rounded-full"
               :class="this.index === 1 ? 'bg-yellow-300 border border-yellow-400' :
              this.index === 2 ? 'bg-neutral-200 border border-neutral-300' :
              this.index === 3 ? 'bg-amber-500 border border-amber-600' : ''">
            {{ this.score }}% match
          </div>

        </div>
      </div>
    </RouterLink>
  </li>
</template>

<script>
import {CheckIcon} from "@heroicons/vue/24/solid"
import {getImageBLOBUrlProject} from "@/utils/imageDecoder";

export default {
  name: "MatchingExpertListItemComponent",
  props: ['user', 'score', 'index', 'candidateList'],
  components: {
    CheckIcon
  },
  mounted() {
    console.log(this.user)
    if (this.candidateList.find(candidate => candidate.id === this.user.id)) {
      this.isCandidate = true;
    }
  },
  data() {
    return {
      isCandidate: false,
    }
  },
  methods: {
    getImageBLOBUrlProject() {
      return getImageBLOBUrlProject(this.project);
    },
    addCandidate() {
      if (!this.isCandidate) {
        this.isCandidate = true;
        this.$emit('addCandidate', this.user);
      } else {
        this.isCandidate = false;
        this.$emit('removeCandidate', this.user);
      }
    }
  }
}
</script>

<style scoped>

</style>