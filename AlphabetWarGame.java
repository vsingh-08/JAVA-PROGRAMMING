import java.util.Scanner;

public class AlphabetWarGame {
    private char[] leftLetters;
    private int[] leftStrengths;
    private char[] rightLetters;
    private int[] rightStrengths;

    // Constructor - default Strengths
    public AlphabetWarGame() {
        leftLetters = new char[]{'w', 'p', 'b', 's'};
        leftStrengths = new int[]{4, 3, 2, 1};
        rightLetters = new char[]{'m', 'q', 'd', 'z'};
        rightStrengths = new int[]{4, 3, 2, 1};
    }

    // Constructor - custom strengths
    public AlphabetWarGame(char[] leftLetters, int[] leftStrengths, char[] rightLetters, int[] rightStrengths) {
        this.leftLetters = leftLetters;
        this.leftStrengths = leftStrengths;
        this.rightLetters = rightLetters;
        this.rightStrengths = rightStrengths;
    }

    // Method - play with a single word
    public String play(String word) {
        int leftScore = getScore(word, leftLetters, leftStrengths);
        int rightScore = getScore(word, rightLetters, rightStrengths);

        if (leftScore > rightScore) return "Left side wins!";
        else if (rightScore > leftScore) return "Right side wins!";
        else return "Let's fight again!";
    }

    // Method - play with two words
    public String play(String leftWord, String rightWord) {
        int leftScore = getScore(leftWord, leftLetters, leftStrengths);
        int rightScore = getScore(rightWord, rightLetters, rightStrengths);

        if (leftScore > rightScore) return "Left side wins!";
        else if (rightScore > leftScore) return "Right side wins!";
        else return "Let's fight again!";
    }

    // Method - calculate score
    private int getScore(String word, char[] letters, int[] strengths) {
        int score = 0;
        for (char ch : word.toCharArray()) {
            for (int i = 0; i < letters.length; i++) {
                if (ch == letters[i]) {
                    score += strengths[i];
                    break;
                }
            }
        }
        return score;
    }

    // Method - validate input strengths
    private static boolean validateInputs(String letters, int[] strengths) {
        return letters.length() == strengths.length;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Do you want to use default strengths? (yes/no): ");
        String useDefault = sc.nextLine().trim().toLowerCase();

        char[] leftLetters;
        int[] leftStrengths;
        char[] rightLetters;
        int[] rightStrengths;

        AlphabetWarGame game;

        if (useDefault.equals("yes")) {
            game = new AlphabetWarGame();
            System.out.println("Using default letters and strengths.");
            leftLetters = game.leftLetters;
            leftStrengths = game.leftStrengths;
            rightLetters = game.rightLetters;
            rightStrengths = game.rightStrengths;
        } else {
            // Custom strengths input
            System.out.print("Enter left-side letters (e.g., wpbs): ");
            String leftInput = sc.nextLine();
            System.out.print("Enter their corresponding strengths (e.g., 4 3 2 1): ");
            String[] leftStrengthsInput = sc.nextLine().split(" ");
            leftStrengths = new int[leftStrengthsInput.length];
            for (int i = 0; i < leftStrengthsInput.length; i++) {
                leftStrengths[i] = Integer.parseInt(leftStrengthsInput[i]);
            }

            System.out.print("Enter right-side letters (e.g., mqdz): ");
            String rightInput = sc.nextLine();
            System.out.print("Enter their corresponding strengths (e.g., 4 3 2 1): ");
            String[] rightStrengthsInput = sc.nextLine().split(" ");
            rightStrengths = new int[rightStrengthsInput.length];
            for (int i = 0; i < rightStrengthsInput.length; i++) {
                rightStrengths[i] = Integer.parseInt(rightStrengthsInput[i]);
            }

            // Validate input lengths
            if (!validateInputs(leftInput, leftStrengths) || !validateInputs(rightInput, rightStrengths)) {
                System.out.println("Error: The number of letters must match the number of strengths for both sides.");
                sc.close();
                return; 
            }
            
            // Convert inputs to char arrays
            leftLetters = leftInput.toCharArray();
            rightLetters = rightInput.toCharArray();

            game = new AlphabetWarGame(leftLetters, leftStrengths, rightLetters, rightStrengths);
        }

        System.out.print("Do you want to play with one word or two words? (1/2): ");
        int wordChoice = sc.nextInt();
        sc.nextLine();  

        if (wordChoice == 1) {
            System.out.print("Enter a word to evaluate: ");
            String word = sc.nextLine();
            String result = game.play(word);
            System.out.println(result);
        } else if (wordChoice == 2) {
            System.out.print("Enter the left-side word: ");
            String leftWord = sc.nextLine();
            System.out.print("Enter the right-side word: ");
            String rightWord = sc.nextLine();
            String result = game.play(leftWord, rightWord);
            System.out.println(result);
        } else {
            System.out.println("Invalid choice. Exiting.");
        }

        sc.close();
    }
}
