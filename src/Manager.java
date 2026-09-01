public class Manager extends Employee {
    

    private double managerBonus;

    public Manager(String name, double baseSalary, double managerBonus) {
        super(name, baseSalary, "Management");

        if (managerBonus < 0) {
            throw new IllegalArgumentException(
                    "Manager bonus cannot be negative."
            );
        }

        this.managerBonus = managerBonus;
    }

    public double getManagerBonus() {
        return managerBonus;
    }

    public void setManagerBonus(double managerBonus) {
        if (managerBonus < 0) {
            throw new IllegalArgumentException(
                    "Manager bonus cannot be negative."
            );
        }

        this.managerBonus = managerBonus;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + managerBonus;
    }
}