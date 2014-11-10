import PlaceData.PlaceDataProto.Place;
import PlaceData.PlaceDataProto.PlaceList;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

class PlaceServer{

	static PlaceDS[] placeDS = new PlaceDS[10];

	static void Print(PlaceList placeList){
		for (Place place: placeList.getPlaceList()){
			System.out.println("State: " + place.getState() + 
				" Name: "  + place.getName() + 
				" Lat: " + place.getLat() +
				" Lon: " + place.getLon());
		}

		placeDS(10).State = "Hello";

		System.out.println(placeList.getPlaceCount());
	}

	public static void main(String[] args) throws Exception{

		if (args.length != 1){
			System.err.println("Usage: List");
			System.exit(-1);
		}

		args[0] = "places-proto.bin";
		PlaceList placeList = PlaceList.parseFrom(new FileInputStream(args[0]));
		Print(placeList);
	}
}