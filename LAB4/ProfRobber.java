import java.util.Scanner;

abstract class Robber {

    
    abstract void RobbingClass();

    
    abstract int RowHouses(int[] rowHouses);
    abstract int RoundHouses(int[] roundHouses);
    abstract int SquareHouse(int[] squareHouses);
    abstract int MultiHouseBuilding(int[][] multiHouseBuilding);

   
    void MachineLearning() {
        System.out.println("I love MachineLearning.");
    }
}

class ProfRobber extends Robber {

    @Override
    void RobbingClass() {
        System.out.println("MScAI&ML");
    }

    @Override
    int RowHouses(int[] rowHouses) {
        int n = rowHouses.length;
        if (n == 0) return 0;
        if (n == 1) return rowHouses[0];
        int[] dp = new int[n];
        dp[0] = rowHouses[0];
        dp[1] = Math.max(rowHouses[0], rowHouses[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + rowHouses[i]);
        }
        return dp[n - 1];
    }

    @Override
    int RoundHouses(int[] roundHouses) {
        int n = roundHouses.length;
        if (n == 0) return 0;
        if (n == 1) return roundHouses[0];
        int max1 = RowHouses(java.util.Arrays.copyOfRange(roundHouses, 0, n - 1));
        int max2 = RowHouses(java.util.Arrays.copyOfRange(roundHouses, 1, n));
        return Math.max(max1, max2);
    }

    @Override
    int SquareHouse(int[] squareHouses) {
        return RowHouses(squareHouses); 
    }

    @Override
    int MultiHouseBuilding(int[][] multiHouseBuilding) {
        int total = 0;
        for (int[] houses : multiHouseBuilding) {
            total += RowHouses(houses);
        }
        return total;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProfRobber robber = new ProfRobber();

  //test robbing class and ml
        robber.RobbingClass();
        robber.MachineLearning();

  //test row houses
        System.out.print("Enter the number of row houses: ");
        int rowCount = scanner.nextInt();
        int[] rowHouses = new int[rowCount];
        System.out.println("Enter the money in each row house:");
        for (int i = 0; i < rowCount; i++) {
            rowHouses[i] = scanner.nextInt();
        }
        System.out.println("RowHouses Max: " + robber.RowHouses(rowHouses));

  //test roundhouses
        System.out.print("Enter the number of round houses: ");
        int roundCount = scanner.nextInt();
        int[] roundHouses = new int[roundCount];
        System.out.println("Enter the money in each round house:");
        for (int i = 0; i < roundCount; i++) {
            roundHouses[i] = scanner.nextInt();
        }
        System.out.println("RoundHouses Max: " + robber.RoundHouses(roundHouses));

  //test square houses
        System.out.print("Enter the number of square houses: ");
        int squareCount = scanner.nextInt();
        int[] squareHouses = new int[squareCount];
        System.out.println("Enter the money in each square house:");
        for (int i = 0; i < squareCount; i++) {
            squareHouses[i] = scanner.nextInt();
        }
        System.out.println("SquareHouse Max: " + robber.SquareHouse(squareHouses));

// test multiBuilding houses
        System.out.print("Enter the number of types of houses in the multi-house building: ");
        int typeCount = scanner.nextInt();
        int[][] multiHouseBuilding = new int[typeCount][];
        for (int i = 0; i < typeCount; i++) {
            System.out.print("Enter the number of houses for type " + (i + 1) + ": ");
            int houseCount = scanner.nextInt();
            multiHouseBuilding[i] = new int[houseCount];
            System.out.println("Enter the money in each house for type " + (i + 1) + ":");
            for (int j = 0; j < houseCount; j++) {
                multiHouseBuilding[i][j] = scanner.nextInt();
            }
        }
        System.out.println("MultiHouseBuilding Max: " + robber.MultiHouseBuilding(multiHouseBuilding));

        scanner.close();
    }
}