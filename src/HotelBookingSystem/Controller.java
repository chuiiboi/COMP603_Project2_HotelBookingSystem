/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
            case "Guests":
                this.view.MainMenuPanel.setVisible(false);
                this.view.GuestMenuPanel.setVisible(true);
                break;
            case "Rooms":
                this.view.MainMenuPanel.setVisible(false);
                this.view.RoomMenuPanel.setVisible(true);
                break;
            case "Bookings":
                this.view.MainMenuPanel.setVisible(false);
                this.view.BookingMenuPanel.setVisible(true);
                break;
            default:
                break;
        }

    }
}
