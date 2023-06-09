/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HotelBookingSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author 21145050
 */
public class Controller implements ActionListener {

    Model model;
    View view;

    public Controller(View view, Model model) {

        this.view = view;
        this.model = model;
        this.view.addActionListener(this); // Add Actionlistener (the instance of this class) to View.
    }

    //invoked when a button is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); // Obtain the text displayed on the component.
        System.out.println(command);
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
                //Get Guest name from user
                String guestName;
                while (true) {
                    try {
                        guestName = this.view.getUserInputString("Enter Guest full name:", "Full Name");
                    } catch (NullPointerException ex) {
                        guestName = null;
                        break; // Break loop if dialog was cancelled.
                    }

                    if (guestName.isEmpty()) {
                        this.view.showMessagePopUp("Invalid Input: Cannot be empty. Try again");
                    } else if (!guestName.matches("[a-zA-Z ]+")) { //Check if input doesnt contains letters
                        this.view.showMessagePopUp("Invalid Input: Must only contain letters. Try again.");
                    } else if (this.model.guestNameExists(guestName)) {
                        this.view.showMessagePopUp("Invalid Input: Guest name already exists. Try again.");
                    } else {
                        break;
                    }
                }
                if (guestName == null) {
                    break; // Break case if dialog was cancelled.
                }

                //Get Guest email from user
                String guestEmail;
                while (true) {
                    guestEmail = this.view.getUserInputString("Enter Guest Email:", "example@email.com");

                    if (guestEmail == null) {
                        break; // Break loop if dialog was cancelled.
                    }

                    if (guestEmail.isEmpty()) {
                        this.view.showMessagePopUp("Invalid Input: Cannot be empty. Try again");
                    } else if (!guestEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) { //Check if isnt valid email format
                        this.view.showMessagePopUp("Invalid Email Format Try again.");
                    } else if (this.model.guestEmailExists(guestEmail)) {
                        this.view.showMessagePopUp("Invalid Input: Guest email already exists. Try again.");
                    } else {
                        break;
                    }
                }
                if (guestEmail == null) {
                    break; // Break case if dialog was cancelled.
                }

                //Get Guest phone number from user
                String guestPhoneNo;
                while (true) {
                    guestPhoneNo = this.view.getUserInputString("Enter Guest PhoneNo:", "00000000");

                    if (guestPhoneNo == null) {
                        break; // Break loop if dialog was cancelled.
                    }

                    if (guestPhoneNo.isEmpty()) {
                        this.view.showMessagePopUp("Invalid Input: Cannot be empty. Try again");
                    } else if (!guestPhoneNo.matches("^[0-9]{7,12}$")) { //Check if isnt valid phone number format
                        this.view.showMessagePopUp("Invalid Phone Number Format Try again.");
                    } else if (this.model.guestPhoneNoExists(guestPhoneNo)) {
                        this.view.showMessagePopUp("Invalid Input: Guest phone number already exists. Try again.");
                    } else {
                        break;
                    }
                }
                if (guestPhoneNo == null) {
                    break; // Break case if dialog was cancelled.
                }

                //Enter details into a new Guest:
                this.model.guests.add(new Guest(guestName, guestEmail, guestPhoneNo));
                this.model.refreshUIGuestList();
                this.view.showMessagePopUp("Guest '" + guestName + "' Added.");
                break;
            case "AddR":
                //Get Room number from user:
                Integer roomNum;
                while (true) {
                    roomNum = view.getUserInputInt("Enter Room Number:", "0");
                    if (roomNum == null) {
                        break; // Break loop if dialog was cancelled.
                    }

                    // Check input is at least 0:
                    if (roomNum < 0) {
                        this.view.showMessagePopUp("Invaled Input: Must be 0 or larger. Try again.");
                    } // Check if room number already exists:
                    else if (this.model.rooms.getRoomMap().containsKey(roomNum)) {
                        this.view.showMessagePopUp("Room Number already exists. Try again.");
                    } else {
                        break; // Break loop
                    }
                }
                if (roomNum == null) {
                    break; // Break case if dialog was cancelled or room number already exists
                }

                //Get Room type from user:
                String roomType;
                roomType = (String) this.view.getUserSelection("Select Room Type:", "Room Type Selection", new String[]{"Standard", "Deluxe"}, "Standard");
                if (roomType == null) {
                    break;// Break case if dialog was cancelled
                }

                //Enter details into a new Room
                if (roomType.equalsIgnoreCase("standard")) {
                    this.model.rooms.add(new RoomStandard(roomNum));
                } else {
                    this.model.rooms.add(new RoomDeluxe(roomNum));
                }
                this.model.refreshUIRoomList();
                this.view.showMessagePopUp("Room '#" + roomNum + "' Added.");
                break;
            case "AddB":
                //Check if there are any guests or rooms to add:
                if (this.model.guests.getGuestList().isEmpty() || this.model.rooms.getRoomMap().isEmpty()) {
                    this.view.showMessagePopUp("Need at Least one Room and one Guest to choose from.");
                    break;
                }

                //Get Room from user:
                Room room;
                while (true) {
                    room = (Room) this.view.getUserSelection("Select Room:", "Room Selection", this.model.rooms.getRoomMap().values().toArray(), "");
                    if (room == null) {
                        break; // Break loop if dialog was cancelled
                    }

                    if (!room.isAvailable()) { // Check if room is available.
                        this.view.showMessagePopUp("That room is not available as it has already been booked.");
                    } else {
                        break;
                    }
                }
                if (room == null) {
                    break; // Break case if dialog was cancelled
                }

                //Get Guest from user:
                Guest guest;
                guest = (Guest) this.view.getUserSelection("Select Guest:", "Guest Selection", this.model.guests.getGuestList().toArray(), "");
                if (guest == null) {
                    break; // Break case if dialog was cancelled
                }

                //Get Room from user:
                Integer numDays;
                while (true) {
                    numDays = this.view.getUserInputInt("Enter Duration of visit (days):", "0");
                    if (numDays == null) {
                        break; // Break loop if dialog was cancelled.
                    }
                    if (numDays <= 0) {
                        this.view.showMessagePopUp("Invaled Input: Must be more than 0. Try again.");
                    } else {
                        break;
                    }
                }
                if (numDays == null) {
                    break; // Break case if dialog was cancelled
                }

                //Enter details into a new Booking
                this.model.bookings.add(new Booking(guest, room, numDays));
                this.model.rooms.updateAvailability(room, false);
                this.model.refreshUIBookingList();
                this.view.showMessagePopUp("Booking Added.");
                break;
            case "RemoveG":
                //Check if there are any guests tor remove:
                if (this.model.guests.getGuestList().isEmpty()) {
                    this.view.showMessagePopUp("There are no Guests to remove.");
                    break; // Break case if there are no guests.
                }

                //Get Guest from user:
                Guest guestRemove;
                guestRemove = (Guest) this.view.getUserSelection("Select Guest to remove:", "Guest Selection", this.model.guests.getGuestList().toArray(), "");

                if (guestRemove == null) {
                    break; // Break case if dialog was cancelled
                }

                //Remove selected Guest (and any bookings its connected to)
                this.model.guests.remove(guestRemove);
                this.model.removeConnectedBooking(guestRemove);
                this.view.showMessagePopUp("Guest '" + guestRemove.getFullName() + "' Removed.");
                this.model.refreshUIGuestList();
                break;
            case "RemoveR":
                //Check if there are any rooms to remove:
                if (this.model.rooms.getRoomMap().isEmpty()) {
                    this.view.showMessagePopUp("There are no Rooms to remove.");
                    break; // Break case if there are no rooms.
                }

                //Get Room from user:
                Room roomRemove;
                roomRemove = (Room) this.view.getUserSelection("Select Room to remove:", "Room Selection", this.model.rooms.getRoomMap().values().toArray(), "");
                if (roomRemove == null) {
                    break; // Break case if dialog was cancelled
                }

                //Remove selected Room (and any bookings its connected to)
                this.model.rooms.remove(roomRemove);
                this.model.removeConnectedBooking(roomRemove);
                this.view.showMessagePopUp("Room '#" + roomRemove.getRoomNum() + "' Removed.");
                this.model.refreshUIRoomList();
                break;
            case "RemoveB":
                //Check if there are any guests or rooms to add:
                if (this.model.bookings.getBookingList().isEmpty()) {
                    this.view.showMessagePopUp("There are no Bookings to remove");
                    break; // Break case if there are no bookings.
                }

                //Get Booking from user:
                Booking bookingRemove;
                bookingRemove = (Booking) this.view.getUserSelection("Select Booking to remove:", "Booking Selection", this.model.bookings.getBookingList().toArray(), "");
                if (bookingRemove == null) {
                    break; // Break case if dialog was cancelled
                }

                //Remove selected Room (and any bookings its connected to)
                this.model.bookings.remove(bookingRemove);
                this.view.showMessagePopUp("Booking Removed.");
                this.model.refreshUIBookingList();
                break;
            default:
                break;
        }

    }
}
