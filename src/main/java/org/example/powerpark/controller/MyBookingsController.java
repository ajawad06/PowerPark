package org.example.powerpark.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.powerpark.model.*;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;

public class MyBookingsController {

    @FXML
    private Button BackBtn;

    @FXML
    private TableColumn<viewBookingsDisplay, Date> BookingDateCol;

    @FXML
    private TableColumn<viewBookingsDisplay,Integer> BookingIdCOl;

    @FXML
    private Button CancelBookingBtn;

    @FXML
    private TableView<viewBookingsDisplay> CurrBookingtable;

    @FXML
    private Label MyTopStation;

    @FXML
    private TableColumn<viewBookingsDisplay,String> StationNameCol;

    @FXML
    private Label TopStation;

    @FXML
    private TableColumn<viewBookingsDisplay, LocalTime> endTimeCol;

    @FXML
    private TextField searchBarBooking;

    @FXML
    private TableColumn<viewBookingsDisplay,LocalTime> startTimeCol;

    @FXML
    void cancelBooking(ActionEvent event) {
        User u = new User();
        String name = searchBarBooking.getText();

        if (name.isEmpty()) {
            CurrBookingtable.setItems(u.viewBookings(Session.getUserId()));
            JOptionPane.showMessageDialog(null, "No booking selected for cancellation.");
        } else {
            try {
                int id = Integer.parseInt(name);
                viewBookingsDisplay foundBooking = u.searchBooking(id);

                if (foundBooking != null) {
                    CurrBookingtable.setItems(FXCollections.observableArrayList(foundBooking));

                    int option = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to cancel this booking?",
                            "Confirm Cancellation",
                            JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        if (u.cancelBooking(id)) {
                            JOptionPane.showMessageDialog(null, "Booking cancelled successfully.");
                            CurrBookingtable.setItems(u.viewBookings(Session.getUserId())); // Refresh the table
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to cancel the booking.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cancellation aborted.");
                    }

                } else {
                    CurrBookingtable.setItems(FXCollections.observableArrayList());
                    JOptionPane.showMessageDialog(null, "Booking with such ID does not exist.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid booking ID.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void initialize(){
        User u=new User();
        Station s=new Station();
        Admin a=new Admin();
        MyTopStation.setText(String.valueOf(s.TopStation(Session.getUsername())));
        BookingIdCOl.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBookingID()));
        StationNameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStationname()));
        startTimeCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStarttime()));
        endTimeCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEndtime()));
        BookingDateCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBooking_date()));
        CurrBookingtable.setItems(u.viewBookings(Session.getUserId()));
    }
    @FXML
    void previousScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/User_Dashboard.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    @FXML
    void typeSearchBooking(ActionEvent event) {
        User u=new User();
        String reqBookingText = searchBarBooking.getText(); // Get the text from the search bar

        if (reqBookingText.isEmpty()) {
            CurrBookingtable.setItems(u.viewBookings(Session.getUserId()));
        } else {
            try {
                int reqBooking = Integer.parseInt(reqBookingText);
                viewBookingsDisplay reqBook=u.searchBooking(reqBooking);
                if (reqBook != null) {
                    CurrBookingtable.setItems(FXCollections.observableArrayList(reqBook));
                } else {
                    CurrBookingtable.setItems(FXCollections.observableArrayList());
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

    }
}

