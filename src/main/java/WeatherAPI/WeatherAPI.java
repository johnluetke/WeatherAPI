//	Copyright 2012 John Luetke
//
//	Licensed under the Apache License, Version 2.0 (the "License");
//	you may not use this file except in compliance with the License.
//	You may obtain a copy of the License at
//
//		http://www.apache.org/licenses/LICENSE-2.0
//
//	Unless required by applicable law or agreed to in writing, software
//	distributed under the License is distributed on an "AS IS" BASIS,
//	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//	See the License for the specific language governing permissions and
//	limitations under the License.

package WeatherAPI;

import WeatherAPI.Providers.LocationType;
import WeatherAPI.Providers.WeatherProvider;
import java.util.ArrayList;
import java.util.List;
import org.reflections.Reflections;

/**
 * Gateway class to the WeatherAPI.
 */
public class WeatherAPI {
	
	private static final WeatherAPI INSTANCE = new WeatherAPI();
	
	/**
	 * Gets the weather for the given city and state.
	 *
	 * @param city City name for which weather information should be retrieved
	 * @param state State name for which weather information should be retrieved
	 *
	 * @return A class adhering to IWeather which will contain weather data for 
	 * the requested location 
	 */	
	public static IWeather getWeather(String city, String state) {
		return WeatherAPI.getInstance(LocationType.CityState, String.format("%s, %s", city, state));
	}

	/**
	 * Gets the weather for the given airport code
	 *
	 * @param airportCode The Airport code for which weather information should be retrieved.
	 *
	 * @return A class adhering to IWeather which will contain weather data for 
	 * the requested location 
	 */	
	public static IWeather getWeather(String airportCode) {
		return WeatherAPI.getInstance(LocationType.AirportCode, airportCode);
	}

	/**
	 * Gets the weather for the given ZIP code
	 *
	 * @param zipCode The ZIP code for which weather information should be retrieved.
	 *
	 * @return A class adhering to IWeather which will contain weather data for 
	 * the requested location 
	 */			
	public static IWeather getWeather(int zipCode) {
		return WeatherAPI.getInstance(LocationType.ZipCode, String.format("%s", zipCode));
	}

	/**
	 * Gets the weather for the given latitude and longitude
	 *
	 * @param latitude The latitude coordinate for which weather information should be
	 * @param longitude The longitude coordinate for which weather information should be retrieved.
	 *
	 * @return A class adhering to IWeather which will contain weather data for 
	 * the requested location 
	 */		
	public static IWeather getWeather(double latitude, double longitude) {
		return WeatherAPI.getInstance(LocationType.LatitudeLongitude, String.format("%s,%s", latitude, longitude));
	}

	private final List<WeatherProvider> _providers;

	/**
	 * Initializes a new instance of the WeatherAPI class. All providers
	 * discovered via reflection and added to a List for use in 
	 * getInstance
	 */
	private WeatherAPI() {
		_providers = new ArrayList<WeatherProvider>();

		Reflections reflections = new Reflections("WeatherAPI.Providers");
		
		try {
			for (Class clazz : reflections.getSubTypesOf(WeatherProvider.class)) {
				_providers.add((WeatherProvider)clazz.newInstance());
			}
		}
		catch (InstantiationException e) {
			e.printStackTrace(System.err);
		}
		catch (IllegalAccessException e) {
			e.printStackTrace(System.err);
		}
	}

	/**
	 * Factory method for obtaining an instance of a provider
	 *
	 * @param sourceType Type of location that weather data will be retrieved from.
	 * @param source The string value of the source to give to the provider.
	 * 
	 * @returns An instance of IWeather containing weather data from the provider.
	 *
	 * @throws ArgumentException when the provider cannot fetch weather data for the LocationType provided.
	 */
	private static IWeather getInstance(LocationType sourceType, String source) {
		
		// Go through the available providers until we get one that can fulfill the request
		for (WeatherProvider provider : INSTANCE._providers) {
			if (provider.IsAvailable() && provider.Supports(sourceType)) {
				provider.setLocation(source);
				provider.setLocationType(sourceType);
				provider.Update();
				return (IWeather)provider;
			}
		}

		// No providers can fullfill the request
		throw new IllegalArgumentException(String.format("No available provider supports a %s source: %s", sourceType, source));
	}
}