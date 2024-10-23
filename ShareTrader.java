import java.util.Scanner;

public class ShareTrader {
    static int maxProfit;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take user input for the number of stock prices
        System.out.print("Enter the number of stock prices: ");
        int n = scanner.nextInt();
        int[] prices = new int[n];

        // Take user input for stock prices
        System.out.println("Enter the stock prices:");
        for (int i = 0; i < n; i++) {
            prices[i] = scanner.nextInt();
        }

        // Calculate maximum profit
        maxProfit = findMaxProfit(prices);
        System.out.println("Maximum Profit: " + maxProfit);

        // Close scanner after use
        scanner.close();
    }

    // Static method to find and return the maximum profit
    static int findMaxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0; // Not enough prices for transactions
        }

        int n = prices.length;
        int profit1 = 0; // Profit from the first transaction
        int profit2 = 0; // Profit from the second transaction
        int minPrice1 = Integer.MAX_VALUE; // Minimum price for first buy
        int minPrice2 = Integer.MAX_VALUE; // Minimum price for second buy

        // First pass to calculate profit for two transactions
        for (int i = 0; i < n; i++) {
            // Update the minimum price for the first buy
            minPrice1 = Math.min(minPrice1, prices[i]);
            // Calculate profit for the first transaction
            profit1 = Math.max(profit1, prices[i] - minPrice1);

            // Update the minimum price for the second buy (after first sell)
            minPrice2 = Math.min(minPrice2, prices[i] - profit1);
            // Calculate profit for the second transaction
            profit2 = Math.max(profit2, prices[i] - minPrice2);
        }

        return profit2;
    }
}

