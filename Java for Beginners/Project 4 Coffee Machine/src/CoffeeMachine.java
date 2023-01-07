import java.util.Scanner;
public class CoffeeMachine {

    static int[] getAmountNeeded(){
        Scanner scanner = new Scanner(System.in);
        int[] amount = new int[4];
        int waterPerCup = 200;
        int milkPerCup = 50;
        int coffeePerCup = 15;

        System.out.println("Write how many cups of coffee you will need:");
        amount[3] = scanner.nextInt();
        amount[0] = amount[3] * waterPerCup;
        amount[1] = amount[3] * milkPerCup;
        amount[2] = amount[3] * coffeePerCup;
        return amount;
    }

    static int[] getAmountAvailable() {
        Scanner scanner = new Scanner(System.in);
        int[] amount = new int[4];

        System.out.println("Write how many ml of water the coffee machine has:");
        amount[0] = scanner.nextInt();
        System.out.println("Write how many ml of milk the coffee machine has:");
        amount[1] = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans the coffee machine has:");
        amount[2] = scanner.nextInt();
        System.out.println("Write how many cups of coffee you will need:");
        amount[3] = scanner.nextInt();

        return amount;
    }

    static String getAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        return scanner.next();
    }

    static void buyCoffee(int[] status) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
        String type = scanner.next();
        switch (type) {
            case "1" -> {
                if (status[0] < 250) {
                    System.out.println("Sorry, not enough water!");
                } else if (status[2] < 16) {
                    System.out.println("Sorry, not enough coffee beans!");
                } else if (status[3] <= 0) {
                    System.out.println("Sorry, not enough disposable cups!");
                } else {
                    System.out.println("I have enough resources, making you a coffee!");
                    status[0] -= 250;
                    status[2] -= 16;
                    status[3] -= 1;
                    status[4] += 4;
                }

            }
            case "2" -> {
                if (status[0] < 350) {
                    System.out.println("Sorry, not enough water!");
                } else if(status[1] < 75) {
                    System.out.println("Sorry, not enough milk!");
                } else if (status[2] < 20) {
                    System.out.println("Sorry, not enough coffee beans!");
                } else if (status[3] <= 0) {
                    System.out.println("Sorry, not enough disposable cups!");
                } else {
                    System.out.println("I have enough resources, making you a coffee!");
                    status[0] -= 350;
                    status[1] -= 75;
                    status[2] -= 20;
                    status[3] -= 1;
                    status[4] += 7;
                }
            }
            case "3" -> {
                if (status[0] < 200) {
                    System.out.println("Sorry, not enough water!");
                } else if(status[1] < 100) {
                    System.out.println("Sorry, not enough milk!");
                } else if (status[2] < 12) {
                    System.out.println("Sorry, not enough coffee beans!");
                } else if (status[3] <= 0) {
                    System.out.println("Sorry, not enough disposable cups!");
                } else {
                    System.out.println("I have enough resources, making you a coffee!");
                    status[0] -= 200;
                    status[1] -= 100;
                    status[2] -= 12;
                    status[3] -= 1;
                    status[4] += 6;
                }
            }
            case "back" -> {
            }
        }
    }
    static void fillMachine(int[] status) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add:");
        status[0] += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        status[1] += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        status[2] += scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        status[3] += scanner.nextInt();
    }

    static void giveMoney(int[] status) {
        System.out.printf("I gave you $%d\n", status[4]);
        status[4] = 0;
    }

    static void printProcessingMessage() {
        System.out.println("""
                Starting to make a coffee
                Grinding coffee beans
                Boiling water
                Mixing boiled water with crushed coffee beans
                Pouring coffee into the cup
                Pouring some milk into the cup
                Coffee is ready!""");
    }
    static void printStatus(int[] status){
        System.out.printf("""
                The coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money
                """,
                status[0], status[1], status[2], status[3], status[4]);
    }

    static void printAmountNeeded(int[] needed) {
        System.out.printf("""
                For %d cups of coffee you will need:
                %d ml of water
                %d ml of milk
                %d g of coffee beans""",
                needed[0], needed[1], needed[2], needed[3]);
    }

    static void printCupsPossible (int[] available) {
        int waterPerCup = 200;
        int milkPerCup = 50;
        int coffeePerCup = 15;
        int cupsWanted = available[3];
        int cupsPossible = 0;

        if (available[0] >= waterPerCup && available[1] >= milkPerCup && available[2] >= coffeePerCup) {
            cupsPossible = available[0] / waterPerCup;
            cupsPossible = Math.min(cupsPossible, available[1] / milkPerCup);
            cupsPossible = Math.min(cupsPossible, available[2] / coffeePerCup);
        }

        if (cupsPossible < cupsWanted) {
            System.out.printf("No, I can make only %d cup(s) of coffee", cupsPossible);
        } else if (cupsPossible == cupsWanted) {
            System.out.println("Yes, I can make that amount of coffee");
        } else {
            int addCups = cupsPossible - cupsWanted;
            System.out.printf("Yes, I can make that amount of coffee (and even %d more than that)", addCups);
        }

    }

    public static void main(String[] args) {
        int[] status = {400, 540, 120, 9, 550};
        while (true) {
            switch (getAction()) {
                case "buy" -> {
                    buyCoffee(status);
                }
                case "fill" -> {
                    fillMachine(status);
                }
                case "take" -> {
                    giveMoney(status);
                }
                case "remaining" -> {
                    printStatus(status);
                }
                case "exit" -> {
                    System.exit(0);
                }
            }
        }
    }
}