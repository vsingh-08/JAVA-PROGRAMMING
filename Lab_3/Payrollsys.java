import java.util.Scanner;

public class Payrollsys {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter details for Hourly Employee:");
        System.out.print("Employee ID: ");
        int hourlyEmpId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Employee Name: ");
        String hourlyEmpName = scanner.nextLine();
        System.out.print("Designation: ");
        String hourlyEmpDesignation = scanner.nextLine();
        System.out.print("Hourly Rate: ");
        double hourlyRate = scanner.nextDouble();
        System.out.print("Hours Worked: ");
        int hoursWorked = scanner.nextInt();
        HEmp hourlyEmp = new HEmp(hourlyEmpId, hourlyEmpName, hourlyEmpDesignation, hourlyRate, hoursWorked);

        System.out.println("\nEnter details for Salaried Employee:");
        System.out.print("Employee ID: ");
        int salariedEmpId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Employee Name: ");
        String salariedEmpName = scanner.nextLine();
        System.out.print("Designation: ");
        String salariedEmpDesignation = scanner.nextLine();
        System.out.print("Monthly Salary: ");
        double monthlySalary = scanner.nextDouble();
        SEmp salariedEmp = new SEmp(salariedEmpId, salariedEmpName, salariedEmpDesignation, monthlySalary);

        System.out.println("\nEnter details for Executive Employee:");
        System.out.print("Employee ID: ");
        int execEmpId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Employee Name: ");
        String execEmpName = scanner.nextLine();
        System.out.print("Designation: ");
        String execEmpDesignation = scanner.nextLine();
        System.out.print("Monthly Salary: ");
        double execMonthlySalary = scanner.nextDouble();
        System.out.print("Bonus Percentage: ");
        double bonusPercentage = scanner.nextDouble();
        ExEmp execEmp = new ExEmp(execEmpId, execEmpName, execEmpDesignation, execMonthlySalary, bonusPercentage);

        System.out.println("\n Hourly Emp Info");
        hourlyEmp.displayEmpInfo();
        System.out.println("\n Salaried Emp Info ");
        salariedEmp.displayEmpInfo();
        System.out.println("\n Executive Emp Info ");
        execEmp.displayEmpInfo();

        Employee[] employees = { hourlyEmp, salariedEmp, execEmp };
        System.out.println("\nTotal Payroll: " + Employee.calTotalPay(employees));
        scanner.close();
    }
}
class Employee {
    private int empId;
    private String empName;
    private String designation;

    public Employee(int empId, String empName, String designation) {
        this.empId = empId;
        this.empName = empName;
        this.designation = designation;
    }
    public double calBonus() {
        return 0.0;
    }
    public void displayEmpInfo() {
        System.out.println("ID: " + empId);
        System.out.println("Name: " + empName);
        System.out.println("Designation: " + designation);
    }
    public static double calTotalPay(Employee[] employees) {
        double totalPayroll = 0;
        for (Employee employee : employees) {
            if (employee instanceof HEmp) {
                totalPayroll += ((HEmp) employee).calWeeklySal() * 52;
            } else if (employee instanceof SEmp) {
                totalPayroll += ((SEmp) employee).calWeeklySal() * 52;
            }
        }
        return totalPayroll;
    }
}
class HEmp extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public HEmp(int empId, String empName, String designation, double hourlyRate, int hoursWorked) {
        super(empId, empName, designation);
        sethourlyRate(hourlyRate);
        sethoursWorked(hoursWorked);
    }
    public void sethourlyRate(double hourlyRate) {
        if (hourlyRate >= 0) {
            this.hourlyRate = hourlyRate;
        } else {
            throw new IllegalArgumentException("Hourly rate must be non-negative.");
        }
    }
    public void sethoursWorked(int hoursWorked) {
        if (hoursWorked >= 0 && hoursWorked <= 168) {
            this.hoursWorked = hoursWorked;
        } else {
            throw new IllegalArgumentException("Hours worked must be between 0 and 168.");
        }
    }
    public double calWeeklySal() {
        return hourlyRate * hoursWorked;
    }
    @Override
    public double calBonus() {
        return calWeeklySal() * 0.1;
    }
    @Override
    public void displayEmpInfo() {
        super.displayEmpInfo();
        System.out.println("Weekly Salary: " + calWeeklySal());
    }
}
class SEmp extends Employee {
    private double monthlySalary;

    public SEmp(int empId, String empName, String designation, double monthlySalary) {
        super(empId, empName, designation);
        setMonthlySalary(monthlySalary);
    }
    public double getMonthlySalary() {
        return monthlySalary;
    }
    public void setMonthlySalary(double monthlySalary) {
        if (monthlySalary >= 0) {
            this.monthlySalary = monthlySalary;
        } else {
            throw new IllegalArgumentException("Monthly salary must be non-negative.");
        }
    }
    public double calWeeklySal() {
        return monthlySalary / 4;
    }
    @Override
    public double calBonus() {
        return monthlySalary * 0.05;
    }
    @Override
    public void displayEmpInfo() {
        super.displayEmpInfo();
        System.out.println("Weekly Salary: " + calWeeklySal());
    }
}
class ExEmp extends SEmp {
    private double bonusPer;

    public ExEmp(int empId, String empName, String designation, double monthlySalary, double bonusPer) {
        super(empId, empName, designation, monthlySalary);
        setbonusPer(bonusPer);
    }
    public void setbonusPer(double bonusPer) {
        if (bonusPer >= 0 && bonusPer <= 100) {
            this.bonusPer = bonusPer;
        } else {
            throw new IllegalArgumentException("Bonus percentage must be between 0 and 100.");
        }
    }
    @Override
    public double calBonus() {
        double baseBonus = super.calBonus();
        return baseBonus + (getMonthlySalary() * 12 * bonusPer / 100);
    }
    @Override
    public void displayEmpInfo() {
        super.displayEmpInfo();
        System.out.println("Bonus Percentage: " + bonusPer);
    }
}
