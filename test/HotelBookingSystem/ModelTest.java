/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HotelBookingSystem;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author GGPC
 */
public class ModelTest {

    public ModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
    * Test of add and remove methods, of class Guests.
     */
    @Test
    public void testGuestsAddAndRemove() {
        System.out.println("test Guests class: add() and Remove()");
        boolean expResult;
        boolean result;
        Guest guestInstance = new Guest("testName", "testEmail", "testPhone");
        Model modelInstance = new Model();
        
        //Test add
        modelInstance.guests.add(guestInstance);
        expResult = true;
        result = modelInstance.guests.getGuestList().contains(guestInstance);
        assertEquals(expResult, result);
        
        //Test remove
        modelInstance.guests.remove(guestInstance);
        expResult = false;
        result = modelInstance.guests.getGuestList().contains(guestInstance);
        assertEquals(expResult, result);
    }
    
    /**
    * Test of add and remove methods, of class Bookings.
     */
    @Test
    public void testBookingsAddAndRemove() {
        System.out.println("test Bookings class: add() and Remove()");
        boolean expResult;
        boolean result;
        Guest guestInstance = new Guest("testName", "testEmail", "testPhone");
        Room roomInstance = new RoomStandard(-1);
        Booking bookingInstance = new Booking(guestInstance, roomInstance, 1);
        Model modelInstance = new Model();
        
        //Test add
        modelInstance.bookings.add(bookingInstance);
        expResult = true;
        result = modelInstance.bookings.getBookingList().contains(bookingInstance);
        assertEquals(expResult, result);
        
        //Test remove
        modelInstance.bookings.remove(bookingInstance);
        expResult = false;
        result = modelInstance.bookings.getBookingList().contains(bookingInstance);
        assertEquals(expResult, result);
    }
    
     /**
     * Test of add and remove methods, of class Rooms.
     */
    @Test
    public void testRoomsAddAndRemove() {
        System.out.println("test Rooms class: add() and Remove()");
        boolean expResult;
        boolean result;
        Room roomStandardInstance = new RoomStandard(-1);
        Room roomDeluxeInstance = new RoomDeluxe(-2);
        Model modelInstance = new Model();
        
        //Test add for a standard room
        modelInstance.rooms.add(roomStandardInstance);
        expResult = true;
        result = modelInstance.rooms.getRoomMap().values().contains(roomStandardInstance);
        assertEquals(expResult, result);
        
        //Test add for a deluxe room
        modelInstance.rooms.add(roomDeluxeInstance);
        expResult = true;
        result = modelInstance.rooms.getRoomMap().values().contains(roomDeluxeInstance);
        assertEquals(expResult, result);
        
        //Test remove for a standard room
        modelInstance.rooms.remove(roomStandardInstance);
        expResult = false;
        result = modelInstance.rooms.getRoomMap().values().contains(roomStandardInstance);
        assertEquals(expResult, result);
        
        //Test remvoe for a deluxe room
        modelInstance.rooms.remove(roomDeluxeInstance);
        expResult = false;
        result = modelInstance.rooms.getRoomMap().values().contains(roomDeluxeInstance);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of guestNameExists method, of class Model.
     */
    @Test
    public void testGuestNameExists() {
        System.out.println("test Model class: guestNameExists()");
        boolean expResult;
        boolean result;
        String string = "testName";
        Guest guestInstance = new Guest("testName", "e", "p");
        Model modelInstance = new Model();
        
        //Test if name does exist
        modelInstance.guests.add(guestInstance);
        expResult = true;
        result = modelInstance.guestNameExists(string);
        assertEquals(expResult, result);
        
        //Test if name does not exist
        modelInstance.guests.remove(guestInstance);
        expResult = false;
        result = modelInstance.guestNameExists(string);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeConnectedBooking method, of class Model.
     */
    @Test
    public void testRemoveConnectedBooking_Guest() {
        System.out.println("test Model class: removeConnectedBooking_Guest");
        //Setup
        boolean expResult;
        boolean result;
        Guest guestInstance = new Guest("testName", "testEmail", "testPhone");
        Room roomInstance = new RoomStandard(-1);
        Booking bookingInstance = new Booking(guestInstance, roomInstance, 1);
        Model modelInstance = new Model();
        
        modelInstance.guests.add(guestInstance);
        modelInstance.rooms.add(roomInstance);
        modelInstance.bookings.add(bookingInstance);
        
        modelInstance.guests.remove(guestInstance);

        //Test
        modelInstance.removeConnectedBooking(guestInstance); // Should remove Booking that had Guest in this case, bookingInstance)
        expResult = false;
        result = modelInstance.bookings.getBookingList().contains(bookingInstance); //check that bookingInstance has been removed.
        assertEquals(expResult, result);
        
        modelInstance.rooms.remove(roomInstance); //Delete test room from database that was used to create bookingInstance
    }

    /**
     * Test of removeConnectedBooking method, of class Model.
     */
    @Test
    public void testRemoveConnectedBooking_Room() {
        System.out.println("test Model class: removeConnectedBooking_Room");
        boolean expResult;
        boolean result;
        Guest guestInstance = new Guest("testName", "testEmail", "testPhone");
        Room roomInstance = new RoomStandard(-1);
        Booking bookingInstance = new Booking(guestInstance, roomInstance, 1);
        Model modelInstance = new Model();
        
        modelInstance.guests.add(guestInstance);
        modelInstance.rooms.add(roomInstance);
        modelInstance.bookings.add(bookingInstance);
        
        modelInstance.rooms.remove(roomInstance);

        //Test
        modelInstance.removeConnectedBooking(roomInstance); // Should remove Booking that had Room, in this case, bookingInstance)
        expResult = false;
        result = modelInstance.bookings.getBookingList().contains(bookingInstance); //check that bookingInstance has been removed.
        assertEquals(expResult, result);
        
        modelInstance.guests.remove(guestInstance); //Delete test guest from database that was used to create bookingInstance
    }
    
}
