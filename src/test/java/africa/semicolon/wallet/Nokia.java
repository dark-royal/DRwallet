package africa.semicolon.wallet;

import java.util.Scanner;


public class Nokia {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);


        String menu = """
                
                	Welcome dear customer,press
                
                	1. phonebook
                	2. Messages
                	3. Chat
                	4. Call Register
                	5. Tones
                	6. Settings
                	7. Call Divert
                	8. Games
                	9. Calculator
                	10. Remainder
                	11. Clock
                	12. Profiles
                	13. Sim Services
                """;

        String phoneBook = """
                Phonebook menu
               \s
                Press
                	1: Search
                	2: Service Nos.
                	3: Add name
                	4: Erase
                	5: Edit\s
                	6: Assign tone
                	7: Send b'card
                	8: Options
                	9: Speed dials
                	10: Voice tags
               \s
               \s
               \s
               \s""";

        System.out.println(menu);
        int answer = input.nextInt();
        switch (answer) {

            case 1:
                System.out.print(phoneBook);
                int phoneBookMenu = input.nextInt();

                switch (phoneBookMenu) {
                    case 1 -> System.out.print("Search");
                    case 2 -> System.out.print("Service Nos.");
                    case 3 -> System.out.print("Add Name.");
                    case 4 -> System.out.print("Erase");
                    case 5 -> System.out.print("Edit");
                    case 6 -> System.out.print("Assign Tones");
                    case 7 -> System.out.print("Send b'card");
                    case 8 -> System.out.print("Options");
                            case 0 -> {
                        System.out.print("Enter option");
                        int option = input.nextInt();
                        switch (option) {

                            case 1 -> System.out.print("Type of view");
                            case 2 -> System.out.print("Memory status");
                        }
                    }
                }
        }
    }
}