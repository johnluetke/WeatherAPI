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

namespace WeatherAPI {
	
	public class WeatherTest {
		
		public static void Main (String[] args) {
			
			IWeather w = (IWeather)WeatherAPI.GetWeather("");
			
			Console.WriteLine(String.Format("Degrees C:          {0}", w.DegreesCelcius));
			Console.WriteLine(String.Format("Degrees F:          {0}", w.DegressFahrienhiet));
			Console.WriteLine(String.Format("Wind (MPH):         {0}", w.WindSpeedMPH));
			Console.WriteLine(String.Format("Wind (KPH):         {0}", w.WindSpeedKPH));
			Console.WriteLine(String.Format("Wind Direction:     {0}", w.WindDirection));
			Console.WriteLine(String.Format("Percipitation (mm): {0}", w.Percipitation));
			Console.WriteLine(String.Format("Clouds:             {0}", w.CloudCover));
			Console.WriteLine(String.Format("Humidity:           {0}", w.Humidity));
			Console.WriteLine(String.Format("Conditions:         {0}", w.Conditions));
		}
	}
}

