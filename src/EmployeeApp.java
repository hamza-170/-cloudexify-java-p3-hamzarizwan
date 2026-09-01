import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class EmployeeApp {

    private ArrayList<Employee> employees = new ArrayList<>();

    private static final String BLUE = "\u001B[34m";
    private static final String RESET = "\u001B[0m";

    private static final String FILE_NAME = "employees.dat";

    public void addEmployee(Employee emp) {

        if (emp == null) {
            System.out.println("Error: Employee cannot be null.");
            return;
        }

        try {
            if (emp.getBaseSalary() <= 0) {
                throw new IllegalArgumentException(
                        "Salary must be positive."
                );
            }

            for (Employee e : employees) {
                if (e.getEmployeeID().equalsIgnoreCase(emp.getEmployeeID())) {
                    System.out.println("\nEmployee ID already exists.");
                    return;
                }
            }

            employees.add(emp);
            saveToFile();

            System.out.println("\nEmployee added successfully!");

        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    public void removeEmployee(String id) {

        if (id == null || id.trim().isEmpty()) {
            System.out.println("\nEmployee ID cannot be empty.");
            return;
        }

        id = id.trim();

        Iterator<Employee> iterator = employees.iterator();

        while (iterator.hasNext()) {
            Employee employee = iterator.next();

            if (employee.getEmployeeID().equalsIgnoreCase(id)) {

                iterator.remove();
                saveToFile();

                System.out.println(
                        "\nEmployee " + id + " removed successfully."
                );

                return;
            }
        }

        System.out.println("\nEmployee ID not found.");
    }

    public void searchEmployeeById(String id) {

        if (id == null || id.trim().isEmpty()) {
            System.out.println("\nEmployee ID cannot be empty.");
            return;
        }

        id = id.trim();

        for (Employee employee : employees) {

            if (employee.getEmployeeID().equalsIgnoreCase(id)) {

                System.out.println("\n--- Employee Found ---");
                employee.displayInfo();

                return;
            }
        }

        System.out.println("\nEmployee ID not found.");
    }

    public void displayFormattedTable() {

        if (employees.isEmpty()) {
            System.out.println("\nNo employees available.");
            return;
        }

        System.out.println();
        System.out.println(
                "--------------------------------------------------------------------------------"
        );

        System.out.print("|");

        System.out.printf(
                "%s %-8s %-16s %-20s %-14s %-14s %s",
                BLUE,
                "ID",
                "Name",
                "Department",
                "Base Salary",
                "Total Salary",
                RESET
        );

        System.out.println("|");

        System.out.println(
                "--------------------------------------------------------------------------------"
        );

        for (Employee employee : employees) {

            System.out.printf(
                    "| %-8s %-16s %-20s $%-13.2f $%-13.2f |%n",
                    employee.getEmployeeID(),
                    employee.getName(),
                    employee.getDepartment(),
                    employee.getBaseSalary(),
                    employee.calculateSalary()
            );
        }

        System.out.println(
                "--------------------------------------------------------------------------------"
        );
    }

    public void sortBySalary() {

        if (employees.isEmpty()) {
            System.out.println("\nNo employees to sort.");
            return;
        }

        List<Employee> sortedEmployees = new ArrayList<>(employees);

        sortedEmployees.sort(
                Comparator.comparingDouble(Employee::calculateSalary)
        );

        System.out.println(
                "\n--- Employees Sorted by Total Salary (Ascending) ---"
        );

        System.out.println(
                "------------------------------------------------------------"
        );

        for (Employee employee : sortedEmployees) {

            System.out.printf(
                    "%-8s %-16s Total Salary: $%.2f%n",
                    employee.getEmployeeID(),
                    employee.getName(),
                    employee.calculateSalary()
            );
        }

        System.out.println(
                "------------------------------------------------------------"
        );
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No previous employee data found.");
            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file))) {

            Object object = in.readObject();

            if (!(object instanceof ArrayList<?>)) {
                throw new IOException("Invalid employee database format.");
            }

            ArrayList<?> loadedList = (ArrayList<?>) object;

            ArrayList<Employee> loadedEmployees = new ArrayList<>();

            int maxID = 99;

            for (Object item : loadedList) {

                if (!(item instanceof Employee)) {
                    throw new IOException(
                            "Employee database contains invalid data."
                    );
                }

                Employee employee = (Employee) item;

                loadedEmployees.add(employee);

                String employeeID = employee.getEmployeeID();

                if (employeeID != null &&
                        employeeID.startsWith("EMP")) {

                    try {

                        int idNumber = Integer.parseInt(
                                employeeID.substring(3)
                        );

                        if (idNumber > maxID) {
                            maxID = idNumber;
                        }

                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            employees = loadedEmployees;

            Employee.updateCounter(maxID + 1);

            System.out.println(
                    employees.size() +
                            " employee(s) loaded successfully."
            );

        } catch (FileNotFoundException e) {

            System.out.println("No previous employee data found.");

        } catch (InvalidClassException e) {

            System.out.println(
                    "Error: Employee database version is incompatible."
            );

        } catch (EOFException e) {

            System.out.println(
                    "Error: Employee database is empty or corrupted."
            );

        } catch (IOException e) {

            System.out.println(
                    "Error loading employee database: " +
                            e.getMessage()
            );

        } catch (ClassNotFoundException e) {

            System.out.println(
                    "Error: Employee class information could not be found."
            );
        }
    }

    public void saveToFile() {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(
                             new FileOutputStream(FILE_NAME))) {

            out.writeObject(employees);

        } catch (IOException e) {

            System.out.println(
                    "Error saving employee database: " +
                            e.getMessage()
            );
        }
    }

    public int getEmployeeCount() {
        return employees.size();
    }
}