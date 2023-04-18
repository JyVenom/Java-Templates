public class BinaryExponentiation {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(binPow(3, 75));
        System.out.println(binPow(7, 100, 1000000007));
        System.out.println(System.currentTimeMillis() - start);
    }

    private static long binPow(long a, long b) {
        long res = 1;
        while (b > 0) {
            if ((b & 1) != 0)
                res = res * a;
            a = a * a;
            b >>= 1;
        }
        return res;
    }

    private static long binPow(long a, long b, long m) {
        a %= m;
        long res = 1;
        while (b > 0) {
            if ((b & 1) != 0)
                res = res * a % m;
            a = a * a % m;
            b >>= 1;
        }
        return res;
    }
}
