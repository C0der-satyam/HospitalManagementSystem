package HospitalManagementSyatem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctors {
    private Connection connection;


    public Doctors(Connection connection){
        this.connection = connection;

    }

    public void viewDoctors(){
        String query = "select * from doctors";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+-----------------------+--------------------+");
            System.out.println("| Doctors Id | Name                  | Specialization      |");
            System.out.println("+------------+-----------------------+--------------------+");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("|%-11s | %-22s| %-18s\n",id,name,specialization);
                System.out.println("+------------+-----------------------+--------------------+");

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getDoctorsById(int id){
        String query = "select * from doctors WHERE id = ?" ;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
            else {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
