package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Dentist {
    private SimpleIntegerProperty ID;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleIntegerProperty salary;
    private SimpleStringProperty hireDate;
    private SimpleStringProperty phoneNumber;

    public Dentist(int id,String firstName,String lastName,int salary,String hireDate,String phoneNumber) {
        ID = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.salary = new SimpleIntegerProperty(salary);
        this.hireDate = new SimpleStringProperty(hireDate);
//        this.hireDate = createSqlDate(hireDate);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
    }

    private static java.sql.Date createSqlDate(String date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM");
        java.sql.Date formattedDate = null;
        try {
            formattedDate = (Date) formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
    public SimpleIntegerProperty getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public SimpleIntegerProperty getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary.set(salary);
    }

    public String getHireDate() {
        return hireDate.get();
    }

    public void setHireDate(String hireDate) {
        this.hireDate.set(hireDate);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
}
