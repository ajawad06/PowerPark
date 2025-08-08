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

public class ViewBookingsController {

    @FXML
    private Button BackBtn;

    @FXML
    private TableColumn<viewBookingsDisplay, Date> BookingDateCol;

    @FXML
    private TableColumn<viewBookingsDisplay, Integer> BookingIdCOl;

    @FXML
    private TableView<viewBookingsDisplay> Bookingtable;

    @FXML
    private Button CancelBookingBtn;

    @FXML
    private TableColumn<viewBookingsDisplay, String> StationNameCol;

    @FXML
    private Label TopStation;

    @FXML
    private Label TopUser;

    @FXML
    private TableColumn<viewBookingsDisplay, LocalTime> endTimeCol;

    @FXML
    private TextField searchBarBooking;

    @FXML
    private TableColumn<viewBookingsDisplay, LocalTime> startTimeCol;

    @FXML
    private TableColumn<viewBookingsDisplay, String> userNameCol;

    public void initialize(){
        User u=new User();
        Station s=new Station();
        Admin a=new Admin();
        TopUser.setText(String.valueOf(u.TopUser()));
        TopStation.setText(String.valueOf(s.TopStation()));

        BookingIdCOl.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBookingID()));
        userNameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUsername()));
        StationNameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStationname()));
        startTimeCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getStarttime()));
        endTimeCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEndtime()));
        BookingDateCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBooking_date()));
        Bookingtable.setItems(a.viewBookings());
    }
    @FXML
    void cancelBooking(ActionEvent event) {
        Admin a = new Admin();
        String name = searchBarBooking.getText();

        if (name.isEmpty()) {
            Bookingtable.setItems(a.viewBookings());
            JOptionPane.showMessageDialog(null, "No booking selected for cancellation.");
        } else {
            try {
                int id = Integer.parseInt(name);
                viewBookingsDisplay foundBooking = a.searchBooking(id);

                if (foundBooking != null) {
                    Bookingtable.setItems(FXCollections.observableArrayList(foundBooking));

                    int option = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to cancel this booking?",
                            "Confirm Cancellation",
                            JOptionPane.YES_NO_OPTION);

                    if (option == JOptionPane.YES_OPTION) {
                        if (a.cancelBooking(id)) {
                            JOptionPane.showMessageDialog(null, "Booking cancelled successfully.");
                            Bookingtable.setItems(a.viewBookings()); // Refresh table
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to cancel the booking.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Cancellation aborted.");
                    }

                } else {
                    Bookingtable.setItems(FXCollections.observableArrayList());
                    JOptionPane.showMessageDialog(null, "Booking with such ID does not exist.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid booking ID.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void previousScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/Admin_Dashboard.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void typeSearchBooking(ActionEvent event)throws Exception {
        Admin a=new Admin();
        String reqBookingText = searchBarBooking.getText(); // Get the text from the search bar

        if (reqBookingText.isEmpty()) {
            Bookingtable.setItems(a.viewBookings());
        } else {
            try {
                int reqBooking = Integer.parseInt(reqBookingText);
                viewBookingsDisplay reqBook=a.searchBooking(reqBooking);
                if (reqBook != null) {
                    Bookingtable.setItems(FXCollections.observableArrayList(reqBook));
                } else {
                    Bookingtable.setItems(FXCollections.observableArrayList());
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }


    }
}
