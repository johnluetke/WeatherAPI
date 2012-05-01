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

namespace WeatherAPI {
	
	/// <summary>
	/// Represents different weather conditions. These can be OR'd together to create complex conditions.
	/// </summary>
	[Flags]
	public enum WeatherCondition {
		Overcast     = 0x000001,
		Cloudy       = 0x000002,
		PartlyCloudy = 0x000004,
		Clear        = 0x000008,
		
		Patchy       = 0x000010,
		Light        = 0x000020,
		Moderate     = 0x000040,
		Heavy        = 0x000080,
		Torrential   = 0x000100,
		
		Fog          = 0x000200,
		Mist         = 0x000400,
		Drizzle      = 0x000800,
		Rain         = 0x001000,
		Sleet        = 0x002000,
		Snow         = 0x004000,
		Ice          = 0x008000,
		Blizzard     = 0x010000,
		
		Pellets      = 0x020000,
		Showers      = 0x040000,
		Freezing     = 0x080000,
		
		Blowing      = 0x100000,
		
		Thunder      = 0x200000
	}
}

