import java.util.ArrayList;

public class BinarySearch {
    public static void main(String[] args) {
        int a = binSearch(new int[]{0, 1, 3, 4}, 2, 0, 3);

        int b = binSearch(new int[][]{{0, 0}, {1, 0}, {3, 0}, {4, 0}}, 1, 0, 3);

        ArrayList<int[]> temp = new ArrayList<>();
        temp.add(new int[]{0, 0});
        temp.add(new int[]{1, 0});
        temp.add(new int[]{3, 0});
        temp.add(new int[]{4, 0});
        int c = binSearch(temp, 4, 0, 3);

        System.out.println(a + " " + b + " " + c);
    }

    private static int binSearch(int[] arr, int key, int low, int high) {
        int index = Integer.MAX_VALUE;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] < key) {
                low = mid + 1;
            } else if (arr[mid] > key) {
                high = mid - 1;
            } else if (arr[mid] == key) {
                index = mid;
                break;
            }
        }
        if (index == Integer.MAX_VALUE) {
            return (-1 * low) - 1;
        }
        else {
            return index;
        }
    }

//    private static int binSearch(ArrayList<Integer> arr, int key, int low, int high) {
//        int index = Integer.MAX_VALUE;
//
//        while (low <= high) {
//            int mid = (low + high) / 2;
//            if (arr.get(mid) < key) {
//                low = mid + 1;
//            } else if (arr.get(mid) > key) {
//                high = mid - 1;
//            } else if (arr.get(mid) == key) {
//                index = mid;
//                break;
//            }
//        }
//        if (index == Integer.MAX_VALUE) {
//            return (-1 * low) - 1;
//        }
//        else {
//            return index;
//        }
//    }

    private static int binSearch(ArrayList<Integer> arr, int key) {
        int low = 0, high = arr.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr.get(mid) < key) {
                low = mid + 1;
            } else if (arr.get(mid) > key) {
                high = mid - 1;
            } else if (arr.get(mid) == key) {
                return mid;
            }
        }
        return (-1 * low) - 1;
    }

    private static int binSearch(int[][] arr, int key, int low, int high) {
        int index = Integer.MAX_VALUE;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid][0] < key) {
                low = mid + 1;
            } else if (arr[mid][0] > key) {
                high = mid - 1;
            } else if (arr[mid][0] == key) {
                index = mid;
                break;
            }
        }
        if (index == Integer.MAX_VALUE) {
            return (-1 * low) - 1;
        }
        else {
            return index;
        }
    }

//    private static int binSearch(ArrayList<ArrayList<Integer>> arr, int key, int low, int high) {
//        int index = Integer.MAX_VALUE;
//
//        while (low <= high) {
//            int mid = (low + high) / 2;
//            if (arr.get(mid).get(0) < key) {
//                low = mid + 1;
//            } else if (arr.get(mid).get(0) > key) {
//                high = mid - 1;
//            } else if (arr.get(mid).get(0) == key) {
//                index = mid;
//                break;
//            }
//        }
//        if (index == Integer.MAX_VALUE) {
//            return (-1 * low) - 1;
//        }
//        else {
//            return index;
//        }
//    }

    private static int binSearch(ArrayList<int[]> arr, int key, int low, int high) {
        int index = Integer.MAX_VALUE;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr.get(mid)[0] < key) {
                low = mid + 1;
            } else if (arr.get(mid)[0] > key) {
                high = mid - 1;
            } else if (arr.get(mid)[0] == key) {
                index = mid;
                break;
            }
        }
        if (index == Integer.MAX_VALUE) {
            return (-1 * low) - 1;
        }
        else {
            return index;
        }
    }
}
