<template>
  <section class="min-h-full min-w-full px-5 space-y-5">
    <div class="mx-auto py-8 px-4 space-y-7 max-w-5xl flex flex-col">
      <h2 class="mb-4 text-xl text-start font-bold text-gray-900">Project information</h2>
      <div class="grid gap-4 sm:grid-cols-2 sm:gap-6 border-b border-gray-900/10 pb-12 text-start">
        <div class="sm:col-span-2 text-start">
          <label for="name" class="block mb-1 text-sm font-medium text-neutral-900">Project Name*</label>
          <input type="text" v-model="this.projectName"
                 :class="this.inputStyle"
                 placeholder="Project name" required="">
        </div>
        <div class="sm:col-span-2 text-start">
          <label class="block mb-1 text-sm font-medium text-neutral-900">Project Image</label>
          <div class="mt-2 flex items-center gap-x-3">
            <img alt="Profile Picture" class="w-[100px] h-[100px] rounded object-scale-down"
                 :src="this.uploadedPfpPreviewUrl ? this.uploadedPfpPreviewUrl : require('@/assets/img/default-project-image.jpg')"
            />
            <input class="text-sm text-stone-500
                         file:px-2.5 file:py-1.5 file:text-sm
                         file:font-semibold file:text-neutral-800 file:font-semibold file:font-sans
                         file:shadow-sm file:ring-1 file:ring-inset file:outline-0 file:border-0
                         file:ring-gray-300 hover:file:bg-gray-50
                         file:rounded-md file:bg-white
                         hover:file:cursor-pointer"
                   type="file"
                   id="pfpInput"
                   name="picture"
                   @input="handlePfpInput"
                   ref="fileInputRef"
                   accept="image/*"
            />
          </div>
        </div>
        <div class="sm:col-span-2 text-start">
          <label for="description" class="block mb-1 text-sm font-medium text-neutral-900">Description*</label>
          <textarea id="description" rows="8" v-model="this.description"
                    :class="this.inputStyle"
                    placeholder="Your description here"></textarea>
        </div>
        <div class="w-full text-start">
          <label for="brand" class="block mb-1 text-sm font-medium text-neutral-900">Start Date*</label>
          <input type="date" v-model="this.startDate"
                 :class="this.inputStyle"
                 placeholder="Select start date" required="">
        </div>
        <div class="w-full text-start">
          <label for="brand" class="block mb-1 text-sm font-medium text-neutral-900">End Date*</label>
          <input type="date" v-model="this.endDate"
                 :class="this.inputStyle"
                 placeholder="Select end date" required="">
        </div>
      </div>

      <h2 class="mb-4 text-xl text-start font-bold text-gray-900">Client information</h2>
      <div class="grid gap-4 sm:grid-cols-2 sm:gap-6 border-b border-gray-900/10 pb-12 text-start">
        <div class="w-full text-start">
          <label for="brand" class="block mb-1 text-sm font-medium text-neutral-900">Client Company Name*</label>
          <input type="text" v-model="this.clientCompanyName"
                 :class="this.inputStyle"
                 placeholder="Company name" required="">
        </div>
        <div class="w-full text-start">
          <label for="brand" class="block mb-1 text-sm font-medium text-neutral-900">Contact Person Name*</label>
          <input type="text" v-model="this.clientContactPerson"
                 :class="this.inputStyle"
                 placeholder="Contact person" required="">
        </div>
        <div class="w-full text-start">
          <label for="brand" class="block mb-1 text-sm font-medium text-neutral-900">Contact Person Email*</label>
          <input type="email" v-model="this.contactPersonEmail"
                 :class="this.inputStyle"
                 placeholder="Contact person email" required="">
        </div>
      </div>


      <div class="flex flex-col w-full">
        <h2 class="mb-6 text-xl text-start font-bold text-gray-900">Experience requirements</h2>
        <div v-for="experienceIndex in experienceInstancesCount" :key="experienceIndex"
             class="space-y-4">
          <div class="w-full text-start relative space-y-1">
            <label for="category" class="block text-sm font-medium text-neutral-900">Sector*</label>
            <ProjectRoleAutoCompleteComponent :inputStyle="inputStyle" inputKey="sector"
                                              @autoCompleteInputChange="handleInputChange"
                                              :items="this.sectors" placeholder="Sector"/>
          </div>
          <div class="w-full text-start relative space-y-1">
            <div class="flex flex-row">
              <label for="category" class="block text-sm font-medium text-neutral-900">Roles*</label>
              <!--              <button class="rounded w-8 h-8 bg-black text-white">+</button>-->
            </div>
            <ProjectRoleAutoCompleteComponent :inputStyle="inputStyle" inputKey="role"
                                              @autoCompleteInputChange="handleInputChange"
                                              :items="this.roles" placeholder="Role"/>
            <p class="text-xs font-light cursor-default">Select roles that fit the chosen sector.</p>
          </div>
          <div class="w-full text-start relative space-y-1">
            <div class="flex flex-row">
              <label for="category" class="block text-sm font-medium text-neutral-900">Tools*</label>
              <!--              <button class="rounded w-8 h-8 bg-black text-white">+</button>-->
            </div>
            <ProjectRoleAutoCompleteComponent :inputStyle="inputStyle" inputKey="tool"
                                              @autoCompleteInputChange="handleInputChange"
                                              :items="this.tools" placeholder="Tool"/>
            <p class="text-xs font-light cursor-default">Select tools that fit the chosen sector and roles.</p>
          </div>
          <div class="w-full text-start space-y-1">
            <label for="brand" class="block text-sm font-medium text-neutral-900">Years of experience*</label>
            <input type="text" v-model="this.qualifierValue"
                   :class="this.inputStyle"
                   placeholder="Number of years" required="">
            <p class="text-xs font-light cursor-default">Specify the minimum years of experience needed for this
              particular project role.</p>
          </div>
          <div class="sm:col-span-2 text-start space-y-1">
            <label for="description" class="block text-sm font-medium text-neutral-900">Short Description <span
                class="text-xs font-normal">(optional)</span></label>
            <textarea id="description" v-model="this.categoryDescription" rows="3"
                      :class="this.inputStyle"
                      placeholder="Your description here"></textarea>
          </div>
        </div>
        <!--        <button @click="addExperienceInstance"-->
        <!--                class="bg-black rounded-lg px-3 py-2 flex items-center justify-center text-white w-fit text-xs">Add-->
        <!--          experience-->
        <!--        </button>-->
      </div>


      <button type="submit" @click="submitProject"
              class="self-center px-5 py-2.5 mt-4 sm:mt-6 text-sm font-medium text-center text-white bg-black rounded-lg focus:ring-4 focus:ring-primary-200 hover:bg-primary-800">
        Add project
      </button>
    </div>
  </section>
</template>

<script>
import ProjectRoleAutoCompleteComponent from "@/components/ProjectRoleAutoCompleteComponent";
import {successHandler, showError} from "@/utils/actionHandler";

export default {
  name: "CreateProjectView",
  components: {ProjectRoleAutoCompleteComponent},
  inject: {
    projectsService: {
      from: 'projectsService'
    },
    projectRoleService: {
      from: 'projectRoleService'
    }
  },
  computed: {
    sectors() {
      try {
        if (!this.projectRoleService.sectors) return [];
        return this.projectRoleService.sectors.filter(detail => detail.inList).map(detail => detail.sectorName);
      } catch (e) {
        console.log("Something went wrong.")
        return [];
      }
    },
    roles() {
      try {
        if (!this.projectRoleService.roles) return [];
        return this.projectRoleService.roles.filter(detail => detail.inList).map(detail => detail.roleName);
      } catch (e) {
        console.log("Something went wrong.")
        return [];
      }
    },
    tools() {
      try {
        if (!this.projectRoleService.tools) return [];
        return this.projectRoleService.tools.filter(detail => detail.inList).map(detail => detail.toolName);
      } catch (e) {
        console.log("Something went wrong.")
        return [];
      }
    }
  },
  async mounted() {
    if (this.projectRoleService.sectors &&
        this.projectRoleService.roles &&
        this.projectRoleService.tools &&
        this.projectRoleService.sectors.length > 0
        && this.projectRoleService.roles.length > 0
        && this.projectRoleService.tools.length > 0) return;

    await this.projectRoleService.getAllSectors();
    await this.projectRoleService.getAllRoles();
    await this.projectRoleService.getAllTools();
  },
  data() {
    return {
      projectName: null,
      description: null,
      startDate: null,
      endDate: null,
      clientCompanyName: null,
      clientContactPerson: null,
      contactPersonEmail: null,
      categoryTitle: null,
      qualifierValue: null,
      categoryDescription: null,
      experienceInstancesCount: 1,
      sectorInput: null,
      rolesInput: null,
      toolsInput: null,
      uploadedPfpPreviewUrl: null,
      imageData: null,
      inputStyle: "pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6",
    }
  },
  methods: {
    handlePfpInput(event) {
      const file = event.target.files[0];

      if (!file) {
        // No file selected
        return;
      }

      // Check if the selected file is an image
      if (!file.type.startsWith('image/')) {
        alert('Please select an image file.');
        this.resetFileInput();
        return;
      }

      // Check if the file size is within the allowed limit (300KB)
      if (file.size > (300 * 1024)) {
        alert('Image size exceeds the maximum allowed (300KB).');
        this.resetFileInput();
        return;
      }

      this.hasChanged = true;

      const reader = new FileReader();
      reader.onload = (e) => {
        // Convert ArrayBuffer to base64-encoded string
        const arrayBuffer = e.target.result;
        const base64Encoded = btoa(String.fromCharCode.apply(null, new Uint8Array(arrayBuffer)));

        // Create an image element
        const img = new Image();

        // Set the source of the image to the base64-encoded string
        img.src = 'data:image;base64,' + base64Encoded;

        // When the image is loaded, resize it and get the resized base64 string
        img.onload = () => {
          const canvas = document.createElement('canvas');
          const ctx = canvas.getContext('2d');

          // Set canvas dimensions to the desired size (100x100 in this case)
          canvas.width = 100;
          canvas.height = 100;

          // Draw the image on the canvas with the desired dimensions
          ctx.drawImage(img, 0, 0, 100, 100);

          // Get the base64-encoded string of the resized image
          const fileType = file.type.split('/')[1]; // Get the image type from the file object
          const resizedBase64 = canvas.toDataURL(`image/${fileType}`).replace(/^data:image\/(png|jpeg|gif|bmp);base64,/, '');

          // Send the resized base64-encoded string
          this.imageData = resizedBase64;
        };
      };

      reader.readAsArrayBuffer(file);

      if (file) {
        this.uploadedPfpPreviewUrl = URL.createObjectURL(file);
      } else {
        this.uploadedPfpPreviewUrl = null;
      }
    },
    resetFileInput() {
      // Reset the file input to clear the selected file
      this.$refs.fileInputRef.value = null;
    },
    handleInputChange(key, value) {
      switch (key) {
        case 'sector':
          this.sectorInput = value;
          break;
        case 'role':
          this.rolesInput = value;
          break;
        case 'tool':
          this.toolsInput = value;
          break;
      }
    },
    addExperienceInstance() {
      if (this.experienceInstancesCount > 4) {
        return;
      }
      this.experienceInstancesCount++;
    },
    inputValidation() {
      return this.projectName
          && this.description
          && this.startDate
          && this.endDate
          && this.clientCompanyName
          && this.clientContactPerson
          && this.contactPersonEmail
          && this.sectorInput
          && this.rolesInput
          && this.toolsInput
          && this.qualifierValue;
    },
    async submitProject() {
      if (!this.inputValidation()) return showError("Please fill in all necessary fields.");

      const project = {
        name: this.projectName,
        description: this.description,
        startDate: this.startDate,
        endDate: this.endDate,
        imageData: this.imageData,
        client: {
          id: 0,
          companyName: this.clientCompanyName,
          contactPerson: this.clientContactPerson,
          contactEmail: this.contactPersonEmail
        },
        projectRoles:
            [
              {
                sector: {
                  sectorName: this.sectorInput
                },
                roles: [
                  {
                    roleName: this.rolesInput
                  }
                ],
                tools: [
                  {
                    toolName: this.toolsInput
                  }
                ],
                qualifierValue: this.qualifierValue
              }
            ],
      }

      const savedProject = await this.projectsService.asyncSave(project);

      if (savedProject && savedProject.id && savedProject.id !== 0) {
        await successHandler("Project succesfully created.");

        this.$router.push({name: "admin-overview"});
      }
    }
  }
}
</script>

<style scoped>

</style>