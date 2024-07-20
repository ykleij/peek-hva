<template>
  <div class="text-neutral-800 space-y-5 h-fit w-full flex flex-col items-center">
    <div class="space-y-5 w-full">

      <div class="flex flex-row justify-between">
        <h1 class="font-bold text-3xl">Match History</h1>
        <div class="flex flex-col text-end">
          <p>Initiated by
            <RouterLink :to="'/profile/' + matchHistoryData.matchAdminId"
                        class="hover:border-b hover:border-neutral-900 font-semibold">{{
                matchHistoryData.matchAdminName
              }}
            </RouterLink>
          </p>
          <p>{{ matchHistoryData.matchDate }}</p>
        </div>
      </div>

      <div v-if="foundMatches && matchData" class="space-y-10">
        <!--        <p>We've found a total of <span>{{ this.matchData.matches.length }}</span> expert matches!</p>-->
        <ul class="max-h-96 space-y-2 overflow-y-scroll">
          <MatchingExpertListItemComponent
              @addCandidate="this.$emit('addCandidate', $event)"
              @removeCandidate="this.$emit('removeCandidate', $event)"
              v-for="(match, i) in this.sortedMatchData"
              :candidateList="candidateList"
              :key="match.id"
              :index="i + 1"
              :user="match.user"
              :score="match.matchingScore">
          </MatchingExpertListItemComponent>

          <li v-if="matchData.length <= 0" class="py-12">
            <p class="text-xs">No expert matches were made at that specific time.</p>
          </li>
        </ul>
      </div>

      <!--      <div v-else>-->
      <!--        <p>Let's wait for the matching results for this project.</p>-->
      <!--      </div>-->
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
  name: "MatchHistoryModalContentComponent",
  emits: ['addCandidate', 'removeCandidate'],
  props: ['foundMatches', 'matchData', 'matchHistoryData', 'candidateList'],
  components: {SpinnerComponent, MatchingExpertListItemComponent},
  computed: {
    sortedMatchData() {
      try {
        return this.matchData.slice().sort((m1, m2) => m2.matchingScore - m1.matchingScore)
      } catch (e) {
        return {}
      }
    }
  },
}
</script>

<style scoped>

</style>