package HotelBookingSystem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author 2114050
 */
public class Rooms {

    private HashMap<Integer, Room> roomMap;

    private final DBManager dbManager;

    // Class that holds most controls for the Room HaskMap:
    public Rooms(DBManager dbManager) {
        this.roomMap = new HashMap<>();
        this.dbManager = dbManager;
        this.initializeRoomsTable(); // Creates ROOMS table in HotelDB if not already created
        this.getRoomsFromDB();
    }

    // Getter:
    public HashMap<Integer, Room> getRoomMap() {
        return roomMap;
    }

    // Adds a new Room object to the HashMap then updates DB table:
    // Returns added Room object;
    public Room add(Room room) {
        roomMap.put(room.getRoomNum(), room);
        dbManager.updateDB("INSERT INTO ROOMS VALUES (" + room.getRoomNum() + ", " + room.isAvailable() + ", '" + room.getTypeName() + "')");
        return room;
    }

    // Removes a specified Room object from the HashMap:
    public void remove(Room room) {
        roomMap.remove(room.getRoomNum());
        dbManager.updateDB("DELETE FROM ROOMS WHERE ROOMNUM=" + room.getRoomNum());
    }
    
    public void updateAvailability(Room room, boolean available) {
        room.changeAvailability(available);
        dbManager.updateDB("UPDATE ROOMS SET AVAILABLE = " + available + " WHERE ROOMNUM = " + room.getRoomNum());
    }

    public void getRoomsFromDB() {
        Room room;
        ResultSet rs = this.dbManager.queryDB("SELECT * FROM ROOMS");
        try {
            while (rs.next()) {
                if (rs.getString(3).equalsIgnoreCase("deluxe")) {
                    room = new RoomDeluxe(rs.getInt(1));
                } else {
                    room = new RoomStandard(rs.getInt(1));
                }
                room.changeAvailability(rs.getBoolean(2));
                roomMap.put(room.getRoomNum(), room);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void initializeRoomsTable() {
        if (!this.dbManager.checkTableExists("ROOMS")) {
            this.dbManager.updateDB("CREATE  TABLE ROOMS  (ROOMNUM  INTEGER, AVAILABLE CHAR(6), TYPE   VARCHAR(30))");
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (roomMap.isEmpty()) {
            sb.append("There are no rooms.");
        }
        else {
            for (Room r : roomMap.values()) {
                sb.append(r).append("\n");
            }
        }
        
        return sb.toString();
    }

}
