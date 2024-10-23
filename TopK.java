import java.util.Arrays;
import java.util.Scanner;

public class TopK {
    static int[] numbers;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: number of elements
        System.out.print("Enter the number of elements in the array: ");
        int n = scanner.nextInt();
        numbers = new int[n];

        // Input: array elements
        System.out.println("Enter the elements of the array:");
        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }

        // Input: value of K
        System.out.print("Enter the value of K: ");
        int K = scanner.nextInt();

        // Output: top K numbers
        System.out.print("Output: ");
        findTopKFrequent(K);

        scanner.close();
    }

    // Static method to print the top K numbers with the highest occurrences
    static void findTopKFrequent(int K) {
        // Find max number in the array to size the frequency array
        int maxNum = Arrays.stream(numbers).max().orElse(0);
        int[] frequency = new int[maxNum + 1];

        // Count frequency of each number
        for (int number : numbers) {
            frequency[number]++;
        }

        // Create a frequency array with number and their frequency
        int[][] freqArray = new int[maxNum + 1][2];
        for (int i = 0; i < frequency.length; i++) {
            freqArray[i][0] = i;         // Number
            freqArray[i][1] = frequency[i]; // Frequency
        }

        // Sort the frequency array first by frequency, then by number (if equal)
        Arrays.sort(freqArray, (a, b) -> {
            if (b[1] == a[1]) {
                return Integer.compare(b[0], a[0]); 
            }
            return Integer.compare(b[1], a[1]); 
        });

        // Print top K numbers
        for (int i = 0; i < K; i++) {
            System.out.print(freqArray[i][0] + " ");
        }
    }
}

