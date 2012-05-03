using System;
using System.IO;
using System.Net;
using System.Xml.XPath;

namespace WeatherAPI.Providers.WorldWeatherOnline {
	
	internal class WWOProvider : WeatherProvider, IWeather {
		
		private const string WWO_API_FORMAT = "xml";
		private const string WWO_API_URL = "http://free.worldweatheronline.com/feed/weather.ashx?q={0}&format={1}&num_of_days=1&key={2}";
		private const string WWO_XPATH_HEADER = "//data/current_condition/{0}";
		
		private XPathDocument _xpath;
		private string _wwo_api_key;
		
		public WWOProvider () : base() {
			if (IsAvailable()) {
				_wwo_api_key = _dllConfig.AppSettings.Settings["WORLD_WEATER_ONLINE_API_KEY"].Value;
			}
		}
		
		public override bool IsAvailable() {
			return _dllConfig.AppSettings.Settings["WORLD_WEATER_ONLINE_API_KEY"] != null &&
					!String.IsNullOrEmpty(_dllConfig.AppSettings.Settings["WORLD_WEATER_ONLINE_API_KEY"].Value);
		}
		
		public override void Update() {
			string url = String.Format(WWO_API_URL, Location, WWO_API_FORMAT, _wwo_api_key);
			HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
			
			HttpWebResponse response = (HttpWebResponse)request.GetResponse();
			string xml = new StreamReader(response.GetResponseStream()).ReadToEnd();
			
			_xpath = new XPathDocument(new StringReader(xml));
		}
		
		public override bool Supports(LocationSource source) { 
			return true;
		}
		
		public double DegreesCelcius {
			get {
				string xpath = String.Format(WWO_XPATH_HEADER, "temp_C/text()");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				return Double.Parse(val.ToString());
			}
		}

		public double DegressFahrienhiet {
			get {
				string xpath = String.Format(WWO_XPATH_HEADER, "temp_F/text()");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				return Double.Parse(val.ToString());
			}
		}

		public double WindSpeedMPH {
			get {
				string xpath = String.Format(WWO_XPATH_HEADER, "windspeedMiles/text()");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				return Double.Parse(val.ToString());
			}
		}
		
		public double WindSpeedKPH {
			get {
				string xpath = String.Format(WWO_XPATH_HEADER, "windspeedKmph/text()");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				return Double.Parse(val.ToString());
			}
		}
		
		public Direction WindDirection {
			get {
				string xpath = String.Format(WWO_XPATH_HEADER, "winddir16Point/text()");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				return (Direction)Enum.Parse(typeof(Direction), val.ToString());
			}
		}

		public double CloudCover {
			get {
				string xpath = String.Format(WWO_XPATH_HEADER, "cloudcover/text()");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				return Double.Parse(val.ToString());
			}
		}
		
		public double Percipitation {
			get {
				string xpath = String.Format(WWO_XPATH_HEADER, "precipMM/text()");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				return Double.Parse(val.ToString());
			}
		}

		public double Humidity { 
			get {
				string xpath = String.Format(WWO_XPATH_HEADER, "humidity/text()");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				return Double.Parse(val.ToString());
			}
		}
		
		public WeatherCondition Conditions {
			get {
				string xpath = String.Format(WWO_XPATH_HEADER, "weatherCode/text()");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				WWOWeatherCode code = (WWOWeatherCode)(int.Parse(val.ToString()));
				
				WeatherCondition currentCondition = translateConditions(code);
				
				return currentCondition;
			}
		}
		
		protected WeatherCondition translateConditions(WWOWeatherCode code) {
			switch (code) {
				case WWOWeatherCode.Blizzard:
					return WeatherCondition.Blizzard | WeatherCondition.Snow;
				case WWOWeatherCode.BlowingSnow:
					return WeatherCondition.Blowing | WeatherCondition.Snow;
				case WWOWeatherCode.ClearSunny:
					return WeatherCondition.Clear;
				case WWOWeatherCode.Cloudy:
					return WeatherCondition.Cloudy;
				case WWOWeatherCode.Fog:
					return WeatherCondition.Fog;
				case WWOWeatherCode.FreezingDrizzle:
					return WeatherCondition.Freezing | WeatherCondition.Drizzle;
				case WWOWeatherCode.FreezingFog:
					return WeatherCondition.Freezing | WeatherCondition.Fog;
				case WWOWeatherCode.HeavyFreezingDrizzle:
					return WeatherCondition.Heavy | WeatherCondition.Freezing | WeatherCondition.Drizzle;
				case WWOWeatherCode.HeavyRain:
					return WeatherCondition.Heavy | WeatherCondition.Rain;
				case WWOWeatherCode.HeavySnow:
					return WeatherCondition.Heavy | WeatherCondition.Snow;
				case WWOWeatherCode.IcePellets:
					return WeatherCondition.Ice | WeatherCondition.Pellets;
				case WWOWeatherCode.LightDrizzle:
					return WeatherCondition.Light | WeatherCondition.Drizzle;
				case WWOWeatherCode.LightFreezingRain:
					return WeatherCondition.Light | WeatherCondition.Freezing | WeatherCondition.Rain;
				case WWOWeatherCode.LightIcePellets:
					return WeatherCondition.Light | WeatherCondition.Ice | WeatherCondition.Pellets;
				case WWOWeatherCode.LightRain:
					return WeatherCondition.Light | WeatherCondition.Rain;
				case WWOWeatherCode.LightRainShower:
					return WeatherCondition.Light | WeatherCondition.Rain | WeatherCondition.Showers;
				case WWOWeatherCode.LightSleet:
					return WeatherCondition.Light | WeatherCondition.Sleet;
				case WWOWeatherCode.LightSleetShowers:
					return WeatherCondition.Light | WeatherCondition.Sleet | WeatherCondition.Showers;
				case WWOWeatherCode.LightSnow:
					return WeatherCondition.Light | WeatherCondition.Snow;
				case WWOWeatherCode.LightSnowShowers:
					return WeatherCondition.Light | WeatherCondition.Snow | WeatherCondition.Showers;
				case WWOWeatherCode.Mist:
					return WeatherCondition.Mist;
				case WWOWeatherCode.ModerateOrHeavyFreezingRain:
					return WeatherCondition.Moderate | WeatherCondition.Heavy | WeatherCondition.Freezing | WeatherCondition.Rain;
				case WWOWeatherCode.ModerateOrHeavyIcePellets:
					return WeatherCondition.Moderate | WeatherCondition.Heavy | WeatherCondition.Ice | WeatherCondition.Pellets;
				case WWOWeatherCode.ModerateOrHeavyRainAndThunder:
					return WeatherCondition.Moderate | WeatherCondition.Heavy | WeatherCondition.Rain | WeatherCondition.Thunder;
				case WWOWeatherCode.ModerateOrHeavyRainShowers:
					return WeatherCondition.Moderate | WeatherCondition.Heavy | WeatherCondition.Rain | WeatherCondition.Showers;
				case WWOWeatherCode.ModerateOrHeavySleet:
					return WeatherCondition.Moderate | WeatherCondition.Heavy | WeatherCondition.Sleet;
				case WWOWeatherCode.ModerateOrHeavySleetshowers:
					return WeatherCondition.Moderate | WeatherCondition.Heavy | WeatherCondition.Sleet | WeatherCondition.Showers;
				case WWOWeatherCode.ModerateOrHeavySnowShowers:
					return WeatherCondition.Moderate | WeatherCondition.Heavy | WeatherCondition.Snow | WeatherCondition.Showers;
				case WWOWeatherCode.ModerateOrHeavySnowWithThunder:
					return WeatherCondition.Moderate | WeatherCondition.Heavy | WeatherCondition.Snow | WeatherCondition.Thunder;
				case WWOWeatherCode.ModerateRain:
					return WeatherCondition.Moderate | WeatherCondition.Rain;
				case WWOWeatherCode.ModerateSnow:
					return WeatherCondition.Moderate | WeatherCondition.Snow;
				case WWOWeatherCode.Overcast:
					return WeatherCondition.Overcast;
				case WWOWeatherCode.PartlyCloudy:
					return WeatherCondition.PartlyCloudy;
				case WWOWeatherCode.PatchyFreezingDrizzleNearby:
					return WeatherCondition.Patchy | WeatherCondition.Freezing | WeatherCondition.Drizzle;
				case WWOWeatherCode.PatchyHeavyRain:
					return WeatherCondition.Patchy | WeatherCondition.Heavy | WeatherCondition.Rain;
				case WWOWeatherCode.PatchyHeavySnow:
					return WeatherCondition.Patchy | WeatherCondition.Heavy | WeatherCondition.Snow;
				case WWOWeatherCode.PatchyLightDrizzle:
					return WeatherCondition.Patchy | WeatherCondition.Light | WeatherCondition.Drizzle;
				case WWOWeatherCode.PatchyLightRain:
					return WeatherCondition.Patchy | WeatherCondition.Light | WeatherCondition.Rain;
				case WWOWeatherCode.PatchyLightRainAndThunder:
					return WeatherCondition.Patchy | WeatherCondition.Light | WeatherCondition.Rain | WeatherCondition.Thunder;
				case WWOWeatherCode.PatchyLightSnow:
					return WeatherCondition.Patchy | WeatherCondition.Light | WeatherCondition.Snow;
				case WWOWeatherCode.PatchyLightSnowWithThunder:
					return WeatherCondition.Patchy | WeatherCondition.Light | WeatherCondition.Snow | WeatherCondition.Thunder;
				case WWOWeatherCode.PatchyModerateRain:
					return WeatherCondition.Patchy | WeatherCondition.Moderate | WeatherCondition.Rain;
				case WWOWeatherCode.PatchyModerateSnow:
					return WeatherCondition.Patchy | WeatherCondition.Moderate | WeatherCondition.Snow;
				case WWOWeatherCode.PatchyRainNearby:
					return WeatherCondition.Patchy | WeatherCondition.Rain;
				case WWOWeatherCode.PatchySleetNearby:
					return WeatherCondition.Patchy | WeatherCondition.Sleet;
				case WWOWeatherCode.PatchySnowNearby:
					return WeatherCondition.Patchy | WeatherCondition.Snow;
				case WWOWeatherCode.ThunderyOutbreaksInNearby:
					return WeatherCondition.Thunder;
				case WWOWeatherCode.TorrentialRainShowers:
					return WeatherCondition.Torrential | WeatherCondition.Rain | WeatherCondition.Showers;
				default:
					return WeatherCondition.Clear;
			}
		}
	}
}

