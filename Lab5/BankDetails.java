import java.util.Scanner;

interface BankInterface {
    double getBal(); 
    double getIntRate(); 
}

class BankA implements BankInterface {
    private double bal;
    private final double IntRate = 7.0;

    BankA(double bal) {
        this.bal = bal;
    }

    @Override
    public double getBal() {
        return bal;
    }

    @Override
    public double getIntRate() {
        return IntRate;
    }
}

class BankB implements BankInterface {
    private double bal;
    private final double IntRate = 7.4; 
    BankB(double bal) {
        this.bal = bal;
    }
    @Override
    public double getBal() {
        return bal;
    }
    @Override
    public double getIntRate() {
        return IntRate;
    }
}
class BankC implements BankInterface {
    private double bal;
    private final double IntRate = 7.9; 
    BankC(double bal) {
        this.bal = bal;
    }
    @Override
    public double getBal() {
        return bal;
    }
    @Override
    public double getIntRate() {
        return IntRate;
    }
}
public class BankDetails {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the bal for Bank A: ");
        double balA = scanner.nextDouble();
        BankA bankA = new BankA(balA);
        System.out.print("Enter the bal for Bank B: ");
        double balB = scanner.nextDouble();
        BankB bankB = new BankB(balB);
        System.out.print("Enter the bal for Bank C: ");
        double balC = scanner.nextDouble();
        BankC bankC = new BankC(balC);
        System.out.println("\nBank A:");
        System.out.println("bal: " + bankA.getBal());
        System.out.println("Interest Rate: " + bankA.getIntRate() + "%");
        System.out.println("\nBank B:");
        System.out.println("bal: " + bankB.getBal());
        System.out.println("Interest Rate: " + bankB.getIntRate() + "%");
        System.out.println("\nBank C:");
        System.out.println("bal: " + bankC.getBal());
        System.out.println("Interest Rate: " + bankC.getIntRate() + "%");
        scanner.close();
    }
}
