package org.example.powerpark.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.powerpark.model.Session;
import org.example.powerpark.model.User;
import org.example.powerpark.model.Admin;


import javax.swing.*;

public class LogInController {

    @FXML
    private RadioButton AdminRadioBtn;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label Signuplabel;

    @FXML
    private RadioButton UserRadioBtn;

    @FXML
    private ToggleGroup roleGroup;

    @FXML
    private Label showMsgLabel;

    @FXML
    void LogIn(ActionEvent event) {
        if (!(username.getText().isBlank()) && !(password.getText().isBlank())&&(roleGroup.getSelectedToggle() != null)){
            validateLogin();
        }
        else if (!(username.getText().isBlank()) && !(password.getText().isBlank())&&(roleGroup.getSelectedToggle() == null)){
            showMsgLabel.setText("Please select role.");
        }
        else if (((username.getText().isBlank())||(password.getText().isBlank()))&&(roleGroup.getSelectedToggle() != null)){
            showMsgLabel.setText("Please enter username and password.");
        }
        else{
            showMsgLabel.setText("Try again.");
        }
    }

    @FXML
    void SignUpPage(MouseEvent event) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/SignUp.fxml"));
            Stage window = (Stage) Signuplabel.getScene().getWindow();
            window.setScene(new Scene(root));
        }
        catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void validateLogin(){
        String user=username.getText();
        String pass=password.getText();
        RadioButton selectedRole=(RadioButton) roleGroup.getSelectedToggle();
        String role= selectedRole.getText();
        try{
            if (role.equals("Admin")) {
                Admin admin = new Admin();
                boolean value =  admin.Login(user, pass , role);
                if (value){
                    int adminId = admin.getIdByUsername(user,role);
                    Session.setSession(adminId, user, "Admin");
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/Admin_Dashboard.fxml"));
                    Stage window = (Stage) LoginButton.getScene().getWindow();
                    window.setScene(new Scene(root));
                }else {
                    showMsgLabel.setText("Invalid Credentials.");
                }
            }else {
                User newUser=new User();
                boolean value=newUser.Login(user,pass,role);
                if (value){
                    int userId = newUser.getIdByUsername(user,role); // Implement this method
                    Session.setSession(userId, user, "User");
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/User_Dashboard.fxml"));
                    Stage window = (Stage) LoginButton.getScene().getWindow();
                    window.setScene(new Scene(root));
                } else {
                    showMsgLabel.setText("Invalid Credentials.");
                }

            }
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
