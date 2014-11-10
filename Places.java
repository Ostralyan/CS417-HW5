import PlaceData.PlaceDataProto.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.List;

public class Places
	extends UnicastRemoteObject
	implements PlacesInterface{

		//Database for storing places 
		private Place[] places;

		//Constructor reads the places in database
		public Places(String inputFile) throws RemoteException, java.io.IOException{
			PlaceList protobufList = PlaceList.parseFrom(new FileInputStream(inputFile));
			List<Place> list = protobufList.getPlaceList();
			places = new Place[list.size()];
			list.toArray(places);
		}

		//Returns the place with a match found else null
		public Place GetPlaces(String city, String state) throws RemoteException{
			for (int i = 0; i < places.length; i++){
				Place place = places[i];
				if (place.getName().regionMatches(true, 0, city, 0, city.length()) && place.getState().equals(state)){
					return place;
				}
			}
			return null;
		}
}