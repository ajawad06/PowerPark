package org.example.powerpark.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.powerpark.model.*;

import java.io.IOException;

public class ViewInvoicesController {

    @FXML
    private TableColumn<viewInvoiceDisplay, Integer> AmountCol;

    @FXML
    private Button BackBtn;

    @FXML
    private TableColumn<viewInvoiceDisplay, Integer> BookingIDCol;

    @FXML
    private TableColumn<viewInvoiceDisplay, Integer> InvoiceIdCol;

    @FXML
    private TableView<viewInvoiceDisplay> Invoicetable;

    @FXML
    private TableColumn<viewInvoiceDisplay, String> StationNameCol;

    @FXML
    private TableColumn<viewInvoiceDisplay,String > StatusCol;

    @FXML
    private Label totalRevenue;

    @FXML
    private TableColumn<viewInvoiceDisplay,String> userNameCol;

    @FXML
    public void initialize(){
        Admin a=new Admin();
        Invoice i=new Invoice();
        totalRevenue.setText(String.valueOf(i.TotalRevenue()));
        InvoiceIdCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getInvoice_id()));
        userNameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUsername()));
        StationNameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStation()));
        BookingIDCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBooking_id()));
        AmountCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAmount()));
        StatusCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStatus()));
        Invoicetable.setItems(a.viewInvoices());

    }
    @FXML
    void previousScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/Admin_Dashboard.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
