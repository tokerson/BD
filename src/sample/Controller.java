package sample;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

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

    //updating table with Patients
    private void getPatients() {
        ObservableList<Patient> data = FXCollections.observableArrayList(dbManager.getPatients());
        patientTableView.setItems(data);
    }

    //updating table with Dentists
    private void getDentists() {
        ObservableList<Dentist> data = FXCollections.observableArrayList(dbManager.getDentists());
        dentistTableView.setItems(data);
    }

    //button addButton
    public void add() {
        if (addChoiceBox.getValue().equals("Pacjent")) {
            Dialog<Patient> dialog = new PatientDialog(0);
            dialog.showAndWait().ifPresent(result -> dbManager.insertPatient(result));
            getPatients();
        }
        if (addChoiceBox.getValue().equals("Dentysta")) {
            Dialog<Dentist> dialog = new DentistDialog(0);
            dialog.showAndWait().ifPresent(result -> dbManager.insertDentist(result));
            getDentists();
        }
    }

    //button deleteButton
    public void delete() {
        if (deleteChoiceBox.getValue().equals("Pacjent")) {
            Dialog<String> dialog = new PatientDialog(2, dbManager.getPesels());
            dialog.showAndWait().ifPresent(result -> dbManager.deletePatient(result));
            getPatients();
        }
        if (deleteChoiceBox.getValue().equals("Dentysta")) {
            Dialog<Integer> dialog = new DentistDialog(2, dbManager.getIds());
            dialog.showAndWait().ifPresent(result -> dbManager.deleteDentist(result));
            getDentists();
        }
    }

    //button editButton
    public void edit(){
        if(editChoiceBox.getValue().equals("Pacjent")){
            Dialog<Patient> dialog = new PatientDialog(1,dbManager.getPesels());
            dialog.showAndWait().ifPresent(result -> dbManager.editPatient(result));
            getPatients();
        }
        if(editChoiceBox.getValue().equals("Dentysta")){
            Dialog<Dentist> dialog = new DentistDialog(1, dbManager.getIds());
            dialog.showAndWait().ifPresent(result -> dbManager.editDentist(result));
            getDentists();
        }
    }

    //button with label "Statystyki"
    public void showStatistics(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Statystyki");

        VBox vBox = new VBox();
        vBox.setMinSize(300,200);
        vBox.setSpacing(5);
        ArrayList<Label> labels = new ArrayList<>();

        labels.add(new Label("Liczba dentystów      = " + dbManager.numberOfDentists()));
        labels.add(new Label("Średnie zarobki       = " + dbManager.averageSalary() +" PLN"));
        labels.add(new Label("Liczba pacjentów      = " + dbManager.numberOfPatients()));
        labels.add(new Label("Średni wiek pacjentów = " + dbManager.averageAge()+" lata"));
        labels.add(new Label("Zarobek ze wszystkich zarejestrowanych zabiegów = " + dbManager.earnings()+" PLN"));

        vBox.getChildren().addAll(labels);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        dialog.getDialogPane().setContent(vBox);
        dialog.showAndWait();
    }

    @FXML
    public void initialize() {
        ObservableList<String> choiceBoxes = FXCollections.observableArrayList("Pacjent", "Dentysta");
        ChoiceBox[] choices = new ChoiceBox[]{addChoiceBox, deleteChoiceBox, editChoiceBox};

        for (ChoiceBox choiceBox : choices) {
            choiceBox.setItems(choiceBoxes);
        }

        //adding listener to choiceboxes to make button clickable after choosing an option from choiceBox
        addChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> addButton.setDisable(false));
        deleteChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> deleteButton.setDisable(false));
        editChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> editButton.setDisable(false));

        //setting types of the data stored in particular columns
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
