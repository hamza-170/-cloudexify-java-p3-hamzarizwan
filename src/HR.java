// HR.java
public class HR extends Employee {
    public HR(String name, double baseSalary) {
        super(name, baseSalary, "Human Resources");
    }

    @Override
    public double calculateSalary() {
        return baseSalary + (baseSalary * 0.05); // Fixed 5% bonus on base salary[cite: 2]
    }
}