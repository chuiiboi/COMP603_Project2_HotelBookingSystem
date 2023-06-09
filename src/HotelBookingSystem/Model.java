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

    public void refreshUIRoomList() {
        setChanged();
        notifyObservers(rooms);
    }
    
    public void refreshUIGuestList() {
        setChanged();
        notifyObservers(guests);
    }
    
    public void refreshUIBookingList() {
        setChanged();
        notifyObservers(bookings);
    }
    
    public boolean guestNameExists(String string) {
        for(Guest g : guests.getGuestList()) {
            if(g.getFullName().equalsIgnoreCase(string)){
                return true;
            }
        }
        return false;
    }
    
    public boolean guestEmailExists(String string) {
        for(Guest g : guests.getGuestList()) {
            if(g.getEmail().equalsIgnoreCase(string)){
                return true;
            }
        }
        return false;
    }
    
    public boolean guestPhoneNoExists(String string) {
        for(Guest g : guests.getGuestList()) {
            if(g.getPhoneNo().equalsIgnoreCase(string)){
                return true;
            }
        }
        return false;
    }
    
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
