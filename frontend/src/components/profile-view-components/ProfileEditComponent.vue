<template>
  <div
      class="rounded-sm w-11/12 bg-white border border-gray-300 rounded-lg shadow h-min w-full">
    <div class="p-6 space-y-6">
      <form action="#" method="post" enctype="multipart/form-data">
        <div class="grid grid-cols-9 gap-6 text-start flex">
          <div class="col-span-full">
            <div class="flex">
              <img alt="Profile Picture" class="w-[100px] h-[100px] rounded-full object-scale-down"
                   :src="this.uploadedPfpPreviewUrl ? this.uploadedPfpPreviewUrl : require('@/assets/img/defaultpfp.jpg')"
              />
              <div class="ml-5 flex flex-col justify-between"> <!-- Adjust margin as needed -->
                <div>
                  <span class="font-bold">Upload new avatar</span>
                </div>
                <div>
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
                <div>
                  <span class="text-sm font-light text-gray-500 block">The maximum file size allowed is 300KB.</span>
                </div>
              </div>
            </div>

          </div>
          <div class="col-span-9 sm:col-span-3">
            <label for="firstName" class="text-sm font-medium text-gray-900 block mb-2">First name</label>
            <input type="text" v-model="this.profileCopy.firstName" name="firstName" id="firstName"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                   required="">
          </div>
          <div class="col-span-9 sm:col-span-3">
            <label for="addition" class="text-sm font-medium text-gray-900 block mb-2">Addition</label>
            <input type="text" v-model="this.profileCopy.addition" name="addition" id="addition"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                   placeholder="bijv. van der" required="">
          </div>
          <div class="col-span-9 sm:col-span-3">
            <label for="lastName" class="text-sm font-medium text-gray-900 block mb-2">Last name</label>
            <input type="text" v-model="this.profileCopy.lastName" name="lastName" id="lastName"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                   required="">
          </div>
          <div class="col-span-full">
            <label for="bio" class="text-sm font-medium text-gray-900 block mb-2">Bio</label>
            <textarea type="text" v-model="profileCopy.bio" name="bio" id="bio"
                      class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                      required=""/>
          </div>
          <div class="col-span-full">
            <label for="phonenumber" class="text-sm font-medium text-gray-900 block mb-2">Phone number</label>
            <input type="tel" v-model="profileCopy.phoneNumber" name="phonenumber" id="phonenumber"
                   class="w-full shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 p-2.5"
                   required="">
          </div>
          <div class="col-start-1 col-span-5 sm:col-start-1 sm:col-span-4">
            <label for="country" class="text-sm font-medium text-gray-900 block mb-2">Country</label>
            <select v-model="profileCopy.country" name="country" id="country"
                    class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                    required="">
              <option v-for="country in this.countries" :key="country.code" :value="country.code">
                {{ country.name }}
              </option>
            </select>
          </div>
          <div class="col-start-6 col-span-4 sm:col-start-5 sm:col-span-5">
            <label for="residence" class="text-sm font-medium text-gray-900 block mb-2">Residence</label>
            <select v-model="profileCopy.residence" name="residence" id="residence"
                    class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                    required="">
              <option v-for="city in sortedCities" :key="city" :value="city">
                {{ city }}
              </option>
            </select>
          </div>
          <div class="col-span-9 sm:col-span-3">
            <label for="address" class="text-sm font-medium text-gray-900 block mb-2">Address</label>
            <input type="text" v-model="profileCopy.address" name="address" id="address"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                   required="">
          </div>
          <div class="col-span-9 sm:col-span-3">
            <label for="houseNumber" class="text-sm font-medium text-gray-900 block mb-2">House Number</label>
            <input type="text" v-model="profileCopy.houseNumber" name="houseNumber" id="houseNumber"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                   placeholder="" required="">
          </div>
          <div class="col-span-9 sm:col-span-3">
            <label for="postalCode" class="text-sm font-medium text-gray-900 block mb-2">Postal Code</label>
            <input type="text" v-model="profileCopy.postalCode" name="postalCode" id="postalCode"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                   placeholder="i.e. 1234AB" required="">
          </div>
        </div>
      </form>
    </div>
    <div class="p-6 border-t border-gray-200 rounded-b space-x-2">
      <button @click="saveChanges" :disabled="!hasChanged"
              :class="!hasChanged ? 'opacity-50 cursor-not-allowed' : 'hover:bg-cyan-700'"
              class="text-white bg-neutral-950 font-medium rounded-lg text-sm px-5 py-1 text-center" type="submit">Save
        all
      </button>
      <button @click="confirmDiscard('reset')" :disabled="!hasChanged"
              :class="!hasChanged ? 'opacity-50 cursor-not-allowed' : 'hover:bg-neutral-700'"
              class="text-white bg-neutral-950 font-medium rounded-lg text-sm px-5 py-1 text-center">Reset
      </button>
      <button @click="confirmDiscard('cancel')"
              class="text-white bg-neutral-950 hover:bg-neutral-700 focus:ring-4 focus:ring-neutral-200 font-medium rounded-lg text-sm px-5 py-1 text-center">
        Cancel
      </button>
    </div>
  </div>
</template>

<script>
import {UserProfile} from "@/models/UserProfile";
import {errorHandler, successHandler} from "@/utils/actionHandler";
import { cloneDeep } from 'lodash';

export default {
  name: "ProfileEditComponent",
  inheritAttrs: false,
  props: ['profile', 'countries'],
  emits: ['save-profile', 'cancel'],
  inject: {
    usersService: {from: 'usersService'},
    userProfileService: {from: 'userProfileService'},
    projectRoleService: {from: 'projectRoleService'},
    expertProfileService: {from: 'expertProfileService'},
    sessionService: {from: 'sessionService'}
  },
  created() {
    this.profileCopy = cloneDeep(UserProfile.copyConstructor(this.profile));
  },
  computed: {
    sortedCities() {
      if (!this.countries) return [];
      const selectedCountryData = this.countries.find(country => country.code === this.profileCopy.country);
      return selectedCountryData ? selectedCountryData.city : [];
    }
  },
  watch: {
    profileCopy: {
      deep: true,
      handler() {
        this.updateHasChanged();
      },
    },
  },
  data() {
    return {
      uploadedPfp: false,
      uploadedPfpPreviewUrl: null,
      cities: null,
      profileCopy: null,
      hasChanged: false,
      inputStyle: "pl-2 block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6",
    }
  },
  methods: {
    async saveChanges() {
      try {
        //this.profileCopy.bio = this.profileCopy.bio.trim();
        this.$emit("save-profile", this.profileCopy);
        await this.userProfileService.asyncSave(this.profileCopy);

        await successHandler("Profile successfully edited!");
        this.hasChanged = false;
        this.uploadedPfpPreviewUrl = null;
      } catch (e) {
        console.error(e);

        await errorHandler("Something went wrong, please try again.")
      }
    },
    updateHasChanged() {
      if (!this.profile || !this.profileCopy) {
        this.hasChanged = false;
        return;
      }

      this.hasChanged = ((JSON.stringify(this.profile) !== JSON.stringify(this.profileCopy)));
    },
    onBlur(e) {
      if (!e.target.value) {
        e.target.style.borderColor = "red";
      } else {
        e.target.style.borderColor = "rgb(209 213 219 / var(--tw-border-opacity))";
      }
    },
    confirmDiscard(buttonClicked) {
      const message =
          buttonClicked === 'delete'
              ? "Are you sure you want to delete \"" + this.profile.firstName + "\"?\n[id=" + this.profile.id + "]"
              : "There are unsaved changes in \"" + this.profile.firstName + "\". Are you sure you want to proceed?\n[id=" + this.profile.id + "]"

      if (this.hasChanged && !window.confirm(message)) {
        return;
      } else if (!this.hasChanged && buttonClicked === 'delete' && !window.confirm(message)) {
        return;
      }

      switch (buttonClicked) {
        case 'clear':
          this.onClear();
          break;
        case 'cancel':
          this.onCancel();
          break;
        case 'reset':
          this.onReset();
          break;
        case 'delete':
          //this.onDelete();
          break;
      }
    },
    handleInputChange(key, value) {
      console.log(key, value)
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

          // Handle GIFs separately
          if (file.type === 'image/gif') {
            // For GIFs, directly use the base64-encoded string without resizing
            this.profileCopy.imageData = base64Encoded;
          } else {
            ctx.drawImage(img, 0, 0, 100, 100);

            const fileType = file.type.split('/')[1];
            this.profileCopy.imageData = canvas.toDataURL(`image/${fileType}`).replace(/^data:image\/(png|jpeg|gif|bmp|);base64,/, '');
          }
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
    onReset() {
      this.profileCopy = cloneDeep(UserProfile.copyConstructor(this.profile));
      this.uploadedPfpPreviewUrl = null;
      const fileInput = this.$refs.fileInputRef;

      // Reset the value of the file input
      if (fileInput) {
        fileInput.value = null;
      }
    },
    onClear() {
      this.profileCopy = {
        id: this.profileCopy.id,
        userProfile: {
          imageData: this.profileCopy.imageData
        }};
    },
    onCancel() {
      this.$emit("cancel");
      this.onReset();
    },
  }
}
</script>

<style scoped>

</style>