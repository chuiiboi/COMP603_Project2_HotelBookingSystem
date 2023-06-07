package HotelBookingSystem;

import java.io.Serializable;

/**
 *
 * @author 21145050
 */
public abstract class Room implements Serializable {

    private final Integer roomNum; // used as map key for hashMap in Rooms class
    private boolean available;

    // Class for an individual Room object:
    public Room(Integer roomNum) {
        this.roomNum = roomNum;
        this.available = true;
    }
    
    // Getter:
    public int getRoomNum() {
        return roomNum;
    }

    // Getter:
    public boolean isAvailable() {
        return available;
    }

    // Modified getter for Room toString() method:
    public String getAvailability() {
        if (this.isAvailable() == true) {
            return "available";
        } else {
            return "not available";
        }
    }

    // Changes availability status.
    public void changeAvailability(boolean availability) {
        this.available = availability;
    }

    // Getter:
    public abstract String getTypeName();

    // Getter:
    public abstract int getCapacity();

    // Getter:
    public abstract double getCostPerNight();

    // Room toString:
    @Override
    public String toString() {
        return "Room#" + roomNum + " (" + getTypeName() + ") is " + getAvailability() + ", $" + getCostPerNight() + " per night (max capacity: " + getCapacity() + ")";
    }

}
