using System;

using WeatherAPI;
using WeatherAPI.Providers;

namespace WearherAPI.Providers.iGoogle {
	
	internal class iGoogleProvider : WeatherProvider, IWeather {
		
		private const string IG_API_URL = "http://www.google.com/ig/api?weather={0}";
		private const string IG_XPATH_HEADER = "//data/current_condition/{0}";
		
		private XPathDocument _xpath;
		private string _wwo_api_key;
		
		public iGoogleProvider () {

		}
		
		public bool isAvailable() {
			return true;
		}
		
		public override bool Supports(LocationSource source) {
			return source != LocationSource.LatitudeLongitude;
		}
		
		public override void Update () {
			string url = String.Format(WWO_API_URL, "98121", WWO_API_FORMAT, _wwo_api_key);
			HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
			
			HttpWebResponse response = (HttpWebResponse)request.GetResponse();
			string xml = new StreamReader(response.GetResponseStream()).ReadToEnd();
			
			_xpath = new XPathDocument(new StringReader(xml));
		}
	}
}

