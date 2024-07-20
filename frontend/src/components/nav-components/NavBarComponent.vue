<template>
  <nav
      class="sticky text-neutral-700 top-0 max-w-screen z-50 bg-white h-20 grid grid-cols-3 md:flex justify-between items-center px-7 md:px-14 border-b border-neutral-200">

    <button v-if="isAdmin" @click="openModal" type="button"
            class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg col-span-1 md:hidden focus:outline-none">
      <span class="sr-only">Open main menu</span>
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="rgb(64 64 64)" class="w-6 h-6">
        <path fill-rule="evenodd"
              d="M3 6.75A.75.75 0 013.75 6h16.5a.75.75 0 010 1.5H3.75A.75.75 0 013 6.75zM3 12a.75.75 0 01.75-.75H12a.75.75 0 010 1.5H3.75A.75.75 0 013 12zm0 5.25a.75.75 0 01.75-.75h16.5a.75.75 0 010 1.5H3.75a.75.75 0 01-.75-.75z"
              clip-rule="evenodd"/>
      </svg>
    </button>

    <RouterLink class="col-span-1 justify-self-center"
                :class="isAdmin ? 'hidden sm:block' : ''"
                :to="logoClick">
      <LogoComponent :closeModal="closeModal"/>
    </RouterLink>

    <div class="flex flex-row items-center gap-7 col-span-2 sm:col-span-1 justify-end">

      <NotificationsComponent />

      <Menu as="div" class="relative w-7 h-7 inline-block text-left">
        <MenuButton>
          <img class="w-7 h-7 rounded-full shadow-sm cursor-pointer" :src="getImageBLOBUrl()"
               alt=""/>
        </MenuButton>

        <transition
            enter-active-class="transition duration-100 ease-out"
            enter-from-class="transform scale-95 opacity-0"
            enter-to-class="transform scale-100 opacity-100"
            leave-active-class="transition duration-75 ease-in"
            leave-from-class="transform scale-100 opacity-100"
            leave-to-class="transform scale-95 opacity-0"
        >
          <MenuItems
              class="absolute p-3 right-0 w-56 bg-white flex flex-col divide-neutral-100 text-start origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black/5 focus:outline-none">

            <div class="flex flex-row items-center justify-start p-3">
              <img class="w-10 h-10 rounded-full shadow-sm" :src="getImageBLOBUrl()"
                   alt=""/>
              <h1 class="font-semibold text-center mx-3">{{ username }}</h1>
            </div>

            <MenuItem v-slot="{ close }" v-for="link in links" as="div" :key="link.name"
                      class="rounded-lg hover:bg-neutral-100">
              <RouterLink :to="link.href" @click="close" class="group flex w-full items-center p-3 text-xs">

                <div class="flex space-x-3 items-center text-neutral-600">
                  <component :is="link.icon" class="w-5 h-5"></component>
                  <span>{{ link.name }}</span>
                </div>

              </RouterLink>
            </MenuItem>

            <MenuItem v-slot="{ close }" as="div"
                      class="rounded-lg hover:bg-neutral-100">
              <div @click="signOut(close)" class="cursor-pointer group flex w-full items-center p-3 text-xs">

                <div class="flex space-x-3 items-center text-red-600">
                  <ArrowLeftOnRectangleIcon class="w-5 h-5"></ArrowLeftOnRectangleIcon>
                  <span>Logout</span>
                </div>

              </div>
            </MenuItem>


          </MenuItems>
        </transition>
      </Menu>
    </div>
  </nav>

  <TransitionRoot v-if="isAdmin" appear :show="isOpen" as="template">
    <Dialog as="div" @close="closeModal" class="relative z-50">
      <TransitionChild
          as="template"
          enter="duration-300 ease-in-out"
          enter-from="opacity-0"
          enter-to="opacity-100"
          leave="transition ease-in-out duration-300 transform"
          leave-from="opacity-100"
          leave-to="opacity-0"
      >
        <div class="fixed inset-0 bg-black bg-opacity-80 backdrop-blur-sm"/>
      </TransitionChild>

      <div class="fixed inset-0 overflow-hidden">
        <div
            class="absolute top-0 left-0"
        >
          <TransitionChild
              enter="transition ease-in-out duration-300 transform"
              enterFrom="opacity-0 -translate-x-80"
              enterTo="opacity-100 translate-x-0"
              leave="transition ease-in-out duration-300 transform"
              leaveFrom="opacity-100 translate-x-0"
              leaveTo="opacity-0 -translate-x-80"
          >
            <DialogPanel
                class="h-screen w-80 max-w-md transform overflow-hidden bg-white text-left align-middle shadow-xl transition-all flex flex-col"
            >

              <DialogTitle
                  as="h3"
                  class="text-lg font-semibold leading-6 text-gray-900 p-8 flex justify-between"
              >

                <button @click="closeModal" class="p-2">
                  <svg class="h-3.5 w-3.5 overflow-visible stroke-slate-900" fill="none" stroke-width="1.5"
                       stroke-linecap="round" xmlns="http://www.w3.org/2000/svg">
                    <path d="M0 0L14 14M14 0L0 14"></path>
                  </svg>
                </button>
              </DialogTitle>

              <div class="flex flex-col overflow-y-auto">
                <RouterLink v-for="item in navItems" :key="item.name" :to="{name:item.to}" @click="closeModal"
                            class="py-5 pl-10 text-neutral-700 transition-colors flex items-center text-base font-normal text-neutral-600 rounded-lg hover:bg-gray-100">
                  <component :is="item.icon" class="h-6 w-6"></component>
                  <span class="ml-3 text-sm text-neutral-600">{{ item.name }}</span>
                </RouterLink>
              </div>
            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>

<script>
import {
  TransitionRoot,
  TransitionChild,
  Dialog,
  DialogPanel,
  DialogTitle,
  Menu,
  MenuButton,
  MenuItems,
  MenuItem,
} from '@headlessui/vue'
import {UserIcon, ArrowLeftOnRectangleIcon, BellIcon} from '@heroicons/vue/24/solid'
import LogoComponent from "@/components/LogoComponent";
import * as NavItems from "@/components/nav-components/NavItems";
import {getImageBLOBUrl} from "@/utils/imageDecoder";
import NotificationsComponent from "@/components/NotificationsComponent";


export default {
  name: "NavBarComponent",
  inject: {
    sessionService: {from: 'sessionService'},
    webSocketService: {from: 'webSocketService'},
  },
  components: {
    NotificationsComponent,
    ArrowLeftOnRectangleIcon,
    Menu,
    MenuButton,
    MenuItems,
    MenuItem,
    LogoComponent,
    TransitionRoot,
    TransitionChild,
    Dialog,
    DialogPanel,
    DialogTitle,
    BellIcon,
  },
  methods: {
    closeModal() {
      this.isOpen = false
    },
    openModal() {
      this.isOpen = true
    },
    signOut(close) {
      close();
      this.$router.push({name: "login", query: {logout: true}})
    },
    getImageBLOBUrl() {
      const profileData = this.sessionService.getUserFromBrowserStorage();

      return getImageBLOBUrl(profileData.userProfile);
    }
  },
  computed: {
    username() {
      return this.sessionService.getUserFromBrowserStorage().userProfile.firstName;
    },
    logoClick() {
      if (this.$route.meta.requiresAuth && this.sessionService.isAdmin()) {
        return {name: "admin-overview"};
      } else {
        return {name: "login"};
      }
    }
  },
  data() {
    return {
      isAdmin: this.sessionService.isAdmin(),
      navItems: NavItems.items,
      isOpen: false,
      links: [
        {
          name: "Profile",
          href: "/profile",
          icon: UserIcon
        },
        {
          name: "Notifications",
          href: "/notifications",
          icon: BellIcon
        },
      ],
    }
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Abril+Fatface&display=swap');


</style>