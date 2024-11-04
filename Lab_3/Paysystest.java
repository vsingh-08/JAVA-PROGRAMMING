import java.util.Scanner;
public class Paysystest {
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

        System.out.println("\n Hourly Employee Details ");
        hourlyEmp.displayEmpInfo();
        System.out.println("Weekly Salary: " + hourlyEmp.calWeeklySal());
        System.out.println("Bonus: " + hourlyEmp.calBonus());
        System.out.println("Annual Earnings: " + hourlyEmp.calAnnualEarn());

        System.out.println("\n Salaried Employee Details ");
        salariedEmp.displayEmpInfo();
        System.out.println("Weekly Salary: " + salariedEmp.calWeeklySal());
        System.out.println("Bonus: " + salariedEmp.calBonus());
        System.out.println("Annual Earnings: " + salariedEmp.calAnnualEarn());

        System.out.println("\n Executive Employee Details ");
        execEmp.displayEmpInfo();
        System.out.println("Weekly Salary: " + execEmp.calWeeklySal());
        System.out.println("Bonus: " + execEmp.calBonus());
        System.out.println("Annual Earnings: " + execEmp.calAnnualEarn());

        Employee[] employees = {hourlyEmp, salariedEmp, execEmp};
        System.out.println("\nTotal Payroll for All Employees: " + Employee.calTotalPay(employees));
        scanner.close();
    }
}
abstract class Employee {
    private int empId;
    private String empName;
    private String designation;

    public Employee(int empId, String empName, String designation) {
        this.empId = empId;
        this.empName = empName;
        this.designation = designation;
    }
    public abstract double calWeeklySal();
    public double calBonus() {
        return 0.0;
    }
    public double calAnnualEarn() {
        return calWeeklySal() * 52;
    }
    public void displayEmpInfo() {
        System.out.println("Employee ID: " + empId);
        System.out.println("Employee Name: " + empName);
        System.out.println("Designation: " + designation);
    }
    public static double calTotalPay(Employee[] employees) {
        double totalPayroll = 0;
        for (Employee employee : employees) {
            totalPayroll += employee.calAnnualEarn();
        }
        return totalPayroll;
    }
}
class HEmp extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public HEmp(int empId, String empName, String designation, double hourlyRate, int hoursWorked) {
        super(empId, empName, designation);
        this.hourlyRate = hourlyRate >= 0 ? hourlyRate : 0;
        this.hoursWorked = hoursWorked >= 0 && hoursWorked <= 168 ? hoursWorked : 0;
    }
    @Override
    public double calWeeklySal() {
        return hourlyRate * hoursWorked;
    }
    @Override
    public double calBonus() {
        return calWeeklySal() * 0.1;
    }
}
class SEmp extends Employee {
    private double monthlySalary;

    public SEmp(int empId, String empName, String designation, double monthlySalary) {
        super(empId, empName, designation);
        this.monthlySalary = monthlySalary >= 0 ? monthlySalary : 0;
    }
    @Override
    public double calWeeklySal() {
        return monthlySalary / 4; 
    }
    @Override
    public double calBonus() {
        return monthlySalary * 0.05; 
    }
}
class ExEmp extends SEmp {
    private double bonusPercentage;

    public ExEmp(int empId, String empName, String designation, double monthlySalary, double bonusPercentage) {
        super(empId, empName, designation, monthlySalary);
        this.bonusPercentage = bonusPercentage >= 0 && bonusPercentage <= 100 ? bonusPercentage : 0;
    }
    @Override
    public double calBonus() {
        double baseBonus = super.calBonus(); 
        return baseBonus + (super.calWeeklySal() * 4 * 12 * bonusPercentage / 100); 
    }
}
