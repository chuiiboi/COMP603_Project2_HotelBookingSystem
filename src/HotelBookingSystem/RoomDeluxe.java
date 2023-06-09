package HotelBookingSystem;

/**
 *
 * @author 21145050
 */
public class RoomDeluxe extends Room {

    private final String typeName;
    private final int capacity;
    private final double costPerNight;

    // Class that extends Room to specify capacity and cost details
    public RoomDeluxe(Integer roomNum) {
        super(roomNum);
        this.typeName = "Deluxe";
        this.capacity = 4;
        this.costPerNight = 278.99;
    }
      
    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public double getCostPerNight() {
        return costPerNight;
    }
}
