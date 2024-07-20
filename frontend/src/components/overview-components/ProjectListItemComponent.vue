<template>
  <li class="w-full">
    <RouterLink :to="'/project/' + project.id"
                class="grid grid-cols-8 rounded-lg bg-white hover:bg-neutral-100 hover:-translate-y-1 transition-all duration-200 my-2 py-8 px-8 items-center">
      <div class="flex min-w-0 gap-x-4 col-span-2">

        <img class="h-12 w-12 flex-none rounded" :src="getImageBLOBUrlProject()" alt=""/>


        <div class="min-w-0 flex-auto text-start space-y-2">
          <div class="space-x-2 truncate flex flex-row">
            <span class="text-sm font-semibold leading-6 text-gray-900 w-fit">{{ project.name }}</span>

          </div>
          <p class="mt-1 truncate text-xs leading-5 text-gray-500">{{ project.description }}</p>
        </div>
      </div>
      <div class="col-span-3 flex flex-row space-x-1 justify-center">
        <div v-for="projectRole in project.projectRoles" :key="projectRole.id" class="flex flex-row space-x-3">
          <div class="text-[10px] w-fit h-fit bg-neutral-300 text-neutral-700 rounded-full px-2 py-0.5 truncate">
            {{ projectRole.sector.sectorName }}
          </div>
          <div class="text-[10px] w-fit h-fit bg-neutral-300 text-neutral-700 rounded-full px-2 py-0.5 truncate">
            {{ projectRole.roles[0].roleName }}
          </div>
          <div class="text-[10px] w-fit h-fit bg-neutral-300 text-neutral-700 rounded-full px-2 py-0.5 truncate">
            {{ projectRole.tools[0].toolName }}
          </div>
        </div>
      </div>
      <div class="col-span-1 flex flex-r items-center justify-center">
        <p class=" truncate text-[10px] leading-5 px-2 rounded-full h-fit w-fit font-normal"
           :class="project.status === 1 ? 'bg-emerald-200 text-emerald-700' : ' bg-red-300 text-red-600'">
          {{ project.status === 1 ? 'ACTIVE' : 'INACTIVE' }}</p>
      </div>

      <div class="col-span-1 flex flex-col items-end">
        <div class="text-end text-xs">
          {{ getDateCreatedString() }}
        </div>
      </div>


        <Menu as="div" class="relative flex flex-col w-full inline-block text-left">
          <MenuButton @click.prevent class="self-end">
            <EllipsisHorizontalIcon
                class="w-9 h-9 transition-colors duration-200 rounded-full p-2 hover:bg-neutral-200"/>
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
                class="absolute z-50 right-0 top-9 w-56 p-2 rounded-lg bg-white flex-col divide-neutral-100 text-start origin-top-right bg-white shadow-lg ring-1 ring-black/5 focus:outline-none">

              <MenuItem class="hover:bg-neutral-100">
                <div @click.prevent="changeStatus"
                     class="cursor-pointer rounded-lg group flex w-full items-center p-3 text-sm">
                  <span>Change Status</span>
                </div>
              </MenuItem>
              <MenuItem class="hover:bg-neutral-100">
                <div @click.prevent="deleteProject"
                     class="cursor-pointer rounded-lg group flex w-full items-center p-3 text-sm">
                  <span class="text-red-400">Delete Project</span>
                </div>
              </MenuItem>

            </MenuItems>
          </transition>
        </Menu>
    </RouterLink>
  </li>
</template>

<script>
import {EllipsisHorizontalIcon} from "@heroicons/vue/24/outline";
import {MenuButton, MenuItems, MenuItem, Menu} from "@headlessui/vue";
import {getImageBLOBUrlProject} from "@/utils/imageDecoder";


export default {
  name: "ProjectListItemComponent",
  props: ['project'],
  components: {
    MenuItems,
    MenuButton,
    MenuItem,
    Menu,
    EllipsisHorizontalIcon
  },
  methods: {
    getImageBLOBUrlProject() {
      return getImageBLOBUrlProject(this.project);
    },
    navigateToProject(route) {
      this.$router.push(route)
    },
    getDateCreatedString() {
      const seconds = Math.floor((new Date() - new Date(this.project.dateCreated)) / 1000);
      const minutes = Math.floor(seconds / 60);
      const hours = Math.floor(minutes / 60);
      const days = Math.floor(hours / 24);
      const weeks = Math.floor(days / 7);
      const months = Math.floor(days / 30);

      // If more than 60 minutes return the hours, if more than 24 hours return the days, if more than 30 days return the date.
      if (months > 0) {
        const date = new Date(this.project.dateCreated);

        const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        const monthAbbreviation = months[date.getMonth()];
        const day = date.getDate();
        const year = date.getFullYear();

        return `${monthAbbreviation} ${day}, ${year}`;
      } else if (weeks > 0) {
        return weeks + (weeks === 1 ? " week" : " weeks") + " ago";
      } else if (days > 0) {
        return days + (days === 1 ? " day" : " days") + " ago";
      } else if (hours > 0) {
        return hours + (hours === 1 ? " hour" : " hours") + " ago";
      } else if (minutes > 0) {
        return minutes + (minutes === 1 ? " minute" : " minutes") + " ago";
      } else {
        return seconds + (seconds === 1 ? " second" : " seconds") + " ago";
      }
    }
  }
}
</script>

<style scoped>

</style>
