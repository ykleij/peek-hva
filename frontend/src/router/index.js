import {createRouter, createWebHashHistory} from 'vue-router';

const routes = [
    {
        path: '/',
        name: 'login',
        component: () => import('../views/LandingsView.vue'),
        meta: {requiresAuth: false, requiresAdmin: false},
    },
    {
        path: '/admin/overview',
        name: 'admin-overview',
        component: () => import('../views/OverviewView.vue'),
        meta: {requiresAuth: true, requiresAdmin: true},
    },
    {
        path: '/admin/create-project',
        name: 'admin-create-project',
        component: () => import('../views/CreateProjectView'),
        meta: {requiresAuth: true, requiresAdmin: true},
    },
    {
        path: '/admin/project-roles',
        name: 'admin-project-roles',
        component: () => import('../views/ProjectRolesView'),
        meta: {requiresAuth: true, requiresAdmin: true},
    },
    {
        path: '/admin/invitation-links',
        name: 'admin-invitation-links',
        component: () => import('../views/InvitationLinksView'),
        meta: {requiresAuth: true, requiresAdmin: true},
    },
    {
        path: '/admin/matching-settings',
        name: 'admin-matching-settings',
        component: () => import('../views/MatchingSettingsView'),
        meta: {requiresAuth: true, requiresAdmin: true},
    },
    {
        path: '/admin/dashboard',
        name: 'admin-dashboard',
        component: () => import('../views/DashboardView'),
        meta: {requiresAuth: true, requiresAdmin: true},
    },
    {
        path: '/pre-sign-up',
        name: 'pre-sign-up',
        component: () => import('../views/PreSignUpView.vue'),
        meta: {requiresAuth: false, requiresAdmin: false},
    },
    {
        path: '/sign-up',
        name: 'sign-up',
        component: () => import('../views/SignUpView.vue'),
        meta: {requiresAuth: true, requiresAdmin: false},
    },
    {
        path: '/not-allowed',
        name: 'not-allowed',
        component: () => import('../views/NotAllowedView.vue'),
        meta: {requiresAuth: false, requiresAdmin: false},
    },
    {
        path: '/expertMatching',
        name: 'expertMatching',
        component: () => import('../views/ExpertMatchingView.vue'),
        meta: {requiresAuth: true, requiresAdmin: false},
    },
    {
        path: '/expert/expert-overview',
        name: 'expert-overview',
        component: () => import('../views/ExpertOverview.vue'),
        meta: { requiresAuth: true , requiresAdmin: false},
    },
    {
        path: '/expert/expertProject/:id',
        name: 'expertProject',
        component: () => import('../views/ExpertProjectView.vue'),
        meta: { requiresAuth: true , requiresAdmin: false},
    },
    {
        path: '/project/:id',
        name: 'project',
        component: () => import('../views/ProjectView.vue'),
        meta: {requiresAuth: true},
    },
    {
        path: '/profile',
        name: 'profile',
        component: () => import('../views/ProfileView.vue'),
        meta: {requiresAuth: true},
        children: [
            {
                path: ':id?',
                component: () => import('../components/profile-view-components/ProfileCardComponent.vue'),
            },
        ]
    },
    {
        path: '/faq',
        name: 'faq',
        component: () => import('../views/FAQView.vue'),
        meta: {requiresAuth: false, requiresAdmin: false},
    },
    {
        path: '/awaiting-approval',
        name: 'awaiting-approval',
        component: () => import('../views/AwaitingApprovalView.vue'),
        meta: {requiresAuth: true, requiresAdmin: false},
    },
    {
        path: '/password-reset/:pathMatch(.*)*',
        name: 'password-reset',
        component: () => import('../views/PasswordResetView.vue'),
        meta: {requiresAuth: false, requiresAdmin: false},
    },
    {
        path: '/request-password-reset',
        name: 'request-password-reset',
        component: () => import('../views/RequestPasswordResetView.vue'),
        meta: {requiresAuth: false, requiresAdmin: false},
    },
    {
        path: '/accept-deny-experts',
        name: 'accept-deny-experts',
        component: () => import('../views/MariusAcceptExpertView'),
        meta: {requiresAuth: true, requiresAdmin: false},
    },
    {
        path: '/notifications',
        name: 'notifications',
        component: () => import('../views/NotificationsView'),
        meta: {requiresAuth: true, requiresAdmin: false},
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'unknown-route',
        component: () => import('../components/UnknownRoute.vue'),
        meta: {requiresAuth: false, requiresAdmin: false},
    },

];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});

export default router;
