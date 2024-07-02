export async function getWeatherInfo(): Promise<CurrentWeatherResponse> {
	try {
		const response = await fetch('/api/weatherforecast/current?city=Tokyo');
		const weather :CurrentWeatherResponse = await response.json();
		console.log(weather);
		return weather;
	} catch (e) {
		console.error(e);
		throw new Error(`Failed to fetch weather information: ${e}`);
	}
}
export async function registerUser (username: string, password: string): Promise<string> {
    const userRegisterRequest = {
      username,
      password
    };
    const response = await fetch('/api/user/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(userRegisterRequest)
    });
    if (response.ok) {
      alert('User succesfully registered');
      return 'User succesfully registered';
    } else return 'Something went wrong';
  }

  interface CurrentWeatherResponse {
    cityName: string,
    temperature: number,
    humidity: number,
    windSpeed: number,
    description: string,
    visibility: number,
    pictureTakingWarning: string,
    sunriseTime: string,
    sunsetTime: string
  }