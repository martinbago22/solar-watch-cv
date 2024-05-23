<template>
    <div>
        {{ message }}
    </div>
</template>
  
<script setup>
    import { ref } from 'vue';
    const message = ref("I am alive");
    async function registerUser() {
        const user = {
            username: "abc123",
            password: "pw1"
        };
        try {
            const response = await fetch("http://localhost:8080/api/user/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(user)
            });
            if (response.ok) {
                console.log("ok")
            }
        } catch (e) {
            console.error(e);
        }
    }
    async function getWeatherInfo() {
        try {
            const response = await fetch("http://localhost:8080/api/weatherforecast/current");
            const weatherInfo = await response.json();
            console.log(weatherInfo);
        } catch (e) {
            console.error(e);
        }
    }
    getWeatherInfo();
</script>