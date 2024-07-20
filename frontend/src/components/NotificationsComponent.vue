<template>
  <Menu as="div" class="relative w-9 h-9 inline-block text-left">
    <MenuButton>
      <div
          class="btn top-0.5 relative !inline-flex !items-center justify-center w-9 h-9 gap-x-2 rounded-[6.25rem] text-sm tracking-[.00714em] text-center font-medium hover:bg-primary-600/[0.08] focus:bg-primary-600/[0.08]">
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 700 700">
          <path fill="currentColor"
                d="M579 372q0 24 9 45t25 37t37 25t45 9v93H0v-93q24 0 45-9t37-25t25-37t9-45V233q0-48 18-90t50-74t73-50t90-18t90 18t74 50t50 74t18 90v139zM347 696q-32 0-56-20t-33-49h179q-8 30-32 49t-58 20z"/>
        </svg>
        <span
            class="pointer-events-none border-[2px] border-white absolute top-[-0.15rem] h-5 flex items-center justify-center rounded-full text-[11px] leading-none tracking-[.045em] font-medium bg-neutral-800 text-white"
            :class="newNotifications.length > 9 ? 'w-6 right-[-0.18rem]' : 'w-5 right-[0.08rem]'">
              {{ newNotifications.length }}
        </span>
      </div>
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
          class="absolute right-0 w-96 p-3 items-center bg-white flex flex-col justify-center divide-neutral-100 text-start origin-top-right rounded-lg bg-white shadow-lg ring-1 ring-black/5 focus:outline-none">

        <div class="flex flex-row items-center justify-between p-2 mb-1 w-full">
          <RouterLink :to="{name: 'notifications'}" class="font-bold text-center mx-3">Notifications</RouterLink>
          <button @click="clearNotifications" class="text-xs bg-black text-white px-2 py-1 rounded">Clear all</button>
        </div>

        <div v-if="!this.isLoading && newNotifications.length <= 0" class="text-xs">
          No new notifications at the moment.
        </div>

        <div class="max-h-72 overflow-y-auto p-1">
          <div v-for="notification in newNotifications" as="div" :key="notification.id"
               class="rounded hover:bg-neutral-100 text-xs flex py-4 px-6 gap-2 cursor-pointer">

            <div class="flex justify-center items-center">
              <div class="bg-blue-400 w-2 h-2 rounded-full"></div>
            </div>

            <div class="w-full space-y-1.5">
              <div class="w-full flex flex-row justify-between">
                <h1 class="font-bold">{{ notification.title }}</h1>
                <span class="text-neutral-500">{{ formatDate(notification.dateCreated) }}</span>
              </div>

              <div>
                <p>{{ notification.description }}</p>
              </div>
            </div>
          </div>
        </div>

      </MenuItems>
    </transition>
  </Menu>
</template>

<script>
import {
  Menu,
  MenuButton,
  MenuItems,
} from '@headlessui/vue'
import {formatJavaDateToTimeAgo} from "@/utils/helperUtils";

export default {
  name: "NotificationsComponent",
  inject: {
    sessionService: {from: 'sessionService'},
    webSocketService: {from: 'webSocketService'},
    notificationService: {from: 'notificationService'}
  },
  components: {
    Menu,
    MenuButton,
    MenuItems,
  },
  async created() {
    this.isLoading = true;

    const userId = this.sessionService.getUserIdFromBrowserStorage();
    if (userId) {
      await this.notificationService.findAllNotificationsByUserId(userId);
    }
    this.isLoading = false;
  },
  data() {
    return {
      isLoading: false,
    }
  },
  methods: {
    async clearNotifications() {
      if (this.newNotifications.length <= 0) return;

      try {
        // await this.notificationService.updateNotificationBatchForUser(this.newNotifications);
        this.newNotifications.forEach((notification) => {
          notification.isRead = true;
        });
      } catch (error) {
        // Handle errors appropriately
        console.error("Error updating notifications:", error);

        // If there's an error, you might want to roll back the local updates
        this.newNotifications.forEach((notification) => {
          notification.isRead = false;
        });
      }
    },
    formatDate(timestamp) {
      return formatJavaDateToTimeAgo(timestamp);
    }
  },
  computed: {
    newNotifications() {
      try {
        if (!this.notificationService || !this.notificationService.notifications || this.notificationService.notifications.length <= 0) {
          return [];
        }

        return this.notificationService.notifications.slice().reverse().filter(notification => notification.isRead === false);
      } catch (error) {
        return [];
      }
    }
  },
}
</script>

<style scoped>

</style>