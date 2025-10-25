package HotelReservationsSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReservationUpdater {
    private Connection conn;
    private Scanner sc;

    public ReservationUpdater(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    public void updateReservation() {
        try {
            System.out.print("Enter reservation ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            String checkSql = "SELECT * FROM reservations WHERE reservations_id = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setInt(1, id);
            ResultSet rs = checkPs.executeQuery();

            if (!rs.next()) {
                System.out.println("Reservation not found.");
                return;
            }

            System.out.print("Enter new guest name: ");
            String name = sc.nextLine();
            System.out.print("Enter new room number: ");
            int room = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter new contact number: ");
            String contact = sc.nextLine();

            String updateSql = "UPDATE reservations SET guest_name = ?, room_number = ?, contact_number = ? WHERE reservations_id = ?";
            PreparedStatement ps = conn.prepareStatement(updateSql);
            ps.setString(1, name);
            ps.setInt(2, room);
            ps.setString(3, contact);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Reservation updated successfully!");
            } else {
                System.out.println("Update failed.");
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
