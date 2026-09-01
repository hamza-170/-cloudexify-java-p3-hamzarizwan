public class Developer extends Employee {

    private int yearsExperience;

    public Developer(String name, double baseSalary, int yearsExperience) {
        super(name, baseSalary, "Development");

        if (yearsExperience < 0) {
            throw new IllegalArgumentException(
                    "Years of experience cannot be negative."
            );
        }

        this.yearsExperience = yearsExperience;
    }

    public int getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(int yearsExperience) {
        if (yearsExperience < 0) {
            throw new IllegalArgumentException(
                    "Years of experience cannot be negative."
            );
        }

        this.yearsExperience = yearsExperience;
    }

    @Override
    public double calculateSalary() {
        double bonusRatio;

        if (yearsExperience >= 5) {
            bonusRatio = 0.20;
        } else {
            bonusRatio = 0.10;
        }

        return baseSalary + (baseSalary * bonusRatio);
    }
}