package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

public class Controller {
    @FXML
    private ChoiceBox addChoiceBox;
    @FXML
    private Button addButton;
    @FXML
    private ChoiceBox deleteChoiceBox;
    @FXML
    private Button deleteButton;
    @FXML
    private ChoiceBox editChoiceBox;
    @FXML
    private Button editButton;

    @FXML
    public void initialize(){
        addChoiceBox.setItems(FXCollections.observableArrayList("Pacjent","Dentysta"));
        addChoiceBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                addButton.setDisable(false);
            }
        });
        deleteChoiceBox.setItems(FXCollections.observableArrayList("Pacjent","Dentysta"));
        deleteChoiceBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                deleteButton.setDisable(false);
            }
        });
        editChoiceBox.setItems(FXCollections.observableArrayList("Pacjent","Dentysta"));
        editChoiceBox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                editButton.setDisable(false);
            }
        });
    }
}
