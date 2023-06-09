package HotelBookingSystem;

/**
 *
 * @author 21145050
 */
public class RoomStandard extends Room {

    private final String typeName;
    private final int capacity;
    private final double costPerNight;

    // Class that extends Room to specify capacity and cost details
    public RoomStandard(Integer roomNum) {
        super(roomNum);
        this.typeName = "Standard";
        this.capacity = 2;
        this.costPerNight = 163.50;
    }

    // Getter: 
    @Override
    public String getTypeName() {
        return typeName;
    }

    // Getter: 
    @Override
    public int getCapacity() {
        return capacity;
    }

    // Getter: 
    @Override
    public double getCostPerNight() {
        return costPerNight;
    }

}
