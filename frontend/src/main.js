import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

// Toasts
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";

// CSS
import "./style.css"

// GSAP
import { gsap } from "gsap";
import TextPlugin from "gsap/TextPlugin";
gsap.registerPlugin(TextPlugin);

createApp(App).use(router).use(Toast, {transition: "Vue-Toastification__fade"}).mount('#app')
