package net.intelie.challenges.sensordataavg;

import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;

@Service
public class ReportFormatter {

    private static final int LEN_SENSORNAME = 35;
    private static final int LEN_MINUTE = 17;
    private static final int LEN_NUMMEASURE = 15;
    private static final int LEN_AVG = 20;

    private static final int NUM_OF_SEPARATORS = 5;

    public String format(List<MeasurementService.ReportItem> items) {
        StringBuilder builder = new StringBuilder();

        String separator = repeat("-", LEN_SENSORNAME + LEN_MINUTE + LEN_NUMMEASURE + LEN_AVG + NUM_OF_SEPARATORS);
        String lineTemplate = "|%-" + LEN_SENSORNAME + "s|%-" + LEN_MINUTE + "s|%" + LEN_NUMMEASURE + "s|%" + LEN_AVG + "s|\n";

        builder.append(separator).append("\n");

        builder.append(String.format(lineTemplate, "Sensor", "Minute", "Num of measures", "Avg value"));

        builder.append(separator).append("\n");

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(4);

        items.forEach(item ->
                builder.append(String.format(lineTemplate,
                        item.sensor,
                        item.minute.replace("T", " "),
                        item.numMeasurements,
                        nf.format(item.minuteAvg))));

        builder.append(separator).append("\n");

        return builder.toString();
    }

    private String repeat(String s, int n) {
        return new String(new char[n]).replaceAll("\0", s);
    }
}
