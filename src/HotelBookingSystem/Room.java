package HotelBookingSystem;

/**
 *
 * @author 21145050
 */
public abstract class Room {

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
    public void changeAvailability(boolean available) {
        this.available = available;
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
        return "#" + roomNum + " (" + getTypeName() + ") is " + getAvailability() + ", $" + getCostPerNight() + " per night (max capacity: " + getCapacity() + ")";
    }

}
