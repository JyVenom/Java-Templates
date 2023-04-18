import java.io.*;
import java.util.*;

public class GetRowsAndColsAndSums {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.out")));

        //variables
        int n = Integer.parseInt(br.readLine());
        HashMap<Integer, ArrayList<Integer>> rows = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> cols = new HashMap<>();
        HashSet<Integer> tempRows = new HashSet<>();
        HashSet<Integer> tempCols = new HashSet<>();
        int[][] points = new int[n][2];
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            int col = Integer.parseInt(st.nextToken());
            int row = Integer.parseInt(st.nextToken());

            points[i][0] = row;
            points[i][1] = col;

            tempRows.add(row);
            tempCols.add(col);

            if (!rows.containsKey(row)) {
                rows.put(row, new ArrayList<>());
            }
            rows.get(row).add(col);
            if (!cols.containsKey(col)) {
                cols.put(col, new ArrayList<>());
            }
            cols.get(col).add(row);
        }

        //sort data
        ArrayList<Integer> rows2 = new ArrayList<>(tempRows);
        ArrayList<Integer> cols2 = new ArrayList<>(tempCols);
        Collections.sort(rows2);
        Collections.sort(cols2);
        for (Integer at : rows2) {
            Collections.sort(rows.get(at));
        }
        for (Integer at : cols2) {
            Collections.sort(cols.get(at));
        }
        //find sum of rows and cols.
        //by sum, i mean for point/col/x-value b in the row/y-value a, we should have at rows3.get(a).get(b) the sum of all edges from that point, (b, a), to all other points in that row
        //same thing for the cols/cols3
        //initialize variables
        HashMap<Integer, HashMap<Integer, Integer>> rows3 = new HashMap<>();
        HashMap<Integer, HashMap<Integer, Integer>> cols3 = new HashMap<>();
        for (int row : rows2) {
            rows3.put(row, new HashMap<>());
        }
        for (int col : cols2) {
            cols3.put(col, new HashMap<>());
        }
        //actual code to find the sums
        //row sums
        for (int row : rows2) {
            int temp = 0;
            for (int i = 1; i < rows.get(row).size(); i++) {
                temp += rows.get(row).get(i) - rows.get(row).get(0);
            }
            rows3.get(row).put(rows.get(row).get(0), temp);
            for (int i = 1; i < rows.get(row).size(); i++) {
                int I = i - 1;
                rows3.get(row).put(rows.get(row).get(i), rows3.get(row).get(rows.get(row).get(I)) + (((2 * i) - rows.get(row).size()) * (rows.get(row).get(i) - rows.get(row).get(I))));
            }
        }
        //col sums
        for (int col : cols2) {
            int temp = 0;
            for (int i = 1; i < cols.get(col).size(); i++) {
                temp += cols.get(col).get(i) - cols.get(col).get(0);
            }
            cols3.get(col).put(cols.get(col).get(0), temp);
            for (int i = 1; i < cols.get(col).size(); i++) {
                int I = i - 1;
                cols3.get(col).put(cols.get(col).get(i), cols3.get(col).get(cols.get(col).get(I)) + (((2 * i) - cols.get(col).size()) * (cols.get(col).get(i) - cols.get(col).get(I))));
            }
        }
        for (int[] point : points) {
            System.out.println(Arrays.toString(point));
        }

        pw.close();
    }
}
