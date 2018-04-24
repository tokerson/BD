package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

import static java.lang.Math.toIntExact;

//class extending Dialog class to specify the View of Dialogs created in Controller class
public class PatientDialog extends Dialog {

    GridPane gridPane;
    //i == 0 -> createAddDialog, i == 1 -> createEditDialog, i == 2 ->createDeleteDialog
    PatientDialog(int i ){
        super();
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        if(i == 0) createAddDialog();
    }

    PatientDialog(int i, ArrayList list){
        super();
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        if(i == 1) createEditDialog(list);
        if(i == 2) createDeleteDialog(list);
    }

    //specifies the look of Add Dialog , adds listeners to some objects and converts the result
    private void createAddDialog(){
        this.setTitle("Dodawanie nowego pacjenta");
        this.setHeaderText("Podaj dane nowego pacjenta");

        TextField name = new TextField();
        TextField surname = new TextField();
        TextField pesel = new TextField();
        TextField age = new TextField();
        TextField phone = new TextField();
        phone.setPromptText("Pole nieobowiązakowe");
        age.setDisable(true);

        //adding listeners to textfields in order to check if an OK button can be clicked

        //this listener handles setting age value depending on a pesel
        pesel.textProperty().addListener((observable, oldValue, newValue) -> {
            PeselValidator peselValidator = new PeselValidator(pesel.getText().toString());
            if(peselValidator.isValid()){
                age.setText(String.valueOf(peselValidator.countAge()));
            }
            else {
                age.setText("");
            }
        });
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            getDialogPane().lookupButton(ButtonType.OK).setDisable(thatLongIfStatement(name,surname,age));
        });
        surname.textProperty().addListener((observable, oldValue, newValue) -> {
            getDialogPane().lookupButton(ButtonType.OK).setDisable(thatLongIfStatement(name,surname,age));

        });
        age.textProperty().addListener((observable, oldValue, newValue) -> {
            getDialogPane().lookupButton(ButtonType.OK).setDisable(thatLongIfStatement(name,surname,age));
        });


        gridPane.add(new Label("Podaj imię:"), 0, 0);
        gridPane.add(new Label("Podaj nazwisko:"), 0, 1);
        gridPane.add(new Label("Podaj PESEL:"), 0, 2);
        gridPane.add(new Label("Wiek:"), 0, 3);
        gridPane.add(new Label("Podaj numer telefonu:"), 0, 4);
        gridPane.add(name, 1, 0);
        gridPane.add(surname, 1, 1);
        gridPane.add(pesel, 1, 2);
        gridPane.add(age, 1, 3);
        gridPane.add(phone, 1, 4);

        //preparing converter for a result returned by Dialog
        //I want Dialog to return a brand new Patient-class object

        setResultConverter(button->{
            if(button == ButtonType.OK)
                return new Patient(name.getText(),surname.getText(),pesel.getText(),phone.getText(),Integer.parseInt(age.getText()));
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        this.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        this.getDialogPane().setContent(gridPane);
    }

    //function that is just a long if that decide if OK button should be clickable
    private boolean thatLongIfStatement(TextField name, TextField surname,TextField age){
        return (age.getText().length() == 0 || name.getText().matches(".*\\d+.*") || name.getText().length() == 0 || surname.getText().matches(".*\\d+.*") || surname.getText().length() == 0) ;
    }

    //specifies the look of Delete Dialog, allow user to choose which Patient he wants to remove by
    //choosing an unique PESEL from comboBox
    private void createDeleteDialog(ArrayList list) {
        this.setTitle("Usuwanie pacjenta");
        this.setHeaderText("Wybierz PESEL pacjenta, którego chcesz usunąć");
//        ChoiceBox choiceBox = new ChoiceBox();
        ComboBox choiceBox = new ComboBox();
        ObservableList<String> pesels = FXCollections.observableArrayList(list);
        choiceBox.setItems(pesels);
        gridPane.add(new Label("Wybierz PESEL:"),0,0);
        gridPane.add(choiceBox,1,0);

        //if no pesel is chosen then no patient should be deleted
        setResultConverter(button->{
            if(button == ButtonType.OK) {
                if (choiceBox.valueProperty().getValue() == null) return null;
                return choiceBox.getValue().toString();
            }
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        this.getDialogPane().setContent(gridPane);
    }

    //specifies the look of Edit Dialog, no field is obligatory.
    private void createEditDialog(ArrayList list){
        this.setTitle("Edytowanie pacjenta");
        this.setHeaderText("Wybierz PESEL pacjenta, którego chcesz edytować");

        ComboBox choiceBox = new ComboBox();
        ObservableList<String> pesels = FXCollections.observableArrayList(list);
        choiceBox.setItems(pesels);
        ArrayList<TextField> textFields = new ArrayList<>();

        TextField name = new TextField();
        TextField surname = new TextField();
        TextField phone = new TextField();
        textFields.add(name);
        textFields.add(surname);
        textFields.add(phone);

        for(TextField textField : textFields){
            textField.setDisable(true);
            textField.setPromptText("Pole nieobowiązkowe");
        }
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            for(TextField textField : textFields){
                textField.setDisable(false);
            }
        });

        gridPane.add(new Label("Wybierz PESEL:"),0,0);
        gridPane.add(choiceBox,1,0);
        gridPane.add(new Label("Zmień imię:"),0,1);
        gridPane.add(new Label("Zmień nazwisko:"),0,2);
        gridPane.add(new Label("Zmień numer telefonu:"),0,3);
        gridPane.add(name,1,1);
        gridPane.add(surname,1,2);
        gridPane.add(phone,1,3);

        //ok button can be clickable only when name and surname contain no numbers
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            getDialogPane().lookupButton(ButtonType.OK).setDisable(name.getText().matches(".*\\d+.*") || surname.getText().matches(".*\\d+.*"));
        });
        surname.textProperty().addListener((observable, oldValue, newValue) -> {
            getDialogPane().lookupButton(ButtonType.OK).setDisable(name.getText().matches(".*\\d+.*") || surname.getText().matches(".*\\d+.*"));
        });
        phone.textProperty().addListener((observable, oldValue, newValue) -> {
            getDialogPane().lookupButton(ButtonType.OK).setDisable(name.getText().matches(".*\\d+.*") || surname.getText().matches(".*\\d+.*"));
        });

        setResultConverter(button->{
            if(button == ButtonType.OK) {
                return new Patient(name.getText(), surname.getText(), choiceBox.getValue().toString(), phone.getText(),  toIntExact(new PeselValidator(choiceBox.getValue().toString()).countAge()));
            }
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        this.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        this.getDialogPane().setContent(gridPane);
    }
}
