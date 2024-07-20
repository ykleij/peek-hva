<template>
  <aside v-if="isAdmin" id="default-sidebar"
         class="z-40 h-full min-w-[18rem] max-w-[18rem] transition-transform -translate-x-full hidden md:block md:translate-x-0"
         aria-label="Sidenav">
    <div
        class="overflow-y-auto p-8 h-full bg-white border-r border-neutral-200">
      <ul class="space-y-2">
        <li v-for="item in navItems" :key="item.name">
          <RouterLink :to="{ name: item.to }" active-class="bg-gray-100"
                      class="flex items-center p-3 text-base font-normal text-neutral-600 rounded-lg hover:bg-gray-100 group">
            <component :is="item.icon" class="h-4 w-4"></component>
            <span class="ml-3 text-xs text-neutral-600">{{ item.name }}</span>
          </RouterLink>
        </li>
      </ul>
    </div>
  </aside>

</template>

<script>
import * as NavItems from "@/components/nav-components/NavItems";

export default {
  name: "SideNavComponent",
  inject: {sessionService: {from: 'sessionService'}},
  data() {
    return {
      isAdmin: this.sessionService.isAdmin(),
      navItems: NavItems.items,
    }
  }
}
</script>

<style scoped>

</style>