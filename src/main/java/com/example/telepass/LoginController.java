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

    @FXML
    void onLogoutClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();

            // Applica il file CSS alla scena
            Scene scene = new Scene(root, 800, 600);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openMainView(boolean isAdmin) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            Parent root = loader.load();

            // Passa la modalità (amministratore o utente) al controller principale
            HelloController mainController = loader.getController();
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