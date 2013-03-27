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

package WeatherAPI.Providers;

import WeatherAPI.IWeather;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Abstract class for Weather Providers to derive from.
 */
public abstract class WeatherProvider implements IWeather  {
	
	protected static Properties PROPERTIES;
	
	private static long _refreshInterval = 1800000; // Default value of 30 mins
	
	protected String _location;
	protected LocationType _source;
	protected long _lastUpdate = 0;
	
	static {
		InputStream stream = null;
		
		try {
			PROPERTIES = new Properties();
			stream = ClassLoader.getSystemClassLoader().getResourceAsStream("WeatherAPI.properties");
			if (stream != null) {
				PROPERTIES.load(stream);
				_refreshInterval = Long.parseLong(PROPERTIES.getProperty("REFRESH_RATE"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try { stream.close(); }
			catch (Exception e) { e.printStackTrace(); }
		}
	}
	
	/**
	 * Initializes a new instance of the <see cref="WeatherAPI.Providers.WeatherProvider"/> class. 
	 */
	public WeatherProvider() {
	}
	
	/**
	 * Determines if this provider is available to use.
	 *
	 * @return Whether or not this provider is available to use
	 */
	public abstract boolean IsAvailable();
	
	/**
	 * Determines if the current data is fresh.
	 * 
	 * @return True if data is current, false if an update is needed.
	 */
	public boolean isFresh() {
		return ((_lastUpdate + _refreshInterval) > 
				System.currentTimeMillis()); 
		
	}
	
	/**
	 * Refresh the weather data behind this interface.
	 */
	public abstract void Update();
	
	/**
	 * Determines if the provider supports the specified LocationType.
	 *
	 * @param source The LocationType to test support for.
	 * 
	 * @return True if the provider supports the given source, false otherwise
	 */
	public abstract boolean Supports(LocationType source);
	
	/**
	 * Gets the location that the provider is to fetch information for.
	 *
	 * @return the location to get weather information for.
	 */
	public String getLocation() { 
		return _location;
	}

	/**
	 * Sets the location that the provider is to fetch information for.
	 *
	 * @param location 
	 */
	public void setLocation(String location) { 
		_location = location;
	}

	/**
	 * Gets or sets the LocationType for the provider.
	 */
	public LocationType getSource() {
		return _source;
	}

	/**
	 * Sets the LocationType for the provider.
	 */
	public void setSource(LocationType source) {
		_source = source;
	}

	/** 
	 * Internal class for reading XML
	 */
	protected class XMLReader {

		private Document _xmlDoc;
		private XPath _xpathDoc;

		public XMLReader() {
			_xpathDoc = XPathFactory.newInstance().newXPath();
		}

		public void load(String xml) {
			try {
				xml = xml.trim();
				_xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
						new InputSource(new StringReader(xml))
					  );
			}
			catch (Exception e) {
				e.printStackTrace();
			}		
		}

		public String read(String xpath) {
			try {
				XPathExpression xpathExpr = _xpathDoc.compile(xpath);
				return (String)xpathExpr.evaluate(_xmlDoc, XPathConstants.STRING);
			}
			catch (Exception e) {
				e.printStackTrace();
				return "";
			}
		}

		public String toString() {
			
			try {
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer transformer = tf.newTransformer();
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				StringWriter writer = new StringWriter();
				transformer.transform(new DOMSource(_xmlDoc), new StreamResult(writer));
				String output = writer.getBuffer().toString().replaceAll("\n|\r", "");

				return output;
			}
			catch (Exception e) {
				e.printStackTrace();
				return "Error";
			}
		}
	}
}
