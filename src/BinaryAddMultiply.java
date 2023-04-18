public class BinaryAddMultiply {
    public static void main(String[] args) {
        System.out.println(sum(3, 5));
        System.out.println(prod(3, 5));
    }

    public static int sum(int a, int b) {
        while (b > 0) {
            int carry = a & b;
            a ^= b;
            b = carry << 1;
        }
        return a;
    }

    public static int prod(int a, int b) {
        int c = 0;
        while (b > 0) {
            if ((b & 1) == 1) {
                c = sum(c, a);
            }
            a <<= 1;
            b >>= 1;
        }
        return c;
    }
}
