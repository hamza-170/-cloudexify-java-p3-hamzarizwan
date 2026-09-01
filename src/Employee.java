import java.io.Serializable;

public abstract class Employee implements Serializable {



    protected String name;
    protected String employeeID;
    protected double baseSalary;
    protected String department;

    private static int counter = 100;

    public Employee(String name, double baseSalary, String department) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty.");
        }

        if (baseSalary <= 0) {
            throw new IllegalArgumentException("Salary must be positive.");
        }

        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty.");
        }

        this.name = name.trim();
        this.baseSalary = baseSalary;
        this.department = department;
        this.employeeID = "EMP" + counter++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty.");
        }

        this.name = name.trim();
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        if (baseSalary <= 0) {
            throw new IllegalArgumentException("Salary must be positive.");
        }

        this.baseSalary = baseSalary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty.");
        }

        this.department = department.trim();
    }


    public static void updateCounter(int nextID) {
        if (nextID > counter) {
            counter = nextID;
        }
    }

    public abstract double calculateSalary();

    public void displayInfo() {
        System.out.println("\n--- Employee Information ---");
        System.out.println("ID: " + employeeID);
        System.out.println("Name: " + name);
        System.out.println("Department: " + department);
        System.out.printf("Base Salary: $%.2f%n", baseSalary);
        System.out.printf("Total Calculated Salary: $%.2f%n", calculateSalary());
    }

    @Override
    public String toString() {
        return String.format(
                "%-8s %-16s %-20s $%-12.2f $%-12.2f",
                employeeID,
                name,
                department,
                baseSalary,
                calculateSalary()
        );
    }
}