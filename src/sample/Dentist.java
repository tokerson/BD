package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


//class to store data of Dentists from database
//has the same fields as the table dentysci from database
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
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
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
