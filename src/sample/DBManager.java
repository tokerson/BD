package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBManager {
    private Connection connection;
    private PreparedStatement selectPatientsStatement ;
    private PreparedStatement selectDentistsStatement ;

    public DBManager(){
        try {
            connection = DBUtil.getConnection();
            selectPatientsStatement = connection.prepareStatement("select * from pacjenci");
            selectDentistsStatement = connection.prepareStatement("select * from dentysci");
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
}
