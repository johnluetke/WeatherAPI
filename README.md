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

WeatherAPI abstracts free weather services into a simple to use API. You will need to register 
with the supported services and obtain an API key for them, should you choose to use them. You 
can use all or none of the supported services. 

Supported Services
-----------

* [Google](http://www.google.com/ig/api?weather)
* [WorldWeatherOnline](http://worldweatheronline.com)

Creating a New Provider
----------

WeatherAPI is built in a provider-agnostic way. That is, it is not tightly coupled to any one 
specific source of data. It obtains data by means of providers, which are tightly coupled, but 
return the data from thier source in a class that adheres to the IWeather interface. It's up to the
provider to take the location information provided by the user, parse it into a useable format,
call the service and parse the data.

WeatherAPI provides an abstract class for providers to derive from: WeatherProvider. All providers
must inherit this class in order to be discovered. 

FAQ
----------

### How can I use WeatherAPI with my mobile application?

Since WeatherAPI is written in C#, it can be used on any mobile platform that supports the language.
Out of the box, this is just Windows Phone. However, with commercial tools such as [MonoTouch](http://ios.xamarin.com) and [Mono
for Android](http://android.xamarin.com), WeatherAPI can be used by iOS and Android apps as well.