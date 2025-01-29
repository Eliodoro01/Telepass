package com.example.telepass;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label loginStatusLabel;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private static final String USER_USERNAME = "user";
    private static final String USER_PASSWORD = "user";

    @FXML
    void onLoginClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            openMainView(true); // Apri la vista principale in modalità amministratore
        } else if (username.equals(USER_USERNAME) && password.equals(USER_PASSWORD)) {
            openMainView(false); // Apri la vista principale in modalità utente
        } else {
            loginStatusLabel.setText("Credenziali non valide.");
        }
    }

    private void openMainView(boolean isAdmin) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            Parent root = loader.load();

            // Ottieni il controller principale
            HelloController mainController = loader.getController();

            // Imposta la modalità direttamente nel controller
            mainController.setAdminMode(isAdmin);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            loginStatusLabel.setText("Errore durante il caricamento della schermata principale.");
        }
    }
}