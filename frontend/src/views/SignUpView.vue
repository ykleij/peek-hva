<template>
  <div class="my-24 w-full flex justify-center">
    <div class="w-1/2">
      <div class="space-y-12">
        <div class="border-b border-gray-900/10 pb-12 text-start">
          <h2 class="text-base font-semibold leading-7 text-gray-900">
            Profile
          </h2>
          <p class="mt-1 text-sm leading-6 text-gray-600">
            This information will be displayed publicly so be careful what you
            share.
          </p>

          <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
            <div class="sm:col-span-4">
              <label
                  for="username"
                  class="block text-sm font-medium leading-6 text-gray-900"
              >Username*</label
              >
              <div class="mt-2">
                <div
                    class="flex rounded-md shadow-sm ring-1 ring-inset ring-gray-300 focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                  <span
                      class="flex select-none items-center pl-3 text-gray-500 sm:text-sm"
                  >www.peek.nl/</span
                  >
                  <input
                      v-model="this.profile.userProfile.userName"
                      type="text"
                      name="username"
                      id="username"
                      autocomplete="username"
                      class="block flex-1 border-0 bg-transparent py-1.5 pl-1 text-gray-900 placeholder:text-gray-400 focus:ring-0 sm:text-sm sm:leading-6"
                      placeholder="username"/>
                </div>
              </div>
            </div>

            <div class="col-span-full">
              <label
                  for="about"
                  class="block text-sm font-medium leading-6 text-gray-900"
              >About</label
              >
              <div class="mt-2">
                <textarea
                    v-model="this.profile.userProfile.bio"
                    id="about"
                    name="about"
                    placeholder="Write a few sentences about yourself."
                    rows="3"
                    class="pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"/>
              </div>
            </div>

            <div class="col-span-full">
              <label
                  for="photo"
                  class="block text-sm font-medium leading-6 text-gray-900"
              >Profile Picture</label
              >
              <div class="mt-2 flex items-center gap-x-3">
                <img alt="Profile Picture" class="w-[100px] h-[100px] rounded-full object-scale-down"
                     :src="this.uploadedPfpPreviewUrl ? this.uploadedPfpPreviewUrl : require('@/assets/img/defaultpfp.jpg')"
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


          </div>
        </div>

        <div class="border-b border-gray-900/10 pb-12 text-start">
          <h2 class="text-base font-semibold leading-7 text-gray-900">
            Personal Information
          </h2>
          <p class="mt-1 text-sm leading-6 text-gray-600">
            Safeguarding your privacy is our top priority.
          </p>

          <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
            <div class="sm:col-span-2">
              <label
                  for="first-name"
                  class="block text-sm font-medium leading-6 text-gray-900"
              >First name*</label
              >
              <div class="mt-2">
                <input
                    v-model="this.profile.userProfile.firstName"
                    type="text"
                    name="first-name"
                    id="first-name"
                    autocomplete="given-name"
                    placeholder="First name"
                    class="pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>
            <div class="sm:col-span-2">
              <label
                  for="first-name"
                  class="block text-sm font-medium leading-6 text-gray-900"
              >Addition</label
              >
              <div class="mt-2">
                <input
                    v-model="this.profile.userProfile.addition"
                    type="text"
                    name="first-name"
                    id="first-name"
                    autocomplete="given-name"
                    placeholder="Addition"
                    class="pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                />
              </div>
            </div>

            <div class="sm:col-span-2">
              <label
                  for="last-name"
                  class="block text-sm font-medium leading-6 text-gray-900"
              >Last name*</label
              >
              <div class="mt-2">
                <input
                    v-model="this.profile.userProfile.lastName"
                    type="text"
                    name="last-name"
                    id="last-name"
                    autocomplete="family-name"
                    placeholder="Last name"
                    class="pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"/>
              </div>
            </div>

            <div class="sm:col-span-2">
              <label
                  for="last-name"
                  class="block text-sm font-medium leading-6 text-gray-900"
              >Phone number*</label
              >
              <div class="mt-2">
                <input
                    v-model="this.profile.userProfile.phoneNumber"
                    type="text"
                    name="phone-number"
                    id="phone-number"
                    placeholder="Phone number"
                    class="pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"/>
              </div>
            </div>

            <div class="sm:col-span-2">
              <label
                  for="last-name"
                  class="block text-sm font-medium leading-6 text-gray-900"
              >Kvk number (optional)</label
              >
              <div class="mt-2">
                <input
                    v-model="this.profile.userProfile.kvkNumber"
                    type="text"
                    name="kvk-number"
                    id="kvk-number"
                    placeholder="Kvk number"
                    class="pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"/>
              </div>
            </div>

            <div class="sm:col-span-2">
              <label
                  for="last-name"
                  class="block text-sm font-medium leading-6 text-gray-900"
              >LinkedIn url (optional)</label
              >
              <div class="mt-2">
                <input
                    v-model="this.profile.userProfile.linkedInUrl"
                    type="text"
                    name="linked-in-url"
                    id="linked-in-url"
                    placeholder="LinkedIn url"
                    class="pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"/>
              </div>
            </div>


            <div class="sm:col-span-3">
              <label for="country" class="text-sm font-medium text-gray-900 block mb-2">Country*</label>
              <select v-model="this.profile.userProfile.country" name="country" id="country"
                      class="shadow-sm bg-white border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                      required="">
                <option v-for="country in this.countries" :key="country.code" :value="country.code">
                  {{ country.name }}
                </option>
              </select>
            </div>
            <div class="sm:col-span-3">
              <label for="residence" class="text-sm font-medium text-gray-900 block mb-2">Residence</label>
              <select v-model="this.profile.userProfile.residence" name="residence" id="residence"
                      class="shadow-sm bg-white border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                      required="">
                <option v-for="city in sortedCities" :key="city" :value="city">
                  {{ city }}
                </option>
              </select>
            </div>
            <div class="sm:col-span-2">
              <label for="address" class="text-sm font-medium text-gray-900 block mb-2">Address*</label>
              <input type="text" v-model="this.profile.userProfile.address" name="address" id="address"
                     class="pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                     placeholder="Address" required="">
            </div>
            <div class="sm:col-span-2">
              <label for="houseNumber" class="text-sm font-medium text-gray-900 block mb-2">House Number*</label>
              <input type="text" v-model="this.profile.userProfile.houseNumber" name="houseNumber" id="houseNumber"
                     class="pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                     placeholder="House number" required="">
            </div>
            <div class="sm:col-span-2">
              <label for="postalCode" class="text-sm font-medium text-gray-900 block mb-2">Postal Code*</label>
              <input type="text" v-model="this.profile.userProfile.postalCode" name="postalCode" id="postalCode"
                     class="pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                     placeholder="i.e. 1234AB" required="">
            </div>
          </div>
        </div>

        <div class="pb-12 text-start">
          <h2 class="text-base font-semibold leading-7 text-gray-900">
            Work Experience
          </h2>
          <p class="mt-1 text-sm leading-6 text-gray-600">
            Illuminate your professional journey with a detailed account of your past roles, responsibilities, and
            achievements, showcasing the expertise and skills acquired throughout your career. </p>

          <div class="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
            <div class="sm:col-span-3">
              <label for="category" class="mb-2 block text-sm font-medium text-neutral-900">Sector*</label>
              <ProjectRoleAutoCompleteComponent :inputStyle="inputStyle" inputKey="sector"
                                                @autoCompleteInputChange="handleInputChange"
                                                :items="this.sectors" placeholder="Sector"/>
            </div>
            <div class="sm:col-span-3">
              <div class="flex flex-row">
                <label for="category" class="mb-2 block text-sm font-medium text-neutral-900">Roles*</label>
                <!--              <button class="rounded w-8 h-8 bg-black text-white">+</button>-->
              </div>
              <ProjectRoleAutoCompleteComponent :inputStyle="inputStyle" inputKey="role"
                                                @autoCompleteInputChange="handleInputChange"
                                                :items="this.roles" placeholder="Role"/>
              <p class="text-xs font-light cursor-default">Select roles that fit the chosen sector.</p>
            </div>
            <div class="sm:col-span-3">
              <div class="flex flex-row">
                <label for="category" class="mb-2 block text-sm font-medium text-neutral-900">Tools*</label>
                <!--              <button class="rounded w-8 h-8 bg-black text-white">+</button>-->
              </div>
              <ProjectRoleAutoCompleteComponent :inputStyle="inputStyle" inputKey="tool"
                                                @autoCompleteInputChange="handleInputChange"
                                                :items="this.tools" placeholder="Tool"/>
              <p class="text-xs font-light cursor-default">Select tools that fit the chosen sector and roles.</p>
            </div>
            <div class="sm:col-span-3">
              <label for="brand" class="mb-2 block text-sm font-medium text-neutral-900">Years of experience*</label>
              <input type="number" v-model="this.profile.projectRoles[0].qualifierValue"
                     :class="this.inputStyle"
                     placeholder="Number of years" required="">
              <p class="text-xs font-light cursor-default">Pick how many years working experience you have with this
                combination of sector, roles and tools.</p>
            </div>
            <div class="sm:col-span-6">
              <label for="description" class="mb-2 block text-sm font-medium text-neutral-900">Short Description <span
                  class="text-xs font-normal">(optional)</span></label>
              <textarea id="description" v-model="this.profile.projectRoles[0].categoryDescription" rows="3"
                        :class="this.inputStyle"
                        placeholder="Your description here"></textarea>
            </div>
          </div>
        </div>
      </div>

      <div class="mt-6 flex items-center justify-center">
        <button
            @click.prevent="onSubmit"
            class="rounded-md bg-neutral-900 px-3 py-2 text-sm font-semibold text-white shadow-sm transition-colors
             duration-200 hover:bg-neutral-700 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2
              focus-visible:outline-neutral-600">
          Submit
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import {fetchCountries} from "@/utils/helperUtils";
import ProjectRoleAutoCompleteComponent from "@/components/ProjectRoleAutoCompleteComponent";
import {showError} from "@/utils/actionHandler";

export default {
  name: 'RegisterView',
  inject: {
    projectRoleService: {from: 'projectRoleService'},
    registerService: {from: 'registerService'},
    sessionService: {from: 'sessionService'},
  },
  components: {
    ProjectRoleAutoCompleteComponent,
  },
  created() {
    this.fetchCountries();
  },
  async mounted() {
    Swal.fire({
      icon: "info",
      title: "Welcome",
      html: 'Thanks for joining our expert crew! ' +
          'Before showing you all our available projects we need to create your profile. <br/><br/>' +
          ' <b>Important:</b> It is essential to make your profile as detailed as possible to have a greater' +
          ' chance of being selected for a project.',
      confirmButtonText: "Got it !",
      confirmButtonColor: "black",
    })

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
  computed: {
    sortedCities() {
      if (!this.countries) return [];
      const selectedCountryData = this.countries.find(country => country.code === this.profile.userProfile.country);
      return selectedCountryData ? selectedCountryData.city : [];
    },
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
  watch: {
    sectorInput(newValue) {
      this.profile.projectRoles[0].sector.sectorName = newValue;
    },
    rolesInput(newValue) {
      this.profile.projectRoles[0].roles[0].roleName = newValue;
    },
    toolsInput(newValue) {
      this.profile.projectRoles[0].tools[0].toolName = newValue;
    },
  },
  data() {
    return {
      isOpen: true,
      profile: {
        userProfile: {},
        projectRoles: [
          {
            sector: {
              sectorName: null
            },
            roles: [
              {
                roleName: null
              }
            ],
            tools: [
              {
                toolName: null
              }
            ],
          }
        ],
      },
      countries: null,
      uploadedPfpPreviewUrl: null,
      sectorInput: null,
      rolesInput: null,
      toolsInput: null,
      inputStyle: "pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6",
    }
  },
  beforeRouteEnter(to, from, next) {
    next(vm => {
      const userProfile = vm.sessionService.getUserFromBrowserStorage().userProfile;

      if (userProfile) {
        if (vm.sessionService.isAdmin()) {
          vm.$router.replace({name: 'admin-overview'});
        } else {
          vm.$router.replace({name: 'expert-overview'});
        }
      }
    });
  },

  methods: {
    async onSubmit() {
      if (!this.profile.userProfile.userName &&
          !this.profile.userProfile.firstName &&
          !this.profile.userProfile.lastName &&
          !this.profile.userProfile.phoneNumber &&
          !this.profile.userProfile.country &&
          !this.profile.userProfile.address &&
          !this.profile.userProfile.houseNumber &&
          !this.profile.userProfile.postalCode
      ) return showError("Please fill in all required fields");

      const user = await this.registerService.saveUserProfile(this.profile, this.sessionService.getUserIdFromBrowserStorage())

      if (user && user.id && user.userProfile) {
        this.sessionService.saveUserIntoBrowserStorage(user);
        this.$router.push({name: 'expert-overview'});
      } else {
        await showError("Something went wrong trying create your profile")
      }
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
          this.profile.userProfile.imageData = resizedBase64;
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

    async fetchCountries() {
      this.countries = await fetchCountries();
    },
  },
};
</script>
