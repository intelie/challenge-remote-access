package net.intelie.challenges.sensordataavg;

import com.mongodb.MongoClient;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
            timestamp = new SimpleDateFormat(TIMESTAMP_FORMAT).parse(rawTimestamp);
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
        return null;
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

    }
}
