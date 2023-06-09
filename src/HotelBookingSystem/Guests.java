package HotelBookingSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author 2114050
 */
public class Guests {

    private ArrayList<Guest> guestList;

    private final DBManager dbManager;

    // Class that holds most controls for the Guest ArrayList:
    public Guests(DBManager dbManager) {
        this.guestList = new ArrayList<>();
        this.dbManager = dbManager;
        this.initializeGuestsTable();
        this.getGuestsFromDB();

    }

    // Getter:
    public ArrayList<Guest> getGuestList() {
        return guestList;
    }

    // Adds a new Guest object to the ArrayList then updates DB GUESTS table:
    // Returns added Guest object;
    public Guest add(Guest guest) {
        guestList.add(guest);
        dbManager.updateDB("INSERT INTO GUESTS VALUES ('" + guest.getFullName() + "', '" + guest.getEmail() + "', '" + guest.getPhoneNo() + "')");
        return guest;
    }

    // Removes a specified Guest object from the ArrayList then updates DB GUESTS table:
    public void remove(Guest guest) {
        if (!guestList.contains(guest)) {
            System.out.println("Guest does not exist.");
        } else {
            guestList.remove(guest);
            dbManager.updateDB("DELETE FROM GUESTS WHERE NAME='" + guest.getFullName() + "'");
            System.out.println("Guest " + guest.getFullName() + " has been removed.");
        }
    }

    // Gets Guest data from DB GUESTS table and inserts into a guestList that holds all guests:
    public void getGuestsFromDB() {
        Guest guest;
        ResultSet rs = this.dbManager.queryDB("SELECT * FROM GUESTS");
        try {
            while (rs.next()) {
                guest = new Guest(rs.getString(1), rs.getString(2), rs.getString(3));
                guestList.add(guest);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

     // Creates GUESTS table in HotelDB if not already created:
    public void initializeGuestsTable() {
        if (!this.dbManager.checkTableExists("GUESTS")) {
            this.dbManager.updateDB("CREATE  TABLE GUESTS  (NAME  VARCHAR(50),   EMAIL   VARCHAR(50),   PHONENO   VARCHAR(12))");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (guestList.isEmpty()) {
            sb.append("There are no guests.");
        }
        else {
            for (Guest g : guestList) {
                sb.append(g).append("\n");
            }
        }
        
        return sb.toString();
    }
    
}
