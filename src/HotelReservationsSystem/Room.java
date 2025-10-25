package HotelReservationsSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Room {
    private Connection conn;
    private Scanner sc;

    public Room(Connection conn, Scanner sc) {
        this.conn = conn;
        this.sc = sc;
    }

    public void getRoomNumber() {
        try {
            System.out.print("Enter reservation ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "SELECT room_number, guest_name FROM reservations WHERE reservations_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int room = rs.getInt("room_number");
                String guest = rs.getString("guest_name");
                System.out.println("Reservation for " + guest + " is in Room Number: " + room);
            } else {
                System.out.println("Reservation not found.");
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
