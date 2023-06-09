package HotelBookingSystem;

import java.io.Serializable;

/**
 *
 * @author 21145050
 */
public class Guest implements Serializable {
    //default serialVersion i

    private final String fullName;
    private final String email;
    private final String phoneNo;

    // Class for an individual Guest object:
    public Guest(String fullname, String email, String phoneNo) {
        this.fullName = fullname;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    // Getter:
    public String getFullName() {
        return fullName;
    }

    // Getter:
    public String getEmail() {
        return email;
    }

    // Getter:
    public String getPhoneNo() {
        return phoneNo;
    }

    // Guest toString:
    @Override
    public String toString() {
        return this.getFullName() + " (" + this.getEmail() + " | " + this.getPhoneNo() + ")";
    }

}
