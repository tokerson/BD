package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Patient {

    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty PESEL;
    private SimpleStringProperty phoneNumber;
    private SimpleIntegerProperty age;

    public Patient(String firstName,String lastName,String PESEL,String phoneNumber,int age){
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.PESEL = new SimpleStringProperty(PESEL);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.age = new SimpleIntegerProperty(age);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPESEL() {
        return PESEL.get();
    }

    public void setPESEL(String PESEL) {
        this.PESEL.set(PESEL);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public SimpleIntegerProperty getAge() {
        return age;
    }

    public int getAgeInt(){
        return age.get();
    }

    public void setAge(int age) {
        this.age.set(age);
    }
}
