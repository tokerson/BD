package sample;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private Connection connection;
    private PreparedStatement selectPatientsStatement ;
    private PreparedStatement selectDentistsStatement ;
    private PreparedStatement insertPatientStatement;

    public DBManager(){
        try {
            connection = DBUtil.getConnection();
            selectPatientsStatement = connection.prepareStatement("select * from pacjenci");
            selectDentistsStatement = connection.prepareStatement("select * from dentysci");
            insertPatientStatement  = connection.prepareStatement("insert into pacjenci (imie,nazwisko,PESEL,wiek,nr_telefonu) values(?,?,?,?,?)");
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

    public void deletePatient(String pesel){
        try{
            PreparedStatement ps = connection.prepareStatement("delete from pacjenci where PESEL = ?");
            ps.setString(1,pesel);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
