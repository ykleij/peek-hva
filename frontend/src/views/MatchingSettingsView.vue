<template>
  <div class="mx-auto p-10 w-2/3 h-full flex flex-col">
    <div class="mb-10">
      <h1 class="font-semibold">Matching Settings</h1>
      <p class="text-xs font-light">These changes are applied to the backend server and used for customization of the
        matching process.</p>
    </div>

    <div class="space-y-5 self-center w-full">
      <div class="grid grid-cols-5 items-center">
        <div class="col-span-4">
          <label for="min-score" class="text-sm font-semibold">Minimum percentage score to match</label>
          <p class="text-xs font-light">Minimum required matching percentage for all projects. Only matches experts who
            score higher or equal.</p>
        </div>
        <div class="col-span-1 flex justify-center">
          <input v-model="this.minScoreValue" id="min-score" type="number"
                 class="w-1/2 text-center h-fit p-2 text-sm rounded-lg border border-neutral-200"/>
        </div>
      </div>

      <div class="grid grid-cols-5 items-center">
        <div class="col-span-4">
          <label class="text-sm font-semibold">Precise matching</label>
          <p class="text-xs font-light">When activated, this setting allows for more precise matching. This means that
            when an expert's sector (of a project role) doesn't match the project's required sector,
            the expert is automatically disqualified from being a match candidate.</p>
          <p class="text-xs font-light italic">(Turn this setting off when the quantity of expert matches is more
            important than the accuracy)</p>
        </div>
        <div class="col-span-1 flex justify-center">
          <Switch
              v-model="preciseMatching"
              :class="preciseMatching ? 'bg-neutral-900' : 'bg-neutral-600'"
              class="relative inline-flex items-center h-[20px] w-[36px] shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-none focus-visible:ring-2 focus-visible:ring-white/75"
          >
            <span class="sr-only">Use setting</span>
            <span
                aria-hidden="true"
                :class="preciseMatching ? 'translate-x-4' : 'translate-x-0'"
                class="pointer-events-none inline-block h-[16px] w-[16px] transform rounded-full bg-white shadow-lg ring-0 transition duration-200 ease-in-out"
            />
          </Switch>
        </div>
      </div>
    </div>

    <button @click="saveSettings"
            class="mt-24 bg-neutral-950 rounded-lg px-7 py-0.5 text-neutral-100 w-fit self-center">Save
    </button>
  </div>
</template>

<script>
import CONFIG from "../../app-config";
import {errorHandler, successHandler} from "@/utils/actionHandler";
import {Switch} from '@headlessui/vue'

export default {
  name: "MatchingSettingsView",
  components: {
    Switch,
  },
  async mounted() {
    const settings = await this.getSettings();

    this.minScoreValue = settings.minMatchingScore;
    this.preciseMatching = settings.preciseMatching;
  },
  data() {
    return {
      minScoreValue: null,
      preciseMatching: false,
    }
  },
  methods: {
    async getSettings() {
      try {
        const response = await fetch(
            CONFIG.BACKEND_URL + "/settings",
            {
              method: "GET",
            });

        if (response.ok) {
          return response.json();
        }
      } catch (e) {
        return null;
      }
    },
    async saveSettings() {
      if (this.minScoreValue == null || this.preciseMatching == null) {
        return;
      }

      try {
        const body = {
          minScoreValue: this.minScoreValue,
          preciseMatching: this.preciseMatching
        };

        const response = await fetch(
            CONFIG.BACKEND_URL + "/settings",
            {
              method: "POST",
              headers: {
                'Content-Type': 'application/json',
              },
              body: JSON.stringify(body),
            });

        if (response.ok) {
          await successHandler("Updated matching settings");
        }
      } catch (e) {
        await errorHandler("Something went wrong");
      }
    }
  }
}
</script>

<style scoped>

</style>