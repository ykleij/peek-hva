<template>
  <main class="grid h-screen place-items-center py-24 sm:py-32 lg:px-8">
    <div class="text-center">
      <p class="text-base font-semibold text-black">404</p>

      <div v-if="type === 'profile'">
        <h1 class="mt-4 text-3xl font-bold tracking-tight text-gray-900 sm:text-5xl">Profile not found</h1>
        <p class="mt-6 text-base leading-7 text-gray-600">Sorry, we couldn’t find that profile.</p>
      </div>
      <div v-else>
        <h1 class="mt-4 text-3xl font-bold tracking-tight text-gray-900 sm:text-5xl">Page not found</h1>
        <p class="mt-6 text-base leading-7 text-gray-600">Sorry, we couldn’t find the page you’re looking for.</p>
      </div>
      <div class="mt-10 flex items-center justify-center gap-x-6">
        <RouterLink to="/" class="rounded-md bg-gray-950 px-3.5 py-2.5 text-sm font-semibold text-white shadow-sm hover:bg-gray-900 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Go back home</RouterLink>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: "UnknownRoute",
  props: ['type'],
}
</script>

<style scoped>

</style>