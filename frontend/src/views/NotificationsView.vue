<template>
  <div class="w-full flex flex-col space-y-2 p-10">

    <div class="flex flex-row items-center justify-between w-full mb-10">
      <h1 class="font-semibold">Notifications</h1>
      <button class="text-xs bg-black text-white px-2 py-1 rounded truncate">Mark all as read</button>
    </div>

    <div v-if="this.isLoading">
      <div v-for="index of 5" :key="index"
           class="w-full animate-pulse flex flex-row justify-between space-x-2 rounded-lg shadow transition-colors
            duration-200 hover:bg-neutral-50 text-xs w-full bg-white flex items-center p-6 mb-2 gap-2 cursor-pointer">

        <div class="flex flex-col w-full">
          <div class="h-3 my-2 bg-gray-200 rounded-full w-1/5"></div>
          <div class="h-3 my-2 bg-gray-200 rounded-full w-2/5"></div>
        </div>
        <div class="h-3 my-2 bg-gray-200 rounded-full w-12"></div>
      </div>
    </div>

    <div v-if="!this.isLoading && notificationsCopy.length <= 0" class="text-xs">
      No notifications at the moment.
    </div>

    <div v-for="notification in notificationsCopy" :key="notification.id"
         class="rounded-lg shadow transition-colors duration-200 hover:bg-neutral-50
                text-xs w-full bg-white flex items-center p-8 gap-2 cursor-pointer"
         :class="notification.isRead ? 'text-neutral-600' : 'text-neutral-700'"
         @click="notification.isRead = true">

      <div v-if="!notification.isRead" class="flex justify-center items-center">
        <div class="bg-blue-400 w-2 h-2 rounded-full"></div>
      </div>

      <div class="w-full space-y-1.5">
        <div class="w-full flex flex-row space-x-2">
          <h1 class="font-semibold">{{ notification.title }}</h1>
          <div class="text-[10px] space-x-2">
            <span>{{ formatDate(notification.dateCreated).dateTime.date }}</span>
            <span class="text-neutral-400 italic">{{ formatDate(notification.dateCreated).timeAgo }}</span>
          </div>
        </div>

        <div>
          <p :class="notification.isRead ? 'font-normal' : 'font-bold'">{{ notification.description }}</p>
        </div>
      </div>

      <!--      <button class="px-3 py-1 bg-neutral-900 rounded text-white">Accept</button>-->
      <!--      <button class="px-3 py-1 bg-red-600 rounded text-white">Deny</button>-->
      <EllipsisHorizontalIcon
          class="h-10 w-10 p-2 cursor-pointer text-black hover:bg-neutral-200 transition-colors duration-100 ease-in rounded-full"/>
    </div>

  </div>
</template>

<script>
import {EllipsisHorizontalIcon} from "@heroicons/vue/24/outline";
import {formatJavaDate, formatJavaDateToTimeAgo} from "@/utils/helperUtils";

export default {
  name: "NotificationsView",
  inject: {
    sessionService: {from: 'sessionService'},
    webSocketService: {from: 'webSocketService'},
    notificationService: {from: 'notificationService'}
  },
  components: {
    EllipsisHorizontalIcon,
  },
  async created() {
    this.isLoading = true;

    if (this.notificationService.notifications && this.notificationService.notifications.length > 0) {
      this.isLoading = false;
      return;
    }

    const userId = this.sessionService.getUserIdFromBrowserStorage();
    if (userId) {
      await this.notificationService.findAllNotificationsByUserId(userId);
    }

    this.isLoading = false;
  },
  watch: {
    notificationsCopy(newVal) {
      this.isLoading = newVal === null;
    },
  },
  computed: {
    notificationsCopy() {
      try {
        if (!this.notificationService || !this.notificationService.notifications || this.notificationService.notifications.length <= 0) {
          return [];
        }

        return this.notificationService.notifications
            .slice().reverse();
        // .sort((a, b) => {
        //   // Order by isRead (true to false)
        //   if (a.isRead === b.isRead) {
        //     // If isRead is the same, order by dateCreated (new to old)
        //     return new Date(b.dateCreated) - new Date(a.dateCreated);
        //   }
        //   return a.isRead ? 1 : -1; // true comes after false
        // });
      } catch (error) {
        // console.error('Error reversing notifications:', error);
        return [];
      }
    }
  },
  methods: {
    formatDate(timestamp) {
      return {
        dateTime: formatJavaDate(timestamp),
        timeAgo: formatJavaDateToTimeAgo(timestamp)
      };
    }
  },
  data() {
    return {
      notifications: null,
      isLoading: false,
    }
  }
}
</script>

<style scoped>

</style>