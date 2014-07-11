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
	
	@Test
	public void testZIPCodes() {
		
		int[] zipCodes = new int[] {
			2045,
			8528,
			12941,
			17344,
			20078,
			27323,
			28480,
			30645,
			36033,
			41554,
			48105,
			50328,
			55345,
			55718,
			56552,
			59114,
			61025,
			69022,
			71066,
			73491,
			79783,
			88042,
			91012,
			92173,
			94284,
			97436,
		};
		
		for (int i = 0; i < zipCodes.length; i++) {
			if (i != 0 && i % 9 == 0) {
				try {
					Thread.sleep(60 * 1000);
				}
				catch (Exception e) {
					
				}
			}
			printWeather(WeatherAPI.getWeather(zipCodes[i]));
		}
	}
	
	private void printWeather(IWeather w) {
		System.out.println(String.format("--- %s ---", w.getLocationName()));
		System.out.println(String.format("Degrees C:          %s", w.getDegreesCelcius()));
		System.out.println(String.format("Degrees F:          %s", w.getDegressFahrienhiet()));
		System.out.println(String.format("Wind (MPH):         %s", w.getWindSpeedMPH()));
		System.out.println(String.format("Wind (KPH):         %s", w.getWindSpeedKPH()));
		System.out.println(String.format("Wind Direction:     %s", w.getWindDirection()));
		System.out.println(String.format("Percipitation (mm): %s", w.getPrecipitation()));
		System.out.println(String.format("Clouds:             %s", w.getCloudCover()));
		System.out.println(String.format("Humidity:           %s", w.getHumidity()));
		System.out.println(String.format("Conditions:         %s", w.getConditions()));
		System.out.println();
	}
}

