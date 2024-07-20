<template>
  <button
      @click="openModal"
      class="absolute right-0 hover:bg-neutral-50 transition-colors duration-200 bg-white border border-neutral-200 rounded-lg text-xs px-5 py-3 w-fit">
    Candidate list
  </button>

  <TransitionRoot appear :show="isOpen" as="template">
    <Dialog as="div" @close="closeModal" class="relative z-50">
      <TransitionChild
          as="template"
          enter="duration-300 ease-out"
          enter-from="opacity-0"
          enter-to="opacity-100"
          leave="duration-200 ease-in"
          leave-from="opacity-100"
          leave-to="opacity-0">
        <div class="fixed inset-0 bg-black/50"/>
      </TransitionChild>

      <div class="fixed inset-0 overflow-y-auto">
        <div
            class="flex min-h-full items-center justify-center p-4 text-center">
          <TransitionChild
              as="template"
              enter="duration-300 ease-out"
              enter-from="opacity-0 scale-95"
              enter-to="opacity-100 scale-100"
              leave="duration-200 ease-in"
              leave-from="opacity-100 scale-100"
              leave-to="opacity-0 scale-95">
            <DialogPanel
                class="w-full md:w-1/2 h-fit space-y-5 transform overflow-hidden rounded-lg bg-white p-10 text-center shadow-xl transition-all">

              <h1 class="font-bold text-2xl">Candidate List</h1>
              <p v-if="this.candidateList && this.candidateList.length <= 0" class="text-xs">No candidates at the moment.</p>
              <ul>
                <CandidateListItemComponent @addCandidate="this.$emit('addCandidate', $event)"
                                            @removeCandidate="this.$emit('removeCandidate', $event)"
                                            v-for="user in candidateList" :key="user.id" :user="user"/>
              </ul>

            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>

<script>
import {
  TransitionRoot,
  TransitionChild,
  Dialog,
  DialogPanel,
} from '@headlessui/vue';
import CandidateListItemComponent from "@/components/project-view-components/CandidateListItemComponent";

export default {
  name: "CandidateListComponent",
  emits: ['addCandidate', 'removeCandidate'],
  props: ['candidateList', 'projectId'],
  components: {
    CandidateListItemComponent,
    TransitionRoot,
    TransitionChild,
    Dialog,
    DialogPanel,
  },
  data() {
    return {
      isOpen: false,
    }
  },
  methods: {
    openModal() {
      this.isOpen = true;
    },
    closeModal() {
      this.isOpen = false;
    },

  }
}
</script>

<style scoped>

</style>