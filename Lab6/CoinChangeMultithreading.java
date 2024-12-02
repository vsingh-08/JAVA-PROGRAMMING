import java.util.*;

public class CoinChangeMultithreading {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of coins: ");
        int N = sc.nextInt();

        int[] coins = new int[N];
        System.out.println("Enter the coin values:");
        for (int i = 0; i < N; i++) {
            coins[i] = sc.nextInt();
        }

        System.out.print("Enter the target sum: ");
        int sum = sc.nextInt();

        Set<List<Integer>> allCombi = Collections.synchronizedSet(new HashSet<>());
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            final int index = i;
            Thread thread = new Thread(() -> findCombi(coins, index, sum, new ArrayList<>(), allCombi));
            threads.add(thread);
            thread.start();
        }
        
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Total number of ways to make sum " + sum + " is: " + allCombi.size());
        System.out.println("Combinations are:");
        for (List<Integer> combination : allCombi) {
            System.out.println(combination);
        }
        sc.close();
    }

    private static void findCombi(int[] coins, int start, int target, List<Integer> currentCombi, Set<List<Integer>> allCombi) {
        if (target == 0) {
            allCombi.add(new ArrayList<>(currentCombi));
            return;
        }

        for (int i = start; i < coins.length; i++) {
            if (coins[i] <= target) {
                currentCombi.add(coins[i]);
                findCombi(coins, i, target - coins[i], currentCombi, allCombi);  
                currentCombi.remove(currentCombi.size() - 1);
            }
        }
    }
}
