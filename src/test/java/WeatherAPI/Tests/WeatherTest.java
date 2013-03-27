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
			4062,
			8528,
			12528,
			12941,
			15061,
			17344,
			20057,
			20078,
			24221,
			27323,
			28450,
			28480,
			30114,
			30645,
			35961,
			36033,
			40295,
			41554,
			42753,
			48105,
			49653,
			50328,
			53545,
			55345,
			55718,
			56473,
			56552,
			57326,
			59114,
			60189,
			61025,
			68381,
			69022,
			70763,
			71066,
			71419,
			73491,
			76903,
			79783,
			84716,
			88042,
			88119,
			91012,
			91978,
			92173,
			93675,
			94284,
			95209,
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
		System.out.println(String.format("Percipitation (mm): %s", w.getPercipitation()));
		System.out.println(String.format("Clouds:             %s", w.getCloudCover()));
		System.out.println(String.format("Humidity:           %s", w.getHumidity()));
		System.out.println(String.format("Conditions:         %s", w.getConditions()));
		System.out.println();
	}
}

