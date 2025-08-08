
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

public class AddStationController {

    @FXML
    private Button AddStationBtn;

    @FXML
    private Button BackBtn;

    @FXML
    private TextField LocField;

    @FXML
    private TextField chargerField;

    @FXML
    private TextField latField;

    @FXML
    private TextField longField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField slotsField;

    @FXML
    void addStation(ActionEvent event) {
        Admin a=new Admin();
        if (nameField.getText().isBlank()||LocField.getText().isBlank()||latField.getText().isBlank()||longField.getText().isBlank()||chargerField.getText().isBlank()||slotsField.getText().isBlank()){
            JOptionPane.showMessageDialog(null, "Please fill all required details.");
        }else{
            String name=nameField.getText();
            String location=LocField.getText();
            double latitude= Double.parseDouble(latField.getText());
            double longitude=Double.parseDouble(longField.getText());
            String charger_type=chargerField.getText();
            int totalslot=Integer.parseInt(slotsField.getText());
            if (a.addStation(name,location,latitude,longitude,charger_type,totalslot)){
                JOptionPane.showMessageDialog(null, "Station added successfully.");
            }
        }
    }


    @FXML
    void previousScreen(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/example/powerpark/Admin_Dashboard.fxml"));
        Stage window = (Stage) BackBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
