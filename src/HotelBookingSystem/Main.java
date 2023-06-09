package HotelBookingSystem;

/**
 *
 * @author 21145050
 */
public class Main {
    public static void main(String[] args) {

        View myView = new View();
        Model myModel = new Model();
        Controller myController = new Controller(myView, myModel);
        myModel.addObserver(myView);
       
    }
            
}
