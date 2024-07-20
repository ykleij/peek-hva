<template>
  <RouterLink to="/" class="fixed bottom-10 right-10 text-sm bg-neutral-900 py-2 px-4 z-50 rounded-xl text-white">HOME</RouterLink>
    <div class="bg-neutral-50 text-neutral-900 py-12 flex flex-col items-center">
    <h1 class="text-2xl font-bold">FAQ</h1>
    <ul class="w-3/4 mt-12 divide-y divide-neutral-200">
      <AccordionComponent v-for="question in questions" :key="question" :question="question"/>
    </ul>
  </div>
</template>

<script>
import AccordionComponent from "@/components/AccordionComponent";

export default {
  name: "FAQView",
  components: {
    AccordionComponent,
  },
  data() {
    return {
      questions: [
        {
          "id": 1,
          "q": "How does the matching process between experts and projects work?",
          "a": "Our matching process uses advanced algorithms to match experts to projects based on their skills and experience."
        },
        {
          "id": 2,
          "q": "What happens after I register as an expert?",
          "a": "After successfully registering as an expert and having filled in your profile, you will be included in our matching process. " +
              "When you are qualified for a project, our team will contact you personally."
        },
        {
          "id": 3,
          "q": "What can I do after I register as an expert?",
          "a": "After logging in you can view all our projects and other applicants. You're also able to like projects for easy review."
        },
        {
          "id": 4,
          "q": "What kind of projects are available on the platform?",
          "a": "Our platform offers various projects in different sectors, from technology to marketing and more."
        },
        {
          "id": 5,
          "q": "What about the confidentiality of account and project information?",
          "a": "We consider the confidentiality of account and project information of great importance and ensure strict security measures to protect data."
        },
        {
          "id": 6,
          "q": "How can I change my password?",
          "a": "If you forgot your password, you can request to reset your password via the login page."
        },
        {
          "id": 7,
          "q": "Do you offer support for international projects?",
          "a": "Yes, our platform supports international projects and experts from all over the world."
        },
        {
          "id": 8,
          "q": "Are there any fees for experts to participate in the platform?",
          "a": "Applying as an expert is free. We only charge a fee for successful collaboration with a project."
        },
        {
          "id": 9,
          "q": "How long does it take before I am matched with a project?",
          "a": "Matching time varies, but we aim to find a match as quickly as possible after registration."
        },
        {
          "id": 10,
          "q": "What should I do if I have problems using the web application?",
          "a": "If you have technical problems or need help using our web application, please contact our customer support. We are ready to help you."
        },
        {
          "id": 11,
          "q": "Where can I contact customer support?",
          "a": "If you have any additional questions or need help, you can contact us via E-mail: peekemploymentservice@gmail.com."
        },
      ],
      showAnswer: false,
    }
  },
  methods: {}
}
</script>

<style scoped>

</style>
