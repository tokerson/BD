package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        ObservableList<String> choiceBoxes = FXCollections.observableArrayList("Pacjent","Dentysta");
        ChoiceBox[] choices = new ChoiceBox[] {addChoiceBox,deleteChoiceBox,editChoiceBox};

        for(ChoiceBox choiceBox : choices){
            choiceBox.setItems(choiceBoxes);
        }

        addChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(false));
        deleteChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> deleteButton.setDisable(false));
        editChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> editButton.setDisable(false));
    }
}
