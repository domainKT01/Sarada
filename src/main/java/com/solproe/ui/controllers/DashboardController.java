package com.solproe.ui.controllers;

import com.solproe.business.dto.DashboardDto;
import com.solproe.ui.viewModels.ConfigFileViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML public TextField tokenWhatsapp;
    @FXML public Button buttonSave;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ConfigFileViewModel viewModel = new ConfigFileViewModel();
        this.buttonSave.setOnAction(_ -> viewModel.createConfigDash(new DashboardDto(tokenWhatsapp.getText())));
    }
}
