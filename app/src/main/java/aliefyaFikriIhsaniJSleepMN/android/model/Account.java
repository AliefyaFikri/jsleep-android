package aliefyaFikriIhsaniJSleepMN.android.model;

/**
 * Kelas Account merupakan turunan dari kelas Serializable yang berfungsi untuk mendefinisikan atribut-atribut yang dimiliki oleh account user.
 *
 * @author Aliefya Fikri Ihsani
 * @version 1.0
 */

public class Account extends Serializable {
    public String name;
    public String password;
    public Renter renter;
    public String email;
    public double balance;

    @Override
    public String toString(){
        return "Account{" +
                "balance=" + balance +
                ", email=" + email +
                ", name=" + name +
                ", password=" + password +
                ", renter=" + renter + '}';
    }
}
