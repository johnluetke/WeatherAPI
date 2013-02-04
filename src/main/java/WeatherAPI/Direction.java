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
	N, E, S, W,	
	NE, SE, NW, SW,
	NNE, ENE, ESE, SSE,
	NNW, WNW, WSW, SSW,
	
	//North = N, East = E, South = S, West = W,
	//Northeast = NE, Southeast = SE, Northwest = NW, Southwest = SW,
	//NorthNortheast = NNE, EastNortheast = ENE, EastSoutheast = ESE, SouthSoutheast = SSE,
	//NorthNorthwest = NNW, WestNorthwest = WNW, Westsouthwest = WSW, SouthSouthwest = SSW
	
}
