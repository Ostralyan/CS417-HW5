import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.*;
import AirportData.AirportDataProto.Airport;
import AirportData.AirportDataProto.AirportList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Comparator;
import java.util.Arrays;

public class Airports
	extends UnicastRemoteObject
	implements AirportInterface{

	//Stores the airports
	private Airport[] airports;

	//constructor that reads the protobuf and stores it
	public Airports(String inputFile) throws RemoteException, java.io.IOException{
		AirportList protobufList = AirportList.parseFrom(new FileInputStream(inputFile));
		List<Airport> list = protobufList.getAirportList();
		airports = new Airport[list.size()];
		list.toArray(airports);
	}

	//Some math stuff
	//Nautical miles * 60 * cos-1(sin(lat1)sin(lat2)+cost(lat1)*cos(lat2)*cos(lon2-lon1))
	private static double calcDist(double y1, double x1, double y2, double x2){
		double lat1 = Math.toRadians(y1);
		double lon1 = Math.toRadians(x1);
		double lat2 = Math.toRadians(y2);
		double lon2 = Math.toRadians(x2);

		return(1.1507794 * 60 * Math.toDegrees(
			Math.acos(
				Math.sin(lat1) * Math.sin(lat2) + 
				Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1))));
	}

	//Creates a new object that includes distances of each airport.
	public AirportStruct[] GetAirports(double lat, double lon) throws RemoteException{
		AirportStruct[] distances = new AirportStruct[airports.length];
		for(int i = 0; i < airports.length; i++){
			Airport airport = airports[i];
			AirportStruct result = new AirportStruct();
			result.Name = airport.getName();
			result.State = airport.getState();
			result.Code = airport.getCode();
			result.Lat = airport.getLat();
			result.Lon = airport.getLon();
			result.Dist = calcDist(lat, lon, airport.getLat(), airport.getLon());
			distances[i] = result;
		}

		//Sorts for top 5
		Arrays.sort(distances, new Comparator<AirportStruct>(){
			public int compare(AirportStruct x, AirportStruct y){
				return ((int)(x.Dist - y.Dist));
			}
			public boolean equals(Object obj){
				return false;
			}
		});

		//Puts top 5 into results array of structs & returns
		AirportStruct[] results = new AirportStruct[5];
		for(int i = 0; i < 5; i++){
			results[i] = distances[i];
		}

		return results;
	}
}