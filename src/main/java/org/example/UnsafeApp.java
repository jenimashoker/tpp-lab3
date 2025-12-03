package org.example;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class UnsafeApp {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Scanner scanner = new Scanner(System.in);
             Statement stmt = conn.createStatement()) {

            System.out.println("--- UNSAFE APP (SQL Injection Demo) ---");
            System.out.println("Введіть ID студента для видалення:");
            System.out.print("> ");
            
            String inputId = scanner.nextLine();

            // ВРАЗЛИВІСТЬ: Пряма склейка рядків
            String sql = "DELETE FROM student WHERE id = " + inputId; 
            
            System.out.println("Виконується SQL: " + sql);
            int rows = stmt.executeUpdate(sql);
            System.out.println("Видалено рядків: " + rows);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}