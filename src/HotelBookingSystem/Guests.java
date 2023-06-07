package HotelBookingSystem;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2114050
 */
public class Guests {

    private ArrayList<Guest> guestList;

//    private final String guestsFilePath; // The HBS_guests.txt file holds the data of each Guest Object in the ArrayList so it can be saved between different runs of the program.
    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;

    // Class that holds most controls for the Guest ArrayList:
    public Guests() {
        this.guestList = new ArrayList<>();
        dbManager = new DBManager();
        conn = dbManager.getConnection();
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
        this.dbManager.updateDB("INSERT INTO GUESTS VALUES ('" + guest.getFullName() + "', '" + guest.getEmail() + "', '" + guest.getPhoneNo() + "')");
        return guest;
    }

    // Removes a specified Guest object from the ArrayList:
    public void remove(Guest guest) {
        if (!guestList.contains(guest)) {
            System.out.println("Guest does not exist.");
        } else {
            guestList.remove(guest);
            dbManager.updateDB("DELETE FROM GUESTS WHERE EMAIL='" + guest.getEmail() + "'");
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
        Guest newGuest;
        ResultSet rs = this.dbManager.queryDB("SELECT * FROM GUESTS");
        try {
            while (rs.next()) {
                newGuest = new Guest(rs.getString(1), rs.getString(2), rs.getString(3));
                add(newGuest);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void initializeGuestsTable() {
        if (!this.checkGuestsTableExists("GUESTS")) {
            this.dbManager.updateDB("CREATE  TABLE GUESTS  (NAME  VARCHAR(50),   EMAIL   VARCHAR(50),   PHONENO   VARCHAR(12))");
        }

    }

    public boolean checkGuestsTableExists(String tableName) {
        try {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, tableName, null);
            return rs.next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public void closeConnection() {
        this.dbManager.closeConnections();
    }

//    public static void main(String[] args) {
//
//        Guests g = new Guests();
//        g.closeConnection();
//        g.displayGuests();
//    }
}
