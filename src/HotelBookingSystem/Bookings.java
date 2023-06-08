package HotelBookingSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author 2114050
 */
public class Bookings {

    private ArrayList<Booking> bookingList;
    public Guests guests;
    public Rooms rooms;
    private final DBManager dbManager;

    // Class that holds most controls for the Booking ArrayList:
    public Bookings(DBManager dbManager, Guests guests, Rooms rooms) {
        this.bookingList = new ArrayList<>();
        this.guests = guests;
        this.rooms = rooms;
        this.dbManager = dbManager;
        this.initializeBookingsTable(); // Creates BOOKINGS table in HotelDB if not already created
        this.getBookingsFromDB();
    }

    // Getter:
    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }

    // Adds a new Booking object to the ArrayList then updates DB table:
    // Returns added Guest object;
    public Booking add(Booking booking) {
        bookingList.add(booking);
        dbManager.updateDB("INSERT INTO BOOKINGS VALUES ('" + booking.getGuest().getFullName() + "', " + booking.getRoom().getRoomNum() + ", " + booking.getDays() + ")");
        return booking;
    }

    // Removes a specified Booking object from the ArrayList:
    public void remove(Booking booking) {
        if (!bookingList.contains(booking)) {
            System.out.println("Booking does not exist.");
        } else {
            bookingList.remove(booking);
            dbManager.updateDB("DELETE FROM BOOKINGS WHERE ROOMNUM=" + booking.getRoom().getRoomNum());
            System.out.println("Booking has been removed.");
        }
    }

    // Allow user to select a Booking object from the ArrayList then return the selected Booking object:
    // uses displayBookings() to show the user options to choose from.
    public Booking selectBooking() {

        if (bookingList.isEmpty()) {
            System.out.println("There are no bookings to choose from");
        } else {
            Scanner scan = new Scanner(System.in);
            int selection;

            System.out.println("Please Select a Bookings (0 to cancel)");
            this.displayBookings();
            do {
                try {
                    selection = scan.nextInt();

                    if (selection < 0 || selection > bookingList.size()) {
                        System.out.println("Booking not found. Please enter a number from the list.");
                    } else if (selection == 0) {
                        return null;
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number from the list.");
                    selection = -1;
                }
            } while (selection < 0 || selection > bookingList.size());

            return bookingList.get(selection - 1);
        }

        return null;
    }

    // Display a numbered list of all of the Booking objects inside the ArrayList:
    public void displayBookings() {
        System.out.println("Bookings:");
        if (bookingList.isEmpty()) {
            System.out.println("  There are no bookings.");
        }
        int i = 1;
        for (Booking b : bookingList) {
            System.out.println("  " + i + ". " + b.toString());
            i++;
        }
    }

        public void getBookingsFromDB() {
        Booking booking;
        Guest guest = null;
        Room room = null;
        ResultSet rs = this.dbManager.queryDB("SELECT * FROM BOOKINGS");
        try {
            while (rs.next()) {
                for(Guest g : guests.getGuestList()) { //Find Guest reference to correct guest
                    if(g.getFullName().equals(rs.getString(1))) {
                        guest = g;
                    }
                }
                room = rooms.getRoomMap().get(rs.getInt(2)); // Find Room reference to correct room
                booking = new Booking(guest, room, rs.getInt(3));
                bookingList.add(booking);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void initializeBookingsTable() {
        if (!this.dbManager.checkTableExists("BOOKINGS")) {
            this.dbManager.updateDB("CREATE  TABLE BOOKINGS  (GUESTNAME  VARCHAR(50),   ROOMNUM   INTEGER,   DAYS   INTEGER)");
        }
    }
    
}
