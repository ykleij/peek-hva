<template>
  <div class="border-l border-neutral-200 flex items-center justify-center h-screen">
    <div class="flex flex-col text-center">
      <h2 class="text-2xl font-bold tracking-tight text-neutral-900">Forgot your password?</h2>
      <h1 class="mt-2 text-sm text-gray-500">Enter your email and receive</h1>
      <h1 class="text-sm text-gray-500">a link to change your password.</h1>

      <form @submit.prevent="onSubmit">
        <input v-model="email" placeholder="Email" id="email" name="email" type="email" required=""
               class="mt-10 pl-3 text-sm block w-full rounded border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6"/>
        <button type="submit"
                :disabled="disableRequestButton"
                class="mt-5 flex w-full justify-center rounded bg-neutral-900 transition-colors px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-neutral-800 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
          Send me an email
        </button>
        <div v-show="awaitPromise" class="flex justify-center mt-5">
          <SpinnerComponent/>
        </div>
        <p v-show="emailSent" class="text-sm mt-3">Email was sent! Please check your email.</p>
        <p v-show="showErrorMsg" class="text-sm mt-3 text-red-400" ref="error">{{ this.errorTextContent }}</p>

        <div class="mt-5 flex justify-center text-sm">
          <RouterLink to="/" class="font-semibold leading-6 text-neutral-950 hover:text-neutral-700">Back to login
          </RouterLink>
        </div>
      </form>

    </div>
  </div>
</template>

<script>
import SpinnerComponent from "@/components/SpinnerComponent";
export default {
  name: "RequestPasswordResetView",
  components: {SpinnerComponent},
  inject: {
    resetPasswordService: {from: 'resetPasswordService'}
  },
  data() {
    return {
      email: '',
      showErrorMsg: false,
      errorTextContent: "",
      emailSent: false,
      awaitPromise: false,
      errorMessages: {
        notFound: "No account exists for this email, try again.",
        error: "Something went wrong, please try again later."
      },
    }
  },
  computed: {
    disableRequestButton() {
      return this.emailSent || this.awaitPromise;
    }
  },
  methods: {
    async onSubmit() {
      this.showErrorMsg = false;
      this.awaitPromise = true;
      try {
        const baseUrl = `${window.location.origin}/#/password-reset?token=`;
        const request = await this.resetPasswordService.requestResetPassword(this.email, baseUrl);
        if (!request) {
          this.errorTextContent = this.errorMessages.notFound;
          this.showErrorMsg = true;
      } else if (request) {
          this.showErrorMsg = false;
          this.emailSent = true;
        }
      } catch (e) {
        this.errorTextContent = this.errorMessages.error;
        this.showErrorMsg = true;
      }
      this.awaitPromise = false;
    }
  }
}
</script>

<style scoped>

</style>
