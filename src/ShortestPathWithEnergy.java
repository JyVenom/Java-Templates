import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;

/**
 * Shortest path in a arid with obstacles if you have/start with limited energy and can refill energy if you go to a
 * specified grid location.
 */
public class ShortestPathWithEnergy {

    private static final int[] rowMods = {-1, 1, 0, 0};
    private static final int[] colMods = {0, 0, -1, 1};
    private static final ArrayDeque<int[]> torchArray = new ArrayDeque<>();
    private static int numRows = 0;
    private static int numCols = 0;
    private static int startRow = 0;
    private static int startCol = 0;
    private static int endRow = 0;
    private static int endCol = 0;
    private static char[][] field = null;
    private static int[][] artifactArray = null;
    private static boolean[] used = null;
    private static int[][] artifactDistances = null;
    private static int[][] tempDistance = null;
    private static int minDistance = Integer.MAX_VALUE;
    private static int treasureIndex = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("Prob16.in.txt"));
        PrintWriter pw = new PrintWriter(System.out);

        String inLine;
        ArrayDeque<String> lines = new ArrayDeque<>();
        while ((inLine = br.readLine()) != null) {
            lines.addFirst(inLine);
        }
        numRows = lines.size();
        numCols = lines.getFirst().length();
        field = new char[numRows][numCols];
        tempDistance = new int[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            inLine = lines.removeFirst();
            for (int col = 0; col < numCols; col++) {
                field[row][col] = inLine.charAt(col);

                if (field[row][col] == '|' || field[row][col] == '-') {
                    field[row][col] = 'x';
                } else if (field[row][col] == 't') {
                    torchArray.addLast(new int[]{row, col});
                }
            }
        }
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (field[row][col] == 'H') {
                    startRow = row;
                    startCol = col;
                    break;
                }
            }
        }
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (field[row][col] == 'T') {
                    endRow = row;
                    endCol = col;
                }
            }
        }
        br.close();

        artifactArray = new int[torchArray.size() + 2][2];
        artifactArray[0][0] = startRow;
        artifactArray[0][1] = startCol;
        int end = torchArray.size() + 1;
        for (int i = 1; i < end; i++) {
            artifactArray[i] = torchArray.removeFirst();
        }
        artifactArray[artifactArray.length - 1][0] = endRow;
        artifactArray[artifactArray.length - 1][1] = endCol;
        treasureIndex = artifactArray.length - 1;
        artifactDistances = new int[artifactArray.length][artifactArray.length];
        for (int i = 0; i < artifactArray.length; i++) {
            for (int j = 0; j < artifactArray.length; j++) {
                artifactDistances[i][j] = -1;
            }
        }
        used = new boolean[artifactArray.length];
        used[0] = true;
        solveRecursively(0, 0, 15);

        pw.println(minDistance);
        pw.close();
    }

    /**
     * Recursive method to solve the game.  See the solution notes for a detailed explanation.
     */
    private static void solveRecursively(int currentIndex, int currentDistance, int currentLightLeft) {
        if (currentIndex == treasureIndex) {
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
            }
        } else {
            int distanceToTreasure = getDistance(currentIndex, treasureIndex);

            if (currentLightLeft >= distanceToTreasure) {
                solveRecursively(treasureIndex, currentDistance + distanceToTreasure, currentLightLeft - distanceToTreasure);
            } else {
                for (int i = 1; i < treasureIndex; i++) {
                    if (!used[i]) {
                        int distanceToNextTorch = getDistance(currentIndex, i);

                        if (distanceToNextTorch > -1) {
                            if (currentLightLeft >= distanceToNextTorch) {
                                int distanceFromNextTorchToTreasure = getDistance(i, treasureIndex);

                                if ((currentDistance + distanceToNextTorch + distanceFromNextTorchToTreasure) < minDistance) {
                                    used[i] = true;
                                    solveRecursively(i, currentDistance + distanceToNextTorch, currentLightLeft + 15 - distanceToNextTorch);
                                    used[i] = false;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to get the distance from place to place.  Uses a lookup table.
     */
    private static int getDistance(int fromIndex, int toIndex) {
        int retVal = artifactDistances[fromIndex][toIndex];

        if (retVal == -1) {
            computeDistance(toIndex);
            retVal = artifactDistances[fromIndex][toIndex];
        }

        return retVal;
    }

    /**
     * Computes the distance to a destination
     */
    private static void computeDistance(int toIndex) {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                tempDistance[row][col] = Integer.MAX_VALUE;
            }
        }

        int toRow = artifactArray[toIndex][0];
        int toCol = artifactArray[toIndex][1];

        tempDistance[toRow][toCol] = 0;
        boolean newDistance = true;

        while (newDistance) {
            newDistance = false;
            for (int row = 1; row < numRows - 1; row++) {
                for (int col = 1; col < numCols - 1; col++) {
                    if (field[row][col] != 'x') {
                        int originalDistance = tempDistance[row][col];

                        for (int i = 0; i < rowMods.length; i++) {
                            int newRow = row + rowMods[i];
                            int newCol = col + colMods[i];

                            if (tempDistance[newRow][newCol] < tempDistance[row][col]) {
                                tempDistance[row][col] = tempDistance[newRow][newCol] + 1;
                            }
                        }

                        if (tempDistance[row][col] != originalDistance) {
                            newDistance = true;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < artifactArray.length; i++) {
            int row = artifactArray[i][0];
            int col = artifactArray[i][1];

            int distance = tempDistance[row][col];
            if (distance == Integer.MAX_VALUE) {
                distance = -2;
            }
            artifactDistances[i][toIndex] = artifactDistances[toIndex][i] = distance;
        }
    }
}
