
package org.example.powerpark.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.powerpark.model.Invoice;
import org.example.powerpark.model.User;

import javax.swing.*;

public class BookingDetailController {

    @FXML
    private TextField b1;

    @FXML
    private TextField b2;

    @FXML
    private TextField invoice;

    @FXML
    private Button payLater;

    @FXML
    private Button payNowButton;

    private int bookingId;
    private int calculatedAmount;

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    @FXML
    void payInvoice(ActionEvent event)throws Exception {
        if (b1.getText().isEmpty() || b2.getText().isEmpty()) {
            showAlert("Please enter both current and desired battery levels.");
        } else{
            JOptionPane.showMessageDialog(null, "Invoice Paid Successfully.");
            Invoice i=new Invoice();
            i.addInvoice(bookingId,calculatedAmount,"Paid");
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/User_Dashboard.fxml"));
            Stage window = (Stage) payNowButton.getScene().getWindow();
            window.setScene(new Scene(root));
        }
    }

    @FXML
    void payLater(ActionEvent event)throws Exception {
        if (b1.getText().isEmpty() || b2.getText().isEmpty()) {
            showAlert("Please enter both current and desired battery levels.");
        }else {
            Invoice i = new Invoice();
            i.addInvoice(bookingId, calculatedAmount, "Unpaid");
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/User_Dashboard.fxml"));
            Stage window = (Stage) payLater.getScene().getWindow();
            window.setScene(new Scene(root));
        }
    }
    @FXML
    public void initialize() {
        b1.textProperty().addListener((observable, oldValue, newValue) -> calculateAmount());
        b2.textProperty().addListener((observable, oldValue, newValue) -> calculateAmount());
    }

    @FXML
    void calculateAmount() {
        String currentText = b1.getText().trim();
        String desiredText = b2.getText().trim();

        if (currentText.isEmpty() || desiredText.isEmpty()) {
            invoice.setText("Rs 0");
            calculatedAmount = 0;
            return;
        }
        try {
            int current = Integer.parseInt(b1.getText());
            int desired = Integer.parseInt(b2.getText());
            if (desired <= current || current < 0 || desired > 100) {
                showAlert("Invalid battery levels.");
                calculatedAmount = -1;
                return;
            }
            int unitsRequired = desired - current;
            int ratePerUnit = 65;
            int amount = unitsRequired * ratePerUnit;

            invoice.setText("Rs " + amount);
            calculatedAmount = amount;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
