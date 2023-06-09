package HotelBookingSystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


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
        this.initializeGuestsTable(); // Creates GUESTS table in HotelDB if not already created
        this.getGuestsFromDB();

    }

    // Getter:
    public ArrayList<Guest> getGuestList() {
        return guestList;
    }

    // Adds a new Guest object to the ArrayList then updates guests DB table:
    // Returns added Guest object;
    public Guest add(Guest guest) {
        guestList.add(guest);
        dbManager.updateDB("INSERT INTO GUESTS VALUES ('" + guest.getFullName() + "', '" + guest.getEmail() + "', '" + guest.getPhoneNo() + "')");
        return guest;
    }

    // Removes a specified Guest object from the ArrayList:
    public void remove(Guest guest) {
        if (!guestList.contains(guest)) {
            System.out.println("Guest does not exist.");
        } else {
            guestList.remove(guest);
            dbManager.updateDB("DELETE FROM GUESTS WHERE NAME='" + guest.getFullName() + "'");
            System.out.println("Guest " + guest.getFullName() + " has been removed.");
        }
    }

    // Allow user to select a Guest object from the ArrayList then return the selected Guest object:
    // uses displayGuests() to show the user options to choose from.
    public Guest selectGuest() {

        if (guestList.isEmpty()) {
            System.out.println("There are no guests to choose from");
        } else {
            Scanner scan = new Scanner(System.in);
            int selection;

            System.out.println("Please Select a Guest (0 to cancel)");
            this.displayGuests();
            do {
                try {
                    selection = scan.nextInt();

                    if (selection < 0 || selection > guestList.size()) {
                        System.out.println("Guest not found. Please enter a number from the list.");
                    } else if (selection == 0) {
                        return null;
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number from the list.");
                    selection = 0;
                }
            } while (selection < 1 || selection > guestList.size());

            return guestList.get(selection - 1);
        }

        return null;
    }

    // Display a numbered list of all of the Guest objects inside the ArrayList:
    public void displayGuests() {
        System.out.println("Guests:");
        if (guestList.isEmpty()) {
            System.out.println("  There are no guests.");
        }
        int i = 1;
        for (Guest g : guestList) {
            System.out.println("  " + i + ". " + g.toString());
            i++;
        }
    }

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
