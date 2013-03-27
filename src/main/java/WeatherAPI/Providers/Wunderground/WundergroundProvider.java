/*
 * Copyright 2013 John Luetke <john@johnluetke.net>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package WeatherAPI.Providers.Wunderground;

import WeatherAPI.WindDirection;
import WeatherAPI.IWeather;
import WeatherAPI.Providers.LocationType;
import WeatherAPI.Providers.WeatherProvider;
import WeatherAPI.WeatherCondition;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.EnumSet;

/**
 *
 * @author jluetke
 */
public class WundergroundProvider extends WeatherProvider implements IWeather {

	private static final String WU_API_URL = "http://api.wunderground.com/api/%s/conditions/q/%s.xml";
	private static final String WU_XPATH_HEADER = "/response/current_observation/%s";

	private XMLReader _reader;
	private String _wu_api_key;
	
	public WundergroundProvider() {
		if (IsAvailable()) {
			_wu_api_key = PROPERTIES.getProperty("WUNDERGROUND_API_KEY");
			_reader = new XMLReader();
		}
	}
	
	@Override
	public boolean IsAvailable() {
		return PROPERTIES.containsKey("WUNDERGROUND_API_KEY") &&
			   (PROPERTIES.getProperty("WUNDERGROUND_API_KEY") != null &&
				!PROPERTIES.getProperty("WUNDERGROUND_API_KEY").equals(""));
	}

	@Override
	public void Update() {
		URL url;
		HttpURLConnection conn = null;
		InputStream stream = null;
		String location = getLocation();

		do {
			try {
				url = new URL(String.format(WU_API_URL, _wu_api_key, location));
				conn = (HttpURLConnection)url.openConnection();
				stream = conn.getInputStream();
				StringBuilder sb = new StringBuilder();
				int data = stream.read();

				while (data != -1) {
					sb.append((char)data);
					data = stream.read();
				}

				_reader.load(sb.toString());

				// Make sure results aren't ambiguous
				String xpath = WU_XPATH_HEADER.substring(0, WU_XPATH_HEADER.length()-3);
				Object val = _reader.read(xpath);

				if (val == null || val.equals("")) {
					xpath = String.format("/response/results/result[1]/zmw");
					val = _reader.read(xpath);
					location = String.format("zmw:%s", val);
				}
				else {	
					_lastUpdate = System.currentTimeMillis();
					break;
				}
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
			}
			finally {
				try { if (stream != null) stream.close(); }
				catch (IOException e) { e.printStackTrace(System.err); }
				finally { if (conn != null ) conn.disconnect(); }
			}
		}
		while (true);
	}

	@Override
	public boolean Supports(LocationType source) {
		return true;
	}

	public double getDegreesCelcius() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WU_XPATH_HEADER, "temp_c/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public double getDegressFahrienhiet() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WU_XPATH_HEADER, "temp_f/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public double getWindSpeedMPH() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WU_XPATH_HEADER, "wind_mph/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public double getWindSpeedKPH() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WU_XPATH_HEADER, "wind_kph/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public WindDirection getWindDirection() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WU_XPATH_HEADER, "wind_dir/text()");
		Object val = _reader.read(xpath);
		
		return WindDirection.getValue(val.toString());
	}

	public double getCloudCover() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WU_XPATH_HEADER, "icon/text()");
        String val = _reader.read(xpath).toString();

		if (val.contains("chance"))
			return 0.8;
		else if (val.equals("clear") || val.equals("sunny"))
			return 0.0;
		else if (val.contains("hazy"))
			return 0.3;
		else if (val.contains("mostlysunny"))
			return 0.5;
		else if (val.contains("partlycloudy"))
			return 0.5;
		else if (val.contains("mostlycloudy") || val.contains("partlysunny"))
			return 0.7;
		else if (val.contains("cloudy"))
			return 0.8;
		else if (val.contains("flurries") || val.contains("sleet") ||
				 val.contains("rain")     || val.contains("fog")  ||
				 val.contains("snow")     || val.contains("tstorms"))
			return 1.0;
		else
			return 0;
	}

	public double getPercipitation() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WU_XPATH_HEADER, "precip_today_metric/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public double getHumidity() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WU_XPATH_HEADER, "relative_humidity/text()");
		String val = _reader.read(xpath);
		val = "." + val.substring(0, val.length() - 1);

		try {
			return Double.parseDouble(val);
		}
		catch (Exception e) {
			return 0.0;
		}
	}

	public EnumSet<WeatherCondition> getConditions() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WU_XPATH_HEADER, "weather/text()");
		String val = _reader.read(xpath);
		EnumSet<WeatherCondition> conditions = EnumSet.noneOf(WeatherCondition.class);
	
		// Special conditions
		if (val.equals("Scattered Clouds") || val.equals("Partly Cloudy"))
			return EnumSet.of(WeatherCondition.PartlyCloudy);
		else if (val.equals("Mostly Cloudy"))
			return EnumSet.of(WeatherCondition.Cloudy);
			
		for (String condition : val.split(" ")) {
			conditions.add(WeatherCondition.valueOf(condition));
		}
		
		return conditions;
	}
	
	@Override
	public String getLocation() {
		if (getLocationType() == LocationType.CityState) {
			String[] parts = super.getLocation().split(",");
			return String.format("%s/%s", parts[1].trim(), parts[0].trim());
		}
		else {
			return super.getLocation();
		}
	}
	
	public LocationType getLocationType() {
		return super.getLocationType();
	}
	
}
