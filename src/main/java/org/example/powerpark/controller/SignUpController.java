package org.example.powerpark.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.powerpark.model.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SignUpController {

    @FXML
    private Label AccountCreatedLabel;

    @FXML
    private TextField SignupPhoneNum;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField SignUpEmail;

    @FXML
    private TextField SignupAddress;

    @FXML
    private PasswordField SignupPassword;

    @FXML
    private TextField SignupUsername;

    @FXML
    void SignUp(ActionEvent event) {
        String username = SignupUsername.getText();
        String password = SignupPassword.getText();
        String email = SignUpEmail.getText();
        String phone = SignupPhoneNum.getText();
        String address = SignupAddress.getText();
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields before signing up.");
            return;
        }
        try {
            User user=new User(username,password,email,phone,address);
            boolean value=user.Signup();
            if (value) {
                JOptionPane.showMessageDialog(null, "Signup Successful");
                Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/Login.fxml"));
                Stage window = (Stage) SignUpButton.getScene().getWindow();
                window.setScene(new Scene(root));
            } else {
                JOptionPane.showMessageDialog(null, "Signup Error");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
