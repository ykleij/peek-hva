<template>
  <div ref="autocomplete" class="relative">
    <input type="text"
           :class="this.inputStyle"
           v-model="this.inputValue"
           @keydown="handleInputKeyDown"
           aria-autocomplete="list"
           aria-controls="autocomplete-list"
           :aria-activedescendant="activeItemId"
           :placeholder="this.placeholder">
    <ul v-show="isListVisible && foundItems && foundItems.length > 0"
        class="absolute z-30 w-full bg-white shadow overflow-y-auto max-h-42 divide-y divide-neutral-200 rounded"
        id="autocomplete-list"
        aria-expanded="false"
        role="listbox"
        @keydown.prevent="handleListKeyDown">
      <li v-for="(item, index) in foundItems"
          :key="index"
          role="option"
          :aria-selected="isActiveItem(index)"
          @click="handleItemClick(item)"
          :id="'item-' + index"
          class="px-3 py-2 cursor-pointer text-black hover:bg-neutral-200 text-sm">
        {{ item }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "ProjectRoleAutocompleteComponent",
  props: {
    items: Object,
    placeholder: String,
    inputKey: String,
    inputStyle: String,
  },
  computed: {
    foundItems() {
      if (!this.inputValue) return [];

      try {
        return this.items.filter(item => item.toLowerCase().includes(this.inputValue.toLowerCase())).slice(0, 10);
      } catch (e) {
        return [];
      }
    },
    activeItemId() {
      return this.activeIndex >= 0 ? `item-${this.activeIndex}` : null;
    },
  },
  data() {
    return {
      activeIndex: -1,
      inputValue: null,
      isListVisible: false,
    }
  },
  watch: {
    inputValue(newValue) {
      this.$emit("autoCompleteInputChange", this.inputKey, newValue)
    }
  },

  methods: {
    handleDocumentClick(event) {
      const isClickInsideAutocomplete = this.$refs.autocomplete.contains(event.target);
      if (!isClickInsideAutocomplete) {
        this.isListVisible = false;
      }
    },

    handleInputKeyDown(event) {
      this.isListVisible = true;

      switch (event.key) {
        case 'ArrowDown':
          this.handleArrowDown();
          break;
        case 'ArrowUp':
          this.handleArrowUp();
          break;
        case 'Enter':
          this.handleEnter();
          break;
        case 'Tab':
          event.preventDefault();
          this.handleArrowDown();
          break;
      }
    },

    handleListKeyDown(event) {
      if (event.key === 'ArrowDown' || event.key === 'ArrowUp') {
        this.$refs.input.focus();
      }
    },

    handleArrowDown() {
      if (this.activeIndex < this.foundItems.length - 1) {
        this.activeIndex++;
      }
    },

    handleArrowUp() {
      if (this.activeIndex > 0) {
        this.activeIndex--;
      }
    },

    handleEnter() {
      if (this.activeIndex !== -1) {
        this.handleItemClick(this.foundItems[this.activeIndex]);
      }
    },

    isActiveItem(index) {
      return index === this.activeIndex;
    },

    handleItemClick(title) {
      this.inputValue = title;
      this.activeIndex = -1;
      this.isListVisible = false;
    },
  },
  mounted() {
    document.addEventListener('click', this.handleDocumentClick);
  },
  beforeUnmount() {
    document.removeEventListener('click', this.handleDocumentClick);
  },
}
</script>

<style scoped>

ul li[aria-selected="true"] {
  background-color: rgb(212 212 212 / var(--tw-bg-opacity));
}

</style>