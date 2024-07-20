<template>
  <div class="flex justify-center h-screen items-center">
    <div
        class="fixed z-[-500] transform-gpu overflow-hidden blur-3xl"
        aria-hidden="true">
      <div
          class="relative aspect-[1155/678] w-[1rem] rotate-[200deg] bg-gradient-to-tr from-[#020029] to-[#9089fc] opacity-10 sm:left-[calc(50%-30rem)] sm:w-[72.1875rem]"
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
        class="bg-opacity-50 bg-blend-color-burn w-1/3 px-6 py-12 lg:px-8 rounded shadow-gray-950">
      <div class="sm:mx-auto sm:w-full sm:max-w-sm">
        <h2
            class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-neutral-950">
          Create your account
        </h2>
      </div>

      <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
        <form
            @submit.prevent="onSubmit"
            class="space-y-6"
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
                  required
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
                  required
                  class="pl-3 text-sm block w-full rounded border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6"/>
            </div>
          </div>

          <div>
            <div class="mt-2">
              <input
                  v-model="repeatPassword"
                  placeholder="Repeat password"
                  id="password-repeat"
                  name="password"
                  type="password"
                  required
                  class="pl-3 text-sm block w-full rounded border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset sm:text-sm sm:leading-6"/>
            </div>
          </div>

          <p v-show="showErrorMsg" class="text-sm text-red-400" ref="error">
            {{ this.errorTextContent }}
          </p>

          <!--          <div class="flex items-start">-->
          <!--            <div class="flex items-center h-5">-->
          <!--              <input id="terms" aria-describedby="terms" type="checkbox"-->
          <!--                     class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800"-->
          <!--                     required="">-->
          <!--            </div>-->
          <!--            <div class="ml-3 text-sm">-->
          <!--              <label for="terms" class="font-light">I accept the <a-->
          <!--                  class="font-medium text-primary-600 hover:underline dark:text-primary-500" href="#!">Terms and-->
          <!--                Conditions</a></label>-->
          <!--            </div>-->
          <!--          </div>-->

          <div>
            <button
                type="submit"
                class="flex w-full justify-center rounded bg-neutral-950 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-neutral-800 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2">
              Sign up
            </button>
          </div>
        </form>

        <p class="mt-10 text-center text-sm text-gray-500">
          Already have an account?
          {{ ' ' }}
          <RouterLink
              to="/"
              class="font-semibold leading-6 text-neutral-950 hover:text-neutral-800"
          >Log In
          </RouterLink
          >
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import CONFIG from '../../app-config';
import {errorHandler} from "@/utils/actionHandler";

export default {
  name: 'LoginComponent',
  inject: {
    registerService: {from: 'registerService'},
    sessionService: {from: 'sessionService'},
    webSocketService: {from: 'webSocketService'}
  },
  data() {
    return {
      email: '',
      password: '',
      repeatPassword: '',
      errorTextContent: '',
      invitationURL: '',
      showErrorMsg: false,
      errorMessages: {
        notEqual: 'Passwords are not equal, try again.',
        error: 'Something went wrong, please try again later.',
      },
    };
  },
  async beforeRouteEnter(to, from, next) {
    const invitationKey = to.query.token;
    let invitationKeyIsValid = null;

    try {
      invitationKeyIsValid = await fetch(
          CONFIG.BACKEND_URL + `/invitations/validation/${invitationKey}`,
          {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
            },
          }
      );

      const onValidInvitation = (vm) => {
        // remove WebSocket connection and logout when logged in
        if (vm.sessionService.isAuthenticated()) {
            vm.webSocketService.disconnect();
            vm.sessionService.signOut();
        }
        vm.invitationURL = invitationKey;
      };

      if (!invitationKeyIsValid || !invitationKeyIsValid.ok) {
        next(() => {
          next({name: 'login', query: {logout: true}});
        });
      } else {
        next(onValidInvitation);
      }
    } catch (e) {
      console.error(e);
      next(() => {
        next({name: 'login', query: {logout: true}});
      });
    }
  },
  methods: {
    async onSubmit() {
      this.showErrorMsg = false;

      if (this.password !== this.repeatPassword) {
        this.errorTextContent = this.errorMessages.notEqual;
        this.showErrorMsg = true;
        return;
      }

      const data = {
        email: this.email,
        password: this.password,
        invitationURL: this.invitationURL,
      };

      const registeredUser = await this.registerService.register(data);

      console.log(registeredUser)
      if (!registeredUser.ok && !registeredUser.id) {
        await errorHandler(null, await registeredUser.text());
        return;
      }

      await this.sessionService.asyncSignIn(data.email, data.password);
      this.$router.push({name: 'sign-up'});
    },
  },
};
</script>

<style scoped></style>
