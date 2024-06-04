<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    Hello!
    <form @submit.prevent="greet">
      <input placeholder="username" v-model="username" />
      <input placeholder="password" type="password" v-model="password" />
      <button type="submit">Register</button>
    </form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
const username = ref<string>('');
const password = ref<string>('');
function greet(): void {
  alert('Thank you for registering');
}
</script>

<style scoped></style>
