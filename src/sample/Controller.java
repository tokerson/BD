package sample;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private TableView<Patient> patientTableView;
    @FXML
    private TableColumn<Patient, String> pName;
    @FXML
    private TableColumn<Patient, String> pSurname;
    @FXML
    private TableColumn<Patient, String> pID;
    @FXML
    private TableColumn<Patient, Integer> pAge;
    @FXML
    private TableColumn<Patient, String> pHone;
    @FXML
    private TableView<Dentist> dentistTableView;
    @FXML
    private TableColumn<Dentist, Integer> dID;
    @FXML
    private TableColumn<Dentist, String> dName;
    @FXML
    private TableColumn<Dentist, String> dSurname;
    @FXML
    private TableColumn<Dentist, Integer> dSalary;
    @FXML
    private TableColumn<Dentist, String> dDate;
    @FXML
    private TableColumn<Dentist, String> dNumber;

    private DBManager dbManager = new DBManager();

    private void getPatients() {
        ObservableList<Patient> data = FXCollections.observableArrayList(dbManager.getPatients());
        patientTableView.setItems(data);
    }

    private void getDentists() {
        ObservableList<Dentist> data = FXCollections.observableArrayList(dbManager.getDentists());
        dentistTableView.setItems(data);
    }

    public void addPatient() {
        if (addChoiceBox.getValue().equals("Pacjent")) {
            Dialog<Patient> dialog = new PatientDialog(0);
            dialog.showAndWait().ifPresent(result -> dbManager.insertPatient(result));
            getPatients();
        }
    }

    public void deletePatient() {
        if (deleteChoiceBox.getValue().equals("Pacjent")) {
            Dialog<String> dialog = new PatientDialog(2, dbManager.getPesels());
            dialog.showAndWait().ifPresent(result -> dbManager.deletePatient(result));
            getPatients();
        }
    }

    public void editPatient(){
        if(editChoiceBox.getValue().equals("Pacjent")){
            Dialog<Patient> dialog = new PatientDialog(1,dbManager.getPesels());
            dialog.showAndWait();
        }
    }

    @FXML
    public void initialize() {
        ObservableList<String> choiceBoxes = FXCollections.observableArrayList("Pacjent", "Dentysta");
        ChoiceBox[] choices = new ChoiceBox[]{addChoiceBox, deleteChoiceBox, editChoiceBox};

        for (ChoiceBox choiceBox : choices) {
            choiceBox.setItems(choiceBoxes);
        }

        addChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(false));
        deleteChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> deleteButton.setDisable(false));
        editChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> editButton.setDisable(false));

        pName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirstName()));
        pSurname.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastName()));
        pAge.setCellValueFactory(cellData -> cellData.getValue().getAge().asObject());
        pID.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPESEL()));
        pHone.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhoneNumber()));

        dID.setCellValueFactory(cellData -> cellData.getValue().getID().asObject());
        dName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFirstName()));
        dSurname.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getLastName()));
        dSalary.setCellValueFactory(cellData -> cellData.getValue().getSalary().asObject());
        dDate.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getHireDate()));
        dNumber.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getPhoneNumber()));

        getPatients();
        getDentists();

    }
}
