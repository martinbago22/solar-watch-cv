<template>
  <div>
    <b-form @submit.prevent="fetchWeatherInfo1(requestedCity)">
      <b-form-group
        id="input-group-1"
        label="Requested city:"
        label-for="input-1"
        description="Look up current weather for this city."
      >
        <b-form-input
          id="input-1"
          type="search"
          placeholder="Requested City"
          v-model="requestedCity"
          required
        ></b-form-input>
      </b-form-group>
      <b-button type="submit" variant="primary">Search</b-button>
    </b-form>
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
