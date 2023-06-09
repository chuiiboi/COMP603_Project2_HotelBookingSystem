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
                this.view.RoomMenuPanel.setVisible(false);
                this.view.GuestMenuPanel.setVisible(false);
                this.view.BookingMenuPanel.setVisible(false);
                this.view.MainMenuPanel.setVisible(true);
                break;
            case "Quit":
                this.view.setVisible(false);
                this.view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
                break;
            case "Guests":
                this.view.MainMenuPanel.setVisible(false);
                this.view.GuestMenuPanel.setVisible(true);
                this.model.refreshUIGuestList();
                break;
            case "Rooms":
                this.view.MainMenuPanel.setVisible(false);
                this.view.RoomMenuPanel.setVisible(true);
                this.model.refreshUIRoomList();
                break;
            case "Bookings":
                this.view.MainMenuPanel.setVisible(false);
                this.view.BookingMenuPanel.setVisible(true);
                this.model.refreshUIBookingList();
                break;
            case "AddG":
                //Get Guest name from user
                String guestName = null;
                boolean validGuestName = false;
                while (!validGuestName) {
                    try {
                        guestName = JOptionPane.showInputDialog(view.GuestMenuPanel, "Enter Guest full name:", "Full Name").trim(); //.trim() ignores any whitespace.
                    } catch (NullPointerException ex) {
                        guestName = null;
                        break; // Break loop if dialog was cancelled.
                    }

                    if (guestName.isEmpty()) {
                        JOptionPane.showMessageDialog(view.GuestMenuPanel, "Invaled Input: Cannot be empty. Try again");
                        validGuestName = false;
                    } else if (!guestName.matches("[a-zA-Z ]+")) { //Check if input doesnt contains letters
                        JOptionPane.showMessageDialog(view.GuestMenuPanel, "Invaled Input: Must only contain letters. Try again.");
                        validGuestName = false;
                    } else if (this.model.guestNameExists(guestName)) {
                        JOptionPane.showMessageDialog(view.GuestMenuPanel, "Invaled Input: Guest name already exists. Try again.");
                        validGuestName = false;
                    } else {
                        validGuestName = true;
                    }
                }
                if (guestName == null) {
                    break; // Break case if dialog was cancelled.
                }

                //Get Guest email from user
                String guestEmail = null;
                boolean validGuestEmail = false;
                while (!validGuestEmail) {
                    try {
                        guestEmail = JOptionPane.showInputDialog(view.GuestMenuPanel, "Enter Guest Email:", "example@email.com").trim(); //.trim() ignores any whitespace.
                    } catch (NullPointerException ex) {
                        guestEmail = null;
                        break; // Break loop if dialog was cancelled.
                    }

                    if (guestEmail.isEmpty()) {
                        JOptionPane.showMessageDialog(view.GuestMenuPanel, "Invaled Input: Cannot be empty. Try again");
                        validGuestEmail = false;
                    } else if (!guestEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) { //Check if isnt valid email format
                        JOptionPane.showMessageDialog(view.GuestMenuPanel, "Invaled Email Format Try again.");
                        validGuestEmail = false;
                    } else if (this.model.guestEmailExists(guestEmail)) {
                        JOptionPane.showMessageDialog(view.GuestMenuPanel, "Invaled Input: Guest email already exists. Try again.");
                        validGuestEmail = false;
                    } else {
                        validGuestEmail = true;
                    }
                }
                if (guestEmail == null) {
                    break; // Break case if dialog was cancelled.
                }

                //Get Guest phone number from user
                String guestPhoneNo = null;
                boolean validGuestPhoneNo = false;
                while (!validGuestPhoneNo) {
                    try {
                        guestPhoneNo = JOptionPane.showInputDialog(view.GuestMenuPanel, "Enter Guest PhoneNo:", "01234567").trim(); //.trim() ignores any whitespace.
                    } catch (NullPointerException ex) {
                        guestPhoneNo = null;
                        break; // Break loop if dialog was cancelled.
                    }

                    if (guestPhoneNo.isEmpty()) {
                        JOptionPane.showMessageDialog(view.GuestMenuPanel, "Invaled Input: Cannot be empty. Try again");
                        validGuestPhoneNo = false;
                    } else if (!guestPhoneNo.matches("^[0-9]{7,12}$")) { //Check if isnt valid phone number format
                        JOptionPane.showMessageDialog(view.GuestMenuPanel, "Invaled Phone Number Format Try again.");
                        validGuestPhoneNo = false;
                    } else if (this.model.guestPhoneNoExists(guestPhoneNo)) {
                        JOptionPane.showMessageDialog(view.GuestMenuPanel, "Invaled Input: Guest phone number already exists. Try again.");
                        validGuestPhoneNo = false;
                    } else {
                        validGuestPhoneNo = true;
                    }
                }
                if (guestPhoneNo == null) {
                    break; // Break case if dialog was cancelled.
                }

                //Enter details into a new Guest:
                this.model.guests.add(new Guest(guestName, guestEmail, guestPhoneNo));
                this.model.refreshUIGuestList();
                JOptionPane.showMessageDialog(view.GuestMenuPanel, "Guest '" + guestName + "' Added.");
                break;
            case "AddR":
                //Get Room number from user:
                Object roomNum = null;
                int roomNumInt = -1;
                boolean validRoomNum = false;
                while (!validRoomNum) {
                    roomNum = (JOptionPane.showInputDialog(view.RoomMenuPanel, "Enter Room Number:", "0"));
                    if (roomNum == null) {
                        break; // Break loop if dialog was cancelled.
                    }
                    try { // Check if input is an integer:
                        roomNumInt = Integer.valueOf(roomNum.toString()); // Throws if input is not an integer.
                        // Check input is at least 0:
                        if (roomNumInt < 0) {
                            JOptionPane.showMessageDialog(view.RoomMenuPanel, "Invaled Input. Try again.");
                            validRoomNum = false;
                        } // Check if room number already exists:
                        else if (this.model.rooms.getRoomMap().containsKey(roomNumInt)) {
                            JOptionPane.showMessageDialog(view.RoomMenuPanel, "Room Number already exists.");
                            roomNum = null;
                            break; // Break loop if room number already exists
                        } else {
                            validRoomNum = true;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(view.RoomMenuPanel, "Invaled Input. Try again.");
                        validRoomNum = false; // repeats loop.
                    }
                }
                if (roomNum == null) {
                    break; // Break case if dialog was cancelled or room number already exists
                }

                //Get Room type from user:
                String roomType = null;
                roomType = (String) JOptionPane.showInputDialog(view.RoomMenuPanel, "Select Room Type:", "Room Type Selection", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Standard", "Deluxe"}, "Standard");
                if (roomType == null) {
                    break;// Break case if dialog was cancelled
                }

                //Enter details into a new Room
                if (roomType.equalsIgnoreCase("standard")) {
                    this.model.rooms.add(new RoomStandard(roomNumInt));
                } else {
                    this.model.rooms.add(new RoomDeluxe(roomNumInt));
                }
                this.model.refreshUIRoomList();
                JOptionPane.showMessageDialog(view.RoomMenuPanel, "Room '#" + roomNumInt + "' Added.");
                break;
            case "AddB":
                //Check if there are any guests or rooms to add:
                if(this.model.guests.getGuestList().isEmpty() || this.model.rooms.getRoomMap().isEmpty()){
                    JOptionPane.showMessageDialog(view.BookingMenuPanel, "Need at Least one Room and one Guest to choose from.");
                    break;
                }
                
                //Get Room from user:
                Room room = null;
                boolean validRoom = false;
                while (!validRoom) {
                    room = (Room) JOptionPane.showInputDialog(view.BookingMenuPanel, "Select Room:", "Room Selection", JOptionPane.QUESTION_MESSAGE, null, this.model.rooms.getRoomMap().values().toArray(), "");
                    if (room == null) {
                        break; // Break loop if dialog was cancelled
                    }

                    if (!room.isAvailable()) { // Check if room is available.
                        JOptionPane.showMessageDialog(view.BookingMenuPanel, "That room is not available as it has already been booked.");
                        validRoom = false;
                    } else {
                        validRoom = true;
                    }
                }
                if (room == null) {
                    break; // Break case if dialog was cancelled
                }

                //Get Guest from user:
                Guest guest = null;
                try {
                    guest = (Guest) JOptionPane.showInputDialog(view.BookingMenuPanel, "Select Guest:", "Guest Selection", JOptionPane.QUESTION_MESSAGE, null, this.model.guests.getGuestList().toArray(), "");
                } catch (NullPointerException ex) {
                    guest = null;
                }
                if (guest == null) {
                    break; // Break case if dialog was cancelled
                }

                //Get Room from user:
                Object numDays = null;
                int numDaysInt = -1;
                boolean validNumDays = false;
                while (!validNumDays) {
                    numDays = (JOptionPane.showInputDialog(view.BookingMenuPanel, "Enter Duration of visit (days):", "0"));
                    if (numDays == null) {
                        break; // Break loop if dialog was cancelled.
                    }
                    try { // Check if input is an integer:
                        numDaysInt = Integer.valueOf(numDays.toString()); // Throws if input is not an integer.
                        // Check input is at least 0:
                        if (numDaysInt <= 0) {
                            JOptionPane.showMessageDialog(view.BookingMenuPanel, "Invaled Input: Must be more than 0. Try again.");
                            validNumDays = false;
                        } else {
                            validNumDays = true;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(view.BookingMenuPanel, "Invaled Input. Try again.");
                        validNumDays = false; // repeats loop.
                    }
                }
                if (numDays == null) {
                    break; // Break case if dialog was cancelled
                }

                //Enter details into a new Booking
                this.model.bookings.add(new Booking(guest, room, numDaysInt));
                this.model.rooms.updateAvailability(room, false);
                this.model.refreshUIBookingList();
                JOptionPane.showMessageDialog(view.BookingMenuPanel, "Booking Added.");
                break;
            case "RemoveG":
                //Check if there are any guests tor remove:
                if(this.model.guests.getGuestList().isEmpty()){
                    JOptionPane.showMessageDialog(view.GuestMenuPanel, "There are no Guests to remove.");
                    break; // Break case if there are no guests.
                }
                
                //Get Guest from user:
                Guest guestRemove = null;
                try {
                    guestRemove = (Guest) JOptionPane.showInputDialog(view.GuestMenuPanel, "Select Guest to remove:", "Guest Selection", JOptionPane.QUESTION_MESSAGE, null, this.model.guests.getGuestList().toArray(), "");
                } catch (NullPointerException ex) {
                    guestRemove = null;
                }
                if (guestRemove == null) {
                    break; // Break case if dialog was cancelled
                }

                //Remove selected Guest (and any bookings its connected to)
                this.model.guests.remove(guestRemove);
                this.model.removeConnectedBooking(guestRemove);
                JOptionPane.showMessageDialog(view.GuestMenuPanel, "Guest '" + guestRemove.getFullName() + "' Removed.");
                this.model.refreshUIGuestList();
                break;
            case "RemoveR":
                //Check if there are any rooms to remove:
                if(this.model.rooms.getRoomMap().isEmpty()){
                    JOptionPane.showMessageDialog(view.RoomMenuPanel, "There are no Rooms to remove.");
                    break; // Break case if there are no rooms.
                }
                
                //Get Room from user:
                Room roomRemove = null;
                try {
                    roomRemove = (Room) JOptionPane.showInputDialog(view.RoomMenuPanel, "Select Room to remove:", "Room Selection", JOptionPane.QUESTION_MESSAGE, null, this.model.rooms.getRoomMap().values().toArray(), "");
                } catch (NullPointerException ex) {
                    roomRemove = null;
                }
                if (roomRemove == null) {
                    break; // Break case if dialog was cancelled
                }

                //Remove selected Room (and any bookings its connected to)
                this.model.rooms.remove(roomRemove);
                this.model.removeConnectedBooking(roomRemove);
                JOptionPane.showMessageDialog(view.RoomMenuPanel, "Room '#" + roomRemove.getRoomNum() + "' Removed.");
                this.model.refreshUIRoomList();
                break;
            case "RemoveB":
                //Check if there are any guests or rooms to add:
                if(this.model.bookings.getBookingList().isEmpty()){
                    JOptionPane.showMessageDialog(view.BookingMenuPanel, "There are no Bookings to remove");
                    break; // Break case if there are no bookings.
                }
                
                //Get Booking from user:
                Booking bookingRemove = null;
                try {
                    bookingRemove = (Booking) JOptionPane.showInputDialog(view.BookingMenuPanel, "Select Booking to remove:", "Booking Selection", JOptionPane.QUESTION_MESSAGE, null, this.model.bookings.getBookingList().toArray(), "");
                } catch (NullPointerException ex) {
                    bookingRemove = null;
                }
                if (bookingRemove == null) {
                    break; // Break case if dialog was cancelled
                }

                //Remove selected Room (and any bookings its connected to)
                this.model.bookings.remove(bookingRemove);
                JOptionPane.showMessageDialog(view.BookingMenuPanel, "Booking Removed.");
                this.model.refreshUIBookingList();
                break;
            default:
                break;
        }

    }
}
