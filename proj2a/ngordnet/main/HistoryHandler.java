package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap map;
    public HistoryHandler(NGramMap map) {
        this.map = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        for (String word : words) {
            labels.add(word);
            lts.add(map.weightHistory(word,startYear,endYear));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        return Plotter.encodeChartAsString(chart);
    }
}
