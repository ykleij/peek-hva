<template>
  <div class="grid md:grid-cols-2 w-full h-full gap-4 p-5">
    <div class="col-span-1 bg-white p-5">
      <div class="grid gap-2 sm:grid-cols-2 sm:gap-3">
        <div class="sm:col-span-2 text-start">
          <label for="name" class="block mb-2 text-sm font-medium text-gray-900"
            >How many people can use this invitation link?</label
          >
          <input
            type="number"
            min="1"
            @blur="onBlur($event)"
            v-model="maxUsers"
            class="bg-white border text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
            placeholder="Maximum amount of people"
            required="" />
        </div>
        <div class="sm:col-span-2 text-start">
          <label
            for="name"
            class="block mb-2 text-sm font-medium text-gray-900">
            Event name
          </label>
          <input
            type="text"
            @blur="onBlur($event)"
            v-model="eventName"
            class="bg-white border text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
            placeholder="Event name"
            required="" />
        </div>
      </div>
      <button
        @click="handleCreateInvite"
        type="submit"
        class="inline-flex items-center px-5 py-2.5 mt-4 sm:mt-6 text-sm font-medium text-center text-white bg-black rounded-lg focus:ring-4 focus:ring-primary-200 hover:bg-primary-800">
        Create invite
      </button>
    </div>
    <div class="col-span-1 h-full bg-white p-5 overflow-y-scroll">
      <div v-if="isLoading">
        <div
          v-for="index in 5"
          :key="index"
          class="rounded-lg border border-neutral-200 mt-4 px-8 py-4 w-full animate-pulse flex flex-row justify-between space-x-2">
          <div class="flex flex-col w-full">
            <div class="h-3 my-2 bg-gray-200 rounded-full w-3/5"></div>
            <div class="h-3 my-2 bg-gray-200 rounded-full w-24"></div>
          </div>
          <div class="flex flex-row items-center space-x-2">
            <div class="h-3 my-3 bg-gray-200 rounded-full w-12"></div>
            <div class="h-3 my-3 bg-gray-200 rounded-full w-12"></div>
          </div>
        </div>
      </div>

      <ul v-if="eventsCopy.length > 0">
        <li
          v-for="event in eventsCopy"
          :key="event.id"
          class="w-full flex flex-row justify-between p-8 rounded-lg border border-neutral-200 mt-4">
          <span class="font-semibold">{{ event.title }}</span>
          <div class="space-x-2 text-xs">
            <button
              class="rounded px-2 py-1 bg-red text-red-600 text-black border border-none"
              @click="handleDeleteEvent(event)">
              delete
            </button>
            <button
              class="rounded px-2 py-1 bg-red text-black border border-black"
              @click="handleOpenUsers(event)">
              {{ event.usedByUsers.length }} of {{ event.maxUsers }} users
            </button>

            <button
              class="rounded px-2 py-1 bg-red text-black border border-black"
              @click="handleShare(event)">
              share
            </button>
            <button
              @click="handleCopy(event.url)"
              class="rounded px-2 py-1 bg-black text-white">
              copy
            </button>
          </div>
        </li>
      </ul>
    </div>

    <TransitionRoot appear :show="isOpen" as="template">
      <Dialog as="div" @close="closeModal" class="relative z-50">
        <TransitionChild
          as="template"
          enter="duration-300 ease-out"
          enter-from="opacity-0"
          enter-to="opacity-100"
          leave="duration-200 ease-in"
          leave-from="opacity-100"
          leave-to="opacity-0">
          <div class="fixed inset-0 bg-black/50" />
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
                class="w-full h-full max-w-md h-56 space-y-5 transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">
                <div class="w-full flex flex-row justify-between">
                  <DialogTitle
                    as="h3"
                    class="text-lg font-medium leading-6 text-gray-900">
                    Send invitation link
                  </DialogTitle>
                  <button @click="closeModal" class="p-2">
                    <svg
                      class="h-3.5 w-3.5 overflow-visible stroke-slate-900"
                      fill="none"
                      stroke-width="1.5"
                      stroke-linecap="round"
                      xmlns="http://www.w3.org/2000/svg">
                      <path d="M0 0L14 14M14 0L0 14"></path>
                    </svg>
                  </button>
                </div>

                <div class="flex flex-row space-x-10">
                  <input
                    v-model="sendToEmail"
                    type="text"
                    @blur="onBlur($event)"
                    class="bg-white border text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
                    placeholder="Add email addresses"
                    required="" />
                  <button
                    class="rounded px-2 w-32 bg-black text-white text-xs"
                    @click="handleSendToEmail">
                    Send invite
                  </button>
                </div>

                <div>
                  <span>Link</span>
                  <ul class="text-xs">
                    <li>
                      <router-link
                        :to="
                          `/${this.pathName}?${this.queryParamName}=` +
                          currentEvent?.url
                        "
                        class="hover:border-b border-blue-600 text-blue-600">
                        URL
                      </router-link>
                    </li>
                  </ul>
                </div>
              </DialogPanel>
            </TransitionChild>
          </div>
        </div>
      </Dialog>
    </TransitionRoot>

    <TransitionRoot appear :show="usersModalIsOpen" as="template">
      <Dialog as="div" @close="closeUsersModal" class="relative z-50">
        <TransitionChild
          as="template"
          enter="duration-300 ease-out"
          enter-from="opacity-0"
          enter-to="opacity-100"
          leave="duration-200 ease-in"
          leave-from="opacity-100"
          leave-to="opacity-0">
          <div class="fixed inset-0 bg-black/50" />
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
                class="w-full max-w-md h-full max-h-[60%] overflow-scroll hide-scrollbar space-y-5 transform overflow-hidden rounded-2xl bg-white p-6 text-left align-middle shadow-xl transition-all">
                <div class="w-full flex flex-row justify-between">
                  <DialogTitle
                    as="h3"
                    class="text-lg font-medium leading-6 text-gray-900">
                    Users used the invite
                  </DialogTitle>
                  <button @click="closeUsersModal" class="p-2">
                    <svg
                      class="h-3.5 w-3.5 overflow-visible stroke-slate-900"
                      fill="none"
                      stroke-width="1.5"
                      stroke-linecap="round"
                      xmlns="http://www.w3.org/2000/svg">
                      <path d="M0 0L14 14M14 0L0 14"></path>
                    </svg>
                  </button>
                </div>

                <div class="max-w-2xl mx-auto">
                  <div
                    class="p-3 max-w-md bg-white rounded-lg border shadow-md sm:p-3 dark:bg-gray-800 dark:border-gray-700">
                    <div class="flow-root">
                      <ul
                        role="list"
                        class="divide-y divide-gray-200 dark:divide-gray-700">
                        <li
                          v-for="user in currentEvent.usedByUsers"
                          :key="user.id"
                          class="py-3 sm:py-4">
                          <div class="flex items-center space-x-4">
                            <div class="flex-1 min-w-0">
                              <p
                                class="text-sm font-medium text-gray-900 truncate dark:text-white">
                                {{ user.email }}
                              </p>
                            </div>
                            <div
                              class="inline-flex items-center text-base font-semibold text-gray-900 dark:text-white">
                              EXPERT
                            </div>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </DialogPanel>
            </TransitionChild>
          </div>
        </div>
      </Dialog>
    </TransitionRoot>
  </div>
</template>

<script>
import {
  Dialog,
  DialogPanel,
  DialogTitle,
  TransitionChild,
  TransitionRoot,
} from '@headlessui/vue';
import { errorHandler, showError, successHandler } from '@/utils/actionHandler';

export default {
  name: 'InvitationLinksView',
  components: {
    TransitionChild,
    Dialog,
    DialogTitle,
    DialogPanel,
    TransitionRoot,
  },
  inject: {
    invitationService: { from: 'invitationService' },
    generalService: { from: 'generalService' },
  },
  data() {
    return {
      isOpen: false,
      usersModalIsOpen: false,
      isLoading: false,
      currentEvent: null,
      maxUsers: 1,
      sendToEmail: '',
      sendToURL: '',
      eventName: null,
      events: [],
      pathName: 'pre-sign-up', // path name used for redirect links
      queryParamName: 'token', // query param name used for redirect links
    };
  },
  computed: {
    eventsCopy() {
      try {
        if (!this.invitationService.entities) return [];

        return this.invitationService.entities.slice().reverse();
      } catch (e) {
        return [];
      }
    },
  },
  async mounted() {
    try {
      this.isLoading = true;

      if (
        this.invitationService.entities &&
        this.invitationService.entities.length > 0
      ) {
        this.events = this.invitationService.entities;
        this.isLoading = false;
        return;
      }

      await this.invitationService.asyncFindAll();
      this.isLoading = false;
    } catch (error) {
      await errorHandler(error);
    }
  },
  methods: {
    async handleCreateInvite() {
      try {
        // check if event already exists
        const eventAlreadyExists = this.eventsCopy.find(
          (event) => event.title === this.eventName
        );

        // // if event already exists, dont even send a request to the server
        if (eventAlreadyExists) {
          return showError('Event already exists!');
        }

        const data = {
          title: this.eventName,
          maxUsers: this.maxUsers,
        };

        this.invitationService.asyncSave(data);
        await successHandler('Created invite link!');
      } catch (error) {
        await errorHandler(error);
      }
    },
    async handleSendToEmail() {
      try {
        const data = {
          email: this.sendToEmail,
          url: this.currentEvent.url,
        };
        console.log(data);
        await this.generalService.sendInvitation(data);
        await successHandler('Sent invite link!');
      } catch (error) {
        console.log(error);
        await errorHandler('Could not send invite!');
      }
    },
    async handleCopy(url) {
      const baseUrl = window.location.origin;

      const link = `${baseUrl}/#/${this.pathName}?${this.queryParamName}=${url}`;

      try {
        await navigator.clipboard.writeText(link);
        await successHandler('Copied to clipboard!');
      } catch (error) {
        console.error('Error copying to clipboard:', error);
      }
    },

    async handleDeleteEvent(deletedEvent) {
      try {
        // find the index
        const index = this.events.findIndex(
          (event) => event.id === deletedEvent.id
        );

        // if index is found, splice 1 on that index (deletes)
        if (index !== -1) {
          this.events.splice(index, 1);
        }
        await this.generalService.deleteInvitation(deletedEvent.id);
        await successHandler('Invite link deleted!');
      } catch (error) {
        console.log(error);
        await errorHandler('Could not send invite!');
      }
    },

    handleShare(event) {
      this.currentEvent = event;
      this.url = event.url;
      this.openModal();
    },

    handleOpenUsers(event) {
      this.currentEvent = event;
      if (event.usedByUsers.length !== 0) {
        this.openUsersModal();
      }
    },

    closeModal() {
      this.isOpen = false;
    },

    openModal() {
      this.isOpen = true;
    },

    openUsersModal() {
      this.usersModalIsOpen = true;
    },

    closeUsersModal() {
      this.usersModalIsOpen = false;
    },

    onBlur(e) {
      if (!e.target.value) {
        e.target.style.borderColor = 'red';
      } else {
        e.target.style.borderColor = 'gray';
      }
    },
  },
};
</script>

<style scoped></style>
