<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <form @submit.prevent="greet">
      <label for="userName">Username</label>
      <input placeholder="John Doe" v-model="username" id="userName" />

      <label for="userName">Password</label>
      <input placeholder="pw123" type="password" v-model="password" />

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
/* async function registerUser(): Promise<string> {
  try {
    const userRegisterRequest = {
      username,
      password
    };
    const response = await fetch
  }
} */
async function fetchWeather(): Promise<string> {
  try {
    const response = await fetch("/api/weatherforecast/current");
    const weather = await response.json();
    console.log(weather);
    return weather;
  } catch (e) {
    console.error(e);
    return "Error fetching weather data";
  }
}
fetchWeather();
</script>

<style scoped>
div {
  font-family: Arial, sans-serif;
  margin: 0 auto;
  width: 300px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}

h1 {
  text-align: center;
  color: #333;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  margin-bottom: 5px;
  color: #666;
}

input {
  margin-bottom: 15px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 3px;
  font-size: 14px;
}

input:focus {
  border-color: #007bff;
  outline: none;
}

button {
  padding: 10px;
  color: #fff;
  background-color: #007bff;
  border: none;
  border-radius: 3px;
  font-size: 16px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
