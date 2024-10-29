import java.util.Scanner;

public class ShareTrader {
    static int maxProfit;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of stock prices: ");
        int n = scanner.nextInt();
        int[] prices = new int[n];

        System.out.println("Enter the stock prices:");
        for (int i = 0; i < n; i++) {
            prices[i] = scanner.nextInt();
        }

        maxProfit = findMaxProfit(prices);
        System.out.println("Maximum Profit: " + maxProfit);

        scanner.close();
    }

    static int findMaxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0; 
        }

        int n = prices.length;
        int profit1 = 0; 
        int profit2 = 0; 
        int minPrice1 = Integer.MAX_VALUE; 
        int minPrice2 = Integer.MAX_VALUE; 

        // First pass to calculate profit for two transactions
        for (int i = 0; i < n; i++) {
            minPrice1 = Math.min(minPrice1, prices[i]);
            profit1 = Math.max(profit1, prices[i] - minPrice1);
            minPrice2 = Math.min(minPrice2, prices[i] - profit1);
            profit2 = Math.max(profit2, prices[i] - minPrice2);
        }

        return profit2;
    }
}

