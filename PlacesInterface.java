import java.rmi.Remote;
import java.rmi.RemoteException;
import PlaceData.PlaceDataProto.*;

public interface PlacesInterface extends Remote {
	public Place GetPlaces(String city, String state) throws RemoteException;
}