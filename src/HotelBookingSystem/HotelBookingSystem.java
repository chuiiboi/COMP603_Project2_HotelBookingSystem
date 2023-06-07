// gittest

package HotelBookingSystem;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author 2114505
 */
public class HotelBookingSystem {

    public Guests guests;
    public Rooms rooms;
    public Bookings bookings;

    ;

    public HotelBookingSystem() {
        this.guests = new Guests("./resources/HBS_guests.txt");
        this.rooms = new Rooms("./resources/HBS_rooms.txt");
        this.bookings = new Bookings("./resources/HBS_bookings.txt");
    }

    public static void main(String[] args) {
        HotelBookingSystem hbs = new HotelBookingSystem(); // Initializes the HotelBookingSystem class.
        Scanner scan = new Scanner(System.in);

        System.out.println("    HOTEL BOOKING SYSTEM");
        int option;
        while (true) {  // Loop can only end if user chooses to exit.
            // Displays the Main Menu:
            System.out.println();
            System.out.println("MAIN MENU - Select an option.");
            System.out.println("1. Guests Menu");
            System.out.println("2. Rooms Menu");
            System.out.println("3. Bookings Menu");
            System.out.println("4. Exit");

            try {
                option = scan.nextInt();
                // Decide what to do based on users choice:
                switch (option) {
                    case 1: // Opens the guest menu.
                        hbs.guestMenu();    
                        break;
                    case 2: // Opens the room menu.
                        hbs.roomMenu();     
                        break;
                    case 3: // Opens the booking menu.
                        hbs.bookingMenu();  
                        break;
                    case 4: // Updates all data to files, ends program.
                        System.out.println("End Of Program. All data has been saved into respective files within resources folder.");
                        hbs.bookings.updateBookingsFile();
                        hbs.rooms.updateRoomsFile();
                        hbs.guests.updateGuestsFile();
                        System.exit(0);
                    default:// Assumes user made wrong input, notifies them, redisplays options.
                        System.out.println("Invalid input. Enter a number from 1 to 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");  // Checks if user does not enter an integer, notifies them, redisplays options.
                scan.next();
            }
        }
    }

    public void guestMenu() {
        Scanner scan = new Scanner(System.in);

        int option;
        while (true) {
            // Displays the Guest Menu:
            System.out.println();
            System.out.println("GUESTS MENU - Select an option:");
            System.out.println("1. Add Guest");
            System.out.println("2. Remove Guest");
            System.out.println("3. View Guests");
            System.out.println("4. Main Menu");

            try {
                option = scan.nextInt();
                // Decide what to do based on users choice:
                switch (option) {
                    case 1: // Calls addGuest() to initiate the process of creating new Guest.
                        addGuest();
                        break;
                    case 2: // Calls removeGuest() to initiate the process of deleting a specified Guest.
                        removeGuest();
                        break;
                    case 3: // Calls viewGuests() to initiate the process of displaying the Guests to the user.
                        viewGuests();
                        break;
                    case 4: // Returns the user to the Main Menu.
                        return;
                    default: // Assumes user made wrong input, notifies them, redisplays options.
                        System.out.println("Invalid input. Enter a number from 1 to 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");  // Checks if user does not enter an integer, notifies them, redisplays options.
                scan.next();
            }
        }
    }

    public void roomMenu() {
        Scanner scan = new Scanner(System.in);

        int option;
        while (true) {
            // Displays the Room Menu:
            System.out.println();
            System.out.println("ROOMS MENU - Select an option:");
            System.out.println("1. Add Room");
            System.out.println("2. Remove Room");
            System.out.println("3. View Rooms");
            System.out.println("4. Main Menu");

            try {
                option = scan.nextInt();
                // Decide what to do based on users choice:
                switch (option) {
                    case 1: // Calls addRoom() to initiate the process of creating new Room.
                        addRoom();
                        break;
                    case 2: // Calls removeRoom() to initiate the process of deleting a specified Room.
                        removeRoom();
                        break;
                    case 3: // Calls viewRooms() to initiate the process of displaying the Rooms to the user.
                        viewRooms();
                        break;
                    case 4: // Returns the user to the Main Menu.
                        return;
                    default: // Assumes user made wrong input, notifies them, redisplays options.
                        System.out.println("Invalid input. Enter a number from 1 to 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");  // Checks if user does not enter an integer, notifies them, redisplays options.
                scan.next();
            }
        }
    }

    public void bookingMenu() {
        Scanner scan = new Scanner(System.in);

        int optionMain;
        while (true) {
            // Displays the Room Menu:
            System.out.println();
            System.out.println("BOOKINGS MENU - Select an option:");
            System.out.println("1. Add Booking");
            System.out.println("2. Remove Booking");
            System.out.println("3. View Bookings");
            System.out.println("4. Main Menu");

            try {
                optionMain = scan.nextInt();
                // Decide what to do based on users choice:
                switch (optionMain) {
                    case 1: // Calls makeBooking() to initiate the process of creating new Booking.
                        makeBooking();
                        break;
                    case 2: // Calls cancelBooking() to initiate the process of deleting a specified Booking.
                        cancelBooking();
                        break;
                    case 3: // Calls viewBookings() to initiate the process of displaying the Bookings to the user.
                        viewBookings();
                        break;
                    case 4: // Returns the user to the Main Menu.
                        return;
                    default: // Assumes user made wrong input, notifies them, redisplays options.
                        System.out.println("Invalid input. Enter a number from 1 to 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");  // Checks if user does not enter an integer, notifies them, redisplays options.
                scan.next();
            }
        }
    }

    // creates new Guest and collect info for new room:
    // gets room number from user.
    // finds out whether user wants to make the new room standard or deluxe.
    // creates the appropriate subclass according to the users input.
    // calls add() from Rooms class to add the new room to the rooms map.
    public void addGuest() {
        Scanner scanG = new Scanner(System.in);
        System.out.println("Enter guest Information Below.");
        System.out.println("Full Name: ");
        String fullName = scanG.nextLine();
        System.out.println("Email Address: ");
        String email = scanG.nextLine();
        System.out.println("Phone Number: ");
        String phoneNo = scanG.nextLine();

        Guest newGuest = new Guest(fullName, email, phoneNo);
        guests.add(newGuest);
        System.out.println("Guest " + newGuest.getFullName() + " has been added.");
    }

    // removes a Guest from the guest list:
    // calls selectGUest() from Guests class to allow user to select which guest to remove.
    // calls remove() from Guests class to remove the chosen guest.
    public void removeGuest() {
        Guest tempGuest = guests.selectGuest();
        if (tempGuest != null) {
            guests.remove(tempGuest);
        }
    }

    // calls displayGuests() from Guests class.
    public void viewGuests() {
        guests.displayGuests();
    }

    // creates new Room and collect info for new room:
    // gets room number from user.
    // finds out whether user wants to make the new room standard or deluxe.
    // creates the appropriate subclass according to the users input.
    // calls add() from Rooms class to add the new room to the rooms map.
    public void addRoom() {
        Scanner scanR = new Scanner(System.in);

        Room newRoom;
        Integer roomNum = 0;
        String roomType;

        System.out.println("\nEnter Room Information Below.");

        while (roomNum < 1) {
            try {
                System.out.println("Room Number: ");
                roomNum = scanR.nextInt();
                if (roomNum < 1) {
                    System.out.println("Invalid input. Enter a number that is larger than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");
                roomNum = 0;
            }
        }

        scanR.nextLine();
        do {
            System.out.println("Room Type (Deluxe or Standard): ");
            roomType = scanR.nextLine();
            if ("standard".equals(roomType.toLowerCase()) || "deluxe".equals(roomType.toLowerCase())) {
                break;
            } else {
                System.out.println("Invalid input. please enter 'Deluxe' or 'Standard'");
            }
        } while (true);

        if ("standard".equals(roomType.toLowerCase())) {
            newRoom = new RoomStandard(roomNum);
        } else {
            newRoom = new RoomDeluxe(roomNum);
        }
        rooms.add(newRoom);
        System.out.println("Room " + newRoom.getRoomNum() + " has been added.");
    }

    // removes a Room from the rooms map:
    // calls selectRoom() from Rooms class to allow user to select which room to remove.
    // calls remove() from Rooms class to remove the chosen room.
    public void removeRoom() {
        Room tempRoom = rooms.selectRoom();
        if (tempRoom != null) {
            rooms.remove(tempRoom);
        }
    }

    // calls displayRooms() from Rooms class which displays all rooms to the user.
    public void viewRooms() {
        rooms.displayRooms();
    }

    // makes new Booking and collects info for new booking:
    // checks to make sure there is at least 1 guest and 1 room to make a booking for.
    // calls selectGuest() from Guests class to allow user to choose which guest the booking will be for.
    // calls selectRoom() from Rooms class to allow user to choose which room the booking will be for.
    // checks whether the selected room is available or not.
    // asks user for durtaion of stay.
    // calls add() from Bookings class to add the new booking to the bookings list.
    // updates the rooms availability to false and saves to HBS_rooms.txt file.
    public void makeBooking() {
        Scanner scanB = new Scanner(System.in);

        if (guests.getGuestList().isEmpty() || rooms.getRoomMap().isEmpty()) {
            System.out.println("Cannot create new booking. Need at least 1 Guest and 1 room.");
            return;
        }

        System.out.println("\nMaking a new Booking:");

        Guest guest = guests.selectGuest();
        Room room;
        do {
            room = rooms.selectRoom();
            if (room == null) {
                return;
            }
            if (!room.isAvailable()) {
                System.out.println("Room is not available. Please select another.");
            }
        } while (!room.isAvailable());

        int days = 0;

        while (days < 1) {
            try {
                System.out.println("Duration of stay (days): ");
                days = scanB.nextInt();
                if (days < 1) {
                    System.out.println("Invalid input. Number can't be less than 1.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter a number.");
                days = 0;
            }
        }

        Booking newBooking = new Booking(guest, room, days);

        bookings.add(newBooking);
        rooms.getRoomMap().get(room.getRoomNum()).changeAvailability(false);
        rooms.updateRoomsFile();
        System.out.println("Booking has been added.");
    }

    // removes a Booking from the bookings list:
    // calls selectBoooking() from Bookings class to allow user to select which booking to remove.
    // calls remove() from Bookings class to remove the chosen booking.
    // updates the availability of the booked room to true and saves to HBS_rooms.txt file.
    public void cancelBooking() {
        Booking tempBooking = bookings.selectBooking();
        if (tempBooking != null) {
            bookings.remove(tempBooking);
            tempBooking.getRoom().changeAvailability(true);
            rooms.updateRoomsFile();
        }
    }
    
    // calls displayBookings() from bookings class which displays all bookings to the user.
    public void viewBookings() {
        bookings.displayBookings();
    }
}
