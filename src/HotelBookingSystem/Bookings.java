package HotelBookingSystem;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2114050
 */
public class Bookings {

    private ArrayList<Booking> bookingList;
    private final String bookingsFilePath; // The HBS_bookings.txt file holds the data of each Booking Object in the ArrayList so it can be saved between different runs of the program.

    // Class that holds most controls for the Booking ArrayList:
    public Bookings(String bookingsFilePath) {
        this.bookingList = new ArrayList<>();
        this.bookingsFilePath = bookingsFilePath;
        this.readBookingsFromFile(this.bookingsFilePath);
    }

    // Getter:
    public ArrayList<Booking> getBookingList() {
        return bookingList;
    }

    // Adds a new Booking object to the ArrayList then updates HBS_bookings.txt file:
    // Returns added Guest object;
    public Booking add(Booking booking) {
        //booking.getRoom().changeAvailability();
        bookingList.add(booking);
        updateBookingsFile();
//        System.out.println("booking added from bookingmgr"); //Testing
        return booking;
    }

    // Removes a specified Booking object from the ArrayList:
    public void remove(Booking booking) {
        if (!bookingList.contains(booking)) {
            System.out.println("Booking does not exist.");
        } else {
            bookingList.remove(booking);
            updateBookingsFile();
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

    // For emptying HBS_bookings.txt file so it can be updated/rewritten:
    public void emptyFile() {
        try {
            FileWriter fw = new FileWriter(bookingsFilePath);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while emptying the file: " + e.getMessage());
        }
    }

    // Update the HBS_bookings.txt file - called each time a new guest is added or removed:
    // writes each object to the file.
    public void updateBookingsFile() {
        this.emptyFile();
        try {
            FileOutputStream fileOut = new FileOutputStream(bookingsFilePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            for (Booking b : bookingList) {
                objectOut.writeObject(b);
            }
            objectOut.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    } 

    //Read all bookings from HBS_bookings.txt file - called at start of program
    public void readBookingsFromFile(String filepath) {
        boolean cont = true;
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            while (cont) { // Loop stops when end of file is reached.
                Object obj = null;
                try {
                    obj = objectIn.readObject(); // reads Booking object from file.
                } catch (ClassNotFoundException e) {
                    Logger.getLogger(HotelBookingSystem.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println(e.getMessage());
                } catch (EOFException e) {
                    break; // Breaks loop when end of file is reached.
                }
                bookingList.add((Booking) obj); // add Booking object to Arraylist.
            }

            objectIn.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
//            System.out.println("IOException Error (in bookings.java)."); // Can appear if HBS_bookings file is completely empty when starting program so this is commented out (code still works).
        }
    }
}
