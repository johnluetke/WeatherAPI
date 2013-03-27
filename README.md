# WeatherAPI

WeatherAPI is a simple abstraction atop one or many free weather services.

[Javadoc](http://jenkins.johnluetke.net/view/WeatherAPI/job/WeatherAPI%20-%20master/javadoc/) | [Jenkins](http://jenkins.johnluetke.net/view/WeatherAPI/)

## How To Use

### Maven

If you're using maven, simply add a dependency for `net.johnluetke.WeatherAPI`:

    <dependency>
        <groupId>net.johnluetke.WeatherAPI</groupId>
        <artifactId>WeatherAPI</artifactId>
        <version>0.9.3</version>
    </dependency>
        
For a list of version numbers, see the [Changelog](CHANGELOG.md) or [tags](https://github.com/johnluetke/WeatherAPI/tags)

### Other / .NET

If you're not using Maven or are using .NET, download the .jar or .dll from my [artifacts repository](https://github.com/johnluetke/artifacts/tree/master/WeatherAPI).
Also make sure to download the required dependencies! They can be found in the file ending in `.dependencies`

### C&#35;

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
        
### Java

        import WeatherAPI.*;
        
        IWeather seattle;
        
        // You can get the weather for a city and state ...
        seattle = WeatherAPI.GetWeather("Seattle", "WA");
        
        // ... for an Airport code ...
        seattle = WeatherAPI.GetWeather("SEA");
        
        // ... for a ZIP code ...
        seattle = WeatherAPI.GetWeather(98158);

        // ... or for a set of latitude and longitude coordinates
        seattle = WeatherAPI.GetWeather(47.44443, -122.300497);
        
## About

WeatherAPI abstracts free weather services into a simple to use API. You will need to register 
with the supported services and obtain an API key for them, should you choose to use them. You 
can use all or none of the supported services. 

## Supported Services

* [WorldWeatherOnline](http://worldweatheronline.com)
* [Wunderground](http://www.wunderground.com/?apiref=a036a431b7eb420b)

## Creating a New Provider

WeatherAPI is built in a provider-agnostic way. That is, it is not tightly coupled to any one 
specific source of data. It obtains data by means of providers, which are tightly coupled, but 
return the data from thier source in a class that adheres to the IWeather interface. It's up to the
provider to take the location information provided by the user, parse it into a useable format,
call the service and parse the data.

WeatherAPI provides an abstract class for providers to derive from: WeatherProvider. All providers
must inherit this class in order to be discovered. 

## FAQ

### How can I use WeatherAPI with my mobile application?

Since WeatherAPI is written in C# and Java, it can easily be used on any mobile platform that supports those languages.
Out of the box, this is just Windows Phone and Android. However, with commercial tools such as 
[MonoTouch](http://ios.xamarin.com), WeatherAPI can be used by iOS as well.

### How do I configure WeatherAPI?

#### C&#35;

WeatherAPI will search for `WeatherAPI.dll.config` on the in the application directory. Take a look at [src/main/config/WeatherAPI.dll.config](src/main/config/WeatherAPI.dll.config) for an example.

#### Java

WeatherAPI will search for `WeatherAPI.properties` on the classpath. Take a look at [src/main/config/WeatherAPI.properties](src/main/config/WeatherAPI.properties) for an example.
