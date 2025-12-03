package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecureApp {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("--- SECURE APP ---");
            System.out.println("Приклади команд:");
            System.out.println("  insert student (id='1', name='Ivan', last_name='Franko', group='IP-11')");
            System.out.println("  delete student (id='1')");
            System.out.println("Введіть 'exit' для виходу.");

            while (true) {
                System.out.print("\nКоманда > ");
                String command = scanner.nextLine().trim();
                
                if (command.equalsIgnoreCase("exit")) break;

                if (command.toLowerCase().startsWith("insert")) {
                    insertStudent(conn, command);
                } else if (command.toLowerCase().startsWith("delete")) {
                    deleteStudent(conn, command);
                } else {
                    System.out.println("Невідома команда.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertStudent(Connection conn, String command) throws SQLException {
        // Парсинг параметрів за допомогою RegEx
        Pattern p = Pattern.compile("id='(.*?)'.*name='(.*?)'.*last_name='(.*?)'.*group='(.*?)'");
        Matcher m = p.matcher(command);

        if (m.find()) {
            String sql = "INSERT INTO student (id, name, last_name, group_name) VALUES (?, ?, ?, ?)";
            // Використання безпечного PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(m.group(1)));
                pstmt.setString(2, m.group(2));
                pstmt.setString(3, m.group(3));
                pstmt.setString(4, m.group(4));
                pstmt.executeUpdate();
                System.out.println("[УСПІХ] Студента додано.");
            }
        } else {
            System.out.println("[ПОМИЛКА] Невірний формат. Спробуйте: insert student (id='...', ...)");
        }
    }

    private static void deleteStudent(Connection conn, String command) throws SQLException {
        Pattern p = Pattern.compile("id='(.*?)'");
        Matcher m = p.matcher(command);

        if (m.find()) {
            String sql = "DELETE FROM student WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(m.group(1)));
                int rows = pstmt.executeUpdate();
                System.out.println("[УСПІХ] Видалено рядків: " + rows);
            }
        } else {
            System.out.println("[ПОМИЛКА] Невірний формат. Спробуйте: delete student (id='...')");
        }
    }
}