package aliefyaFikriIhsaniJSleepMN.android.model;

import java.util.ArrayList;
import java.util.Date;

public class Room extends Serializable {
    public int accountId;
    public String name;
    public ArrayList<Date> booked;
    public String address;
    public Price price;
    public City city;
    public int Size;
    public BedType bedType;
    public Facility facility;
}
