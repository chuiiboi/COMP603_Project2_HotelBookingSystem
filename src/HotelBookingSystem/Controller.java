package HotelBookingSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author 21145050
 */
public class Controller implements ActionListener {

    Model model;
    View view;

    // Class that controls the user input from GUI (View):
    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;
        this.view.addActionListener(this); // Add Actionlistener (the instance of this class) to View.
    }

    //invoked when a button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); // Obtain the actionCommand.
        switch (command) {
            case "Main Menu":
                this.view.showMainMenu();
                break;
            case "Quit":
                this.view.closeWindow();
                break;
            case "Guests":
                this.view.showGuestMenu();
                this.model.refreshUIGuestList();
                break;
            case "Rooms":
                this.view.showRoomMenu();
                this.model.refreshUIRoomList();
                break;
            case "Bookings":
                this.view.showBookingMenu();
                this.model.refreshUIBookingList();
                break;
            case "AddG":
                addGuest();
                break;
            case "AddR":
                addRoom();
                break;
            case "AddB":
                addBooking();
                break;
            case "RemoveG":
                removeGuest();
                break;
            case "RemoveR":
                removeRoom();
                break;
            case "RemoveB":
                removeBooking();
                break;
            default:
                break;
        }
    }

    public void addGuest() {
        //Get Guest name from user
        String name = getGuestNameFromUser();
        if (name == null) {
            return; // Return if dialog was cancelled.
        }

        //Get Guest email from user
        String email = getGuestEmailFromUser();
        if (email == null) {
            return; // Return if dialog was cancelled.
        }

        //Get Guest phone number from user
        String phoneNo = getGuestPhoneNoFromUser();
        if (phoneNo == null) {
            return; // Return if dialog was cancelled.
        }

        //Enter details into a new Guest:
        this.model.guests.add(new Guest(name, email, phoneNo));
        this.model.refreshUIGuestList();
        this.view.showMessagePopUp("Guest '" + name + "' Added.");
    }

    public void addRoom() {
        //Get Room number from user:
        Integer roomNum = getRoomNum();
        if (roomNum == null) {
            return; // Return if dialog was cancelled=
        }

        //Get Room type from user:
        String roomType = getRoomTypeFromUser();
        if (roomType == null) {
            return;// Return if dialog was cancelled
        }

        //Enter details into a new Room:
        if (roomType.equalsIgnoreCase("standard")) {
            this.model.rooms.add(new RoomStandard(roomNum));
        } else {
            this.model.rooms.add(new RoomDeluxe(roomNum));
        }
        this.model.refreshUIRoomList();
        this.view.showMessagePopUp("Room '#" + roomNum + "' Added.");
    }

    public void addBooking() {
        //Check if there are any guests or rooms to add:
        if (this.model.guests.getGuestList().isEmpty() || this.model.rooms.getRoomMap().isEmpty()) {
            this.view.showMessagePopUp("Need at Least one Room and one Guest to choose from.");
            return;
        }

        //Get Room from user:
        Room room = getRoomFromUserAndCheckAvailablility();
        if (room == null) {
            return; // Return if dialog was cancelled
        }

        //Get Guest from user:
        Guest guest = getGuestFromUser("Select Guest:");
        if (guest == null) {
            return; // Return if dialog was cancelled
        }

        //Get Room from user:
        Integer numDays = getNumDaysFromUser();
        if (numDays == null) {
            return; // Return if dialog was cancelled
        }

        //Enter details into a new Booking:
        this.model.bookings.add(new Booking(guest, room, numDays));
        this.model.rooms.updateAvailability(room, false);
        this.model.refreshUIBookingList();
        this.view.showMessagePopUp("Booking Added.");
    }

    public void removeGuest() {
        //Check if there are any guests to remove:
        if (this.model.guests.getGuestList().isEmpty()) {
            this.view.showMessagePopUp("There are no Guests to remove.");
            return; // Return if there are no guests.
        }

        //Get Guest from user:
        Guest guest = getGuestFromUser("Select Guest to remove:");

        if (guest == null) {
            return; // Return if dialog was cancelled
        }

        //Remove selected Guest (and any bookings its connected to):
        this.model.guests.remove(guest);
        this.model.removeConnectedBooking(guest);
        this.view.showMessagePopUp("Guest '" + guest.getFullName() + "' Removed.");
        this.model.refreshUIGuestList();
    }

    public void removeRoom() {
        //Check if there are any rooms to remove:
        if (this.model.rooms.getRoomMap().isEmpty()) {
            this.view.showMessagePopUp("There are no Rooms to remove.");
            return; // Return if there are no rooms.
        }

        //Get Room from user:
        Room room = getRoomFromUser("Select Room to remove:");
        if (room == null) {
            return; // Return if dialog was cancelled
        }

        //Remove selected Room (and any bookings its connected to):
        this.model.rooms.remove(room);
        this.model.removeConnectedBooking(room);
        this.view.showMessagePopUp("Room '#" + room.getRoomNum() + "' Removed.");
        this.model.refreshUIRoomList();
    }

    public void removeBooking() {
        //Check if there are any guests or rooms to add:
        if (this.model.bookings.getBookingList().isEmpty()) {
            this.view.showMessagePopUp("There are no Bookings to remove");
            return; // Return if there are no bookings.
        }

        //Get Booking from user:
        Booking booking = getBookingFromUser("Select booking to remove");
        if (booking == null) {
            return; // Return if dialog was cancelled
        }

        //Remove selected Booking:
        this.model.bookings.remove(booking);
        this.view.showMessagePopUp("Booking Removed.");
        this.model.refreshUIBookingList();
    }

    public String getGuestNameFromUser() {
        String guestName;
        while (true) {
            guestName = this.view.getUserInputString("Enter Guest full name:", "Full Name");

            if (guestName == null) {
                return null; // Return if dialog was cancelled.
            }

            if (guestName.isEmpty()) {
                this.view.showMessagePopUp("Invalid Input: Cannot be empty. Try again");
            } else if (!guestName.matches("[a-zA-Z ]+")) { //Check if input doesnt contains letters
                this.view.showMessagePopUp("Invalid Input: Must only contain letters. Try again.");
            } else if (this.model.guestNameExists(guestName)) {
                this.view.showMessagePopUp("Invalid Input: Guest name already exists. Try again.");
            } else {
                break; // Break loop
            }
        }
        return guestName;
    }

    public String getGuestEmailFromUser() {
        String guestEmail;
        while (true) {
            guestEmail = this.view.getUserInputString("Enter Guest Email:", "example@email.com");

            if (guestEmail == null) {
                return null; // Return if dialog was cancelled.
            }

            if (guestEmail.isEmpty()) {
                this.view.showMessagePopUp("Invalid Input: Cannot be empty. Try again");
            } else if (!guestEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) { //Check if input isnt valid email format.
                this.view.showMessagePopUp("Invalid Email Format Try again.");
            } else if (this.model.guestEmailExists(guestEmail)) {
                this.view.showMessagePopUp("Invalid Input: Guest email already exists. Try again.");
            } else {
                break; // Break loop
            }
        }
        return guestEmail;
    }

    public String getGuestPhoneNoFromUser() {
        String guestPhoneNo;
        while (true) {
            guestPhoneNo = this.view.getUserInputString("Enter Guest PhoneNo:", "00000000");

            if (guestPhoneNo == null) {
                return null; // Return if dialog was cancelled.
            }

            if (guestPhoneNo.isEmpty()) {
                this.view.showMessagePopUp("Invalid Input: Cannot be empty. Try again");
            } else if (!guestPhoneNo.matches("^[0-9]{7,12}$")) { //Check if input isnt valid phone number format.
                this.view.showMessagePopUp("Invalid Phone Number Format Try again.");
            } else if (this.model.guestPhoneNoExists(guestPhoneNo)) {
                this.view.showMessagePopUp("Invalid Input: Guest phone number already exists. Try again.");
            } else {
                break; // Break loop
            }
        }
        return guestPhoneNo;
    }

    public Integer getRoomNum() {
        Integer roomNum;
        while (true) {
            roomNum = view.getUserInputInt("Enter Room Number:", "0");
            if (roomNum == null) {
                return null; // Return if dialog was cancelled.
            }

            // Check input is at least 0:
            if (roomNum < 0) {
                this.view.showMessagePopUp("Invaled Input: Must be 0 or larger. Try again.");
            } else if (this.model.rooms.getRoomMap().containsKey(roomNum)) {// Check if room number already exists.
                this.view.showMessagePopUp("Room Number already exists. Try again.");
            } else { // valid input:
                break; // Break loop
            }
        }
        return roomNum;
    }

    public String getRoomTypeFromUser() {
        String roomType;
        roomType = (String) this.view.getUserSelection("Select Room Type:", "Room Type Selection", new String[]{"Standard", "Deluxe"}, "Standard");
        return roomType;
    }

    public Room getRoomFromUserAndCheckAvailablility() {
        Room room;
        while (true) {
            room = (Room) this.view.getUserSelection("Select Room:", "Room Selection", this.model.rooms.getRoomMap().values().toArray(), "");
            if (room == null) {
                return null; // Return null if dialog was cancelled
            }

            if (!room.isAvailable()) { // Check if room is available.
                this.view.showMessagePopUp("That room is not available as it has already been booked.");
            } else {
                break; // Break loop
            }
        }
        return room;
    }

    public Guest getGuestFromUser(String prompt) {
        Guest guest;
        guest = (Guest) this.view.getUserSelection(prompt, "Guest Selection", this.model.guests.getGuestList().toArray(), "");
        return guest;
    }

    public Room getRoomFromUser(String prompt) {
        Room room;
        room = (Room) this.view.getUserSelection(prompt, "Room Selection", this.model.rooms.getRoomMap().values().toArray(), "");
        return room;
    }

    public Booking getBookingFromUser(String prompt) {
        Booking booking;
        booking = (Booking) this.view.getUserSelection(prompt, "Booking Selection", this.model.bookings.getBookingList().toArray(), "");
        return booking;
    }

    public Integer getNumDaysFromUser() {
        Integer numDays;
        while (true) {
            numDays = this.view.getUserInputInt("Enter Duration of visit (days):", "0");
            if (numDays == null) {
                return null; // Return null if dialog was cancelled.
            }
            if (numDays <= 0) {
                this.view.showMessagePopUp("Invaled Input: Must be more than 0. Try again.");
            } else {
                break; // Break loop
            }
        }
        return numDays;
    }
}
