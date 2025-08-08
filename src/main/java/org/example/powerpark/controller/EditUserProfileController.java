package org.example.powerpark.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.powerpark.model.*;


import javax.swing.*;
import java.io.IOException;

public class EditUserProfileController {

    @FXML
    private TextField AddressField;

    @FXML
    private Button BackBtn;

    @FXML
    private TextField EmailField;

    @FXML
    private TextField PasswordField;

    @FXML
    private TextField PhoneNumField;

    @FXML
    private Button SaveBtn;

    @FXML
    private TextField nameField;

    public void initialize() {
        User u = new User();
        User currentUser = u.fetchUserDetails(Session.getUsername());
        if (currentUser != null) {
            nameField.setPromptText(currentUser.getUsername());
            PasswordField.setPromptText(currentUser.getPassword());
            EmailField.setPromptText(currentUser.getEmail());
            PhoneNumField.setPromptText(currentUser.getPhone_num());
            AddressField.setPromptText(currentUser.getAddress());
        } else {
            JOptionPane.showMessageDialog(null, "Failed to load user details.");
        }
    }
    @FXML
    void SaveInfo(ActionEvent event) {
        User u = new User();
        User currentUser = u.fetchUserDetails(Session.getUsername());
        if (currentUser == null) {
            JOptionPane.showMessageDialog(null, "Failed to fetch current user data.");
            return;
        }
        String username,password,email,phone,address;
        if (nameField.getText().isEmpty()){
            username=currentUser.getUsername();
        }else{
            username=nameField.getText();
        }
        if (PasswordField.getText().isEmpty()) {
            password = currentUser.getPassword();
        } else {
            password = PasswordField.getText();
        }
        if (EmailField.getText().isEmpty()) {
            email = currentUser.getEmail();
        } else {
            email = EmailField.getText();
        }
        if (PhoneNumField.getText().isEmpty()) {
            phone = currentUser.getPhone_num();
        } else {
            phone = PhoneNumField.getText();
        }
        if (AddressField.getText().isEmpty()) {
            address = currentUser.getAddress();
        } else {
            address = AddressField.getText();
        }

        User updatedUser = new User(username, password, email, phone,address);
        boolean success = updatedUser.editProfile(Session.getRole());
        if (success) {
            JOptionPane.showMessageDialog(null, "Information updated successfully.");

        } else {
            JOptionPane.showMessageDialog(null, "Failed to update information.");
        }
    }

    @FXML
    void PreviousScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/User_Dashboard.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}

