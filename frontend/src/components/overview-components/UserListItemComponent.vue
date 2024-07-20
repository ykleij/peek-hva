<template>
  <li class="w-full">
    <RouterLink :to="'/profile/' + user.id"
                class="rounded-lg bg-white hover:bg-neutral-100 hover:-translate-y-1 transition-all duration-200 grid grid-cols-5 py-8 px-8 items-center">

      <div class="flex min-w-0 gap-x-4 col-span-1">
        <img class="h-12 w-12 flex-none rounded-full shadow-sm bg-gray-100" :src="getImageBLOBUrl()" alt=""/>

        <div class="min-w-0 flex-auto text-start space-y-2">

          <div class="space-x-1.5 truncate flex flex-row items-center">
            <span class="text-sm font-semibold leading-6 text-gray-900 w-fit">{{
                userProfile.firstName
              }} {{ userProfile.addition }} {{ userProfile.lastName }}</span>
            <p class="truncate text-[10px] leading-5 px-2 rounded-full h-fit w-fit font-normal hide-role-expert-overview"
               :class="user.role === 'admin' ? 'bg-red-300 text-red-600' : ' bg-neutral-200 text-neutral-600'">
              {{ user.role }}</p>
          </div>

          <p class="mt-1 truncate text-xs leading-5 text-gray-500">{{ userProfile.bio }}</p>
        </div>
      </div>

      <div class="col-span-1 flex flex-col items-center">
        <div class="text-end text-xs bg-black text-white font-light px-3 py-1 rounded">
          {{ user.email }}
        </div>
      </div>

      <div class="col-span-1 flex flex-col items-center">
        <div class="text-center items-center flex flex-col px-8"
             :class="userProfile.country && userProfile.residence ? ' bg-white border px-3 py-1 rounded' : ''">
          <div class="text-end text-xs">
            {{ userProfile.country }}
          </div>
          <div class="text-end text-xs">
            {{ userProfile.residence }}
          </div>
        </div>
      </div>

      <div class="col-span-1 flex flex-col items-center">
        <div class="text-end text-xs"
             :class="userProfile.phoneNumber ? ' bg-black text-white font-light px-3 py-1 rounded-full': ''">
          {{ userProfile.phoneNumber }}
        </div>
      </div>

      <div class="col-span-1 flex flex-col items-center">
        <div class="text-end text-xs">
          {{ dateCreated().date }}
        </div>
      </div>


    </RouterLink>
  </li>
</template>

<script>
import {getImageBLOBUrl} from "@/utils/imageDecoder";
import {formatJavaDate} from "@/utils/helperUtils";

export default {
  name: "UserListItemComponent",
  props: ['user'],
  methods: {
    getImageBLOBUrl() {
      return getImageBLOBUrl(this.userProfile);
    },
    dateCreated() {
      return formatJavaDate(this.user.dateCreated);
    }
  },
  data() {
    return {
      userProfile: this.user.userProfile,
    }
  },
}
</script>

<style scoped>

</style>
