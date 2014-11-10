public class PlaceDS implements java.io.Serializable{
	public String State;
	public String Name;
	public double Lat;
	public double Lon;

	public void Print(){
		System.out.println(State);
		System.out.println(Name);
		System.out.println(Lat);
		System.out.println(Lon);
	}
}