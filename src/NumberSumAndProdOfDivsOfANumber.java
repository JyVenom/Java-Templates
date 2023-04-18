import java.io.IOException;

public class NumberSumAndProdOfDivsOfANumber {
    private static final byte[] buf = new byte[1314690];
    private static int curChar;

    public static void main(String[] args) throws IOException {
        curChar = 0;
        System.in.read(buf);

        int n = readInt();
        int[][] primes = new int[n][2];
        for (int i = 0; i < n; i++) {
            primes[i][0] = readInt();
            primes[i][1] = readInt();
        }

        long num = 1;
        for (int i = 0; i < n; i++) {
            num *= (primes[i][1] + 1);
            num %= 1000000007;
        }
        System.out.print(num);
        System.out.print(" ");
        long sum = 1;
        long modInv, temp;
        for (int i = 0; i < n; i++) {
            temp = binPow(primes[i][0], primes[i][1] + 1);
            if (temp == 0)
                temp = 1000000006;
            else
                temp -= 1;
            modInv = binPow(primes[i][0] - 1, 1000000005);
            temp *= modInv;
            temp %= 1000000007;
            sum *= temp;
            sum %= 1000000007;
        }
        System.out.print(sum);
        System.out.print(" ");
        boolean square = true;
        for (int i = 0; i < n; i++)
            if (primes[i][1] % 2 == 1) {
                square = false;
                break;
            }
        long N = 1;
        if (square) {
            for (int i = 0; i < n; i++) {
                N *= binPow(primes[i][0], primes[i][1] / 2);
                N %= 1000000007;
            }
            long num2 = 1;
            for (int i = 0; i < n; i++) {
                num2 *= (primes[i][1] + 1);
                num2 %= 1000000006;
            }
            System.out.print(binPow(N, num2));
        } else {
            for (int i = 0; i < n; i++) {
                N *= binPow(primes[i][0], primes[i][1]);
                N %= 1000000007;
            }
            long num2 = 1;
            int i, temp2;
            for (i = 0; i < n; i++) {
                temp2 = primes[i][1] + 1;
                if (temp2 % 2 == 0) {
                    num2 *= (temp2 / 2);
                    num2 %= 1000000006;
                    i++;
                    break;
                }
                num2 *= temp2;
                num2 %= 1000000006;
            }
            for (; i < n; i++) {
                num2 *= (primes[i][1] + 1);
                num2 %= 1000000006;
            }
            System.out.print(binPow(N, num2) % 1000000007);
        }
    }

    private static long binPow(long a, long b) {
        long res = 1;
        while (b > 0) {
            if (b % 2 == 1) {
                res *= a;
                res %= 1000000007;
            }
            a *= a;
            a %= 1000000007;
            b /= 2;
        }
        return res;
    }

    private static int readInt() {
        int c = buf[curChar++];
        while (c <= ' ')
            c = buf[curChar++];
        int res = 0;
        do
            res = res * 10 + c - '0';
        while ((c = buf[curChar++]) >= '0' && c <= '9');
        return res;
    }
}
