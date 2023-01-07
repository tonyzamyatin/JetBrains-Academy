import java.util.Arrays;
import java.util.Scanner;
public class Main {
    static int[] getMetrics() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();

        return new int[]{rows, seats};
    }

    static char[][] getSeating(int[] metrics) {
        int rows = metrics[0];
        int seats = metrics[1];

        // Create two-dim. array to save selected seat in the seating scheme.
        char[][] seating = new char[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++){
                seating[i][j] = 'S';
            }
        }
        return seating;
    }

    static void printRevenue() {
        // Get user rows and number of seats from the user.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();

        // Calculate revenue depending on theater size.
        int totalSeats = rows * seats;

        int revenue = totalSeats * 10;
        if (totalSeats > 60) {
            revenue = (rows / 2) * seats * 10 + (rows - (rows / 2)) * seats * 8;
        }

        System.out.printf("Total income:%n$%d", revenue);
    }

    static int[] getSeat() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        int rowNum = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seatNum = scanner.nextInt();
        return new int[]{rowNum, seatNum};
    }

    static int calcTicketPrice(int[] theaterMetrics, int[] seat) {
        int rows = theaterMetrics[0];
        int seats = theaterMetrics[1];
        int myRow = seat[0];
        int totalSeats = rows * seats;

        int ticketPrice = 10;
        if (totalSeats > 60 && myRow > (rows / 2)) {
            ticketPrice = 8;
        }
        return ticketPrice;
    }

    static void printSelection (char[][] seating, int[] seat) {
        int rows = seating.length;
        int seats = seating[0].length;


        int myRow = seat[0] - 1;
        int mySeat = seat[1] - 1;

        // Build a string to display the seating scheme.
        // 1. The title and the top bar showing the number of the seat.
        String scheme = "\nCinema:\n ";
        for (int i = 1; i <= seats; i++) {
            scheme += " %d".formatted(i);
        }
        scheme += "\n";

        // 2. The left bar with the number of the row on the right and the seating scheme.
        for (int i = 0; i < rows; i++) {
            scheme += (i + 1);
            for (int j = 0; j < seats; j++) {
                scheme += " %c".formatted(seating[i][j]);
            }
            scheme += "\n";
        }
        System.out.println(scheme);
    }

    static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    static int getAction() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    static void printStats(char[][] seating) {
        int rows = seating.length;
        int seats = seating[0].length;
        int purchased = 0;
        int currentIncome = 0;
        int totalSeats = rows * seats;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                if (seating[i][j] == 'B') {
                    purchased++;
                    if (totalSeats > 60 && i >= rows / 2) {
                        currentIncome += 8;
                    } else {
                        currentIncome += 10;
                    }
                }
            }
        }

        double percentage = Math.round(((double) purchased / (double) totalSeats * 100.0) * 100.0) / 100.0;

        int totalIncome = totalSeats * 10;
        if (totalSeats > 60) {
            totalIncome = (rows / 2) * seats * 10 + (rows - (rows / 2)) * seats * 8;
        }

        System.out.println("Number of purchased tickets: " + purchased);
        System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    public static void main(String[] args) {
        int[] mySeat = new int[2];
        int action = 0;
        boolean run = true;

        int[] theatreMetrics = getMetrics();
        char[][] seating = getSeating(theatreMetrics);
        do {
            printMenu();
            action = getAction();
            switch (action) {
                case 1:
                    printSelection(seating, mySeat);
                    break;
                case 2:
                    boolean taken = true;
                    do {
                        mySeat = getSeat();
                        if (mySeat[0] < 1 ||
                            mySeat[1] < 1 ||
                            mySeat[0] > theatreMetrics[0] ||
                            mySeat[1] > theatreMetrics[1]
                        ) {
                            System.out.println("Wrong input!");
                            continue;
                        }
                        if (seating[mySeat[0]-1][mySeat[1]-1] != 'B') {
                            taken = false;
                        } else {
                            System.out.println("That ticket has already been purchased!");
                        }
                    } while (taken);
                    seating[mySeat[0]-1][mySeat[1]-1] = 'B';

                    System.out.printf("%nTicket price: $%d%n", calcTicketPrice(theatreMetrics, mySeat));
                    break;
                case 3:
                    printStats(seating);
                    break;
                case 0:
                    run = false;
            }
        } while (run);


        /*printScheme(theatreMetrics);
        int[] mySeat = getSeat();
        int myTicketPrice = calcTicketPrice(mySeat, theatreMetrics);
        System.out.printf("%nTicket price: $%d%n", myTicketPrice);
        printSelection(theatreMetrics, mySeat);*/
    }
}