package WeatherAPI.Providers.WorldWeatherOnline;
	
import WeatherAPI.Direction;
import WeatherAPI.IWeather;
import WeatherAPI.Providers.LocationSource;
import WeatherAPI.Providers.WeatherProvider;
import WeatherAPI.WeatherCondition;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.EnumSet;

public class WWOProvider extends WeatherProvider implements IWeather {

	private static final String WWO_API_FORMAT = "xml";
	private static final String WWO_API_URL = "http://free.worldweatheronline.com/feed/weather.ashx?q=%s&format=%s&num_of_days=1&key=%s";
	private static final String WWO_XPATH_HEADER = "//data/current_condition/%s";

	private XMLReader _reader;
	private String _wwo_api_key;

	public WWOProvider () {
		if (IsAvailable()) {
			_wwo_api_key = PROPERTIES.getProperty("WORLD_WEATHER_ONLINE_API_KEY");
			_reader = new XMLReader();
		}
	}

	public boolean IsAvailable() {
		return PROPERTIES.containsKey("WORLD_WEATHER_ONLINE_API_KEY") &&
			   (PROPERTIES.getProperty("WORLD_WEATHER_ONLINE_API_KEY") != null ||
				!PROPERTIES.getProperty("WORLD_WEATHER_ONLINE_API_KEY").equals(""));
	}

	public void Update() {
		URL url;
		HttpURLConnection conn = null;
		InputStream stream = null;

		try {
			url = new URL(String.format(WWO_API_URL, getLocation(), WWO_API_FORMAT, _wwo_api_key));
			conn = (HttpURLConnection)url.openConnection();
			stream = conn.getInputStream();
			StringBuilder sb = new StringBuilder();
			int data = stream.read();

			while (data != -1) {
				sb.append((char)data);
				data = stream.read();
			}

			_reader.load(sb.toString());
			_lastUpdate = System.currentTimeMillis();
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

	public boolean Supports(LocationSource source) { 
		return true;
	}

	public double getDegreesCelcius() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WWO_XPATH_HEADER, "temp_C/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public double getDegressFahrienhiet() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WWO_XPATH_HEADER, "temp_F/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public double getWindSpeedMPH() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WWO_XPATH_HEADER, "windspeedMiles/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public double getWindSpeedKPH() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WWO_XPATH_HEADER, "windspeedKmph/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public Direction getWindDirection() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WWO_XPATH_HEADER, "winddir16Point/text()");
		Object val = _reader.read(xpath);

		return Direction.valueOf(val.toString());
	}

	public double getCloudCover() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WWO_XPATH_HEADER, "cloudcover/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public double getPercipitation() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WWO_XPATH_HEADER, "precipMM/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public double getHumidity() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WWO_XPATH_HEADER, "humidity/text()");
		Object val = _reader.read(xpath);

		return Double.parseDouble(val.toString());
	}

	public EnumSet<WeatherCondition> getConditions() {
		if (!isFresh()) { Update(); }
		String xpath = String.format(WWO_XPATH_HEADER, "weatherCode/text()");
		Object val = _reader.read(xpath);

		WWOWeatherCode code = WWOWeatherCode.findByCode(Integer.parseInt(val.toString()));

		EnumSet<WeatherCondition> currentCondition = translateConditions(code);

		return currentCondition;
	}

	protected EnumSet<WeatherCondition> translateConditions(WWOWeatherCode code) {
		switch (code) {
			case Blizzard:
				return EnumSet.of(WeatherCondition.Blizzard, WeatherCondition.Snow);
			case BlowingSnow:
				return EnumSet.of(WeatherCondition.Blowing, WeatherCondition.Snow);
			case ClearSunny:
				return EnumSet.of(WeatherCondition.Clear);
			case Cloudy:
				return EnumSet.of(WeatherCondition.Cloudy);
			case Fog:
				return EnumSet.of(WeatherCondition.Fog);
			case FreezingDrizzle:
				return EnumSet.of(WeatherCondition.Freezing, WeatherCondition.Drizzle);
			case FreezingFog:
				return EnumSet.of(WeatherCondition.Freezing, WeatherCondition.Fog);
			case HeavyFreezingDrizzle:
				return EnumSet.of(WeatherCondition.Heavy, WeatherCondition.Freezing, WeatherCondition.Drizzle);
			case HeavyRain:
				return EnumSet.of(WeatherCondition.Heavy, WeatherCondition.Rain);
			case HeavySnow:
				return EnumSet.of(WeatherCondition.Heavy, WeatherCondition.Snow);
			case IcePellets:
				return EnumSet.of(WeatherCondition.Ice, WeatherCondition.Pellets);
			case LightDrizzle:
				return EnumSet.of(WeatherCondition.Light, WeatherCondition.Drizzle);
			case LightFreezingRain:
				return EnumSet.of(WeatherCondition.Light, WeatherCondition.Freezing, WeatherCondition.Rain);
			case LightIcePellets:
				return EnumSet.of(WeatherCondition.Light, WeatherCondition.Ice, WeatherCondition.Pellets);
			case LightRain:
				return EnumSet.of(WeatherCondition.Light, WeatherCondition.Rain);
			case LightRainShower:
				return EnumSet.of(WeatherCondition.Light, WeatherCondition.Rain, WeatherCondition.Showers);
			case LightSleet:
				return EnumSet.of(WeatherCondition.Light, WeatherCondition.Sleet);
			case LightSleetShowers:
				return EnumSet.of(WeatherCondition.Light, WeatherCondition.Sleet, WeatherCondition.Showers);
			case LightSnow:
				return EnumSet.of(WeatherCondition.Light, WeatherCondition.Snow);
			case LightSnowShowers:
				return EnumSet.of(WeatherCondition.Light, WeatherCondition.Snow, WeatherCondition.Showers);
			case Mist:
				return EnumSet.of(WeatherCondition.Mist);
			case ModerateOrHeavyFreezingRain:
				return EnumSet.of(WeatherCondition.Moderate, WeatherCondition.Heavy, WeatherCondition.Freezing, WeatherCondition.Rain);
			case ModerateOrHeavyIcePellets:
				return EnumSet.of(WeatherCondition.Moderate, WeatherCondition.Heavy, WeatherCondition.Ice, WeatherCondition.Pellets);
			case ModerateOrHeavyRainAndThunder:
				return EnumSet.of(WeatherCondition.Moderate, WeatherCondition.Heavy, WeatherCondition.Rain, WeatherCondition.Thunder);
			case ModerateOrHeavyRainShowers:
				return EnumSet.of(WeatherCondition.Moderate, WeatherCondition.Heavy, WeatherCondition.Rain, WeatherCondition.Showers);
			case ModerateOrHeavySleet:
				return EnumSet.of(WeatherCondition.Moderate, WeatherCondition.Heavy, WeatherCondition.Sleet);
			case ModerateOrHeavySleetshowers:
				return EnumSet.of(WeatherCondition.Moderate, WeatherCondition.Heavy, WeatherCondition.Sleet, WeatherCondition.Showers);
			case ModerateOrHeavySnowShowers:
				return EnumSet.of(WeatherCondition.Moderate, WeatherCondition.Heavy, WeatherCondition.Snow, WeatherCondition.Showers);
			case ModerateOrHeavySnowWithThunder:
				return EnumSet.of(WeatherCondition.Moderate, WeatherCondition.Heavy, WeatherCondition.Snow, WeatherCondition.Thunder);
			case ModerateRain:
				return EnumSet.of(WeatherCondition.Moderate, WeatherCondition.Rain);
			case ModerateSnow:
				return EnumSet.of(WeatherCondition.Moderate, WeatherCondition.Snow);
			case Overcast:
				return EnumSet.of(WeatherCondition.Overcast);
			case PartlyCloudy:
				return EnumSet.of(WeatherCondition.PartlyCloudy);
			case PatchyFreezingDrizzleNearby:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Freezing, WeatherCondition.Drizzle);
			case PatchyHeavyRain:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Heavy, WeatherCondition.Rain);
			case PatchyHeavySnow:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Heavy, WeatherCondition.Snow);
			case PatchyLightDrizzle:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Light, WeatherCondition.Drizzle);
			case PatchyLightRain:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Light, WeatherCondition.Rain);
			case PatchyLightRainAndThunder:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Light, WeatherCondition.Rain, WeatherCondition.Thunder);
			case PatchyLightSnow:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Light, WeatherCondition.Snow);
			case PatchyLightSnowWithThunder:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Light, WeatherCondition.Snow, WeatherCondition.Thunder);
			case PatchyModerateRain:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Moderate, WeatherCondition.Rain);
			case PatchyModerateSnow:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Moderate, WeatherCondition.Snow);
			case PatchyRainNearby:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Rain);
			case PatchySleetNearby:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Sleet);
			case PatchySnowNearby:
				return EnumSet.of(WeatherCondition.Patchy, WeatherCondition.Snow);
			case ThunderyOutbreaksInNearby:
				return EnumSet.of(WeatherCondition.Thunder);
			case TorrentialRainShowers:
				return EnumSet.of(WeatherCondition.Torrential, WeatherCondition.Rain, WeatherCondition.Showers);
			default:
				return EnumSet.of(WeatherCondition.Clear);
		}
	}
}