<template>
  <div class="container">
    <h1>Hello this is the main page!</h1>
    <p>Search for current weather in city</p>
    <input type="search" placeholder="City Name" v-model="requestedCity" class="input"/>
    <button type="submit" @click.prevent="fetchWeatherInfo1(requestedCity)" class="button">Search</button>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import type { CurrentWeatherResponse } from '../../api/fetchingMethods';

async function fetchWeatherInfo1(cityName :string): Promise<CurrentWeatherResponse> {
  try {
    const response = await fetch(`/api/weatherforecast/current?city=${cityName}`);
    const currentWeatherResponse: CurrentWeatherResponse = await response.json();
    console.log(currentWeatherResponse);
    return currentWeatherResponse;
  } catch (e) {
    console.error(e);
    throw new Error(`Failed to fetch weather information: ${e}`);
  }
}
const requestedCity = ref('');
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
  font-family: Arial, sans-serif;
}

h1 {
  font-size: 24px;
  margin-bottom: 10px;
}

p {
  font-size: 18px;
  margin-bottom: 20px;
}

.input {
  padding: 10px;
  font-size: 16px;
  width: 80%;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button {
  padding: 10px 20px;
  font-size: 16px;
  color: white;
  background-color: #007bff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.button:hover {
  background-color: #0056b3;
}
</style>
