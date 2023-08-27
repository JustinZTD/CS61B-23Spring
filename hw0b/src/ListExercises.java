import java.util.List;
import java.util.ArrayList;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        if (L.isEmpty()) {
            return 0;
        } else {
            int sum = 0;
            for (int i : L) {
                sum += i;
            }
            return sum;
        }
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evenList = new ArrayList<>();
        for (int i : L) {
            if (i % 2 == 0) {
                evenList.add(i);
            }
        }
        return evenList;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> commonNumber = new ArrayList<>();
        for (int i1 : L1) {
            if (L2.contains(i1) && !commonNumber.contains(i1)) {
                commonNumber.add(i1);
            }
        }
        return commonNumber;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int occurrences = 0;
        for (String word : words) {
            for (char charInWord : word.toCharArray()) {
                if (charInWord == c) {
                    occurrences += 1;
                }
            }
        }
        return occurrences;
    }
}
