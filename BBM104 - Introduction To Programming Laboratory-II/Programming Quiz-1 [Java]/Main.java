import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static void printMenu(){
    	System.out.println("");
        System.out.println("1- Book a seat");
        System.out.println("2- Cancel booking");
        System.out.println("3- Print all passengers");
        System.out.println("4- Print available seats");
        System.out.println("5- Print all seats");
        System.out.println("6- Search passenger");
        System.out.println("7- Exit");
    }

    private static int scanChoice() {
	do {
        System.out.println("Enter your choose: ");
        int choice = scanner.nextInt();
        if ((choice < 8) & (0 < choice)) {
        	return choice;
    	} else {
    		System.out.println("Invalid parameter!!");
    		}
    	} while (true);
    	}

    private static boolean handleCommand(int choice, Bus bus) {
        switch (choice) {
            case 1:
                System.out.print("Enter seat id: ");
                int seatId = scanner.nextInt();
                System.out.println("");
                scanner.nextLine();
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.println("");

                System.out.print("Enter surname: ");
                String surName = scanner.nextLine();
                System.out.println("");

                Gender gender;
                do {
                    System.out.print("Enter gender: ");
                    String genderStr = scanner.nextLine();
                    genderStr = genderStr.toUpperCase();
                    if (genderStr.equals("K")) {
                        gender = Gender.FEMALE;
                        break;
                    } else if (genderStr.equals("E")) {
                        gender = Gender.MALE;
                        break;
                    } else {
                        System.out.println("Invalid parameter!!");
                        System.out.println("You can just type E or K");
                    }
                } while (true);
                System.out.println("");

                System.out.print("Enter country code: ");
                String countryCode = scanner.nextLine();
                System.out.println("");

                System.out.print("Enter code: ");
                String code = scanner.nextLine();
                System.out.println("");

                String number = "";
                do {
                    System.out.print("Enter number: ");
                    number = scanner.nextLine();
                    if (number.length() == 7) {
                        break;
                    } else {
                        System.out.println("Invalid number length!!");
                        System.out.println("Number must be 7 digits");
                    }
                } while (true);	
                System.out.println("");

                PhoneType type;
                do {
                    System.out.print("Enter type: ");
                    String typeStr = scanner.nextLine();
                    typeStr = typeStr.toUpperCase();
                    if (typeStr.equals("MOBİLE")) {
                        type = PhoneType.MOBILE;
                        break;
                    } else if (typeStr.equals("OFFİCE")) {
                        type = PhoneType.OFFICE;
                        break;
                    } else if (typeStr.equals("HOME")) {
                        type = PhoneType.HOME;
                        break;
                    } else {
                        System.out.println("Invalid parameter!!");
                        System.out.println("You can just type MOBİLE, OFFİCE and HOME");
                    }
                } while (true);
                System.out.println("");

                var phone = new Phone(countryCode, code, number, type);
                var passenger = new Passenger(name, surName, gender, phone);
                var seat = new Seat(seatId, true, passenger);
                bus.bookSeat(seat, seatId);
                break;

            case 2:
                System.out.print("Enter seat id: ");
                seatId = scanner.nextInt();
                System.out.println("");
                bus.cancelBooking(seatId);
                break;
            case 3:
                bus.printAllPaasengers();
                break;
            case 4:
                bus.printAllAvailableSeatIDs();
                break;
            case 5:
                bus.printAllSeats();
                break;
            case 6:
                scanner.nextLine();
                System.out.print("Enter name: ");
                name = scanner.nextLine();
                System.out.println("");

                System.out.print("Enter surname: ");
                surName = scanner.nextLine();
                System.out.println("");
                
                bus.search(name, surName);
                break;
            case 7:
                return false;
            default:
                System.out.println("Invalid choice!");
                break;
        }
        return true;
    }

    public static void main(String[] args) {
        Bus bus = new Bus("06 HUBM 06", 42);
        int choice = 0;
        do {
            printMenu();
            choice = scanChoice();
        } while (handleCommand(choice, bus));
    }
}