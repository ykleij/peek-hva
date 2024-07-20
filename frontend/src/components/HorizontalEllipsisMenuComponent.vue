<template>
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

        <MenuItem v-for="(menuItem, index) in menuItems" :key="index" class="hover:bg-neutral-100">
          <div @click.prevent="menuItem.action"
               class="cursor-pointer rounded-lg group flex w-full items-center p-3 text-sm">
            <span>{{ menuItem.text }}</span>
          </div>
        </MenuItem>


      </MenuItems>
    </transition>
  </Menu>
</template>

<script>
import {MenuButton, MenuItems, MenuItem, Menu} from "@headlessui/vue";
import {EllipsisHorizontalIcon} from "@heroicons/vue/24/outline";


export default {
  name: "HorizontalEllipsisMenuComponent",
  props: {
    menuItems: {
      type: Array,
      required: true,
    },
  },
  components: {
    MenuItems,
    MenuButton,
    MenuItem,
    Menu,
    EllipsisHorizontalIcon
  },
}
</script>

<style scoped>

</style>