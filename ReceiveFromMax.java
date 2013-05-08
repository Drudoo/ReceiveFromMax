//
//  Copyright 2013 the original author or authors.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//
//
//  ReceiveFromMax.java
//  
//
//  Created by Frederik Vanggaard on 2013/04/29
//
//  Contact: contact@drudoo.com
//  
//


/*----------------------------

Known Bugs: 

	* Can't use values higher than 65535. 65536 is 0. 65537 is 1 and so on.
	* Doesn't like negative values. -1 is 65535. -2 is 65534 and so on.
	* Can't use floats or doubles. Only accepts Integers from Max. 


-----------------------------*/
import java.io.*;
import java.net.*;

public class ReceiveFromMax {

	private int integerNumberFirst;
	private int integerNumberSecond;
	private int integerNumberTotal;
	private int port;

	/* Used for tentative float calculations. Not yet supported
	private double a = 0;
	private double b = 0;
	private double c = 0;
	private double d = 0;
	*/

	public ReceiveFromMax(int port) { //The constructor needs a number as argument. This number will be the port.
		this.port = port;
		returnData();
	}

	public int getReturnData() { //Instead of using public variables we use a kind of get/set.
		if (integerNumberTotal == 0) {
			integerNumberTotal = 1; //Can't use 0. Remove if-statement if you want to use 0.
		}
		/* Use if you want to have negative numbers from -1 to -5535. Positive numbers will go from 1-59999.
		if (integerNumberTotal >= 60000) {
			integerNumberTotal = integerNumberTotal-65536;
		}
		*/
		return integerNumberTotal;
	}

	private void closeConnection(DatagramSocket socket) { //Always close the connection so it can be reused on the same port.
		socket.disconnect();
		socket.close();
	}

	private int returnData() {
		try {
			DatagramSocket socket = new DatagramSocket(port); //Set up the connection.

			byte[] buffer = new byte[512]; //Create a buffer

			for (; ; ) { //Run a for loop until we receive a number from Max 6.
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length); //Create a new packet and store it in the buffer with the buffer length. 
				socket.receive(packet); //Receive the data from the socket (port) and store it in the packet.

				ByteArrayInputStream bin = new ByteArrayInputStream(packet.getData()); //Make a new bytearray from the packets data.
				boolean isFloat = false;

				for (int i = 0; i < packet.getLength(); i++) { //Run through the packet and read the data.
					int data = bin.read(); //Read the next byte of data from this input steam.
					if (data == -1) { //If the data is equal to -1, we have reached EOF.
						break;
					} else {
						if (packet.getLength() > 12) {
							//Our number is a float and cannot be translated or returned.
							
							/* Tentative float calculations. 
							if (i == 12) {
								switch ((int)data) {
									case 61: a = 0.03125;
									break;
									case 62: a = 0.125;
									break;
									case 63: a = 0.5;
									break;
									case 64: a = 2;
									break;
									case 65: a = 8;
									break;
									case 66: a = 32;
									break;
									case 67: a = 128;
									break;
								}
							}
							if (i == 13) b = (int) data;
							if (i == 14) c = (int) data;
							if (i == 15) d = (int) data;

							*/
							isFloat = true;
						}

						if (packet.getLength() < 13) {
							if (i == 10) integerNumberSecond = (int) data;
							if (i == 11) integerNumberFirst = (int) data;
						}
					}
				}

				if (isFloat) {
					/* 	Used for tentative float calculations. Not yet supported.
						if b or c or d is larger than 128 we will have a margin of error -/+ 0.1-0.25.

					double temp;
					temp = a + ((b/128)*a) + ((c/128)*(a/256)) + ((d/128)*(a/(256*256)));
					System.out.println(temp);
					
					*/
					System.out.println("Trying to parse float value...\nCannot return value");
					closeConnection(socket);
					break;
				}
				
				integerNumberTotal = (256*integerNumberSecond)+integerNumberFirst;
				closeConnection(socket);
				break;
			}


		} catch(Exception e) {
			System.out.println("\n_______\tERROR MESSAGE START...\t____");
			System.out.println("\t" + e.getMessage());
			System.out.println("_______\tERROR MESSAGE ENDS...\t____\n");

		}
		return 0;
	}
}