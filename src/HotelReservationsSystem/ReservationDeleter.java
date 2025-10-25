package HotelReservationsSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ReservationDeleter {
    private Connection conn;
    private Scanner sc;

    public ReservationDeleter(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    public void deleteReservation() {
        try {
            System.out.print("Enter reservation ID to delete: ");
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

            String deleteSql = "DELETE FROM reservations WHERE reservations_id = ?";
            PreparedStatement ps = conn.prepareStatement(deleteSql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Reservation deleted successfully!");
            } else {
                System.out.println("Deletion failed.");
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
