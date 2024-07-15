<template>
  <div class="page-header">
    <h1>Welcome user, you are logged in!</h1>
    <div class="input-group mb-3">
      <input type="text" class="form-control" placeholder="Requested city" aria-label="Requested city" aria-describedby="button-addon2">
      <button class="btn btn-primary" type="button" id="button-addon2">Search</button>
    </div>
  </div>
    
</template>

<script setup lang="ts">
import { ref } from 'vue';
import type { CurrentWeatherResponse } from '../../api/fetchingMethods';

async function fetchWeatherInfo1(cityName: string): Promise<CurrentWeatherResponse> {
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
