<template>
  <head>
    <title>yo</title>
  </head>

  <NavBarComponent v-if="loadNav" />
  <div class="flex flex-row" :class="loadNav ? 'h-[calc(100vh-80px)]' : ''">
    <SideNavComponent v-if="loadAdminNav" />

    <div
      id="content"
      class="overflow-y-auto w-full"
      :class="loadNav ? 'max-h-[calc(100vh-80px)]' : ''">
      <router-view />
    </div>
  </div>
</template>

<script>
import CONFIG from '../app-config';

import NavBarComponent from '@/components/nav-components/NavBarComponent';
import SideNavComponent from '@/components/nav-components/SideNavComponent';

import { CachedRestAdaptor } from '@/services/CachedRestAdaptor';
import { SessionSbService } from '@/services/SessionSbService';
import { FetchInterceptor } from '@/services/FetchInterceptor';
import { MatchingService } from '@/services/MatchingService';
import { LikingService } from '@/services/LikingService';

import { Project } from '@/models/Project';

import {reactive, shallowReactive} from 'vue';
import {Profile} from '@/models/Profile';
import {Invitation} from '@/models/Invitation';
import {errorHandler} from '@/utils/actionHandler';
import {ProjectService} from '@/services/ProjectService';
import {UserService} from '@/services/UserService';
import {ProjectRoleService} from '@/services/ProjectRoleService';
import {RegisterService} from "@/services/RegisterService";
import {NotificationService} from "@/services/NotificationService";
import {WebSocketService} from "@/services/WebSocketService";
import {ResetPasswordService} from "@/services/ResetPasswordService";
import {UserProfile} from "@/models/UserProfile";
import {ExpertProfileService} from "@/services/ExpertProfileService";
import {CommentService} from "@/services/CommentService";
import {Comment} from '@/models/Comment';

export default {
  components: {
    SideNavComponent,
    NavBarComponent,
  },
  provide() {
    this.theSessionService = shallowReactive(
      new SessionSbService(
        CONFIG.BACKEND_URL + '/authentication',
        CONFIG.JWT_STORAGE_ITEM
      )
    );
    this.theFetchInterceptor = new FetchInterceptor(
      this.theSessionService,
      this.$router
    );
    this.notificationService = reactive(
      new NotificationService(
        CONFIG.BACKEND_URL + '/notifications',
        this.webSocketService
      )
    );
    this.webSocketService = new WebSocketService(
      CONFIG.BACKEND_URL + '/ws/notifications',
      this.theSessionService.getTokenFromBrowserStorage()
    );

    if (this.webSocketService.isConnected()) {
      this.webSocketService.subscribeToWebSocketTopics(
        this.notificationService,
        this.theSessionService.getUserRoleFromBrowserStorage(),
        this.theSessionService.getUserIdFromBrowserStorage()
      );
    }

    return {
      fetchInterceptor: this.theFetchInterceptor,
      sessionService: this.theSessionService,
      webSocketService: this.webSocketService,
      notificationService: this.notificationService,

      registerService: new RegisterService(CONFIG.BACKEND_URL + '/users'),
      projectsService: reactive(new CachedRestAdaptor(CONFIG.BACKEND_URL + '/projects', Project.copyConstructor)),
      projectYesterdayService: reactive(new ProjectService(CONFIG.BACKEND_URL + '/projects',)),
      usersYesterdayService: reactive(new UserService(CONFIG.BACKEND_URL + '/users',)),
      projectRoleService: reactive(new ProjectRoleService(CONFIG.BACKEND_URL + '/project-roles',)),
      expertProfileService: reactive(new ExpertProfileService(CONFIG.BACKEND_URL + '/expert-profile',)),
      commentService: reactive(new CommentService(CONFIG.BACKEND_URL + '/comment',)),
      commentRestService: reactive(new CachedRestAdaptor(CONFIG.BACKEND_URL + '/comment', Comment.copyConstructor)),
      normalMatchingService: reactive(new CachedRestAdaptor(CONFIG.BACKEND_URL + '/matching',)),
      usersService: reactive(new CachedRestAdaptor(CONFIG.BACKEND_URL + '/users', Profile.copyConstructor)),
      userProfileService: reactive(new CachedRestAdaptor(CONFIG.BACKEND_URL + '/user-profile', UserProfile.copyConstructor)),
      matchingService: reactive(new MatchingService(CONFIG.BACKEND_URL + '/matching')),
      invitationService: reactive(new CachedRestAdaptor(CONFIG.BACKEND_URL + '/invitations', Invitation.copyConstructor)),
      likingService: reactive(new LikingService(CONFIG.BACKEND_URL + '/like')),
      resetPasswordService: reactive(
        new ResetPasswordService(CONFIG.BACKEND_URL + '/resetPassword')
      ),
    };
  },
  unmounted() {
    this.theFetchInterceptor.unregister();
  },
  data() {
    return {
      topics: {
        admin: ['userLogin', 'userRegistration'],
        expert: [],
      },
      isAuthenticated: false,
      webSocketService: null,
    };
  },
  async created() {
    this.$router.beforeEach(this.routerGuard);
  },
  methods: {
    routerGuard(to, from, next) {
      if (to.meta.requiresAuth) {
        if (!this.theSessionService.isAuthenticated()) {
          next({ name: 'login', query: { logout: true } });
          errorHandler('Not authenticated, please log in.');
          return;
        } else {
          this.isAuthenticated = true;
        }
        if (to.meta.requiresAdmin) {
          if (!this.theSessionService.isAdmin()) {
            next({ name: 'login', query: { logout: true } });
            errorHandler('You are not authorized to visit this page.');
            return;
          }
        }
        if (!this.theSessionService.getUserFromBrowserStorage().userProfile) {
          if (to.name !== 'sign-up') {
            errorHandler('Please create a profile before logging in.');
            return next({ name: 'sign-up' });
          }
        }
      }
      if (to.query.logout) {
        this.isAuthenticated = false;
      }

      next();
    },
    connectWebSocket() {
      this.webSocketService.connect(
        this.theSessionService.getTokenFromBrowserStorage()
      );

      this.webSocketService.subscribeToWebSocketTopics(
        this.notificationService,
        this.theSessionService.getUserRoleFromBrowserStorage(),
        this.theSessionService.getUserIdFromBrowserStorage()
      );
    },
  },
  watch: {
    async isAuthenticated(newStatus) {
      // If authenticated and no WebSocket connection, create new connection and subscribe to topics
      if (
        newStatus &&
        (!this.webSocketService || !this.webSocketService.isConnected())
      ) {
        this.connectWebSocket();

        // If not authenticated disconnect the WebSocket connection
      } else if (
        !newStatus &&
        this.webSocketService &&
        this.webSocketService.isConnected()
      ) {
        this.webSocketService.disconnect();
      }
    },
  },
  computed: {
    loadNav() {
      return (
        this.$route.meta.requiresAuth &&
        this.theSessionService.isAuthenticated() &&
        this.$route.path !== '/sign-up'
      );
    },
    loadAdminNav() {
      return (
        this.$route.meta.requiresAuth &&
        this.theSessionService.isAdmin() &&
        this.$route.path !== '/sign-up'
      );
    },
  },
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap');

#content {
  /*max-height: calc(100vh - 80px); */
  margin: 0;
  /*overflow-x: hidden;*/
}

body {
  background-color: rgb(246, 246, 246);
  /*max-width: 100vw;*/
}

#app {
  font-family: 'Inter', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

.card-header {
  padding: 1rem 1.35rem;
  margin-bottom: 0;
  background-color: rgba(33, 40, 50, 0.03);
  border-bottom: 1px solid rgba(33, 40, 50, 0.125);
}
</style>
