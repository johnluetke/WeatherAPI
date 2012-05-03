WeatherAPI
==========

WeatherAPI is a simple abstraction atop one or many free weather services.

How To Use
----------

        using WeatherAPI;
        
        IWeather seattle;
        
        // You can get the weather for a city and state ...
        seattle = WeatherAPI.GetWeather("Seattle", "WA");
        
        // ... for an Airport code ...
        seattle = WeatherAPI.GetWeather("SEA");
        
        // ... for a ZIP code ...
        seattle = WeatherAPI.GetWeather(98158);

        // ... or for a set of latitude and longitude coordinates
        seattle = WeatherAPI.GetWeather(47.44443, -122.300497);
        
About
----------

WeatherAPI abstracts free weather services into a simple to use API. You will need to register with the supported services and obtain an API key for them, should you choose to use them. You can use all or none of the supported services. 

Supported Services
-----------

* [Google](http://www.google.com/ig/api?weather)
* [WorldWeatherOnline](http://worldweatheronline.com)