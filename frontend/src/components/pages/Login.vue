<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <form class="row g-1 needs-validation" novalidate>
    <div class="row-md-4">
      <label for="validationCustom01" class="form-label">Username</label>
      <input type="text" class="form-control" id="validationCustom01" required />
      <span class="help-block">Must be between 4 and 10 characters long and cannot contain any special characters.</span>
      <div class="valid-feedback">Looks good!</div>
    </div>
    <div class="row-md-4">
      <label for="validationCustom02" class="form-label">Password</label>
      <input type="password" class="form-control" id="validationCustom02" required />
      <div class="valid-feedback">Looks good!</div>
      <span class="help-block">Must be between 4 and 10 characters long.</span>
    </div>
    <div class="btn-group">
      <button class="btn btn-outline-primary" type="submit">Register</button>
      <button class="btn btn-outline-primary" type="submit">Submit form</button>
    </div>
  </form>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { fetchWeatherInfo, registerUser } from '../../api/fetchingMethods';
const username = ref<string>('');
const password = ref<string>('');
</script>
