package HotelBookingSystem;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2114050
 */
public class Rooms {

    private HashMap<Integer, Room> roomMap;
    private final String roomsFilePath;  // The HBS_rooms.txt file holds the data of each Room Object in the HashMap so it can be saved between different runs of the program.

    // Class that holds most controls for the Room HaskMap:
    public Rooms(String roomsFilePath) {
        this.roomMap = new HashMap<>();
        this.roomsFilePath = roomsFilePath;
        this.readRoomsFromFile(this.roomsFilePath);
    }

    // Getter:
    public HashMap<Integer, Room> getRoomMap() {
        return this.roomMap;
    }

    // Adds a new Room object to the HashMap then updates HBS_rooms.txt file:
    // Returns added Room object;
    public Room add(Room room) {
        this.roomMap.put(room.getRoomNum(), room);
        this.updateRoomsFile();
        return room;
    }

    // Removes a specified Room object from the HashMap:
    public void remove(Room room) {
        if (!this.roomMap.containsKey(room.getRoomNum())) {
            System.out.println("Room does not exist.");
        } else {
            this.roomMap.remove(room.getRoomNum());
            this.updateRoomsFile();
            System.out.println("Room " + room.getRoomNum() + " has been removed.");

        }
    }

    // Allow user to select a Room object from the HashMap then return the selected Room object:
    // uses displayRooms() to show the user options to choose from.
    // reads the room number from the user and uses it to retrieve Object.
    public Room selectRoom() {

        if (roomMap.isEmpty()) {
            System.out.println("There are no rooms to choose from.");
        } else {
            Scanner scan = new Scanner(System.in);
            int selection;

            System.out.println("Please Select a Room Number from the list (0 to cancel).");
            displayRooms();
            do {
                try {
                    selection = scan.nextInt();

                    if (!(roomMap.containsKey((Integer) selection)) && selection != 0) {
                        System.out.println("Room not found. Please enter a room number from the list.");
                    } else if (selection == 0) {
                        return null;
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a room number from the list.");
                    selection = -1;
                }
            } while (true);

            return roomMap.get((Integer) selection);
        }

        return null;
    }

    // Display a list of all of the Room objects inside the HashMap:
    public void displayRooms() {
        System.out.println("Rooms:");
        if (roomMap.isEmpty()) {
            System.out.println("  There are no rooms");
        }
        for (Room r : roomMap.values()) {
            System.out.println("  " + r.toString());
        }
    }

    // For emptying HBS_rooms.txt file so it can be updated/rewritten 
    public void emptyFile() {
        try {
            FileWriter fw = new FileWriter(this.roomsFilePath);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while emptying the file: " + e.getMessage());
        }
    }

    // Update the HBS_roooms.txt file - called each time a new room is added or removed:
    // writes each object to the file.
    public void updateRoomsFile() {
        this.emptyFile();
        try {
            FileOutputStream fileOut = new FileOutputStream(this.roomsFilePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            for (Room r : roomMap.values()) {
                objectOut.writeObject(r);
            }
            objectOut.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Read all rooms from HBS_rooms.txt file - called at start of program
    public void readRoomsFromFile(String filepath) {
        boolean cont = true;
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            while (cont) { // Loop stops when end of file is reached.
                Object obj = null;
                try {
                    obj = objectIn.readObject(); // reads Room object from file.
                } catch (ClassNotFoundException e) {
                    Logger.getLogger(HotelBookingSystem.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println(e.getMessage() + "1");
                } catch (EOFException e) {
                    break; // Breaks loop when end of file is reached.
                }
                roomMap.put(((Room) obj).getRoomNum(), (Room) obj);  // add Room object to HashMap using roomNum as mapKey.
            }

            objectIn.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
//            System.out.println("IOException Error (in rooms.java.)."); // Can appear if HBS_rooms file is completely empty when starting program so this is commented out (code still works).
        }
    }

}
