import {HomeIcon, PresentationChartLineIcon, PlusIcon, LinkIcon, Cog8ToothIcon, ClipboardIcon} from '@heroicons/vue/24/outline'

export const items = [
    {
        name: "Overview",
        to: "admin-overview",
        icon: HomeIcon
    },
    {
        name: "Statistics",
        to: "admin-dashboard",
        icon: PresentationChartLineIcon
    },
    {
        name: "Create Project",
        to: "admin-create-project",
        icon: PlusIcon
    },
    {
        name: "Manage Project Roles",
        to: "admin-project-roles",
        icon: ClipboardIcon
    },
    {
        name: "Invitation Links",
        to: "admin-invitation-links",
        icon: LinkIcon
    },
    {
        name: "Matching Settings",
        to: "admin-matching-settings",
        icon: Cog8ToothIcon
    },

]