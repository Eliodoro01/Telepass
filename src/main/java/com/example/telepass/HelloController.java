package com.example.telepass;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloController implements Observer {
    @FXML
    private VBox adminPanel;

    @FXML
    private VBox userPanel;

    @FXML
    private TextField deviceCodeField;

    @FXML
    private TextField licensePlateField;

    @FXML
    private TextField ownerNameField;

    @FXML
    private TextField paymentMethodField;

    @FXML
    private TextField deviceCodeFieldUser;

    @FXML
    private TextField newLicensePlateField;

    @FXML
    private Label statusLabel;

    @FXML
    private Label entryStatsLabel;

    @FXML
    private Label exitStatsLabel;

    private TelepassSystem telepassSystem = new TelepassSystem();

    private boolean isAdminMode = false;

    public void setAdminMode(boolean isAdminMode) {
        this.isAdminMode = isAdminMode;
        initializePanels();
    }

    @FXML
    public void initialize() {
        telepassSystem.addObserver(this);
        initializePanels();
    }

    private void initializePanels() {
        adminPanel.setVisible(isAdminMode);
        userPanel.setVisible(!isAdminMode);
    }

    @Override
    public void update(String message) {
        onUpdateStatsClick();
    }

    @FXML
    void onUpdateStatsClick() {
        List<String> stats = telepassSystem.getStatistics();
        for (String stat : stats) {
            if (stat.startsWith("ENTRY")) {
                entryStatsLabel.setText("Entrate: " + stat.substring(7));
            } else if (stat.startsWith("EXIT")) {
                exitStatsLabel.setText("Uscite: " + stat.substring(6));
            }
        }
    }

    @FXML
    void onInsertDeviceClick() {
        String deviceCode = deviceCodeField.getText();
        String licensePlate = licensePlateField.getText();
        String ownerName = ownerNameField.getText();
        String paymentMethod = paymentMethodField.getText();

        if (telepassSystem.insertDevice(deviceCode, licensePlate, ownerName, paymentMethod)) {
            statusLabel.setText("Dispositivo inserito con successo!");
        } else {
            statusLabel.setText("Errore: dispositivo già esistente.");
        }
    }

    @FXML
    void onRevokeDeviceClick() {
        String deviceCode = deviceCodeField.getText();

        if (telepassSystem.revokeDevice(deviceCode)) {
            statusLabel.setText("Dispositivo revocato con successo!");
        } else {
            statusLabel.setText("Errore: dispositivo non trovato.");
        }
    }

    @FXML
    void onEnterTollBoothClick() {
        String deviceCode = deviceCodeFieldUser.getText();
        double amount = telepassSystem.enterTollBooth(deviceCode);

        if (amount >= 0) {
            statusLabel.setText("Ingresso registrato. Importo addebitato: " + amount + "€");
        } else {
            statusLabel.setText("Errore: dispositivo non valido.");
        }
    }

    @FXML
    void onExitTollBoothClick() {
        String deviceCode = deviceCodeFieldUser.getText();
        double amount = telepassSystem.exitTollBooth(deviceCode);

        if (amount >= 0) {
            statusLabel.setText("Uscita registrata. Importo addebitato: " + amount + "€");
        } else {
            statusLabel.setText("Errore: dispositivo non valido.");
        }
    }

    @FXML
    void onAssociateLicensePlateClick() {
        String deviceCode = deviceCodeFieldUser.getText();
        String newLicensePlate = newLicensePlateField.getText();

        if (telepassSystem.associateLicensePlate(deviceCode, newLicensePlate)) {
            statusLabel.setText("Targa associata con successo!");
        } else {
            statusLabel.setText("Errore: dispositivo non trovato.");
        }
    }

    @FXML
    void onConvertToTelepassPlusClick() {
        String deviceCode = deviceCodeFieldUser.getText();

        if (telepassSystem.isTelepassPlus(deviceCode)) {
            statusLabel.setText("Il dispositivo è già Telepass+.");
        } else if (telepassSystem.convertToTelepassPlus(deviceCode)) {
            statusLabel.setText("Contratto convertito a Telepass+ con successo!");
        } else {
            statusLabel.setText("Errore: dispositivo non trovato.");
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

            Stage stage = (Stage) statusLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}