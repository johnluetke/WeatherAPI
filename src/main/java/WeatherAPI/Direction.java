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

package WeatherAPI;
	
/**
 * Represents the compass directions. Provides the 16 points of direction
 */
public enum Direction {
	N("N", "North"),
	E("E", "East"),
	S("S", "South"),
	W("W", "West"),	
	
	NE("NE", "Northeast"),
	SE("SE", "Southeast"),
	NW("NW", "Northwest"),
	SW("SW", "Southwest"),
	
	NNE("NNE", "North-Northeast"),
	ENE("ENE", "East-Northeast"),
	ESE("ESE", "East-southeast"),
	SSE("SSE", "South-Southeast"),
	
	NNW("NNW", "North-Northwest"),
	WNW("WNW", "West-Northwest"),
	WSW("WSW", "West-Southwest"),
	SSW("SSW", "South-Southwest");
	
	private String[] _aliases;
	
	/**
	 * Creates a new direction with the given aliases
	 * 
	 * @param alias 
	 */
	private Direction(String... alias) {
		_aliases = alias;
	}
	
	/**
	 * Gets all aliases for this Direction
	 * 
	 * @return 
	 */
	public String[] getAliases() {
		return _aliases;
	}

	/**
	 * More verbose way to convert a string into a Direction. Consider's Direction aliases
	 * 
	 * @param value the string to parse
	 * 
	 * @return Direction corresponding to the given string
	 * 
	 * @throws IllegalArgumentException if no match is found
	 */
	public static Direction getValue(String value) {
		for (Direction d : Direction.values()) {
			for (String alias : d.getAliases()) {
				if (alias.equalsIgnoreCase(value)) {
					return d;
				}
			}
		}
		
		throw new IllegalArgumentException();
	}	
}
