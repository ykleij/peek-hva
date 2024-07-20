<template>
  <div class="text-neutral-800 space-y-5 h-fit w-full flex flex-col items-center">
    <div class="space-y-3 w-full">

      <h1 class="font-bold text-3xl">
        <span v-if="!foundMatches">Greetings <span>{{ this.adminName }}</span>.</span>
        <span v-else>Matches Found</span>
      </h1>

      <div v-if="foundMatches" class="space-y-10">
        <p>We've found a total of <span>{{ this.matchedExperts.length }}</span> expert matches!</p>
        <ul class="max-h-96 space-y-2 overflow-y-scroll">
          <MatchingExpertListItemComponent
              @addCandidate="this.$emit('addCandidate', $event)"
              @removeCandidate="this.$emit('removeCandidate', $event)"
              v-for="(expert, i) in matchedExperts" :key="expert.id"
              :candidateList="candidateList"
              :index="i + 1"
              :user="expert.user"
              :score="expert.score">
          </MatchingExpertListItemComponent>
        </ul>
      </div>

      <div v-else>
        <p>Let's wait for the matching results for this project.</p>
      </div>
    </div>

    <div v-if="!foundMatches" class="flex justify-center items-center w-full h-full min-h-[10rem]">
      <SpinnerComponent/>
    </div>
  </div>
</template>

<script>
import MatchingExpertListItemComponent from "@/components/project-view-components/MatchingExpertListItemComponent";
import SpinnerComponent from "@/components/SpinnerComponent";

export default {
  name: "MatchModalContentComponent",
  emits: ['addCandidate', 'removeCandidate'],
  props: ['foundMatches', 'matchedExperts', 'candidateList'],
  inject: {
    sessionService: {from: 'sessionService'}
  },
  data() {
    return {
      adminName: this.sessionService.getUserFirstNameFromBrowserStorage()
    }
  },
  components: {SpinnerComponent, MatchingExpertListItemComponent},
}
</script>

<style scoped>

</style>