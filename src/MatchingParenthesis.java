/**
 * finds the maximum number of matching brackets ("(" and ")", "[" and "]", and "{" and "}")
 * in correct order so "([{}])" would be 3 but "([)]" would only be 1: either "()" or "[]'
 *
 * @author jerry
 * @version 1.0
 * @since 2020-11-27
 */

public class MatchingParenthesis {
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder("({)([)(])(})");
        int[][] data = new int[s.length()][s.length()];
        for (int i = s.length() - 2; i >= 0; i--) {
            for (int j = i + 1; j < s.length(); j++) {
                int max = data[i + 1][j - 1]; //don't use i or j
                if ((s.charAt(i) == '(' && s.charAt(j) == ')') || (s.charAt(i) == '{' && s.charAt(j) == '}') || (s.charAt(i) == '[' && s.charAt(j) == ']')) { //if i and j are matching, add 1
                    max++;
                }
                for (int k = i; k < j; k++) { //use i to k and k+1 to j and see if is better fo all k from i to j (exclusive because is included from k + 1)
                    max = Math.max(max, data[i][k] + data[k + 1][j]);
                }
                data[i][j] = max; //update with newfound max
            }
        }
    }
}
