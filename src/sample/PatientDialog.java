package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

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


    private void createAddDialog(){
        this.setTitle("Dodawanie nowego pacjenta");
        this.setHeaderText("Podaj dane nowego pacjenta");
        TextField name = new TextField();
        TextField surname = new TextField();
        TextField pesel = new TextField();
        TextField age = new TextField();
        TextField phone = new TextField();
        gridPane.add(new Label("Podaj imię:"), 0, 0);
        gridPane.add(new Label("Podaj nazwisko:"), 0, 1);
        gridPane.add(new Label("Podaj PESEL:"), 0, 2);
        gridPane.add(new Label("Podaj wiek:"), 0, 3);
        gridPane.add(new Label("Podaj numer telefonu:"), 0, 4);
        gridPane.add(name, 1, 0);
        gridPane.add(surname, 1, 1);
        gridPane.add(pesel, 1, 2);
        gridPane.add(age, 1, 3);
        gridPane.add(phone, 1, 4);
        setResultConverter(button->{
            if(button == ButtonType.OK)
                return new Patient(name.getText(),surname.getText(),pesel.getText(),phone.getText(),Integer.parseInt(age.getText()));
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        this.getDialogPane().setContent(gridPane);
    }

    private void createDeleteDialog(ArrayList list) {
        this.setTitle("Usuwanie pacjenta");
        this.setHeaderText("Wybierz PESEL pacjenta, którego chcesz usunąć");
//        ChoiceBox choiceBox = new ChoiceBox();
        ComboBox choiceBox = new ComboBox();
        ObservableList<String> pesels = FXCollections.observableArrayList(list);
        choiceBox.setItems(pesels);
        gridPane.add(new Label("Wybierz PESEL:"),0,0);
        gridPane.add(choiceBox,1,0);
        setResultConverter(button->{
            if(button == ButtonType.OK)
                return choiceBox.getValue().toString();
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        this.getDialogPane().setContent(gridPane);
    }

    private void createEditDialog(ArrayList list){
        this.setTitle("Edytowanie pacjenta");
        this.setHeaderText("Wybierz PESEL pacjenta, którego chcesz edytować");
        ComboBox choiceBox = new ComboBox();
        ObservableList<String> pesels = FXCollections.observableArrayList(list);
        choiceBox.setItems(pesels);
        ArrayList<TextField> textFields = new ArrayList<>();
        TextField name = new TextField();
        TextField surname = new TextField();
        TextField age = new TextField();
        TextField phone = new TextField();
        textFields.add(name);
        textFields.add(surname);
        textFields.add(age);
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
        gridPane.add(new Label("Zmień wiek:"),0,3);
        gridPane.add(new Label("Zmień numer telefonu:"),0,4);
        gridPane.add(name,1,1);
        gridPane.add(surname,1,2);
        gridPane.add(age,1,3);
        gridPane.add(phone,1,4);

        setResultConverter(button->{
            if(button == ButtonType.OK) {
                int AGE;
                if(age.getText().isEmpty()) AGE = -1; // this if to not parse nothing to Integer
                else AGE = Integer.parseInt(age.getText());
                return new Patient(name.getText(), surname.getText(), choiceBox.getValue().toString(), phone.getText(), AGE);
            }
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        this.getDialogPane().setContent(gridPane);
    }
}
