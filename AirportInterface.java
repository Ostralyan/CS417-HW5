import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AirportInterface extends Remote{
	public AirportStruct[] GetAirports(double lat, double lon) throws RemoteException;
}
