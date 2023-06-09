package HotelBookingSystem;

/**
 *
 * @author 21145050
 */
public class Booking {

    public Guest guest;
    public Room room;
    private final int days;

    // Class for an individual Booking object:
    public Booking(Guest guest, Room room, int days) {
        this.guest = guest;
        this.room = room;
        this.days = days;
    }

    // Getter:
    public Guest getGuest() {
        return guest;
    }

    // Getter:
    public Room getRoom() {
        return room;
    }

    // Getter:
    public int getDays() {
        return days;
    }

    // Calculates total price for when booking is displayed to user (toString):
    public double calculateTotalPrice() {
        return this.days * this.room.getCostPerNight();
    }

    // Booking toString:
    @Override
    public String toString() {
        return "Room " + this.getRoom().getRoomNum() + " for " + this.getGuest().getFullName() + ", " + this.getDays() + " days: | $" + this.calculateTotalPrice();
    }
}
