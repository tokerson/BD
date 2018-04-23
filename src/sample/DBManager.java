package sample;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private Connection connection;
    private PreparedStatement selectPatientsStatement ;
    private PreparedStatement selectDentistsStatement ;
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

    public void deletePatient(String pesel){
        try{
            PreparedStatement ps = connection.prepareStatement("delete from pacjenci where PESEL = ?");
            ps.setString(1,pesel);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteDentist(int id){
        try{
            PreparedStatement ps = connection.prepareStatement("delete from dentysci where idDentysty= ?");
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

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

}
