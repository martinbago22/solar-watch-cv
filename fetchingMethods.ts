import axios from 'axios';
export async function fetchWeather(): Promise<string> {
	try {
		const response = await fetch('/api/weatherforecast/current');
		const weather = await response.json();
		console.log(weather);
		return weather;
	} catch (e) {
		console.error(e);
		return 'Error fetching weather data';
	}
}

// szeparálni a megjelenítést , if !response.ok  throw error
// axios library
// options - composition összehasonlítása
export async function registerUser(
	username: string,
	password: string
): Promise<string> {
	if (isUserNameValid(username)) {
		const userRegisterRequest = {
			username,
			password,
		};
		const response = await fetch('/api/user/register', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
			},
			body: JSON.stringify(userRegisterRequest),
		});
		if (response.ok) {
			alert('User succesfully registered');
			return 'User succesfully registered';
		} else return 'Something went wrong';
	} else return "Username must be between 2-8 letters and it can't contain any whitespaces";
}

function isUserNameValid(username: string): boolean {
	let isValid: boolean = true;
	if (username.length < 2 || username.length > 8) {
		isValid = false;
	} else if (/\s/.test(username)) {
		isValid = false;
	}
	return isValid;
}

async function getWeatherInfo() {
	try {
		const response :APIResponse= axios.get('/api/weatherforecast/current');
		console.log(response)
	} catch (e) {
		console.error(e);
	}
}
type APIResponse = {
    cityName: string,
    temperature: number,
    humidity: number,
    windSpeed: number,
    description: string,
    visibility: number,
    pictureTakingWarning: string,
    sunriseTime: string
    sunsetTime: string
};