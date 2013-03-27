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

package WeatherAPI.Tests;
	
import WeatherAPI.IWeather;
import WeatherAPI.WeatherAPI;
import org.junit.Test;

public class WeatherTest {
		
	@Test
	public void testAirportCodes() {
		
		String[] airports = new String[] {
			"SEA",
			"SFO",
			"LAX",
			"ORD",
			"DEN",
			"ATL",
			"JFK",
			"LHR",
			"HND",
			"CDG",
			"HKG",
			"MAD"
		};
		
		for (int i = 0; i < airports.length; i++) {
			if (i != 0 && i % 10 == 0) {
				try {
					Thread.sleep(60 * 1000);
				}
				catch (Exception e) {
					
				}
			}
			printWeather(WeatherAPI.getWeather(airports[i]));
		}
	}
	
	private void printWeather(IWeather w) {
		System.out.println(String.format("Degrees C:          %s", w.getDegreesCelcius()));
		System.out.println(String.format("Degrees F:          %s", w.getDegressFahrienhiet()));
		System.out.println(String.format("Wind (MPH):         %s", w.getWindSpeedMPH()));
		System.out.println(String.format("Wind (KPH):         %s", w.getWindSpeedKPH()));
		System.out.println(String.format("Wind Direction:     %s", w.getWindDirection()));
		System.out.println(String.format("Percipitation (mm): %s", w.getPercipitation()));
		System.out.println(String.format("Clouds:             %s", w.getCloudCover()));
		System.out.println(String.format("Humidity:           %s", w.getHumidity()));
		System.out.println(String.format("Conditions:         %s", w.getConditions()));
		System.out.println();
	}
}

