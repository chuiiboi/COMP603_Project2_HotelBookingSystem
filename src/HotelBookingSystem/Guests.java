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
public class Guests {

    private ArrayList<Guest> guestList;
    private final String guestsFilePath; // The HBS_guests.txt file holds the data of each Guest Object in the ArrayList so it can be saved between different runs of the program.

    // Class that holds most controls for the Guest ArrayList:
    public Guests(String guestsFilePath) {
        this.guestList = new ArrayList<>();
        this.guestsFilePath = guestsFilePath;
        this.readGuestsFromFile(this.guestsFilePath);
    }

    // Getter:
    public ArrayList<Guest> getGuestList() {
        return guestList;
    }

    // Adds a new Guest object to the ArrayList then updates HBS_guests.txt file:
    // Returns added Guest object;
    public Guest add(Guest guest) {
        guestList.add(guest);
        updateGuestsFile();
        return guest;
    }

    // Removes a specified Guest object from the ArrayList:
    public void remove(Guest guest) {
        if (!guestList.contains(guest)) {
            System.out.println("Guest does not exist.");
        } else {
            guestList.remove(guest);
            updateGuestsFile();
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

    // For emptying HBS_guests.txt file so it can be updated/rewritten:
    public void emptyFile() {
        try {
            FileWriter fw = new FileWriter(guestsFilePath);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while emptying the file: " + e.getMessage());
        }
    }

    // Update the HBS_guests.txt file - called each time a new guest is added or removed:
    // writes each object to the file.
    public void updateGuestsFile() {
        this.emptyFile();
        try {
            FileOutputStream fileOut = new FileOutputStream(guestsFilePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            for (Guest g : guestList) {
                objectOut.writeObject(g);
            }
            objectOut.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Read all guests from HBS_guests.txt file - called at start of program
    public void readGuestsFromFile(String filepath) {
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            while (true) { // Loop stops when end of file is reached.
                Object obj = null;
                try {
                    obj = objectIn.readObject(); // reads Guest object from file.
                } catch (ClassNotFoundException e) {
                    Logger.getLogger(HotelBookingSystem.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println(e.getMessage());
                } catch (EOFException e) {
                    break; // Breaks loop when end of file is reached.
                }
                guestList.add((Guest) obj); // add Guest object to Arraylist.
            }

            objectIn.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            //System.out.println("IOException Error (in guests.java)."); // Can appear if HBS_guests file is completely empty when starting program so this is commented out (code still works).
        }
    }
}
