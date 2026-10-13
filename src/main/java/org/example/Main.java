package org.example;



import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String URL =
            "jdbc:mysql://localhost:3306/employee_management";
    static final String USER = "root";
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Delete Employee");
            System.out.println("5. Update Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                addEmployee(sc);
            } else if (choice == 2) {
                viewEmployees();
            } else if (choice == 3) {
                searchEmployeeById(sc);
            } else if (choice == 4) {
                deleteEmployee(sc);
            } else if (choice == 5) {
                updateEmployee(sc);
            } else if (choice == 6) {
                System.out.println("Program closed!");
                break;
            } else {
                System.out.println("Invalid choice!");
            }
        }

        sc.close();
    }

    static void addEmployee(Scanner sc) {

        System.out.print("Enter employee name: ");
        String name = sc.nextLine();

        System.out.print("Enter employee email: ");
        String email = sc.nextLine();

        System.out.print("Enter department: ");
        String department = sc.nextLine();

        System.out.print("Enter salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        String query = "INSERT INTO employees " +
                "(name, email, department, salary) VALUES (?, ?, ?, ?)";

        try (Connection con =
                     DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, department);
            ps.setDouble(4, salary);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    static void viewEmployees() {
        String query = "SELECT * FROM employees";

        try (
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query)
        ) {
            System.out.println("\n===== ALL EMPLOYEES =====");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("--------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    static void searchEmployeeById(Scanner sc) {
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        String query = "SELECT * FROM employees WHERE id = ?";

        try (
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = con.prepareStatement(query)
        ) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\n===== EMPLOYEE FOUND =====");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Department: " + rs.getString("department"));
                    System.out.println("Salary: " + rs.getDouble("salary"));
                } else {
                    System.out.println("Employee not found!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    static void deleteEmployee(Scanner sc) {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        String query = "DELETE FROM employees WHERE id = ?";

        try (
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = con.prepareStatement(query)
        ) {
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee deleted successfully!");
            } else {
                System.out.println("Employee not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    static void updateEmployee(Scanner sc) {

        System.out.print("Enter Employee ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new name: ");
        String name = sc.nextLine();

        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        System.out.print("Enter new department: ");
        String department = sc.nextLine();

        System.out.print("Enter new salary: ");
        double salary = sc.nextDouble();
        sc.nextLine();

        String query = "UPDATE employees SET name = ?, email = ?, department = ?, salary = ? WHERE id = ?";

        try (
                Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = con.prepareStatement(query)
        ) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, department);
            ps.setDouble(4, salary);
            ps.setInt(5, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee updated successfully!");
            } else {
                System.out.println("Employee not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
