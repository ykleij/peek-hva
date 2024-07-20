<template>
  <li
      class="flex flex-col text-start items-start justify-center h-fit py-8 font-semibold">
    <div @click="() => toggleAnswer('answer' + question.id)" class="py-1 cursor-pointer flex justify-between w-full">
      <h1>{{ question.q }}</h1>
      <div v-if="!showAnswer" class="mx-4 text-2xl">+</div>
      <div v-else class="mx-4 text-4xl">-</div>
    </div>
    <div v-show="showAnswer" :id="'answer' + question.id" class="text-neutral-600 font-light">
      {{ question.a }}
    </div>
  </li>
</template>

<script setup>
import {ref, defineProps} from 'vue';
import {gsap} from "gsap";

defineProps(['question']);

const showAnswer = ref(false);

const toggleAnswer = (id) => {
  gsap.from('#' + id, {y: '-10px', duration: 0.1});
  showAnswer.value = !showAnswer.value;
}
</script>

<style scoped>

</style>