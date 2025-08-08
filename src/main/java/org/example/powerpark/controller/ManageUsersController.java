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

public class ManageUsersController {

    @FXML
    private Button BackBtn;

    @FXML
    private TableColumn<User, String> PhoneNumCol;

    @FXML
    private TableColumn<User ,String> emailCol;

    @FXML
    private Button removeuserBtn;

    @FXML
    private TextField searchBarUser;

    @FXML
    private TableColumn<User,Integer> userIDcol;

    @FXML
    private TableColumn<User, String> addressCol;

    @FXML
    private TableColumn<User, String> userNameCol;

    @FXML
    private TableView<User> userTable;

    @FXML
    public void initialize(){
        Admin a=new Admin();
        userIDcol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        userNameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getUsername()));
        emailCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEmail()));
        PhoneNumCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhone_num()));
        addressCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAddress()));
        userTable.setItems(a.getUsers());
    }

    @FXML
    void previousScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/Admin_Dashboard.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void removeUser(ActionEvent event) {
        Admin a = new Admin();
        String username = searchBarUser.getText();

        if (username.isEmpty()) {
            userTable.setItems(a.getUsers());
            JOptionPane.showMessageDialog(null, "No user selected for removal.");
        } else {
            User foundUser = a.searchUser(username);

            if (foundUser != null) {
                userTable.setItems(FXCollections.observableArrayList(foundUser));

                int option = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to remove this user?",
                        "Confirm User Removal",
                        JOptionPane.YES_NO_OPTION
                );

                if (option == JOptionPane.YES_OPTION) {
                    if (a.removeUser(username)) {
                        JOptionPane.showMessageDialog(null, "User removed successfully.");
                        userTable.setItems(a.getUsers()); // Refresh table after deletion
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to remove the user.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "User removal cancelled.");
                }

            } else {
                userTable.setItems(FXCollections.observableArrayList());
                JOptionPane.showMessageDialog(null, "User with such username does not exist.");
            }
        }
    }

    @FXML
    void typeSearchUser(ActionEvent event) {
        Admin a=new Admin();
        String reqUsername=searchBarUser.getText();
        if (reqUsername.isEmpty()){
            userTable.setItems(a.getUsers());
        }else{
            User foundUser=a.searchUser(reqUsername);
            if (foundUser != null) {
                userTable.setItems(FXCollections.observableArrayList(foundUser)); // Show only that user
            } else {
                userTable.setItems(FXCollections.observableArrayList()); // Clear table if not found

            }
        }
    }

}


