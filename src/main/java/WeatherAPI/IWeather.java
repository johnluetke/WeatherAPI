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
import java.util.EnumSet;

/**
 * Interface for obtaining weather information from an abstracted service
 */
public interface IWeather {
	
	/**
	 * Gets the type of location used to retrieve weather data
	 * 
	 * @return type of location
	 */
	LocationType getLocationType();
	
	/**
	 * Gets the location used to retrieve weather data
	 * 
	 * @return location
	 */
	String getLocation();
	
	/**
	 * Gets the the friendly name of the location
	 * 
	 * @return location
	 */
	String getLocationName();
	
	/**
	 * Gets the degrees in Celcius at the time of the last update.
	 * 
	 * @return The degrees in Celcius.
	 */
	double getDegreesCelcius();

	/**
	 * Gets the degress in Fahrienhiet at the time of the last update.
	 * 
	 * @return The degress in Fahrienhiet.
	 */
	double getDegressFahrienhiet();
	
	/**
	 * Gets the wind speed in miles per hour at the time of the last update.
	 * 
	 * @return The wind speed in MPH.
	 */
	double getWindSpeedMPH();
	
	/**
	 * Gets the wind speed in kilometers per hour at the time of the last update.
	 * 
	 * @return The wind speed in KPH.
	 */
	double getWindSpeedKPH();
	
	/**
	 * Gets the wind direction at the time of the last update.
	 * 
	 * @return The wind direction.
	 */
	WindDirection getWindDirection();
	
	/**
	 * Gets the cloud cover at the time of the last update. The value returned 
	 * is a percentage. 0 is no clouds, 100 is overcast.
	 * 
	 * @return The cloud cover.
	 */
	double getCloudCover();
	
	/**
	 * Gets the precipitation in millimeters at the time of the last update.
	 * 
	 * @return The precipitation in millimeters
	 */
	double getPrecipitation();
	
	/**
	 * Gets the humidity at the time of the last update.
	 * 
	 * @return The humidity.
	 */
	double getHumidity();
	
	/**
	 * Gets the weather conditions at the time of the last update.
	 * 
	 * @return The weather conditions.
	 */
	EnumSet<WeatherCondition> getConditions();
}
