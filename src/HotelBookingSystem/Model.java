package HotelBookingSystem;

import java.util.Observable;

/**
 *
 * @author 21145050
 */
public class Model extends Observable {
    public Guests guests;
    public Rooms rooms;
    public Bookings bookings;
    private final DBManager dbManager;

    public Model() {
        this.dbManager = new DBManager();
        this.guests = new Guests(dbManager);
        this.rooms = new Rooms(dbManager);
        this.bookings = new Bookings(dbManager, guests, rooms);
    }

    //Updates the displayed list within the Room menu:
    public void refreshUIRoomList() {
        setChanged();
        notifyObservers(rooms);
    }
    
    //Updates the displayed list within the Guest menu:
    public void refreshUIGuestList() {
        setChanged();
        notifyObservers(guests);
    }
    
    //Updates the displayed list within the Booking menu:
    public void refreshUIBookingList() {
        setChanged();
        notifyObservers(bookings);
    }
    
    //Checks if an input guest name already exists within Guests, if so, return true:
    public boolean guestNameExists(String string) {
        for(Guest g : guests.getGuestList()) {
            if(g.getFullName().equalsIgnoreCase(string)){
                return true;
            }
        }
        return false;
    }
    
    //Checks if an input guest name already exists within Guests, if so, return true:
    public boolean guestEmailExists(String string) {
        for(Guest g : guests.getGuestList()) {
            if(g.getEmail().equalsIgnoreCase(string)){
                return true;
            }
        }
        return false;
    }
    
    //Checks if an input guest name already exists within Guests, if so, return true:
    public boolean guestPhoneNoExists(String string) {
        for(Guest g : guests.getGuestList()) {
            if(g.getPhoneNo().equalsIgnoreCase(string)){
                return true;
            }
        }
        return false;
    }
    
    // Checks if the input guest is used in a booking, if so then delete the booking:
    public void removeConnectedBooking(Guest guest) {
        for(Booking b : bookings.getBookingList()) {
            if(b.getGuest().equals(guest)) {
                bookings.remove(b);
                removeConnectedBooking(guest); // one guest can have multiple bookings, so check bookingList again for any other instances of guest.
                return;
            }
        }
        return;
    }
    
    // Checks if the input room is used in a booking, if so then delete the booking:
    public void removeConnectedBooking(Room room) {
        for(Booking b : bookings.getBookingList()) {
            if(b.getRoom().equals(room)) {
                bookings.remove(b);
                return;
            }
        }
        return;
    }
}
