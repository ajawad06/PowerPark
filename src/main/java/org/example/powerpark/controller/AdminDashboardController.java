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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.powerpark.model.*;

import javax.swing.*;
import java.io.IOException;
import java.util.Date;

public class AdminDashboardController {

    @FXML
    private Button EditAdminProfileBtn;

    @FXML
    private Button LogOutBtn;

    @FXML
    private TableColumn<RecentBookingsDisplay, Date> dateColRecBookingTable;

    @FXML
    private Button manageStationBtn;

    @FXML
    private Button managerUserBtn;

    @FXML
    private TableView<RecentBookingsDisplay> recentBookingsTable;

    @FXML
    private TableColumn<RecentBookingsDisplay, String> stationColRecBookingTable;

    @FXML
    private Label totalStationId;

    @FXML
    private Label totalUsersId;

    @FXML
    private TableColumn<RecentBookingsDisplay, String> userColRecentBookingTable;

    @FXML
    private Button viewBookingBtn;

    @FXML
    private Button viewInvoicesBtn;

    @FXML
    public void initialize() {
        User u = new User();
        Admin a=new Admin();
        Station s = new Station();

        totalUsersId.setText(String.valueOf(u.getTotalUsers()));
        totalStationId.setText(String.valueOf(s.getTotalStations()));
        stationColRecBookingTable.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getStationName()));
        userColRecentBookingTable.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUserName()));
        dateColRecBookingTable.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getBookingDate()));

        recentBookingsTable.setItems(a.getRecentBookings());
    }

    @FXML
    void EditProfile(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/EditAdminProfile.fxml"));
        Stage window = (Stage) EditAdminProfileBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void Logout(ActionEvent event) throws IOException{
        Session.clear();
        JOptionPane.showMessageDialog(null, "Logout Successful");
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/Login.fxml"));
        Stage window = (Stage) LogOutBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    @FXML
    void manageStations(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/ManageStations.fxml"));
        Stage window = (Stage) manageStationBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    @FXML
    void manageUsers(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/ManageUsers.fxml"));
        Stage window = (Stage) managerUserBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void viewBookings(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/ViewBookings.fxml"));
        Stage window = (Stage) viewBookingBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void viewInvoices(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/ViewInvoices.fxml"));
        Stage window = (Stage) viewInvoicesBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
