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

public class EditAdminProfileController {

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
        Admin admin = new Admin();
        Admin currentAdmin = admin.getAdminDetails(Session.getUsername());

        if (currentAdmin != null) {
            nameField.setPromptText(currentAdmin.getUsername());
            PasswordField.setPromptText(currentAdmin.getPassword());
            EmailField.setPromptText(currentAdmin.getEmail());
            PhoneNumField.setPromptText(currentAdmin.getPhone_num());
        } else {
            System.out.println(Session.getUsername());
            JOptionPane.showMessageDialog(null, "Failed to load admin details.");
        }
    }

    @FXML
    void PreviousScreen(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/Admin_Dashboard.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void SaveInfo(ActionEvent event) {
        Admin admin = new Admin();
        Admin currentAdmin = admin.getAdminDetails(Session.getUsername());
        if (currentAdmin == null) {
            JOptionPane.showMessageDialog(null, "Failed to fetch current admin data.");
            return;
        }
        String username,password,email,phone;
        if (nameField.getText().isEmpty()){
            username=currentAdmin.getUsername();
        }else{
            username=nameField.getText();
        }
        if (PasswordField.getText().isEmpty()) {
            password = currentAdmin.getPassword();
        } else {
            password = PasswordField.getText();
        }
        if (EmailField.getText().isEmpty()) {
            email = currentAdmin.getEmail();
        } else {
            email = EmailField.getText();
        }
        if (PhoneNumField.getText().isEmpty()) {
            phone = currentAdmin.getPhone_num();
        } else {
            phone = PhoneNumField.getText();
        }
        Admin updatedAdmin = new Admin(username, password, email, phone);
        boolean success = updatedAdmin.editProfile(Session.getRole());
        if (success) {
            JOptionPane.showMessageDialog(null, "Information updated successfully.");
            Session.setUsername(username);
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update information.");
        }
    }


}
