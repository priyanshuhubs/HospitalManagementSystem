package HotelReservationsSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DriverClass {

    private static String url = "jdbc:mysql://localhost:3306/mydatabase";
    private static String user = "root";
    private static String password = "Priyanshu@12";

    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m";
    private static final String BLINK = "\u001B[5m";

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner sc = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            Reservation reservation = new Reservation(conn, sc);
            Room room = new Room(conn, sc);
            ReservationUpdater updater = new ReservationUpdater(conn, sc);
            ReservationDeleter deleter = new ReservationDeleter(conn, sc);

            while (true) {
                System.out.println(CYAN + "\n========= HOTEL MANAGEMENT SYSTEM =========" + RESET);
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> reservation.reserveRoom();
                    case 2 -> reservation.viewReservations();
                    case 3 -> room.getRoomNumber();
                    case 4 -> updater.updateReservation();
                    case 5 -> deleter.deleteReservation();
                    case 0 -> {
                        blinkingExit("Thank you for using Hotel Management System! Exiting");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice, try again!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void blinkingExit(String message) {
        System.out.print(BLINK + message + RESET);
        try {
            for (int i = 0; i < 5; i++) {
                System.out.print(".");
                Thread.sleep(500);
            }
            System.out.println("\n" + CYAN + "Goodbye!" + RESET);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
