<template>
  <div
      v-if="this.isLoading === true"
      class="flex justify-center items-center h-screen">
    <SpinnerComponent/>
  </div>

  <div v-else-if="profileData && userData && userData.id !== undefined">


    <div class="md:h-auto p-5">
      <div class="w-full h-fit mb-8">
        <ArrowLeftIcon
            @click="this.$router.go(-1)"
            class="self-start h-[40px] w-[40px] p-3 cursor-pointer text-black hover:bg-neutral-200 transition-colors duration-100 ease-in rounded-full"/>
      </div>
      <!-- Left Div with Image and Upload Button -->
      <div
          class="grid grid-cols-3 w-full shadow-sm rounded-sm p-12 bg-white border border-gray-300 rounded-lg shadow h-min">
        <!-- Image -->
        <div class="col-span-1 space-y-8">
          <img
              :src="getImageBLOBUrl()"
              alt="Image"
              class="w-32 h-32 rounded-full"
              height="100"
              width="100"
          />
          <div class="flex">
            <div class="w-1/2 w-max text-left space-y-3">
                <span class="font-bold text-3xl">{{
                    profileData.firstName + ' ' + profileData.addition + ' ' + profileData.lastName
                  }}</span>
              <span class="text-sm font-light text-gray-500 block">{{ profileData.bio }}</span>
            </div>
          </div>
        </div>

        <div class="col-span-2">
          <router-view
              :profileData="profileData"
              v-if="profileData"
              v-model:profileModel="profileData"
              v-model:email="userData.email"
              @saver="onSaveProfile"/>
        </div>
      </div>
    </div>
    <div class="h-screen md:h-auto flex p-4">
      <div
          class="w-full shadow-sm rounded-sm bg-white border border-gray-300 rounded-lg shadow h-min">
        <div
            class="card-header flex items-center space-x-2 font-semibold text-gray-900 leading-8">
          <span class="tracking-wide">Discussion</span>
        </div>
        <form>
          <div class="w-full px-3 my-2">
            <textarea
                class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-cyan-600 focus:border-cyan-600 block w-full p-2.5"
                name="body"
                placeholder="Type Your Comment"
                v-model="this.commentBox"
                required></textarea>
          </div>
          <div class="w-full flex justify-start px-3">
            <input
                type="button"
                :disabled="commentIsProcessing"
                :class="commentIsProcessing ? 'opacity-50 cursor-not-allowed' : 'hover:bg-cyan-700'"
                class="text-white bg-cyan-600 hover:bg-cyan-700 focus:ring-4 focus:ring-cyan-200 font-medium rounded-lg text-sm px-5 py-2.5 mb-2 text-center"
                @click="saveComment"
                value="Post Comment"/>
            <div v-if="commentIsProcessing">
              <SpinnerComponent/>
            </div>
          </div>
        </form>
          <div v-for="comment in this.comments" :key="comment.id" class="flex flex-col">
            <div class="border rounded-md p-3 ml-3 my-3 mr-3">
              <div class="flex gap-3 items-center">
                <img alt=""
                    :src="getCommentImage(comment.poster.userProfile)"
                    class="object-cover w-8 h-8 rounded-full border-2 shadow-emerald-400"/>
                <h3 class="font-bold">{{ comment.poster.userProfile.firstName + ' ' + comment.poster.userProfile.addition + ' ' + comment.poster.userProfile.lastName}}</h3>
                <span class="text-sm font-light text-gray-500 block text-right">{{ getCommentDate(comment.dateCreated) }}</span>
              </div>
              <p class="text-gray-600 mt-2">{{ comment.body }}</p>
              <input
                  v-if="comment.poster.userProfile.id === this.loggedInUser"
                  type="button"
                  class="text-white bg-red-600 hover:bg-red-700 focus:ring-4 focus:ring-red-200 font-medium rounded-lg text-xs px-3 py-1.5 text-center"
                  @click="confirmCommentDelete(comment)"
                  value="Delete"/>
            </div>
          </div>
      </div>
    </div>
  </div>
  <div v-else>
    <UnknownRoute :type="'profile'"/>
  </div>
</template>

<script>
import SpinnerComponent from '@/components/SpinnerComponent.vue';
import UnknownRoute from '@/components/UnknownRoute.vue';
import {ArrowLeftIcon} from '@heroicons/vue/24/outline';
import {getImageBLOBUrl} from "@/utils/imageDecoder";
import {errorHandler, successHandler} from "@/utils/actionHandler";
import {Comment} from "@/models/Comment";

export default {
  name: 'ProfileView',
  inject: {
    usersService: {from: 'usersService'},
    usersYesterdayService: {from: 'usersYesterdayService'},
    userProfileService: {from: 'userProfileService'},
    expertProfileService: {from: 'expertProfileService'},
    commentService: {from: 'commentService'},
    commentRestService: {from: 'commentRestService'},
    sessionService: {from: 'sessionService'},
    projectRoleService: {from: 'projectRoleService'}
  },
  components: {
    UnknownRoute,
    SpinnerComponent,
    ArrowLeftIcon,
  },
  data() {
    return {
      cachedImageData: null,
      pfpDirectory: "@/assets/img",
      isLoading: false,
      profileData: [],
      userData: [],
      comments: [],
      commentBox: "",
      commentIsProcessing: false,
      loggedInUser: this.sessionService.getUserFromBrowserStorage().id,
      startId: 10,
    };
  },
  created() {
    this.loadProfile(this.$route);
  },
  watch: {
    $route() {
      this.loadProfile(this.$route);
    },
  },
  methods: {
    async saveComment() {
      try {
        this.commentIsProcessing = true;

        const newComment = Comment.create(this.commentBox, "", this.userData.id, this.loggedInUser);

        if(this.commentBox !== "") {
          if(this.commentBox.length > 1000) {
            this.commentIsProcessing = false;
            await errorHandler("Your comment is too long! (max. 1000 characters)");
          } else {
            await this.commentService.saveComment(newComment);
            await this.loadComments();

            this.commentBox = "";
            this.commentIsProcessing = false;
            await successHandler("Comment posted!");
          }
        } else {
          this.commentBox = "";
          this.commentIsProcessing = false;
          await errorHandler("You can't post an empty comment!")
        }
      } catch (e) {
        console.error(e);

        await errorHandler("Something went wrong, please try again.")
      }
    },
    async deleteComment(commentId) {
      try {
        this.commentIsProcessing = true;

        await this.commentService.deleteComment(commentId);
        await this.loadComments();
        this.commentIsProcessing = false;
        await successHandler("Comment deleted!");
      } catch (e) {
        console.error(e);

        await errorHandler("Something went wrong, please try again.")
      }
    },
    confirmCommentDelete(comment) {
      const message = "Are you sure you want to delete this comment?\n[id=" + comment.id + "]";

      if (!window.confirm(message)) {
        return;
      }

      this.deleteComment(comment.id);

    },
    async loadProfile(route) {
      try {
        this.isLoading = true;

        this.profileData = await this.userProfileService.entities
            .slice()
            .find((profile) => profile.id === parseInt(route.params.id));

        // If the project is cached (on the overview page), we load the cached project because of quicker load times.
        if (this.profileData) return (this.isLoading = false);

        // If the project doesn't exist in cache, we fetch the project from the database
        if (route.params.id !== '') {
          this.userData = await this.usersService.asyncFindById(route.params.id);
          this.profileData = await this.userProfileService.asyncFindById(
              this.userData.userProfile.id
          );
        } else {
          this.userData = await this.usersService.asyncFindById(
              this.loggedInUser
          );
          this.profileData = await this.userProfileService.asyncFindById(
              this.userData.userProfile.id
          );
        }

        await this.loadComments();

        this.isLoading = false;
      } catch (e) {
        console.error(e);
      }
    },
    async loadComments() {
      this.comments = await this.commentService.getCommentByPostedOnId(this.$route.params.id || this.loggedInUser);
    },
    getCommentDate(date) {
      const newDate = new Date(date);
      return newDate.toLocaleString('nl-NL', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
      });
    },
    onSaveProfile(profileCopy) {
      // Update the profileData after a successful save
      this.profileData = {...profileCopy};
      this.sessionService.saveUserIntoBrowserStorage(this.userData);
      // Save the new image data to localStorage
    },

    getImageBLOBUrl() {
      return getImageBLOBUrl(this.profileData);
    },

    getCommentImage(user) {
      return getImageBLOBUrl(user);
    }
  },
};
</script>

<style scoped></style>
