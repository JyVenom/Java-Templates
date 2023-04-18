import java.io.*;
import java.util.StringTokenizer;

/*
instead of doing [ax][ay][bx][by], can do [# of moves made by a][# of moves made by b]
so, from 4d to 2d
this requires the path taken by a and b to be set
so, if a and b can move up, down, left right at each location, this may not work
can then squash 2d to 1d by changing in place instead of in a new (the next) row
 */
public class ArraySpaceOptimization4Dto2D {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("file.in"));
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("file.in")));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] f = new int[2]; //current/starting location of a
        f[0] = Integer.parseInt(st.nextToken());
        f[1] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        int[] b = new int[2]; //current/starting location of b
        b[0] = Integer.parseInt(st.nextToken());
        b[1] = Integer.parseInt(st.nextToken());
        String line = br.readLine();
        int[] fPath = new int[n];
        for (int i = 0; i < n; i++) { //get path taken by a
            fPath[i] = line.charAt(i);
        }
        line = br.readLine();
        int[] bPath = new int[m];
        for (int i = 0; i < m; i++) { //get path taken by b
            bPath[i] = line.charAt(i);
        }

        int[][] posF = new int[n + 1][2];
        posF[0] = f.clone();
        for (int i = 0; i < n; i++) { //record location of a after x moves for all n moves
            int I = i + 1;
            posF[I] = posF[i].clone();
            if (fPath[i] == 78) { //go up/north because decimal representation of 'N' is 78
                posF[I][1]++;
            } else if (fPath[i] == 69) { //go right/east because decimal representation of 'E' is 69
                posF[I][0]++;
            } else if (fPath[i] == 83) { //go down/south because decimal representation of 'S' is 83
                posF[I][1]--;
            } else if (fPath[i] == 87) { //go left/west because decimal representation of 'W' is 78
                posF[I][0]--;
            }
        }
        int[][] posB = new int[m + 1][2];
        posB[0] = b.clone();
        for (int i = 0; i < m; i++) { //record location of b after x moves for all m moves
            int I = i + 1;
            posB[I] = posB[i].clone();
            if (bPath[i] == 78) { //go up/north because decimal representation of 'N' is 78
                posB[I][1]++;
            } else if (bPath[i] == 69) { //go right/east because decimal representation of 'E' is 69
                posB[I][0]++;
            } else if (bPath[i] == 83) { //go down/south because decimal representation of 'S' is 83
                posB[I][1]--;
            } else if (bPath[i] == 87) { //go left/west because decimal representation of 'W' is 78
                posB[I][0]--;
            }
        }
        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) { //initialize dp array with values for if b never moves and a moved i times for i from 0 to n (no moves, 1 move, 2 moves, ..., all n moves)
            dp[i + 1] = dp[i] + (posF[i + 1][0] - b[0]) * (posF[i + 1][0] - b[0]) + (posF[i + 1][1] - b[1]) * (posF[i + 1][1] - b[1]);
        }
        int[] prev = dp.clone();
        for (int i = 1; i <= m; i++) { //if b first moved i times for i from 1 (because just already did 0) to m (1 move, 2 moves, 3 moves, ..., all m moves)
            dp[0] = prev[0] + (f[0] - posB[i][0]) * (f[0] - posB[i][0]) + (f[1] - posB[i][1]) * (f[1] - posB[i][1]);
            for (int j = 1; j <= n; j++) { //what if a then moved j times for j from 1 (just did 0 above) to n
                int cost = (posF[j][0] - posB[i][0]) * (posF[j][0] - posB[i][0]) + (posF[j][1] - posB[i][1]) * (posF[j][1] - posB[i][1]); //cost of current position
                int up = prev[j]; //if b just moved
                int left = dp[j - 1]; //if a just moved
                int dia = prev[j - 1]; //if a and b just moved at same time

                int min = Math.min(up, Math.min(left, dia)) + cost; //which state is smallest + cost to find min for dp
                dp[j] = min; //set to just found min from above;
            }
            prev = dp.clone();
        }


        pw.println(dp[n]);
        pw.close();
    }

}
