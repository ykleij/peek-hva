<template>
  <div
      class="flex justify-center items-center">
    <div
        class="fixed -z-50 transform-gpu overflow-hidden blur-xl"
        aria-hidden="true">
      <div
          class="hidden md:block relative aspect-[1155/678] w-[1rem] rotate-[200deg] bg-gradient-to-tr from-[#020029] to-[#000000] opacity-5 sm:left-[calc(50%-30rem)] sm:w-[72.1875rem]"
          style="
          clip-path: polygon(
            74.1% 44.1%,
            100% 61.6%,
            97.5% 26.9%,
            85.5% 0.1%,
            80.7% 2%,
            72.5% 32.5%,
            60.2% 62.4%,
            52.4% 68.1%,
            47.5% 58.3%,
            45.2% 34.5%,
            27.5% 76.7%,
            0.1% 64.9%,
            17.9% 100%,
            27.6% 76.8%,
            76.1% 97.7%,
            74.1% 44.1%
          );
        "></div>
    </div>

    <div
        class="z-10 bg-opacity-50 bg-blend-color-burn w-4/5 h-fit px-6 py-12 lg:px-8 rounded shadow-gray-950">
      <div class="sm:mx-auto sm:w-full sm:max-w-sm">
        <h2
            class="text-center text-2xl font-bold leading-9 tracking-tight text-neutral-700">
          Login
        </h2>
      </div>

      <div class="mt-10 flex flex-col w-full sm:mx-auto sm:w-full sm:max-w-sm">
        <form
            @submit.prevent="onSubmit"
            class="space-y-4 mb-4"
            action="#"
            method="POST">
          <div>
            <div class="mt-2">
              <input
                  v-model="email"
                  placeholder="Email"
                  id="email"
                  name="email"
                  type="email"
                  required=""
                  class="pl-3 text-sm block w-full rounded border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6"/>
            </div>
          </div>

          <div>
            <div class="mt-2">
              <input
                  v-model="password"
                  placeholder="Password"
                  id="password"
                  name="password"
                  type="password"
                  autocomplete="current-password"
                  required=""
                  class="pl-3 text-sm block w-full rounded border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6"/>
            </div>
          </div>

          <p v-show="showErrorMsg" class="text-sm text-red-400" ref="error">{{ this.errorTextContent }}</p>

          <div>
            <button
                type="submit"
                class="flex w-full justify-center rounded bg-neutral-900 transition-colors px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-neutral-800 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
              Sign in
            </button>
          </div>
        </form>

        <div class="w-full flex justify-center">
          <router-link
              to="/request-password-reset"
              class="border-none outline-none w-fit text-sm font-semibold leading-6 text-neutral-950"
          >Forgot password?
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from "@/router";

export default {
  name: 'LoginComponent',
  inject: {
    sessionService: {from: 'sessionService'},
    webSocketService: {from: 'webSocketService'},
    notificationService: {from: 'notificationService'},
  },
  created() {
    if (this.$route.query.logout === "true") {
      this.sessionService.signOut();
    }
  },
  data() {
    return {
      email: '',
      password: '',
      showErrorMsg: false,
      errorTextContent: "",
      errorMessages: {
        notFound: "Your email or password is incorrect, try again.",
        error: "Something went wrong, please try again later."
      },
    };
  },
  methods: {
    async onSubmit() {
      try {
        const user = await this.sessionService.asyncSignIn(this.email, this.password);
        if (!user) {
          this.errorTextContent = this.errorMessages.notFound;
          this.showErrorMsg = true;
          return;
        }

        if (user.role === "admin") {
          await router.push({name: "admin-overview"});
        } else {
          await router.push({name: "expert-overview"})
        }
      } catch (e) {
        this.errorTextContent = this.errorMessages.error;
        this.showErrorMsg = true;
      }
    },
  },
};
</script>

<style scoped>

</style>
