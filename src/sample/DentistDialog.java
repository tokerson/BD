package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DentistDialog extends Dialog {

    GridPane gridPane;
    //i == 0 -> createAddDialog, i == 1 -> createEditDialog, i == 2 ->createDeleteDialog
    DentistDialog(int i ){
        super();
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        if(i == 0) createAddDialog();
    }

    DentistDialog(int i, ArrayList list){
        super();
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        if(i == 1) createEditDialog(list);
        if(i == 2) createDeleteDialog(list);
    }

    private void createAddDialog(){
        this.setTitle("Dodawanie nowego dentysty");
        this.setHeaderText("Podaj dane nowego dentysty");

        TextField name = new TextField();
        TextField surname = new TextField();
        TextField salary = new TextField();
        TextField phone = new TextField();
        name.setPromptText("Pole obowiązkowe");
        surname.setPromptText("Pole obowiązkowe");
        salary.setPromptText("Pole obowiązkowe, max 9 cyfr");

        //these 3 listeners will check if OK button can be pressed

        salary.textProperty().addListener((observable, oldValue, newValue) -> {
            if(thatLongIfStatement(name,surname,salary))
                getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            else
                getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        });
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if(thatLongIfStatement(name,surname,salary))
                getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            else
                getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        });
        surname.textProperty().addListener((observable, oldValue, newValue) -> {
            if(thatLongIfStatement(name,surname,salary))
                getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            else
                getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        });

        //creating datePicker and disabling manually adding date with keyboard plus
        //disabling choosing a date from the future

        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);
        datePicker.setDayCellFactory(picker -> new DateCell(){
            public void updateItem(LocalDate date,boolean empty){
                super.updateItem(date,empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) > 0 );
            }
        });

        // preparing Converter for chosen date
        // for Database purposes I want the Date to be in format "yyyy-dd-MM"

        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "yyyy-dd-MM";
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
            {
                datePicker.setPromptText(pattern.toLowerCase());
            }
            @Override
            public String toString(LocalDate date) {
                if(date != null){
                    return dateTimeFormatter.format(date);
                }
                else{
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()){
                    return LocalDate.parse(string,dateTimeFormatter);
                }
                else{
                    return null;
                }
            }
        });

        gridPane.add(new Label("Podaj imię:"), 0, 0);
        gridPane.add(new Label("Podaj nazwisko:"), 0, 1);
        gridPane.add(new Label("Podaj zarobki:"), 0, 2);
        gridPane.add(new Label("Podaj date zatrudnienia:"), 0, 3);
        gridPane.add(new Label("Podaj numer telefonu:"), 0, 4);
        gridPane.add(name, 1, 0);
        gridPane.add(surname, 1, 1);
        gridPane.add(salary, 1, 2);
        gridPane.add(datePicker,1,3);
        gridPane.add(phone, 1, 4);

        //adding 2 buttons ( ok , cancel ) and disabling button ok

        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        this.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);

        //preparing converter for a result returned by Dialog
        //I want Dialog to return a brand new Dentist-class object

        setResultConverter(button->{
            if(button == ButtonType.OK) {
                String hireDate ;
                if(datePicker.getValue() == null) hireDate = null;
                else hireDate = datePicker.getValue().toString();
                return new Dentist(0, name.getText(), surname.getText(), Integer.parseInt(salary.getText()),hireDate, phone.getText());
            }
            else return null;
        });

        this.getDialogPane().setContent(gridPane);
    }

    //function used to check whether OK button can be clicked in createAddDialog() or not

    private boolean thatLongIfStatement(TextField name, TextField surname, TextField salary){
        int Salary;
        if(!salary.getText().matches("[0-9]+$") || !name.getText().matches("[a-zA-Z]+$") || !surname.getText().matches("[a-zA-z]+$") || salary.getText().length() >= 10) return true;
        else Salary = Integer.parseInt(salary.getText().toString());
        return Salary < 0 || salary.getText().equals("") || name.getText().equals("") || surname.getText().equals("");
    }

    private void createDeleteDialog(ArrayList list) {
        this.setTitle("Usuwanie dentysty");
        this.setHeaderText("Wybierz ID dentysty, którego chcesz usunąć");
        ComboBox choiceBox = new ComboBox();

        //this is the list of Dentists' ids. These ids may be found in a table
        ObservableList<String> ids = FXCollections.observableArrayList(list);
        choiceBox.setItems(ids);

        gridPane.add(new Label("Wybierz ID dentysty:"),0,0);
        gridPane.add(choiceBox,1,0);

        //if there is no ID chosen then return null, otherwise return chosen ID;
        setResultConverter(button->{
            if(button == ButtonType.OK) {
                if(choiceBox.valueProperty().getValue() == null) return null;
                return Integer.parseInt(choiceBox.getValue().toString());
            }
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        this.getDialogPane().setContent(gridPane);
    }

    private void createEditDialog(ArrayList list){
        this.setTitle("Edytowanie dentysty");
        this.setHeaderText("Wybierz ID dentysty, którego chcesz edytować");
        ComboBox choiceBox = new ComboBox();
        //this is the list of Dentists' ids. These ids may be found in a table
        ObservableList<String> ids = FXCollections.observableArrayList(list);
        choiceBox.setItems(ids);
        ArrayList<TextField> textFields = new ArrayList<>();
        TextField name = new TextField();
        TextField surname = new TextField();
        TextField salary = new TextField();
        TextField phone = new TextField();

        //these 3 listeners will check if OK button can be pressed
        salary.textProperty().addListener((observable, oldValue, newValue) -> {
            if((salary.getText().length() >0 && !salary.getText().matches("[0-9]+$")) || (name.getText().length() > 0 && !name.getText().matches("[a-zA-Z]+$")) || ( surname.getText().length() > 0 && !surname.getText().matches("[a-zA-z]+$")) || salary.getText().length() >= 10)
                getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            else
                getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        });
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if((salary.getText().length() >0 && !salary.getText().matches("[0-9]+$")) || (name.getText().length() > 0 && !name.getText().matches("[a-zA-Z]+$")) || ( surname.getText().length() > 0 && !surname.getText().matches("[a-zA-z]+$")) || salary.getText().length() >= 10)
                getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            else
                getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        });
        surname.textProperty().addListener((observable, oldValue, newValue) -> {
            if((salary.getText().length() >0 && !salary.getText().matches("[0-9]+$")) || (name.getText().length() > 0 && !name.getText().matches("[a-zA-Z]+$")) || ( surname.getText().length() > 0 && !surname.getText().matches("[a-zA-z]+$")) || salary.getText().length() >= 10)
                getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
            else
                getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
        });

        //adding my textfields to an ArrayList textFields to have easier work when operating on all textfields
        textFields.add(name);
        textFields.add(surname);
        textFields.add(salary);
        textFields.add(phone);

        for(TextField textField : textFields){
            textField.setDisable(true);
            textField.setPromptText("Pole nieobowiązkowe");
        }
        //if there is no id chosen, then user cannot add changes;
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            for(TextField textField : textFields){
                textField.setDisable(false);
            }
        });
        //adding all things to gridPane
        gridPane.add(new Label("Wybierz ID:"),0,0);
        gridPane.add(choiceBox,1,0);
        gridPane.add(new Label("Zmień imię:"),0,1);
        gridPane.add(new Label("Zmień nazwisko:"),0,2);
        gridPane.add(new Label("Zmień zarobki:"),0,3);
        gridPane.add(new Label("Zmień numer telefonu:"),0,4);
        gridPane.add(name,1,1);
        gridPane.add(surname,1,2);
        gridPane.add(salary,1,3);
        gridPane.add(phone,1,4);

        //preparing converter for a result returned by Dialog
        //I want Dialog to return a brand new Dentist-class object
        //if there is no salary edited, then I set it on -1,
        //because later in DBManager I check its value and if it's -1
        //then I apply no changes to the column salary in table

        setResultConverter(button->{
            if(button == ButtonType.OK) {
                int SALARY;
                if(salary.getText().isEmpty()) SALARY = -1; // this if to not parse nothing to Integer
                else SALARY = Integer.parseInt(salary.getText());
                return new Dentist(Integer.parseInt(choiceBox.getValue().toString()),name.getText(), surname.getText(),SALARY,"" ,phone.getText());
            }
            else return null;
        });
        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
        this.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        this.getDialogPane().setContent(gridPane);
    }
}
