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
