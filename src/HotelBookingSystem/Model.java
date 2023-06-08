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

//    public void enterGuestMenu() {
//        setChanged();
//        notifyObservers("enterGuestMenu");
//    }
//    public void enterRoomMenu() {
//        setChanged();
//        notifyObservers("enterRoomMenu");
//    }
//    public void enterBookingtMenu() {
//        setChanged();
//        notifyObservers("enterBookingtMenu");
//    }
//    public void returnToMainMenu() {
//        setChanged();
//        notifyObservers("returnToMainMenu");
//    }
    

}
