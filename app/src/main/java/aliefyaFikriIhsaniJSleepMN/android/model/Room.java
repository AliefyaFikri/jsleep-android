package aliefyaFikriIhsaniJSleepMN.android.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Kelas Room merupakan kelas yang berfungsi untuk mendefinisikan room yang akan disewakan
 * oleh renter
 *
 * @author Aliefya Fikri Ihsani
 * @version 1.0
 */

public class Room extends Serializable {
    public int accountId;
    public String name;
    public ArrayList<Date> booked;
    public String address;
    public Price price;
    public City city;
    public int size;
    public BedType bedType;
    public ArrayList<Facility> facility;

    public String toString() {
        return name;
    }
}
