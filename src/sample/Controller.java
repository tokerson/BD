package sample;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.Optional;


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
    private TableView<Patient> tableView;
    @FXML
    private TableColumn<Patient,String> pName;
    @FXML
    private TableColumn<Patient,String> pNazwisko;
    @FXML
    private TableColumn<Patient,String> pID;
    @FXML
    private TableColumn<Patient,Integer> pAge;
    @FXML
    private TableColumn<Patient,String> pHone;


    public void addPatient(){
        Dialog<String> dialog = new Dialog<>();
        GridPane gridPane = new GridPane();

        gridPane.add(new Label("Podaj imię:"),0,0);
        gridPane.add(new TextField(),1,0);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL,ButtonType.APPLY);
        dialog.getDialogPane().setContent(gridPane);

        Optional<String> result = dialog.showAndWait();
    }

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

        pName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirstName()));
        pNazwisko.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastName()));
        pAge.setCellValueFactory(cellData -> cellData.getValue().getAge().asObject());
        pID.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPESEL()));
        pHone.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhoneNumber()));

        ObservableList<Patient> patients = FXCollections.observableArrayList(new Patient("Karol","Borowski","97041506455","510223041",21));

        tableView.setItems(patients);

    }
}
