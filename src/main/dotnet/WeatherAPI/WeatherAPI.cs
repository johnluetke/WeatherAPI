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

using System;
using System.Collections.Generic;
using System.Reflection;
using WeatherAPI.Providers;

namespace WeatherAPI {
	
	/// <summary>
	/// Gateway class to the WeatherAPI.
	/// </summary>
	/// <exception cref='ArgumentException'>
	/// Thrown when an argument passed to a method is invalid.
	/// </exception>
	public class WeatherAPI {
		
		/// <summary>
		/// Gets the weather for the given city and state
		/// </summary>
		/// <remarks>
		/// This is the most general level for which weather can be retrieved. 
		/// </remarks>
		/// <param name='city'>
		/// City name for which weather information should be retrieved
		/// </param>
		/// <param name='state'>
		/// State name for which weather information should be retrieved
		/// </param>
		/// <returns>
		/// A class adhereing to IWeather which will contain weather data for 
		/// the requested location 
		/// </returns>
		public static IWeather GetWeather(string city, string state) {
			return new WeatherAPI().getInstance(LocationType.CityState, String.Format("{0}, {1}", city, state));
		}
		
		/// <summary>
		/// Gets the weather for the given airport code
		/// </summary>
		/// <param name='airportCode'>
		/// The Airport code for which weather information should be retrieved.
		/// </param>
		/// <returns>
		/// A class adhereing to IWeather which will contain weather data for 
		/// the requested location 
		/// </returns>
		public static IWeather GetWeather(string airportCode) {
			return new WeatherAPI().getInstance(LocationType.AirportCode, airportCode);
		}
		
		/// <summary>
		/// Gets the weather for the given ZIP code
		/// </summary>
		/// <param name='zipCode'>
		/// The ZIP code for which weather information should be retrieved.
		/// </param>
		/// <returns>
		/// A class adhereing to IWeather which will contain weather data for 
		/// the requested location 
		/// </returns>		
		public static IWeather GetWeather(int zipCode) {
			return new WeatherAPI().getInstance(LocationType.ZipCode, String.Format("{0}", zipCode));
		}
		
		/// <summary>
		/// Gets the weather for the given latitude and longitude
		/// </summary>
		/// <param name='latitude'>
		/// The latitude coordinate for which weather information should be
		/// retrieved.
		/// </param>
		/// <param name='longitude'>
		/// The latitude coordinate for which weather information should be
		/// retrieved.
		/// </param>
		/// <returns>
		/// A class adhereing to IWeather which will contain weather data for 
		/// the requested location 
		/// </returns>			
		public static IWeather GetWeather(double latitude, double longitude) {
			return new WeatherAPI().getInstance(LocationType.LatitudeLongitude, String.Format("{0},{1}", latitude, longitude));
		}

		private List<WeatherProvider> _providers;
		
		/// <summary>
		/// Initializes a new instance of the WeatherAPI class. All providers
		/// discovered via reflection and added to a List for use in 
		/// getInstance
		/// </summary>
		private WeatherAPI() {
			_providers = new List<WeatherProvider>();
			
			Assembly assembly = Assembly.Load("WeatherAPI");
			Type[] assemblyTypes = assembly.GetTypes();
			
			foreach (Type t in assemblyTypes) {
				if (t.Namespace.Contains("WeatherAPI.Providers") && 
				    !t.IsAbstract &&
				    t.IsClass &&
				    Activator.CreateInstance(t) is WeatherProvider &&
				    ((WeatherProvider)Activator.CreateInstance(t)).IsAvailable()) {
					_providers.Add((WeatherProvider)Activator.CreateInstance(t));
				}
			}
		}
		
		/// <summary>
		/// Factory method for obtaining an instance of a provider
		/// </summary>
		/// <param name='sourceType'>
		/// Type of location that weather data will be retrieved from.
		/// </param>
		/// <param name='source'>
		/// The string value of the source to give to the provider.
		/// </param>
		/// <returns>
		/// An instance of IWeather containing weather data from the provider.
		/// </returns>
		/// <exception cref='ArgumentException'>
		/// Is thrown when the provider cannot fetch weather data for the 
		/// LocationSource provided.
		/// </exception>
		private IWeather getInstance(LocationType sourceType, string source) {
			
			// Go through the available providers until we get one that can fulfill the request
			foreach (WeatherProvider provider in _providers) {
				if (provider.Supports(sourceType)) {
					provider.Location = source;
					provider.Source = sourceType;
					provider.Update();
					return (IWeather)provider;
				}
			}
			
			// No providers can fullfill the request
			throw new ArgumentException(String.Format("No available provider supports a {0} source", source));
		}
	}
}

