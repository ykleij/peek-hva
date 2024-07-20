<template>
  <div class="p-5 flex flex-col gap-8 min-h-full max-h-screen items-center">
    <div class="w-full h-fit">
      <ArrowLeftIcon
          @click="
          this.$router.go(-1)
            ? this.$router.go(-1)
            : this.$router.push({ name: 'admin-overview' })
        "
          class="self-start h-[40px] w-[40px] p-3 cursor-pointer text-black hover:bg-neutral-200 transition-colors duration-100 ease-in rounded-full"/>
    </div>
    <div v-if="this.isLoading === true" class="absolute top-[50%]">
      <SpinnerComponent/>
    </div>
    <div
        v-else-if="project"
        class="bg-white shadow-sm p-10 text-left flex flex-col gap-10 h-fit w-full">

      <div class="relative flex justify-center">
        <button
            @click="startMatchingAlgorithm(this.project.id)"
            class="bg-black hover:bg-neutral-900 transition-colors duration-200 self-center rounded-full text-white text-xs px-5 py-3 w-fit shadow-md">
          Start matching algorithm
        </button>

        <CandidateListComponent @addCandidate="saveCandidate"
                                @removeCandidate="deleteCandidate"
                                :candidateList="this.candidateList"
                                :projectId="this.project.id"/>
      </div>

      <div class="w-full flex flex-row justify-end gap-2">
        <!--        <div class="heart" @click="clickHeart"></div>-->

        <!-- ADMIN MENU -->
        <Menu as="div" class="relative w-9 h-9 inline-block text-left">
          <MenuButton>
            <EllipsisHorizontalIcon
                class="h-10 w-10 p-2 cursor-pointer text-black hover:bg-neutral-200 transition-colors duration-100 ease-in rounded-full"/>
          </MenuButton>

          <transition
              enter-active-class="transition duration-100 ease-out"
              enter-from-class="transform scale-95 opacity-0"
              enter-to-class="transform scale-100 opacity-100"
              leave-active-class="transition duration-75 ease-in"
              leave-from-class="transform scale-100 opacity-100"
              leave-to-class="transform scale-95 opacity-0">
            <MenuItems
                class="absolute right-0 w-56 p-2 rounded-lg bg-white flex flex-col divide-neutral-100 text-start origin-top-right bg-white shadow-lg ring-1 ring-black/5 focus:outline-none">
              <MenuItem class="hover:bg-neutral-100">
                <div
                    @click="changeStatus"
                    class="cursor-pointer rounded-lg group flex w-full items-center p-3 text-sm">
                  <span>Change Status</span>
                </div>
              </MenuItem>
              <MenuItem class="hover:bg-neutral-100">
                <div
                    @click="deleteProject"
                    class="cursor-pointer rounded-lg group flex w-full items-center p-3 text-sm">
                  <span class="text-red-400">Delete Project</span>
                </div>
              </MenuItem>
            </MenuItems>
          </transition>
        </Menu>
      </div>

      <div class="grid grid-cols-5 gap-5 h-full">
        <!--        <img class="h-24 w-24 flex-none rounded-lg p-5" :src="selectedProject.imageUrl" alt=""/>-->

        <!-- PROJECT INFO -->
        <div class="col-span-3 space-y-2 flex flex-row gap-20">
          <div>
            <img class="self-center h-32 w-32 flex-none rounded mb-8" :src="getImageBLOBUrlProject()" alt=""/>
            <div class="flex flex-row items-center gap-3">
              <h1 class="text-xl font-semibold">{{ project.name }}</h1>
              <p
                  class="mt-1 truncate text-[10px] leading-5 px-2 rounded-full w-fit font-normal"
                  :class="
                project.status === 1
                  ? 'bg-emerald-200 text-emerald-700'
                  : ' bg-red-300 text-red-600'
              ">
                {{ project.status === 1 ? 'ACTIVE' : 'INACTIVE' }}
              </p>
            </div>
            <p class="text-sm font-light text-neutral-600">
              {{ project.description }}
            </p>
          </div>
          <div class="flex flex-col gap-5">
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
            <div>
              <div class="text-xs">from: {{ dateCreated(project.startDate).date }}</div>
              <div class="text-xs">to: {{ dateCreated(project.endDate).date }}</div>
            </div>

            <div>
              <h1 class="text-sm font-semibold">Client</h1>
              <div class="text-xs">
                <p>Company: {{ project.client.companyName }}</p>
                <p>Contact Person: {{ project.client.contactPerson }}</p>
                <p>Email: {{ project.client.contactEmail }}</p>
              </div>

            </div>
          </div>

        </div>

        <!-- MATCH HISTORY -->
        <div class="col-span-2 w-full">
          <div class="text-center bg-neutral-50 border border-neutral-200 p-8">
            <h1 class="mb-10 font-bold">Match History</h1>

            <ul
                v-if="matchHistory.length > 0"
                class="space-y-2 max-h-96 overflow-y-scroll">
              <li
                  @click="findMatchFromHistory(
                      match.id,
                      getLocalDateTime(match.dateTimeMatched),
                      match.adminName,
                      match.adminId
                    )"
                  v-for="match in matchHistory"
                  :key="match.id"
                  class="space-y-2 cursor-pointer p-7 bg-neutral-50 transition-colors duration-200 hover:bg-neutral-100 rounded text-xs text-left flex flex-col justify-between">
                <div class="flex flex-row justify-between">
                  <p>{{ getLocalDateTime(match.dateTimeMatched) }}</p>
                  <p class="italic">
                    by
                    <RouterLink :to="'/profile/' + match.adminId"
                                class="hover:border-b hover:border-neutral-900 font-semibold">
                      {{ match.adminName }}
                    </RouterLink>
                  </p>
                </div>
                <div class="text-sm font-semibold flex flex-row justify-between items-center">
                  <span>A total of {{ match.matchedExpertsCount }} matches.</span>
                </div>
              </li>
            </ul>

            <p v-else class="text-xs">This project has no match history yet.</p>
          </div>
          <!--          <h1 class="text-md">Client details:</h1>-->
          <!--          <h2 class="">{{ project.client.companyName }}</h2>-->
          <!--          <p class="text-sm font-light text-neutral-600">{{ project.client.contactPerson }}</p>-->
          <!--          <p class="text-sm font-light text-neutral-600">{{ project.client.contactEmail }}</p>-->
        </div>
      </div>
    </div>
  </div>

  <!-- MATCHING MODAL -->
  <TransitionRoot appear :show="isOpen" as="template">
    <Dialog as="div" @close="closeModal" class="relative z-50">
      <TransitionChild
          as="template"
          enter="duration-300 ease-out"
          enter-from="opacity-0"
          enter-to="opacity-100"
          leave="duration-200 ease-in"
          leave-from="opacity-100"
          leave-to="opacity-0">
        <div class="fixed inset-0 bg-black/50"/>
      </TransitionChild>

      <div class="fixed inset-0 overflow-y-auto">
        <div
            class="flex min-h-full items-center justify-center p-4 text-center">
          <TransitionChild
              as="template"
              enter="duration-300 ease-out"
              enter-from="opacity-0 scale-95"
              enter-to="opacity-100 scale-100"
              leave="duration-200 ease-in"
              leave-from="opacity-100 scale-100"
              leave-to="opacity-0 scale-95">
            <DialogPanel
                class="w-full md:w-1/2 h-fit space-y-5 transform overflow-hidden rounded-lg bg-white p-10 text-center shadow-xl transition-all">
              <MatchHistoryModalContentComponent
                  @addCandidate="saveCandidate"
                  @removeCandidate="deleteCandidate"
                  v-if="loadHistoryMatches && matchHistoryData"
                  :candidateList="candidateList"
                  :matchHistoryData="matchHistoryData"
                  :foundMatches="foundMatches"
                  :matchData="matchedExpertsFromHistory"/>
              <MatchModalContentComponent
                  v-else
                  @addCandidate="saveCandidate"
                  @removeCandidate="deleteCandidate"
                  :candidateList="candidateList"
                  :foundMatches="foundMatches"
                  :matchedExperts="matchedExperts"/>
            </DialogPanel>
          </TransitionChild>
        </div>
      </div>
    </Dialog>
  </TransitionRoot>
</template>

<script>
import {
  Menu,
  MenuButton,
  MenuItems,
  MenuItem,
  TransitionRoot,
  TransitionChild,
  Dialog,
  DialogPanel,
} from '@headlessui/vue';
import {
  ArrowLeftIcon,
  EllipsisHorizontalIcon,
} from '@heroicons/vue/24/outline';
import SpinnerComponent from '@/components/SpinnerComponent';
import MatchModalContentComponent from '@/components/project-view-components/MatchModalContentComponent';
import MatchHistoryModalContentComponent from '@/components/project-view-components/MatchHistoryModalContentComponent';
import CandidateListComponent from "@/components/project-view-components/CandidateListComponent";
import CONFIG from "../../app-config";
import {errorHandler, successHandler} from "@/utils/actionHandler";
import {getImageBLOBUrlProject} from "@/utils/imageDecoder";
import {formatJavaDate} from "@/utils/helperUtils";

export default {
  name: 'ProjectView',
  inject: {
    projectsService: {from: 'projectsService'},
    matchingService: {from: 'matchingService'},
    sessionService: {from: 'sessionService'},
  },
  components: {
    CandidateListComponent,
    MatchHistoryModalContentComponent,
    MatchModalContentComponent,
    Dialog,
    DialogPanel,
    TransitionChild,
    TransitionRoot,
    SpinnerComponent,
    ArrowLeftIcon,
    EllipsisHorizontalIcon,
    Menu,
    MenuButton,
    MenuItems,
    MenuItem,
  },
  async mounted() {
    try {
      this.isLoading = true;
      this.projectId = this.$route.params.id;

      this.project = this.projectsService.entities
          .slice()
          .find((project) => project.id === parseInt(this.projectId));
      console.log(this.project)

      this.matchHistory = await this.matchingService.getMatchHistoryByProjectId(this.projectId);

      if (this.matchHistory && this.matchHistory.length > 0) {
        this.matchHistory = this.matchHistory.slice().sort(
            (m1, m2) => new Date(m2.dateTimeMatched) - new Date(m1.dateTimeMatched));
      }

      // If the project is cached (on the overview page), we load the cached project because of quicker load times.
      if (this.project) {
        this.project.candidates.forEach(candidate => this.candidateList.push(candidate));
        return this.isLoading = false;
      }

      // If the project doesn't exist in cache, we fetch the project from the database
      this.project = await this.projectsService.asyncFindById(this.projectId);
      console.log(this.project)

      if (!this.project) {
        return this.$router.push({name: 'admin-overview'});
      }

      this.project.candidates.forEach(candidate => this.candidateList.push(candidate));
      this.isLoading = false;
    } catch (e) {
      console.error(e);
    }
  },
  watch: {
    async $route() {
      this.$router.go(0);
      // this.projectId = this.$route.params.id;
      // this.project = await this.projectsService.asyncFindById(this.projectId);
      //
      // if (!this.project) {
      //   return this.$router.push({name: 'admin-overview'});
      // }
    },
  },
  data() {
    return {
      isLoading: false,
      isOpen: false,
      project: null,
      matchedExperts: null,
      matchHistory: null,
      foundMatches: false,
      matchedExpertsFromHistory: null,
      loadHistoryMatches: false,
      matchHistoryData: null,
      currentAdminName: this.sessionService.getUserFirstLastNameFromBrowserStorage(),
      projectId: null,
      candidateList: [],
    };
  },
  methods: {
    getImageBLOBUrlProject() {
      return getImageBLOBUrlProject(this.project);
    },
    dateCreated(date) {
      return formatJavaDate(date);
    },
    getLocalDateTime(inputDateString) {
      const inputDate = new Date(inputDateString);

      const day = String(inputDate.getDate()).padStart(2, '0');
      const month = String(inputDate.getMonth() + 1).padStart(2, '0');
      const year = inputDate.getFullYear();

      const hours = String(inputDate.getHours()).padStart(2, '0');
      const minutes = String(inputDate.getMinutes()).padStart(2, '0');

      return `${day}-${month}-${year} • ${hours}:${minutes}`;
    },
    closeModal() {
      this.isOpen = false;
    },
    async startMatchingAlgorithm(projectId) {
      try {

        this.loadHistoryMatches = false;
        this.foundMatches = false;
        this.isOpen = true;

        this.matchedExperts =
            await this.matchingService.startAlgorithmAndGetMatchesByProjectId(
                projectId,
                this.sessionService.getUserIdFromBrowserStorage()
            );

        this.matchedExperts = this.matchedExperts.slice().map((expert) => {
          return {user: expert.user, score: expert.matchingScore};
        });

        if (this.matchedExperts) {
          this.foundMatches = true;

          await successHandler('Matching tool has been successfully activated.');
        } else {
          await errorHandler('Something went wrong.');
        }
      } catch (e) {
        console.log("Something went wrong while trying to activate the matching tool", e)
      }
    },
    async findMatchFromHistory(matchId, matchDate, matchAdminName, matchAdminId) {
      try {
        this.matchHistoryData = {matchDate, matchAdminName, matchAdminId};
        this.loadHistoryMatches = false;
        this.foundMatches = false;
        this.isOpen = true;

        this.matchedExpertsFromHistory =
            await this.matchingService.getMatchesFromHistoryByMatchId(matchId);

        if (this.matchedExpertsFromHistory) {
          this.loadHistoryMatches = true;
          this.foundMatches = true;
        }
      } catch (e) {
        console.log("Something went wrong trying to find the match history", e)
      }
    },
    async changeStatus() {
      try {
        if (this.project.status === 1) {
          this.project.status = 0;
        } else this.project.status = 1;

        await this.projectsService.asyncSave(this.project);
      } catch (e) {
        console.error(e);
      }
    },
    async deleteProject() {
      try {
        await this.projectsService.asyncDeleteById(this.project.id);

        await successHandler("Project has successfully been deleted.");
        this.$router.push({name: 'admin-overview'});
      } catch (e) {
        console.error(e);
      }
    },
    async saveCandidate(user) {
      try {
        this.candidateList.push(user);

        const url = CONFIG.BACKEND_URL + `/projects/${this.projectId}/candidates/${user.id}`
        await fetch(url, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
          }
        });
      } catch (e) {
        await errorHandler("Something went wrong")
      }
    },
    async deleteCandidate(user) {
      try {
        const candidate = this.candidateList.find(candidate => candidate.id === user.id);
        const index = this.candidateList.indexOf(candidate);
        this.candidateList.splice(index, 1);

        const url = CONFIG.BACKEND_URL + `/projects/${this.projectId}/candidates/${user.id}`
        await fetch(url, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/json',
          }
        });
      } catch (e) {
        await errorHandler("Something went wrong")
      }
    },
  },
};
</script>

<style scoped>
.heart {
  cursor: pointer;
  height: 50px;
  width: 50px;
  background-image: url('https://abs.twimg.com/a/1446542199/img/t1/web_heart_animation.png');
  background-position: left;
  background-repeat: no-repeat;
  background-size: 2900%;
}

.liked {
  background-position: right;
}

.is_animating {
  animation: heart-burst 0.8s steps(28) 1;
}

@keyframes heart-burst {
  from {
    background-position: left;
  }
  to {
    background-position: right;
  }
}
</style>
