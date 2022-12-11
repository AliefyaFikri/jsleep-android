package aliefyaFikriIhsaniJSleepMN.android.model;

import java.util.Date;

/**
 * Kelas Payment merupakan kelas yang berfungsi untuk mendefinisikan pembayaran yang dilakukan oleh user
 *
 * @author Aliefya Fikri Ihsani
 * @version 1.0
 */
public class Payment extends Invoice{

    public Date to;
    public Date from;
    private int roomId;

    public String print() {
        String print = "\nRoom Id : " + roomId + "\nFrom : " + from + "\nTo : " + to;
        return print;
    }

}
