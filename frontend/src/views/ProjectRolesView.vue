<template>
  <div class="w-full min-h-full flex flex-col p-4 gap-5 lg:grid lg:grid-cols-3">
    <div
      v-for="projectRole in projectRoles"
      :key="projectRole.title"
      class="rounded-lg col-span-1 text-center bg-white shadow-lg border border-neutral-200 p-7 space-y-10">
      <h1 class="font-semibold text-sm">{{ projectRole.title }}</h1>
      <div class="add border-b-2 flex flex-col pb-6 mb-10">
        <div class="grid gap-2 sm:grid-cols-2 sm:gap-3">
          <div class="sm:col-span-2 text-start">
            <label
              for="name"
              class="block mb-2 text-sm font-medium text-gray-900">
              {{ projectRole.title }} name
            </label>
            <input
              v-model="inputValues[projectRole.type]"
              type="text"
              class="bg-white border text-gray-900 text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5"
              placeholder="type name..."
              required="" />
          </div>
        </div>
        <button
          @click="handleCreateProjectRole(projectRole)"
          type="submit"
          class="inline-flex items-center px-5 py-2.5 mt-4 sm:mt-6 text-sm font-medium text-center text-white bg-black rounded-lg focus:ring-4 focus:ring-primary-200 hover:bg-primary-800">
          Create {{ projectRole.title }}
        </button>
      </div>
      <SearchBarComponent />

      <div v-if="this.isLoading">
        <div
          v-for="index of 5"
          :key="index"
          class="w-full py-2 animate-pulse flex flex-row justify-between space-x-2">
          <div class="flex flex-col w-full">
            <div class="h-3 my-2 bg-gray-200 rounded-full w-3/5"></div>
            <div class="h-3 my-2 bg-gray-200 rounded-full w-24"></div>
          </div>
          <div class="h-3 my-2 bg-gray-200 rounded-full w-12"></div>
        </div>
      </div>

      <ul v-if="projectRole.title === 'Sectors'">
        <li
          v-for="item in projectRole.list"
          :key="item.id"
          class="flex flex-row justify-between text-xs cursor-pointer rounded-lg bg-white hover:bg-neutral-100 transition-all duration-200 py-5 px-5 items-center">
          {{ item.sectorName }}
          {{ console.log(item) }}
          <div
            v-if="!item.inList"
            class="bg-red-500 text-white font-semibold py-1 px-3 rounded-lg shadow">
            Inactive
          </div>

          <div class="flex flex-col space-y-1">
            <HorizontalEllipsisMenuComponent
              :menuItems="menuItems(item, projectRole)" />
          </div>
        </li>
      </ul>

      <ul v-else-if="projectRole.title === 'Roles'">
        <li
          v-for="item in projectRole.list"
          :key="item.id"
          class="flex flex-row justify-between text-xs cursor-pointer rounded-lg bg-white hover:bg-neutral-100 transition-all duration-200 py-5 px-5 items-center">
          {{ item.roleName }}
          <div
            v-if="!item.inList"
            class="bg-red-500 text-white font-semibold py-1 px-3 rounded-lg shadow">
            Deleted
          </div>
          <div class="flex flex-col space-y-1">
            <HorizontalEllipsisMenuComponent
              :menuItems="menuItems(item, projectRole)" />
          </div>
        </li>
      </ul>
      <ul v-else-if="projectRole.title === 'Tools'">
        <li
          v-for="item in projectRole.list"
          :key="item.id"
          class="flex flex-row justify-between text-xs cursor-pointer rounded-lg bg-white hover:bg-neutral-100 transition-all duration-200 py-5 px-5 items-center">
          {{ item.toolName }}
          <div
            v-if="!item.inList"
            class="bg-red-500 text-white font-semibold py-1 px-3 rounded-lg shadow">
            Deleted
          </div>
          <div class="flex flex-col space-y-1">
            <HorizontalEllipsisMenuComponent
              :menuItems="menuItems(item, projectRole)" />
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import SearchBarComponent from '@/components/overview-components/SearchBarComponent';
import HorizontalEllipsisMenuComponent from '@/components/HorizontalEllipsisMenuComponent';
import {
  showError,
  successHandler,
  alertGetInput,
  showConfirmation,
} from '@/utils/actionHandler';

export default {
  name: 'ProjectRolesView',
  components: {
    HorizontalEllipsisMenuComponent,
    SearchBarComponent,
  },
  inject: {
    projectRoleService: {
      from: 'projectRoleService',
    },
  },
  computed: {
    sectors() {
      if (!this.projectRoleService.sectors) return [];
      return this.projectRoleService.sectors;
    },
    roles() {
      if (!this.projectRoleService.roles) return [];
      return this.projectRoleService.roles;
    },
    tools() {
      if (!this.projectRoleService.tools) return [];
      return this.projectRoleService.tools;
    },
  },
  async mounted() {
    this.isLoading = true;

    if (
      this.projectRoleService.sectors &&
      this.projectRoleService.roles &&
      this.projectRoleService.tools &&
      this.projectRoleService.sectors.length > 0 &&
      this.projectRoleService.roles.length > 0 &&
      this.projectRoleService.tools.length > 0
    ) {
      this.updateProjectRolesLists();
      this.isLoading = false;
      return;
    }

    await this.projectRoleService.getAllSectors();
    await this.projectRoleService.getAllRoles();
    await this.projectRoleService.getAllTools();

    this.updateProjectRolesLists();

    this.isLoading = false;
  },
  methods: {
    async refresh(type) {
      switch (type) {
        case 'roles':
          await this.projectRoleService.getAllRoles();

          break;
        case 'sectors':
          await this.projectRoleService.getAllSectors();
          break;
        case 'tools':
          await this.projectRoleService.getAllTools();
          break;
        default:
          await this.projectRoleService.getAllSectors();
          await this.projectRoleService.getAllRoles();
          await this.projectRoleService.getAllTools();
          break;
      }
      this.updateProjectRolesLists();
    },
    menuItems(item, projectRole) {
      const data = {
        id: JSON.stringify(item.id),
        type: projectRole.type,
        title: item.sectorName,
        newInList: item.inList,
      };

      return [
        { text: 'Edit', action: () => this.editItem(data) },
        { text: 'Toggle status', action: () => this.toggleItemStatus(data) },
      ];
    },
    async editItem(data) {
      const input = await alertGetInput('Edit title');

      if (input?.lenght === 0) {
        showError('You must fill in a title!');
      } else {
        data.title = input;
        await this.projectRoleService.editProjectRole(data);
        await this.refresh(data.type);
        await successHandler('Edited title');
      }
    },
    async toggleItemStatus(data) {
      const toggle = !data.newInList;
      data.newInList = toggle;

      const sure = await showConfirmation(
        'Are you sure you want to toggle this?'
      );

      if (sure) {
        await this.projectRoleService.editProjectRole(data);
        await this.refresh();
        await successHandler('Toggled!');
      }
    },

    updateProjectRolesLists() {
      this.projectRoles.forEach((role) => {
        role.list = this[`${role.title.toLowerCase()}`];
      });
    },
    async handleCreateProjectRole(projectRole) {
      try {
        const inputValue = this.inputValues[projectRole.type];

        const data = {
          type: projectRole.type,
          title: inputValue,
        };

        if (inputValue.length === 0) {
          return showError('You must fill in a title!');
        }

        await this.projectRoleService.createProjectRole(data);
        await this.refresh(data.type);
        successHandler('New project role created!');
      } catch (error) {
        console.log(error);
        showError('Could not create Project role');
      }
    },
    handleDeleteProjectRole(id) {
      console.log(id);
    },
  },
  data() {
    return {
      isLoading: false,
      inputValues: {},
      projectRoles: [
        {
          type: 'sectors',
          title: 'Sectors',
          list: [],
        },
        {
          type: 'roles',
          title: 'Roles',
          list: [],
        },
        {
          type: 'tools',
          title: 'Tools',
          list: [],
        },
      ],
    };
  },
};
</script>

<style scoped></style>
