package WeatherAPI.Providers.WorldWeatherOnline;

public enum WWOWeatherCode {
	ModerateOrHeavySnowWithThunder(395),
	PatchyLightSnowWithThunder(392),
	ModerateOrHeavyRainAndThunder(389),
	PatchyLightRainAndThunder(386),
	ModerateOrHeavyIcePellets(377),
	LightIcePellets(374),
	ModerateOrHeavySnowShowers(371),
	LightSnowShowers(368),
	ModerateOrHeavySleetshowers(365),
	LightSleetShowers(362),
	TorrentialRainShowers(359),
	ModerateOrHeavyRainShowers(356),
	LightRainShower(353),
	IcePellets(350),
	HeavySnow(338),
	PatchyHeavySnow(335),
	ModerateSnow(332),
	PatchyModerateSnow(329),
	LightSnow(326),
	PatchyLightSnow(323),
	ModerateOrHeavySleet(320),
	LightSleet(317),
	ModerateOrHeavyFreezingRain(314),
	LightFreezingRain(311),
	HeavyRain(308),
	PatchyHeavyRain(305),
	ModerateRain(302),
	PatchyModerateRain(299),
	LightRain(296),
	PatchyLightRain(293),
	HeavyFreezingDrizzle(284),
	FreezingDrizzle(281),
	LightDrizzle(266),
	PatchyLightDrizzle(263),
	FreezingFog(260),
	Fog(248),
	Blizzard(230),
	BlowingSnow(227),
	ThunderyOutbreaksInNearby(200),
	PatchyFreezingDrizzleNearby(185),
	PatchySleetNearby(182),
	PatchySnowNearby(179),
	PatchyRainNearby(176),
	Mist(143),
	Overcast(122),
	Cloudy(119),
	PartlyCloudy(116),
	ClearSunny(113);
	
	int _value;
	
	private WWOWeatherCode(int value) {
		_value = value;
	}
	
	public int getCode() {
			return _value;
	}
	
	public static WWOWeatherCode findByCode(int code) {
		for (WWOWeatherCode wc : WWOWeatherCode.values()) {
			if (wc.getCode() == code)
				return wc;
		}
		
		return null;
	}
}

