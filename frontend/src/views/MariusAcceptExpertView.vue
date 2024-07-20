<template>
  <div class="h-full my-12 flex flex-col items-center">
    <h1 class="w-3/4 font-bold text-2xl mb-12 text-start">
      Expert aanmeldingen
    </h1>
    <div class="w-3/4 divide-y divide-gray-100">
<!--      <div v-for="profile in profiles" :key="profile.id"-->
<!--           class="grid grid-cols-10 p-4 rounded">-->
<!--        <div class="flex-shrink-0 col-span-1 flex flex-col items-center">-->
<!--          <img :src="profile.image" alt="Profile Image" class="w-16 h-16 rounded-full">-->
<!--        </div>-->
<!--        <div class="text-start col-span-7">-->
<!--          <h3 class="text-lg font-bold mb-2">{{ profile.name }}</h3>-->
<!--          <p class="text-gray-700 truncate text-xs leading-5">{{ profile.bio }}</p>-->
<!--        </div>-->

        <div v-for="profile in profiles" :key="profile.id" class="flex p-5 min-w-0 gap-x-4 col-span-3">
          <img class="h-12 w-12 flex-none rounded-full shadow-sm" :src="profile.image" alt=""/>
          <div class="min-w-0 flex-auto text-start">
            <p class="text-sm font-semibold leading-6 text-gray-900 w-fit">{{ profile.name }}
            </p>
            <p class="mt-1 truncate text-xs leading-5 text-gray-500">{{ profile.bio }}</p>
          </div>

        <div class="flex-shrink-0 ml-auto col-span-2 gap-x-2 flex flex-row items-center">
          <button @click="acceptProfile(profile.id)"
                  class="text-white rounded py-1 px-3 text-xs col-span-1 bg-neutral-950">Accept
          </button>
          <button @click="denyProfile(profile.id)"
                  class="text-neutral-600 rounded py-1 px-3 text-xs col-span-1 bg-neutral-300">Reject
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "MariusAcceptExpertView.vue",

  data() {
    return {
      profiles: [
        {
          id: 1,
          name: 'John Doe',
          image: 'https://placekitten.com/100/100',
          bio: 'Hi I am an electrical engineer, I have been interested in this line of work since I was 15 years old.' +
              'I am very excited about this oppertunity to work with companies i havent worked with before.' +
              'I have been doing this job for over 30 years now. I am currently 50 years old.' +
              'I studied electirical engineering when I was 16 at MIT in the United States.'
        },
        {
          id: 2,
          name: 'Jane Smith',
          image: 'https://placekitten.com/101/101',
          bio: 'Hi im from Amsterdam, i have a major in Business Psychology. I have 15 years of experience. ' +
              'I like to work near Amsterdam so i wont have to travel very far. Futhermore I like to work in a group.' +
              'I am very driven and enthusiastic.'
        },
        {
          id: 3,
          name: "Anne gootsteen",
          image: 'https://placekitten.com/101/102',
          bio: 'I am currently out of a job and looking for one. I have just graduated from the University of Delft.' +
              'I studied Accountancy, and I would like to work at a big firm where i can learn lots of new things.' +
              'I am 25 years old and live in The Hague.'
        },
        {
          id: 4,
          name: 'Henk van der Spek',
          image: 'https://placekitten.com/101/107',
          bio: "I currently work at Microsoft as a Software Engineer. I would like to pursue different job opportunities" +
              " and work with new people. I have 10 years of experience, and I graduated at the Hogeschool van Amsterdam at the age of 22." +
              "I live in Groningen and I would like to work near my hometown."
        }]
    };
  },
  methods: {
    acceptProfile(id) {
      // Add your logic for accepting a profile here
      console.log(`Profile ${id} accepted`);
    },
    denyProfile(id) {
      // Add your logic for denying a profile here
      console.log(`Profile ${id} denied`);
    }
  }
};
</script>

<style scoped>
/* Add custom styles here */
</style>


<!--<template>-->
<!--<div>-->
<!--  <h1>-->
<!--    helloo-->
<!--  </h1>-->
<!--</div>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--  name: "MariusAcceptExpertView.vue"-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->

<!--</style>-->