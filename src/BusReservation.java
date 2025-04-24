import java.util.*;

class Bus {
    int busNumber;
    String route;
    int totalSeats;
    int availableSeats;
    float fare;

    public Bus(int busNumber, String route, int totalSeats, float fare) {
        this.busNumber = busNumber;
        this.route = route;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.fare = fare;
    }
}

class Booking {
    int bookingID;
    int busNumber;
    int tickets;
    boolean paymentStatus;
    float totalFare;
    String userName;
    String mobileNumber;
    String paymentMethod;

    public Booking(int bookingID, int busNumber, int tickets, boolean paymentStatus, float totalFare,
                   String userName, String mobileNumber, String paymentMethod) {
        this.bookingID = bookingID;
        this.busNumber = busNumber;
        this.tickets = tickets;
        this.paymentStatus = paymentStatus;
        this.totalFare = totalFare;
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.paymentMethod = paymentMethod;
    }
}

public class BusReservation {
    static Scanner scanner = new Scanner(System.in);
    static List<Bus> buses = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static final String ADMIN_PASSWORD = "jatrajibon";

    public static void main(String[] args) {
        initializeBuses();
        while (true) {
            System.out.println("\n1. Admin\n2. User\n3. About Us\n4. Exit\nEnter your choice: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> {
                    if (adminLogin()) {
                        adminMenu();
                    } else {
                        System.out.println("Incorrect password. Access denied.");
                    }
                }
                case 2 -> userMenu();
                case 3 -> aboutUs();
                case 4 -> {
                    System.out.println("Thank you for using our system. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void initializeBuses() {
        buses.add(new Bus(1, "Dhaka to Chittagong", 40, 550.0f));
        buses.add(new Bus(2, "Dhaka to Rajshahi", 35, 500.0f));
        buses.add(new Bus(3, "Dhaka to Khulna", 30, 600.0f));
        buses.add(new Bus(4, "Dhaka to Sylhet", 25, 450.0f));
    }

    static boolean adminLogin() {
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        return ADMIN_PASSWORD.equals(password);
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu:\n1. View Buses\n2. View Bookings\n3. Back to Main Menu");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> viewBuses();
                case 2 -> viewBookings();
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void userMenu() {
        while (true) {
            System.out.println("\nUser Menu:\n1. Book Ticket\n2. View Buses\n3. View Bookings\n4. Back to Main Menu");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> bookTicket();
                case 2 -> viewBuses();
                case 3 -> viewBookings();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void aboutUs() {
        System.out.println("This Bus Reservation System is developed by Team BusVerse.");
    }

    static void viewBuses() {
        System.out.println("\nAvailable Buses:");
        for (Bus bus : buses) {
            System.out.println("Bus No: " + bus.busNumber + ", Route: " + bus.route + ", Total Seats: " + bus.totalSeats + ", Available: " + bus.availableSeats + ", Fare: " + bus.fare);
        }
    }

    static void viewBookings() {
        System.out.println("\nBooking Records:");
        for (Booking booking : bookings) {
            System.out.println("Booking ID: " + booking.bookingID + ", Bus No: " + booking.busNumber + ", Tickets: " + booking.tickets +
                    ", Payment: " + (booking.paymentStatus ? "Paid" : "Pending") + ", User: " + booking.userName +
                    ", Mobile: " + booking.mobileNumber + ", Method: " + booking.paymentMethod + ", Total Fare: " + booking.totalFare);
        }
    }

    static void bookTicket() {
        viewBuses();
        System.out.print("Enter Bus Number to book: ");
        int busNumber = getIntInput();
        Bus selectedBus = null;

        for (Bus bus : buses) {
            if (bus.busNumber == busNumber) {
                selectedBus = bus;
                break;
            }
        }

        if (selectedBus == null) {
            System.out.println("Invalid bus number.");
            return;
        }

        System.out.print("Enter number of tickets: ");
        int tickets = getIntInput();

        if (tickets <= 0 || tickets > selectedBus.availableSeats) {
            System.out.println("Invalid number of tickets. Not enough seats available.");
            return;
        }

        float totalFare = selectedBus.fare * tickets;

        System.out.print("Enter your name: ");
        String userName = scanner.nextLine();

        System.out.print("Enter your mobile number: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Enter payment method (e.g., bKash, Nagad, Rocket): ");
        String paymentMethod = scanner.nextLine();

        boolean paymentStatus = true;

        selectedBus.availableSeats -= tickets;
        int bookingID = bookings.size() + 1;
        Booking booking = new Booking(bookingID, busNumber, tickets, paymentStatus, totalFare, userName, mobileNumber, paymentMethod);
        bookings.add(booking);

        System.out.println("Booking successful. Your booking ID is: " + bookingID);
    }

    static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
