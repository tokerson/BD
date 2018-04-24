package sample;

import java.sql.*;
import java.util.ArrayList;

//class used to return data from database, whole backend job is done here

public class DBManager {
    private Connection connection;
    private PreparedStatement selectPatientsStatement;
    private PreparedStatement selectDentistsStatement;
    private PreparedStatement insertPatientStatement;
    private PreparedStatement insertDentistStatement;

    public DBManager(){
        try {
            connection = DBUtil.getConnection();
            selectPatientsStatement = connection.prepareStatement("select * from pacjenci");
            selectDentistsStatement = connection.prepareStatement("select * from dentysci");
            insertPatientStatement  = connection.prepareStatement("insert into pacjenci (imie,nazwisko,PESEL,wiek,nr_telefonu) values(?,?,?,?,?)");
            insertDentistStatement  = connection.prepareStatement("insert into dentysci (imie,nazwisko,wynagrodzenie,`data zatrudnienia`,nr_telefonu) values(?,?,?,?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //returns an arraylist with Patient-class objects according to data in database
    public ArrayList<Patient> getPatients(){
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            ResultSet rs = selectPatientsStatement.executeQuery();
            while (rs.next()) {
                String PESEL = rs.getString(1);
                String name = rs.getString(2);
                String forname = rs.getString(3);
                int age = rs.getInt(4);
                String phone = rs.getString(5);
                patients.add(new Patient(name, forname, PESEL, phone, age));
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return patients;
    }

    //returns an arraylist with Dentist-class objects according to data in database
    public ArrayList<Dentist> getDentists(){
        ArrayList<Dentist> dentists = new ArrayList<>();
        try {
            ResultSet rs = selectDentistsStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String forname = rs.getString(3);
                int salary = rs.getInt(4);
                String date = rs.getString(5);
                String phone = rs.getString(6);
                dentists.add(new Dentist(id,name, forname, salary, date, phone));
            }
            rs.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return dentists;
    }

    //inserts given patient into a database
    public void insertPatient(Patient patient){
        try{
            insertPatientStatement.setString(1,patient.getFirstName());
            insertPatientStatement.setString(2,patient.getLastName());
            insertPatientStatement.setString(3,patient.getPESEL());
            insertPatientStatement.setInt(4,patient.getAgeInt());
            insertPatientStatement.setString(5,patient.getPhoneNumber());

            insertPatientStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //inserts given dentist into a database
    public void insertDentist(Dentist dentist){
        try{
            insertDentistStatement.setString(1,dentist.getFirstName());
            insertDentistStatement.setString(2,dentist.getLastName());
            insertDentistStatement.setInt(3,dentist.getSalary().get());
            insertDentistStatement.setString(4,dentist.getHireDate());
            insertDentistStatement.setString(5,dentist.getPhoneNumber());

            insertDentistStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //returns an arraylist with all pesels from table pacjenci
    public ArrayList<String> getPesels(){
        ArrayList<String> pesels = new ArrayList<>();
        String query = "select PESEL from pacjenci";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                pesels.add(rs.getString("PESEL"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pesels;
    }

    //returns an arraylist with all idDentysty from table dentysci
    public ArrayList<Integer> getIds(){
        ArrayList<Integer> ids = new ArrayList<>();
        String query = "select idDentysty from dentysci";
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                ids.add(rs.getInt("idDentysty"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ids;
    }

    //removes Patient with given PESEL from database
    public void deletePatient(String pesel){
        try{
            PreparedStatement ps = connection.prepareStatement("delete from pacjenci where PESEL = ?");
            ps.setString(1,pesel);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //removes Dentist with given ID from database
    public void deleteDentist(int id){
        try{
            PreparedStatement ps = connection.prepareStatement("delete from dentysci where idDentysty= ?");
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //allows user to edit a Patient
    public void editPatient(Patient result){
        try{//first i want to save previous state of Patient so if someone leave empty space in Dialog, then there will be no change on empty value
            Patient patient = getPatient(result.getPESEL());
            PreparedStatement ps = connection.prepareStatement("update pacjenci set imie=?,nazwisko=?,wiek=?,nr_telefonu=? where PESEL = ?");
            ps.setString(5,result.getPESEL());
            if(!result.getFirstName().isEmpty())
                ps.setString(1,result.getFirstName());
            else ps.setString(1,patient.getFirstName());
            if(!result.getLastName().isEmpty())
                ps.setString(2,result.getLastName());
            else ps.setString(2,patient.getLastName());
            if(result.getAgeInt() != -1)
                ps.setInt(3,result.getAgeInt());
            else ps.setInt(3,patient.getAgeInt());
            if(!result.getPhoneNumber().isEmpty())
                ps.setString(4,result.getPhoneNumber());
            else ps.setString(4,patient.getPhoneNumber());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //allows user to edit a Dentist
    public void editDentist(Dentist result){
        try{//first i want to save previous state of Patient so if someone leave empty space in Dialog, then there will be no change on empty value
            Dentist dentist = getDentist(result.getID().get());
            PreparedStatement ps = connection.prepareStatement("update dentysci set imie=?,nazwisko=?,wynagrodzenie=?,nr_telefonu=? where idDentysty = ?");
            ps.setInt(5,result.getID().get());
            if(!result.getFirstName().isEmpty())
                ps.setString(1,result.getFirstName());
            else ps.setString(1,dentist.getFirstName());
            if(!result.getLastName().isEmpty())
                ps.setString(2,result.getLastName());
            else ps.setString(2,dentist.getLastName());
            if(result.getSalary().get() != -1)
                ps.setInt(3,result.getSalary().get());
            else ps.setInt(3,dentist.getSalary().get());
            if(!result.getPhoneNumber().isEmpty())
                ps.setString(4,result.getPhoneNumber());
            else ps.setString(4,dentist.getPhoneNumber());
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    //returns a Patient with that particular unique PESEL
    private Patient getPatient(String pesel){
        Patient patient = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from pacjenci where PESEL = ?");
            ps.setString(1,pesel);
            ResultSet rs = ps.executeQuery();
            rs.next(); // because rs is standing BEFORE first element in column
            patient = new Patient(rs.getString(2),rs.getString(3),
                    pesel,rs.getString(5),rs.getInt(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return patient;
    }

    //returns a Dentist with that particular unique ID
    private Dentist getDentist(int id){
        Dentist dentist = null;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from dentysci where idDentysty = ?");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            rs.next(); // because rs is standing BEFORE first element in column
            dentist = new Dentist(rs.getInt(1),rs.getString(2),rs.getString(3),
                    rs.getInt(4),rs.getString(5),rs.getString(6));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dentist;
    }

    //==========================================================================
    // now bunch of methods for statistics purpose
    //==========================================================================

    //returns number of dentists in database
    public int numberOfDentists(){
        int number = 0;
        try{
            String query = "select count(idDentysty) from dentysci";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            number = rs.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return number;
    }

    //returns number of patients in database
    public int numberOfPatients(){
        int number = 0;
        try{
            String query = "select count(PESEL) from pacjenci";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            number = rs.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return number;
    }

    //returns an averageSalary of all the Dentists
    public double averageSalary(){
        double avg = 0;
        try{
            String query = "select avg(wynagrodzenie) from dentysci";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            avg = rs.getDouble(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return avg;
    }

    //returns an averageAge of all the Patients
    public double averageAge(){
        double avg = 0;
        try{
            String query = "select avg(wiek) from pacjenci";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            avg = rs.getDouble(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return avg;
    }

    //returns a sum of costs of registered treatments
    public int earnings(){
        int earnings = 0;
        try{
            String query = "SELECT SUM(cena) FROM zabiegi,zabiegi_na_rejestracje,rejestracje,pacjenci " +
                 "WHERE idZabiegu = Zabiegi_idZabiegu AND idRejestracji = Rejestracje_idRejestracji AND pacjent = PESEL";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            earnings = rs.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return earnings;
    }
}
