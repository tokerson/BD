package sample;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class PatientDialog extends Dialog {

    GridPane gridPane;

    PatientDialog(){
        super();
        this.setTitle("Dodawanie nowego pacjenta");
        this.setHeaderText("Podaj dane nowego pacjenta");
        gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
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
}
