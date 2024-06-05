export async function getWeatherInfo(): Promise<string> {
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
export async function registerUser(username: string, password: string): Promise<string> {
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