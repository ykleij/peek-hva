<template>
  <div>

    <SearchBarComponent class="w-full mb-6" @searchKeyup="onSearchKeyup"/>

<!--    <div class="w-full grid grid-cols-2 justify-between my-5">-->
<!--      <Menu as="div" class="col-span-1 relative w-full inline-block text-left">-->
<!--        <MenuButton>-->
<!--          <div-->
<!--              class="cursor-pointer flex flex-row items-center space-x-2 px-3 py-2 bg-neutral-200 rounded-full font-semibold text-xs">-->
<!--            <ArrowsUpDownIcon class="w-4 h-4"/>-->
<!--            <span>Sort</span>-->
<!--          </div>-->
<!--        </MenuButton>-->

<!--        <transition-->
<!--            enter-active-class="transition duration-100 ease-out"-->
<!--            enter-from-class="transform scale-95 opacity-0"-->
<!--            enter-to-class="transform scale-100 opacity-100"-->
<!--            leave-active-class="transition duration-75 ease-in"-->
<!--            leave-from-class="transform scale-100 opacity-100"-->
<!--            leave-to-class="transform scale-95 opacity-0"-->
<!--        >-->
<!--          <MenuItems-->
<!--              class="absolute z-50 left-0 w-56 p-2 rounded-lg bg-white flex-col divide-neutral-100 text-start origin-top-left bg-white shadow-lg ring-1 ring-black/5 focus:outline-none">-->

<!--            <div class="py-2">-->
<!--              <h1 class="text-xs font-bold">Sort by</h1>-->
<!--            </div>-->

<!--            <MenuItem class="hover:bg-neutral-200 transition-colors duration-50 text-center">-->
<!--              <div @click="clickMenuItem" class="cursor-pointer rounded bg-neutral-100 text-xs py-3">-->
<!--                <span>Date created</span>-->
<!--              </div>-->
<!--            </MenuItem>-->

<!--          </MenuItems>-->
<!--        </transition>-->
<!--      </Menu>-->

<!--    </div>-->

    <ul role="list" class="flex flex-col justify-center items-center">
      <ProjectListItemComponent
          v-for="project in filteredProjects"
          :key="project.id"
          :project="project"
      />
      <li class="text-sm" v-if="filteredProjects.length < 1">
        {{ projectsMessage }}
      </li>
    </ul>
  </div>
</template>

<script>
import SearchBarComponent from "@/components/overview-components/SearchBarComponent";
import ProjectListItemComponent from "@/components/overview-components/ProjectListItemComponent";
// import {ArrowsUpDownIcon} from "@heroicons/vue/24/solid";
// import {Menu, MenuButton, MenuItem, MenuItems} from "@headlessui/vue";


export default {
  name: "ProjectsListComponent",
  props: {
    projects: Array,
  },
  components: {
    // Menu,
    // MenuButton,
    // MenuItems,
    // MenuItem,
    ProjectListItemComponent,
    SearchBarComponent,
    // ArrowsUpDownIcon
  },
  methods: {
    onSearchKeyup(input) {
      this.selectedProject = null;
      if (input) {
        this.searchInput = input;
      } else {
        this.searchInput = null;
      }
    },
  },
  data() {
    return {
      searchInput: null,
    }
  },
  computed: {
    filteredProjects() {
      try {
        if (!this.searchInput) {
          return this.projects.slice().sort((p1, p2) => {
            if (p1.status !== p2.status) {
              return p2.status - p1.status;
            }

            return new Date(p2.dateCreated) - new Date(p1.dateCreated);
          });
        }

        return this.projects
            .filter((project) =>
                project.name ? project.name.toLowerCase().includes(this.searchInput.toLowerCase()) : null,
            )
            .sort((p1, p2) => {
              if (p1.status !== p2.status) {
                return p2.status - p1.status;
              }

              return new Date(p2.dateCreated) - new Date(p1.dateCreated);
            });
      } catch (e) {
        return [];
      }
    },


    projectsMessage() {
      return this.projects.length < 1
          ? "It's a little quiet here..."
          : this.filteredProjects.length < 1
              ? "Couldn't find any projects with that name."
              : '';
    },
  },
}
</script>

<style scoped>

</style>
