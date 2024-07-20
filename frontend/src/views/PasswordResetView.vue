<template>
  <div class="border-l border-neutral-200 flex items-center justify-center h-screen">
    <div class="flex-col text-center">
      <h2 class="mt-10 text-2xl font-bold tracking-tight text-neutral-900">Reset your password</h2>
      <div class="mt-2">
        <form @submit.prevent="onSubmit" method="post">
          <div class="w-72">
            <div class="relative mt-10">
              <input v-model="newPassword"
                     placeholder="New password"
                     :type="passwordFieldType"
                     class="pl-3 pr-10 text-sm block w-full rounded border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6">
              <button @click="togglePasswordVisibility" class=" mt-2 mr-2 h-5 w-10 absolute inset-y-0 right-0 px-3 py-1.5 bg-gray-200 text-gray-700 rounded flex items-center justify-center">
                <p v-if="!showPassword"
                   class="text-xs">show</p>
                <p v-else class="text-xs">hide</p>
              </button>
            </div>
          </div>

          <div class="">
            <input v-model="repeatPassword"
                   placeholder="Repeat your new password"
                   :type="passwordFieldType"
                   class="w-96 mt-5 pl-3 text-sm block w-full rounded border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6">
          </div>

          <div class="text-center">
            <p v-show="showValidationMessage" class="text-sm mt-3 text-red-400">{{ incorrectPasswordMessage }}</p>
            <button @click="sendResetPassword()"
                    :disabled="disableRequestButton"
                    type="submit"
                    class="mt-7 flex w-full justify-center rounded bg-neutral-900 transition-colors px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-neutral-800 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
              Reset password
            </button>
            <div v-show="awaitPromise" class="flex justify-center mt-5">
              <SpinnerComponent/>
            </div>
            <div v-show="showErrorMsg" class="mt-10 flex flex-col text-center justify-center text-sm">
              <p class="text-sm mt-3 text-red-400" ref="error">{{ this.errorTextContent }}</p>
              <RouterLink to="/request-password-reset"
                          class="font-semibold leading-6 text-neutral-950 hover:text-neutral-700">Change password
              </RouterLink>
            </div>
          </div>
        </form>
        <div v-show="loginRedirect" class="mt-10 flex flex-col text-center justify-center text-sm">
          <p>Your password is reset!</p>
          <RouterLink to="/" class="font-semibold leading-6 text-neutral-950 hover:text-neutral-700">Back to login
          </RouterLink>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import SpinnerComponent from "@/components/SpinnerComponent";

export default {
  name: "PasswordResetView",
  components: {SpinnerComponent},
  inject: {
    resetPasswordService: {from: 'resetPasswordService'}
  },
  data() {
    return {
      newPassword: '',
      repeatPassword: '',
      showPassword: false,
      url: null,
      awaitPromise: false,
      loginRedirect: false,
      showValidationMessage: false,
      showErrorMsg: false,
      errorTextContent: "",
      errorMessages: {
        urlNotValid: "Link not valid, please request a new one below.",
        error: "Something went wrong, please try again later."
      },
    }
  },
  computed: {
    disableRequestButton() {
      return this.loginRedirect || this.awaitPromise;
    },
    incorrectPasswordMessage() {
      if (this.newPassword.length < 8 || this.newPassword.length > 20) {
        return "Password must have 8-20 characters";
      } else   if (!/\d/.test(this.newPassword)) {
        return "Password must have at least one digit";
      } else if (this.newPassword !== this.repeatPassword) {
        return "Fields do not match"
      }
      return '';
    },
    passwordFieldType() {
      return this.showPassword ? 'text' : 'password';
    },
  },
  methods: {
    async sendResetPassword() {
      try {
        this.reboot();

        if (this.incorrectPasswordMessage !== '') {
          return;
        }

        const result = await this.resetPasswordService.resetPassword(this.url, this.newPassword);

        if (!result) {
          this.errorTextContent = this.errorMessages.urlNotValid;
          this.showErrorMsg = true;
        } else {
          this.loginRedirect = true;
        }
      } catch (e) {
        this.errorTextContent = this.errorMessages.error;
        this.showErrorMsg = true;
      } finally {
        this.awaitPromise = false;
      }
    },
    getUrl() {
      const path = this.$route.fullPath;
      const index = path.lastIndexOf("token=");
      this.url = path.substring(index + 6);
    },
    resetErrorMessages() {
      this.loginRedirect = false;
      this.showErrorMsg = false;
    },
    reboot() {
      this.showValidationMessage = true;
      this.awaitPromise = true;
      this.getUrl();
      this.resetErrorMessages();
    },
    togglePasswordVisibility() {
      this.showPassword = !this.showPassword;
    },
  }
}
</script>

<style scoped>

</style>
