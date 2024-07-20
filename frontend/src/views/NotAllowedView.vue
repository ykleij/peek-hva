<template>
  <div class="flex justify-center items-center h-screen">
    <div class="my-24 w-1/3 bg-neutral-200 p-8 rounded text-start shadow-lg">
      <h1 class="text-2xl text-neutral-800 font-semibold mb-3">
        Niet toegestaan
      </h1>
      <span class="font-light text-sm"
        >Ongeldige link. Vraag de beheerder om een nieuwe link te sturen.
      </span>
      <span class="font-light text-sm"
        >u wordt binnen {{ timer }} terug gestuurd naar de home pagina
      </span>
      <div class="mt-10 flex gap-x-6">
        <RouterLink
          to="/"
          class="rounded-md bg-gray-950 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-gray-900 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
          >Go back home</RouterLink
        >
      </div>
      <!--      Awaiting approval.-->
      <!--      We thank you for showing interest in becoming an expert. We appreciate your dedication and look forward to reviewing your application. Your expertise is valued, and we'll be in touch soon.-->
    </div>
  </div>
</template>

<script>
import router from '@/router';
import { onMounted, ref } from 'vue';

export default {
  name: 'AwaitingApprovalView',

  setup() {
    const timer = ref(10);

    onMounted(() => {
      const interval = setInterval(() => {
        timer.value = timer.value - 1;

        // if timer ends send back to home
        if (timer.value === 0) {
          clearInterval(interval);
          router.push('/');
        }
      }, 1000);
    });
    // watch(timer, () => {
    //   if (timer.value === 0) {
    //   }
    // });

    return { timer };
  },
};
</script>

<style scoped></style>
