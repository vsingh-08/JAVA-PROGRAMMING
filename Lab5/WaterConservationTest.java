import java.util.Scanner;
interface WaterconsvSys {
    int calTrappedWater(int[] blockHeights);
}
abstract class RainySznWater implements WaterconsvSys {
    @Override
    public abstract int calTrappedWater(int[] blockHeights);
}
class cityblockConsv extends RainySznWater {
    @Override
    public int calTrappedWater(int[] blockHeights) {
        int n = blockHeights.length;
        if (n == 0) return 0;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        int waterTrapped = 0;
        leftMax[0] = blockHeights[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], blockHeights[i]);
        }
        rightMax[n - 1] = blockHeights[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], blockHeights[i]);
        }
        for (int i = 0; i < n; i++) {
            waterTrapped += Math.min(leftMax[i], rightMax[i]) - blockHeights[i];
        }

        return waterTrapped;
    }
}
public class WaterConservationTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        cityblockConsv conservationSystem = new cityblockConsv();
        System.out.print("Enter the number of blocks: ");
        int numBlocks = scanner.nextInt();
        int[] blockHeights = new int[numBlocks];
        System.out.println("Enter the heights of the blocks:");
        for (int i = 0; i < numBlocks; i++) {
            blockHeights[i] = scanner.nextInt();
        }
        int result = conservationSystem.calTrappedWater(blockHeights);
        System.out.println("Total water trapped: " + result + " units");
    }
}
