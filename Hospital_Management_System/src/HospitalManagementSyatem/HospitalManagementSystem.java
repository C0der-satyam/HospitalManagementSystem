package HospitalManagementSyatem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    public static final String url = "jdbc:mysql://localhost:3306/hospital";
    public static final String username = "root";
    public static final String password = "Satyam@1";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.fillInStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patients patients = new Patients(connection, sc);
            Doctors doctors = new Doctors(connection);
            while (true) {
                System.out.println("----Hospital Management System-----");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        patients.addPatient();
                        break;
                    case 2:
                        patients.viewPatients();
                        break;
                    case 3:
                        doctors.viewDoctors();
                        break;
                    case 4:
                        Book(patients, doctors, connection, sc);
                        break;
                    case 5:
                        System.out.println("Thank you for using HospitalEase");
                        return;
                    default:
                        System.out.println("Enter valid choice");
                        break;
                }
            }


        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public static void Book(Patients patients, Doctors doctors, Connection connection, Scanner scanner) {
        System.out.print("Enter Patient Id: ");
        int patientID = scanner.nextInt();
        System.out.print("Enter Doctor Id: ");
        int DoctorID = scanner.nextInt();
        System.out.print("Enter Appointment Date(YYYY-MM-DD): ");
        String AppointmentDate = scanner.next();
        if (patients.getPatientById(patientID) && doctors.getDoctorsById(DoctorID)) {
            if (check(DoctorID, AppointmentDate, connection)) {
                try {
                    String AppointmentQery = "INSERT INTO appointments(patient_id,doctor_id,appointmen_date) VALUE (?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(AppointmentQery);
                    preparedStatement.setInt(1, patientID);
                    preparedStatement.setInt(2, DoctorID);
                    preparedStatement.setString(3, AppointmentDate);
                    int rowsaffected = preparedStatement.executeUpdate();
                    if (rowsaffected > 0) {
                        System.out.println("Apointment Booked!!");
                    } else {
                        System.out.print("Appointment is not booked");
                    }
                } catch (SQLException e) {
                    e.fillInStackTrace();
                }
            }
        } else {
            System.out.print("Either Doctor and patient does not exist !!");
        }

    }

    public static boolean check(int doctorId, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id =? AND appointmen_date =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointmentDate);
            ResultSet resultset = preparedStatement.executeQuery();
            if (resultset.next()) {
                int count = resultset.getInt(1);
                return count == 0;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return false;
    }
}