package HotelReservationsSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Reservation {
    private Connection conn;
    private Scanner sc;

    public Reservation(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    // Reserve Room: asks input one by one
    public void reserveRoom() {
        try {
            System.out.println();
            System.out.print("Enter guest name: ");
            String guestName = sc.nextLine();

            System.out.print("Enter room number: ");
            int roomNumber = sc.nextInt();
            sc.nextLine(); // consume newline

            System.out.print("Enter contact number: ");
            String contactNumber = sc.nextLine();

            String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, guestName);
            ps.setInt(2, roomNumber);
            ps.setString(3, contactNumber);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("\nReservation Successful!");
            } else {
                System.out.println("Reservation Failed!");
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    // View Reservations: neatly formatted table with wide date
    public void viewReservations() {
        String sql = "SELECT reservations_id, guest_name, room_number, contact_number, reservation_date FROM reservations";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) { // empty table check
                System.out.println("\nNo reservations found!");
                return;
            }

            System.out.println("\nCurrent Reservations:");
            System.out.println("+----+-----------------+-----------+----------------+--------------------------+");
            System.out.println("| ID | Guest           | Room No   | Contact        | Date                     |");
            System.out.println("+----+-----------------+-----------+----------------+--------------------------+");

            while (rs.next()) {
                int id = rs.getInt("reservations_id");
                String name = rs.getString("guest_name");
                int room = rs.getInt("room_number");
                String contact = rs.getString("contact_number");
                String date = rs.getTimestamp("reservation_date").toString();

                System.out.printf("| %-2d | %-15s | %-9d | %-14s | %-24s |\n", id, name, room, contact, date);
            }

            System.out.println("+----+-----------------+-----------+----------------+--------------------------+");

        } catch (SQLException e) {
            System.out.println("SQL Error while viewing reservations: " + e.getMessage());
        }
    }
}
