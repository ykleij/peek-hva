<template>
  <div class="h-full my-12 flex flex-col items-center">
    <h1 class="w-3/4 font-bold text-2xl mb-12 text-center">
      Your matches
    </h1>
    <div class="w-3/4 divide-y divide-gray-100">
      <div v-for="project in projects" :key="project.id" class="flex p-5 min-w-0 gap-x-4 col-span-3">
        <img class="h-12 w-12 flex-none shadow-sm" :src="project.image" alt=""/>
        <div class="min-w-0 flex-auto text-start">
          <p class="text-sm font-semibold leading-6 text-gray-900 w-fit">{{ project.name }}</p>
          <p class="mt-1 truncate text-xs leading-5 text-gray-500">{{ project.description }}</p>
        </div>
        <div class="flex-shrink-0 ml-auto col-span-2 gap-x-2 flex flex-row items-center">
          <div class="w-40">
            <div v-if="project.mariusApproved === true" class="flex-shrink-0 ml-auto col-span-2 gap-x-2 flex flex-row items-center">
              <p class="text-xs text-lime-500">
                Approved by Marius</p>
              <p class=" text-sm text-lime-500">✔</p>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "ExpertMatchingView",
  components: {},
  data() {
    return {
      projects: [
        {
          id: 1,
          name: 'Google',
          image: 'https://cdn.iconscout.com/icon/free/png-256/free-google-160-189824.png',
          description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
          mariusApproved: true,
        },
        {
          id: 2,
          name: 'Apple',
          image: 'https://cdn-icons-png.flaticon.com/512/152/152752.png',
          description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo ',
          mariusApproved: false,
        }
      ]
    }
  }
}

</script>

<style scoped>
.circle-wrap {
  display: grid;
  grid-template-columns: repeat(1, 160px);
  grid-gap: 80px;
  margin-top: 100px;
  margin-left: 50px;
  width: 50px;
  height: 50px;
  background: #d9d7da;
  border-radius: 50%;
}


</style>
