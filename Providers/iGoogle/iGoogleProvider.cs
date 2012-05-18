using System;
using System.IO;
using System.Net;
using System.Text.RegularExpressions;
using System.Xml.XPath;

using WeatherAPI;
using WeatherAPI.Providers;

namespace WeatherAPI.Providers.iGoogle {
	
	internal class iGoogleProvider : WeatherProvider, IWeather {
		
		private const string IG_API_URL = "http://www.google.com/ig/api?weather={0}";
		private const string IG_XPATH_HEADER = "//xml_api_reply/weather/current_conditions/{0}";
		
		private XPathDocument _xpath;
		private string _wwo_api_key;
		
		private Direction _windDirection;
		
		public iGoogleProvider () {

		}
		
		public override bool IsAvailable(){
			return true;
		}
		
		public override bool Supports(LocationSource source) {
			return source != LocationSource.LatitudeLongitude;
		}
		
		public override void Update () {
			string url = String.Format(IG_API_URL, Location);
			HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
			
			HttpWebResponse response = (HttpWebResponse)request.GetResponse();
			string xml = new StreamReader(response.GetResponseStream()).ReadToEnd();
			
			_xpath = new XPathDocument(new StringReader(xml));
		}
		
		public double DegreesCelcius {
			get {
				string xpath = String.Format(IG_XPATH_HEADER, "temp_c/@data");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				return Double.Parse(val.ToString());
			}
		}

		public double DegressFahrienhiet {
			get {
				string xpath = String.Format(IG_XPATH_HEADER, "temp_f/@data");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				return Double.Parse(val.ToString());
			}
		}

		public double WindSpeedMPH {
			get {
				string xpath = String.Format(IG_XPATH_HEADER, "wind_condition/@data");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				
				Match m = Regex.Match(val.ToString(), "Wind: ([NESW]{1,2}) at ([0-9]{1,}) mph");
				
				_windDirection = (Direction)Enum.Parse(typeof(Direction), m.Groups[1].Value);
				
				return Double.Parse(m.Groups[2].Value);
			}
		}
		
		public double WindSpeedKPH {
			get {
				return WindSpeedMPH * 1.609344;
			}
		}
		
		public Direction WindDirection {
			get {
				return _windDirection;
			}
		}

		public double CloudCover {
			get {
				return Double.NaN;
			}
		}
		
		public double Percipitation {
			get {
				return Double.NaN;
			}
		}

		public double Humidity { 
			get {
				string xpath = String.Format(IG_XPATH_HEADER, "humidity/@data");
				object val = _xpath.CreateNavigator().SelectSingleNode(xpath);
				val = Regex.Replace(val.ToString(), "[^0-9]", "");
				
				return Double.Parse(val.ToString());
			}
		}
		
		public WeatherCondition Conditions {
			get { 
				string xpath = String.Format(IG_XPATH_HEADER, "condition/@data");
				string val = _xpath.CreateNavigator().SelectSingleNode(xpath).ToString();
				
				switch (val) {
					case "Partly Sunny": return WeatherCondition.PartlyCloudy;
					default:
						return (WeatherCondition)Enum.Parse(typeof(WeatherCondition), Regex.Replace(val, "[^A-Za-z]", ""));
				}	
			}
		}	
	}
}

