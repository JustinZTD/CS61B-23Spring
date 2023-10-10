package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    private static HashMap<String,TimeSeries> wordsCount = new HashMap<>();
    private static TimeSeries wordsTotalCount = new TimeSeries();

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        In wordsFile = new In(wordsFilename);
        while (wordsFile.hasNextLine() & !wordsFile.isEmpty()) {
            String word = wordsFile.readString();
            int year = wordsFile.readInt();
            double count = wordsFile.readDouble();
            wordsFile.readDouble();
            wordsCount.computeIfAbsent(word, k -> new TimeSeries()).put(year, count);
        }
        In countsFile = new In(countsFilename);
        while (countsFile.hasNextLine() & !countsFile.isEmpty()) {
            String[] line = countsFile.readLine().split(",");
            int year = Integer.parseInt(line[0]);
            double count = Double.parseDouble(line[1]);
            wordsTotalCount.put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (wordsCount.containsKey(word)) {
            return new TimeSeries(wordsCount.get(word), startYear, endYear);
        }
        return new TimeSeries();
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        TimeSeries result = new TimeSeries();
        if (wordsCount.containsKey(word)) {
            return result.plus(wordsCount.get(word));
        }
        return result;
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        TimeSeries result = new TimeSeries();
        return result.plus(wordsTotalCount);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries countHistory = this.countHistory(word, startYear, endYear);
        return countHistory.dividedBy(wordsTotalCount);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year. If the word is not in the data files, return an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries countHistory = this.countHistory(word);
        return countHistory.dividedBy(wordsTotalCount);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        for (String word : words) {
            if (wordsCount.containsKey(word)) {
                TimeSeries countHistory = this.countHistory(word, startYear, endYear);
                result = result.plus(countHistory.dividedBy(wordsTotalCount));
            }
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries result = new TimeSeries();
        for (String word : words) {
            if (wordsCount.containsKey(word)) {
                TimeSeries countHistory = this.countHistory(word);
                result = result.plus(countHistory.dividedBy(wordsTotalCount));
            }
        }
        return result;
    }

}
