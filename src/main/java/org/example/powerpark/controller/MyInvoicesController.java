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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.powerpark.model.*;


import javax.swing.*;
import java.io.IOException;

public class MyInvoicesController {
    @FXML
    private Button BackBtn;
    @FXML
    private TableColumn<viewInvoiceDisplay,Integer> AmountCol;
    @FXML
    private TableColumn<viewInvoiceDisplay,Integer> BookingIDCol;

    @FXML
    private TableColumn<viewInvoiceDisplay,Integer> InvoiceIdCol;

    @FXML
    public void initialize(){
        User u=new User();
        InvoiceIdCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getInvoice_id()));
        StationNameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStation()));
        BookingIDCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBooking_id()));
        AmountCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAmount()));
        StatusCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStatus()));
        Invoicetable.setItems(u.viewInvoices(Session.getUserId()));
    }
    @FXML
    private TableView<viewInvoiceDisplay> Invoicetable;

    @FXML
    private TableColumn<viewInvoiceDisplay, String> StationNameCol;

    @FXML
    private TableColumn<viewInvoiceDisplay,String> StatusCol;


    @FXML
    private Button PayInvoiceBtn;

    @FXML
    private TextField searchBarInvoice;

    @FXML
    void payInvoice(ActionEvent event) {
        User u = new User();
        String invoice = searchBarInvoice.getText();

        if (invoice.isEmpty()) {
            Invoicetable.setItems(u.viewInvoices(Session.getUserId()));
            JOptionPane.showMessageDialog(null, "No invoice selected for payment.");
        } else {
            try {
                int id = Integer.parseInt(invoice);
                viewInvoiceDisplay foundInv = u.searchInvoice(id);

                if (foundInv != null) {
                    Invoicetable.setItems(FXCollections.observableArrayList(foundInv));
                    int confirmation = JOptionPane.showConfirmDialog(null, "Confirm payment?", "Payment Confirmation", JOptionPane.YES_NO_OPTION);

                    if (confirmation == JOptionPane.YES_OPTION) {
                        boolean paymentSuccess = u.payInvoice(id);

                        if (paymentSuccess) {
                            JOptionPane.showMessageDialog(null, "Invoice paid successfully.");
                            // Optionally refresh the table to reflect status change
                            viewInvoiceDisplay updatedInvoice = u.searchInvoice(id);
                            Invoicetable.setItems(FXCollections.observableArrayList(updatedInvoice));
                        } else {
                            JOptionPane.showMessageDialog(null, "Invoice is already paid.");
                        }
                    }
                } else {
                    Invoicetable.setItems(FXCollections.observableArrayList());
                    JOptionPane.showMessageDialog(null, "Invoice with such ID does not exist.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid invoice ID.");
                e.printStackTrace();
            }
        }
    }


    @FXML
    void previousScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/User_Dashboard.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    @FXML
    void typeSearchInvoice(ActionEvent event) {
        User u=new User();
        String reqInvoiceText = searchBarInvoice.getText();
        if (reqInvoiceText.isEmpty()) {
            Invoicetable.setItems(u.viewInvoices(Session.getUserId()));
        } else {
            try {
                int reqInvoice = Integer.parseInt(reqInvoiceText);
                viewInvoiceDisplay Invoice=u.searchInvoice(reqInvoice);
                if (Invoice != null) {
                    Invoicetable.setItems(FXCollections.observableArrayList(Invoice));
                } else {
                    Invoicetable.setItems(FXCollections.observableArrayList());
                }
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
