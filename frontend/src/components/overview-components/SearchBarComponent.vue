<template>
  <form>
    <div class="relative">
      <div class="absolute inset-y-0 left-0 flex items-center pl-5 pointer-events-none">
        <svg class="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
             viewBox="0 0 20 20">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
        </svg>
      </div>
      <input v-model="this.input" @keyup="this.onSearchKeyup" type="search" id="default-search"
             class="block w-full p-3 pl-12 text-sm text-gray-900 border-neutral-200 rounded-lg bg-neutral-100"
             :style="{ backgroundColor: backgroundColor }"
             placeholder="Search" required>
    </div>
  </form>
</template>

<script>

export default {
  name: "SearchBarComponent",
  props: {
    backgroundColor: String,
  },
  methods: {
    onSearchKeyup() {
      this.$emit('searchKeyup', this.input)
    },
    clearInput() {
      this.input = null;
    }
  },
  data() {
    return {
       input: null,
    }
  }
}
</script>

<style scoped>

</style>
