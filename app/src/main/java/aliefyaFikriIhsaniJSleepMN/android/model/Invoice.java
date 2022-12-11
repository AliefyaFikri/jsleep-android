package aliefyaFikriIhsaniJSleepMN.android.model;

/**
 * Kelas Invoice merupakan kelas yang berfungsi untuk mendefinisikan invoice yang dibuat oleh user
 *
 * @author Aliefya Fikri Ihsani
 * @version 1.0
 */

public class Invoice extends Serializable{
    public enum RoomRating { NONE, BAD, NEUTRAL, GOOD};
    public enum PaymentStatus { FAILED, WAITING, SUCCESS};
    public int buyerId;
    public int renterId;
    public PaymentStatus status;
    public RoomRating rating;

    public String print(){
        String print =
                "\nBuyer Id : " + buyerId +
                "\nRenter Id : " + renterId ;
        return print;
    }

}
