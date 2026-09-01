import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        EmployeeApp app = new EmployeeApp();

        app.loadFromFile();

        Scanner input = new Scanner(System.in);

        while (true) {

            displayMenu();

            int choice = readInt(
                    input,
                    "Choose (1-6): "
            );

            switch (choice) {

                case 1:
                    addEmployee(input, app);
                    break;

                case 2:
                    removeEmployee(input, app);
                    break;

                case 3:
                    searchEmployee(input, app);
                    break;

                case 4:
                    app.displayFormattedTable();
                    break;

                case 5:
                    app.sortBySalary();
                    break;

                case 6:
                    System.out.println(
                            "\nExiting Employee Management System. Goodbye!"
                    );

                    input.close();
                    return;

                default:
                    System.out.println(
                            "\nInvalid option. Please choose between 1 and 6."
                    );
            }
        }
    }



    private static void displayMenu() {

        System.out.println(
                "\n\n============== EMPLOYEE SYSTEM MENU =============="
        );

        System.out.println("1. Add Employee");
        System.out.println("2. Remove Employee");
        System.out.println("3. Search Employee by ID");
        System.out.println("4. Display All Employees (Formatted Table)");
        System.out.println("5. Sort Employees by Salary");
        System.out.println("6. Exit");
    }


    private static void addEmployee(
            Scanner input,
            EmployeeApp app
    ) {

        System.out.println("\nSelect Employee Type:");
        System.out.println("1. Manager");
        System.out.println("2. Developer");
        System.out.println("3. HR");

        int type = readInt(
                input,
                "Choice: "
        );

        if (type < 1 || type > 3) {

            System.out.println(
                    "\nInvalid employee type. Please choose 1, 2, or 3."
            );

            return;
        }

        String name = readNonEmptyString(
                input,
                "Enter Name: "
        );

        double salary = readPositiveDouble(
                input,
                "Enter Base Salary: "
        );

        try {

            if (type == 1) {

                double bonus = readNonNegativeDouble(
                        input,
                        "Enter Manager Bonus: "
                );

                Manager manager =
                        new Manager(name, salary, bonus);

                app.addEmployee(manager);

            } else if (type == 2) {

                int experience = readNonNegativeInt(
                        input,
                        "Enter Years of Experience: "
                );

                Developer developer =
                        new Developer(
                                name,
                                salary,
                                experience
                        );

                app.addEmployee(developer);

            } else {

                HR hr =
                        new HR(name, salary);

                app.addEmployee(hr);
            }

        } catch (IllegalArgumentException e) {

            System.out.println(
                    "\nError: " + e.getMessage()
            );
        }
    }


    private static void removeEmployee(
            Scanner input,
            EmployeeApp app
    ) {

        String id = readNonEmptyString(
                input,
                "Enter Employee ID to remove: "
        );

        app.removeEmployee(id);
    }


    private static void searchEmployee(
            Scanner input,
            EmployeeApp app
    ) {

        String id = readNonEmptyString(
                input,
                "Enter Employee ID to search: "
        );

        app.searchEmployeeById(id);
    }


    private static int readInt(
            Scanner input,
            String message
    ) {

        while (true) {

            System.out.print(message);

            String value = input.nextLine().trim();

            try {

                return Integer.parseInt(value);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! Please enter a whole number."
                );
            }
        }
    }

    private static int readNonNegativeInt(
            Scanner input,
            String message
    ) {

        while (true) {

            int value = readInt(input, message);

            if (value < 0) {

                System.out.println(
                        "Invalid input! Value cannot be negative."
                );

                continue;
            }

            return value;
        }
    }

    private static double readDouble(
            Scanner input,
            String message
    ) {

        while (true) {

            System.out.print(message);

            String value = input.nextLine().trim();

            try {

                double number = Double.parseDouble(value);

                if (Double.isNaN(number) ||
                        Double.isInfinite(number)) {

                    throw new NumberFormatException();
                }

                return number;

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input! Please enter a valid number."
                );
            }
        }
    }

    private static double readPositiveDouble(
            Scanner input,
            String message
    ) {

        while (true) {

            double value = readDouble(input, message);

            if (value <= 0) {

                System.out.println(
                        "Invalid input! Salary must be greater than 0."
                );

                continue;
            }

            return value;
        }
    }

    private static double readNonNegativeDouble(
            Scanner input,
            String message
    ) {

        while (true) {

            double value = readDouble(input, message);

            if (value < 0) {

                System.out.println(
                        "Invalid input! Value cannot be negative."
                );

                continue;
            }

            return value;
        }
    }

    private static String readNonEmptyString(
            Scanner input,
            String message
    ) {

        while (true) {

            System.out.print(message);

            String value = input.nextLine().trim();

            if (value.isEmpty()) {

                System.out.println(
                        "Invalid input! This field cannot be empty."
                );

                continue;
            }

            return value;
        }
    }
}