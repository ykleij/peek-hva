<template>
  <div @click="closeModal" class="peek cursor-pointer space-x-1 font-bold z-50 text-3xl text-neutral-800">
    <span>PEEK</span>
    <span class="logo-animation"></span>
  </div>
</template>

<script>
export default {
  name: "LogoComponent",
  props: ['closeModal']
}
</script>


<style scoped>
.logo-animation {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  display: inline-block;
  box-sizing: border-box;
  animation: animloader 10s ease infinite;
}

@keyframes animloader {
  0% {
    box-shadow: -100px 0 rgb(38 38 38 / var(--tw-text-opacity)) inset;
  }
  50% {
    box-shadow: 100px 0 rgb(38 38 38 / var(--tw-text-opacity)) inset;
  }
  100% {
    box-shadow: -100px 0 rgb(38 38 38 / var(--tw-text-opacity)) inset;
  }
}
</style>