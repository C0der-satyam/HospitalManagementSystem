package HospitalManagementSyatem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patients {
    private Connection connection;
    private Scanner scanner;

    public Patients(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }
    public void addPatient(){
        System.out.print("Enter the name of the patient: ");
        String name = scanner.next();
        System.out.print("Enter the age of the patient: ");
        int age = scanner.nextInt();
        System.out.print("Enter the gender of the patient: ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(name ,age, gender) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Patient add successfully");
            }
            else {
                System.out.println("Failed to add!!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void viewPatients(){
        String query = "select * from patients";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients: ");
            System.out.println("+------------+-----------------------+---------+------------+");
            System.out.println("| Patient Id | Name                  | Age     | Gender     |");
            System.out.println("+------------+-----------------------+---------+------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                System.out.printf("|%-12s|%-23s|%-9s|%-7s\n",id,name,age,gender);
                System.out.println("+------------+-----------------------+---------+-------------+");

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id){
        String query = "select * from patients WHERE id = ?" ;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
