package org.example.powerpark.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.powerpark.model.*;
import javax.swing.*;
import java.time.LocalDate;

public class BookSlotController {

    @FXML
    private Button BackBtn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button BookBtn;

    @FXML
    private ComboBox<Slot> ChooseSlot;

    @FXML
    private ComboBox<Station> ChooseStation;

    @FXML
    public void initialize() {
        loadStations();

        ChooseStation.setOnAction(event -> {
            Station selectedStation = ChooseStation.getValue();
            if (selectedStation != null) {
                loadSlotsForStation(selectedStation.getStation_id());
            }
        });

        ChooseSlot.setOnMouseClicked(event -> {
            if (ChooseStation.getValue() == null) {
                showAlert("Please select a station first.");
            }
        });
    }

    private void loadStations() {
        Station s = new Station();
        ObservableList<Station> stations = s.getStations();
        ChooseStation.getItems().addAll(stations);
    }

    private void loadSlotsForStation(int stationId) {
        Slot slotHelper = new Slot();
        ObservableList<Slot> slots = slotHelper.getSlots(stationId);
        ChooseSlot.getItems().clear();
        ChooseSlot.getItems().addAll(slots);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void bookSlot(ActionEvent event) throws Exception {
        Station selectedStation = ChooseStation.getValue();
        Slot selectedSlot = ChooseSlot.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedStation == null || selectedSlot == null || selectedDate == null) {
            showAlert("Please select station, slot, and date.");
            return;
        }

        try {
            java.sql.Date bookingDate = java.sql.Date.valueOf(selectedDate);
            int stationId = selectedStation.getStation_id();
            int slotId = selectedSlot.getSlotId();
            int userId = Session.getUserId();

            Booking b = new Booking(userId, stationId, slotId, bookingDate);
            boolean isAvailable = b.isSlotAvailable(stationId, slotId, bookingDate);

            if (!isAvailable) {
                showAlert("This slot is already booked on this date.");
                return;
            }

            User u = new User();
            int bookingId = u.bookSlot(userId, stationId, slotId, bookingDate);

            if (bookingId != -1) {
                JOptionPane.showMessageDialog(null, "Slot booked successfully.");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/powerpark/BookingDetail.fxml"));
                Parent root = loader.load();

                BookingDetailController controller = loader.getController();
                controller.setBookingId(bookingId);

                Stage window = (Stage) BackBtn.getScene().getWindow();
                window.setScene(new Scene(root));
            } else {
                JOptionPane.showMessageDialog(null, "Error while booking slot, try again.");
            }

        } catch (Exception e) {
            showAlert("Error during booking: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void previousScreen(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/User_Dashboard.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
