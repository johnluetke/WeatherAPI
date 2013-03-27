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

using System;
using System.Configuration;
using System.Reflection;

namespace WeatherAPI.Providers {
	
	/// <summary>
	/// Abstract class for Weather Providers to derive from.
	/// </summary>
	internal abstract class WeatherProvider  {
		
		protected Configuration _dllConfig;
		
		protected String _location;
		protected LocationType _source;
		
		/// <summary>
		/// Initializes a new instance of the <see cref="WeatherAPI.Providers.WeatherProvider"/> class. 
		/// </summary>
		public WeatherProvider() {
			string me = Assembly.GetExecutingAssembly().Location;
			_dllConfig = ConfigurationManager.OpenExeConfiguration(me);
		}
		
		/// <summary>
		/// Determines if this provider is available to use.
		/// </summary>
		/// <returns>
		/// Whether or not this provider is available to use
		/// </returns>
		public abstract bool IsAvailable();
		
		/// <summary>
		/// Refresh the weather data behind this interface.
		/// </summary>
		public abstract void Update();
		
		/// <summary>
		/// Determines if the provider supports the specified LocationSource.
		/// </summary>
		/// <param name='source'>
		/// The LocationSource to test support for.
		/// </param>
		/// <returns>
		/// True if the provider supports the given source, false otherwise
		/// </returns>
		public abstract bool Supports(LocationType source);
		
		/// <summary>
		/// Gets or sets the location for the provider to fetch information for.
		/// </summary>
		/// <value>
		/// The location.
		/// </value>
		public string Location { 
			get { return _location; }
			set { _location = value; }
		}
		
		/// <summary>
		/// Gets or sets the LocationSource for the provider.
		/// </summary>
		/// <value>
		/// The source.
		/// </value>
		public LocationType Type {
			get { return _source; }
			set { _source = value; }
		}
	}
}

