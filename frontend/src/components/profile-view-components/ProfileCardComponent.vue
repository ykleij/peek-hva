<template>
  <TransitionRoot appear :show="showEditModal" as="template">
    <Dialog as="div" :show="showEditModal" class="relative z-50">
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
                class="w-full md:w-3/5 h-fit space-y-5 transform overflow-hidden rounded-lg bg-white p-10 text-center shadow-xl transition-all">

              <DialogTitle class="font-semibold text-neutral-700 text-xl">Edit Profile</DialogTitle>

              <ProfileEditComponent :countries="countries" @save-profile="saveProfile" @cancel="closeModal" v-model:profile="profileCopy"/>

            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>


  <div class="flex flex-row">
    <div class="rounded-sm w-11/12 bg-white rounded-lg h-min w-full">
      <div class="p-6 space-y-6">
        <div class="grid grid-cols-9 gap-6">
          <div class="col-span-4">
            <label for="fullName" class="font-medium text-gray-900 block mb-2">Full name</label>
            <span type="text"
                  id="fullName" class="font-light text-sm break-words">{{profileCopy.firstName + " " + profileCopy.addition + " " + profileCopy.lastName }}</span>
          </div>

          <div class="col-span-4 sm:col-start-1 sm:col-span-4">
            <label for="email" class="font-medium text-gray-900 block mb-2">Email address</label>
            <span type="text" id="email" class="font-light text-sm break-words">{{ this.email }}</span>
          </div>
          <div v-if="profileCopy.kvkNumber" class="col-span-4 sm:col-start-5 sm:col-span-5">
            <label for="email" class="font-medium text-gray-900 block mb-2">Kvk</label>
            <span type="text" id="email" class="font-light text-sm break-words">{{ profileCopy.kvkNumber }}</span>
          </div>
          <div v-if="profileCopy.linkedInUrl" class="col-span-4 sm:col-start-1 sm:col-span-4">
            <label for="email" class="font-medium text-gray-900 block mb-2">LinkedIn</label>
              <div class="flex justify-start">
                <a href="https://www.linkedin.com/in/mgtpeek/" class="mr-9 text-neutral-100">
                  <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="h-4 w-4"
                      fill="lightgrey"
                      viewBox="0 0 24 24">
                    <path
                        d="M4.98 3.5c0 1.381-1.11 2.5-2.48 2.5s-2.48-1.119-2.48-2.5c0-1.38 1.11-2.5 2.48-2.5s2.48 1.12 2.48 2.5zm.02 4.5h-5v16h5v-16zm7.982 0h-4.968v16h4.969v-8.399c0-4.67 6.029-5.052 6.029 0v8.399h4.988v-10.131c0-7.88-8.922-7.593-11.018-3.714v-2.155z"/>
                  </svg>
                </a>
              </div>
          </div>
          <div class="col-span-4 sm:col-start-5 sm:col-span-5">
            <label for="phonenumber" class="font-medium text-gray-900 block mb-2">Phone number</label>
            <span type="text" id="phonenumber" class="font-light text-sm">{{ profileCopy.phoneNumber }}</span>
          </div>

          <div class="col-span-4 sm:col-start-1 sm:col-span-4">
            <label for="country" class="font-medium text-gray-900 block mb-2">Country</label>
            <span type="text" id="country" class="font-light text-sm">{{ fullCountryName }}</span>
          </div>
          <div class="col-span-4 sm:col-start-5 sm:col-span-5">
            <label for="residence" class="font-medium text-gray-900 block mb-2">Residence</label>
            <span type="text" id="residence" class="font-light text-sm">{{ profileCopy.residence }}</span>
          </div>
        </div>
      </div>
    </div>

    <button v-if="showEditBtn" type="button" @click="openModal"
            class="hover:text-neutral-700 text-neutral-500 rounded-lg text-sm p-1.5 h-fit">
      Edit
    </button>
  </div>
</template>

<script>

import {UserProfile} from "@/models/UserProfile";
import {Dialog, DialogPanel, DialogTitle, TransitionChild, TransitionRoot} from "@headlessui/vue";
import ProfileEditComponent from "@/components/profile-view-components/ProfileEditComponent";
import {fetchCountries} from "@/utils/helperUtils";

export default {
  name: "ProfileCardComponent",
  inheritAttrs: false,
  components: {DialogTitle, ProfileEditComponent, Dialog, DialogPanel, TransitionChild, TransitionRoot},
  inject: {
    usersService: {
      from: 'usersService'
    },
    userProfileService: {
      from: 'userProfileService'
    },
    sessionService: {
      from: 'sessionService'
    },
  },
  props: ['profileModel', 'email'],
  emits: ['saver'],
  data() {
    return {
      profileCopy: null,
      editForm: false,
      showEditModal: false,
      countries: null,
      showEditBtn: false,
    }
  },
  created() {
    this.isMyProfile(this.$route);
    this.profileCopy = UserProfile.copyConstructor(this.profileModel);
    this.fetchCountries();
  },
  computed: {
    items() {
      if (!this.userProfileService.entities) return [];

      return this.usersService.entities;
    },

    // Add a new computed property to find the full country name
    fullCountryName() {
      if (this.countries && this.profileCopy) {
        const iso3 = this.profileCopy.country;
        const country = this.countries.find(c => c.code === iso3);

        return country ? country.name : 'Unknown Country';
      }

      return 'Unknown Country';
    },

  },
  methods: {
    isMyProfile(route) {
      this.showEditBtn = (route.params.id === '' || parseInt(route.params.id) === this.sessionService.getUserIdFromBrowserStorage());
    },

    saveProfile(profileCopy) {
      this.closeModal();
      this.profileCopy = {...profileCopy};
      this.$emit('saver', profileCopy);
    },
    openModal() {
      this.showEditModal = true;
    },
    closeModal() {
      this.showEditModal = false;
    },

    async fetchCountries() {
      this.countries = await fetchCountries();
    },
  },
}
</script>

<style scoped>

</style>
