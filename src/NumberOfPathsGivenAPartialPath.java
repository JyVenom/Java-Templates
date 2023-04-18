import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class NumberOfPathsGivenAPartialPath {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out);

        String s = br.readLine();
        int[] path = new int[48];
        for (int i = 0; i < 48; i++) {
            char c = s.charAt(i);
            if (c == 'D') {
                path[i] = 2;
            } else if (c == 'L') {
                path[i] = 3;
            } else if (c == 'R') {
                path[i] = 1;
            } else if (c == '?') {
                path[i] = 4;
            }
        }

        boolean max = true;
        for (int i = 0; i < 48; i++) {
            if (path[i] != 4) {
                max = false;
                break;
            }
        }
        if (max) {
            pw.println(88418);
        } else {
            boolean[][] visited = new boolean[7][7];
            visited[0][0] = true;
            pw.println(findAns(visited, path, 0, 0, 0, 0));
        }

        pw.close();
    }

    private static int findAns(boolean[][] visited, int[] path, int row, int col, int count, int at) {
        if (row == 6 && col == 0) {
            if (at == 48) {
                count++;
            }
            return count;
        }

        if (path[at] == 4 || path[at] == 0) {
            if (row > 0) {
                if (!visited[row - 1][col]) {
                    boolean locked = !isPossible(visited, row - 2, col) && isPossible(visited, row - 1, col - 1) && isPossible(visited, row - 1, col + 1);

                    if (!locked) {
                        visited[row - 1][col] = true;
                        count = findAns(visited, path, row - 1, col, count, at + 1);
                        visited[row - 1][col] = false;
                    }
                }
            }
        }
        if (path[at] == 4 || path[at] == 1) {
            if (col < 6) {
                if (!visited[row][col + 1]) {
                    boolean locked = !isPossible(visited, row, col + 2) && isPossible(visited, row - 1, col + 1) && isPossible(visited, row + 1, col + 1);

                    if (!locked) {
                        visited[row][col + 1] = true;
                        count = findAns(visited, path, row, col + 1, count, at + 1);
                        visited[row][col + 1] = false;
                    }
                }
            }
        }
        if (path[at] == 4 || path[at] == 2) {
            if (row < 6) {
                if (!visited[row + 1][col]) {
                    boolean locked = !isPossible(visited, row + 2, col) && isPossible(visited, row + 1, col - 1) && isPossible(visited, row + 1, col + 1);

                    if (!locked) {
                        visited[row + 1][col] = true;
                        count = findAns(visited, path, row + 1, col, count, at + 1);
                        visited[row + 1][col] = false;
                    }
                }
            }
        }
        if (path[at] == 4 || path[at] == 3) {
            if (col > 0) {
                if (!visited[row][col - 1]) {
                    boolean locked = !isPossible(visited, row, col - 2) && isPossible(visited, row - 1, col - 1) && isPossible(visited, row + 1, col - 1);

                    if (!locked) {
                        visited[row][col - 1] = true;
                        count = findAns(visited, path, row, col - 1, count, at + 1);
                        visited[row][col - 1] = false;
                    }
                }
            }
        }
        return count;
    }

    private static boolean isPossible(boolean[][] visited, int row, int col) {
        return inBounds(row, col) && !visited[row][col];
    }

    private static boolean inBounds(int row, int col) {
        return row >= 0 && row <= 6 && col >= 0 && col <= 6;
    }
}
