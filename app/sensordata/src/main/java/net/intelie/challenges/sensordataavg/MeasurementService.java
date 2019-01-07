package net.intelie.challenges.sensordataavg;

import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

import static net.intelie.challenges.sensordataavg.utils.MapUtils.newMap;

/*
 Note: this implementation is intended for use in labs and exercises. It is not optimized for a high throughput
 */
@Service
public class MeasurementService {

    private static final String TIMESTAMP_FORMAT = "yyyy-MM-dd_HH:mm:ss";

    private static final String DB_NAME = "sensor_data";
    private static final String COL_NAME = "measurements";

    private final MongoClient mongoClient;

    public MeasurementService() {
        this.mongoClient = new MongoClient();
    }

    public void save(Map<String, Object> measurement) throws ValidationException {
        basicValidation(measurement);

        String sensor = (String) measurement.get("sensor");
        String rawTimestamp = (String) measurement.get("timestamp");
        Number value = (Number) measurement.get("value");

        Date timestamp;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(TIMESTAMP_FORMAT);
            dateFormat.setLenient(false);
            timestamp = dateFormat.parse(rawTimestamp);
        } catch (ParseException e) {
            throw new ValidationException("Invalid timestamp: " + rawTimestamp + ". Use format " + TIMESTAMP_FORMAT);
        }

        this.mongoClient.getDatabase(DB_NAME).getCollection(COL_NAME).insertOne(
                new Document()
                        .append("sensor", sensor)
                        .append("timestamp", timestamp)
                        .append("value", value)
        );
    }

    public List<ReportItem> getMinuteAvgReport() {
        AggregateIterable<Document> documents = this.mongoClient.getDatabase(DB_NAME).getCollection(COL_NAME).aggregate(Arrays.asList(
                new Document()
                        .append("$group",
                                newMap(
                                        "_id",
                                        newMap("sensor", "$sensor",
                                                "minute", newMap("$dateToString",
                                                        newMap("date", "$timestamp", "format", "%Y-%m-%dT%H:%M"))
                                        ),
                                        "minuteAvg", newMap("$avg", "$value"),
                                        "numMeasurements", newMap("$sum", 1)
                                )
                        ),
                new Document()
                        .append("$sort", newMap("_id.minute", 1, "_id.sensor", 1))
        ));

        List<ReportItem> result = new ArrayList<>();
        documents.forEach((Consumer<Document>) doc -> result.add(new ReportItem(
                (String) ((Document) doc.get("_id")).get("sensor"),
                (String) ((Document) doc.get("_id")).get("minute"),
                (Double) doc.get("minuteAvg"),
                (Integer) doc.get("numMeasurements"))
        ));

        return result;
    }

    private void basicValidation(Map<String, Object> measurement) throws ValidationException {
        if (measurement == null) throw new ValidationException("Object cannot be null");

        if (measurement.get("sensor") == null) throw new ValidationException("sensor cannot be null");
        if (measurement.get("timestamp") == null) throw new ValidationException("timestamp cannot be null");
        if (measurement.get("value") == null) throw new ValidationException("value cannot be null");

        if (!(measurement.get("sensor") instanceof String)) throw new ValidationException("sensor must be a String");
        if (!(measurement.get("timestamp") instanceof String))
            throw new ValidationException("timestamp must be a String");
        if (!(measurement.get("value") instanceof Number)) throw new ValidationException("value must be a Number");
    }

    // ---------------------------------------------------------------

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }

    public static class ReportItem {
        public final String sensor;
        public final String minute;
        public final Double minuteAvg;
        public final Integer numMeasurements;

        ReportItem(String sensor, String minute, Double minuteAvg, Integer numMeasurements) {
            this.sensor = sensor;
            this.minute = minute;
            this.minuteAvg = minuteAvg;
            this.numMeasurements = numMeasurements;
        }
    }
}
