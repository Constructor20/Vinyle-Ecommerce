package com.vinylstore.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class CatalogController {

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> categoryFilter;

    @FXML
    private FlowPane vinylGrid;

    @FXML
    public void initialize() {
    }

    @FXML
    private void handleHome(ActionEvent event) {
    }

    @FXML
    private void handleCart(ActionEvent event) {
    }

    @FXML
    private void handleLogout(ActionEvent event) {
    }
}
