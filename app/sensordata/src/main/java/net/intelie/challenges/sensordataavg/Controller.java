package net.intelie.challenges.sensordataavg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {
    private MeasurementService service;
    private ReportFormatter reportFormatter;

    @Autowired
    public Controller(MeasurementService service, ReportFormatter reportFormatter) {
        this.service = service;
        this.reportFormatter = reportFormatter;
    }

    @GetMapping(path = "/", produces = "text/plain")
    public String index() {
        return "SENSOR-DATA-AVG: Calculate minute averages of measures\n"
                + "How to use this service:\n"
                + "  To send a new measurement, POST a JSON to /measures with the attributes:\n"
                + "          sensor (String), timestamp (YYYY-MM-DD_HH:mm:SS), value (number)\n"
                + "          (Content-Type must be application/json)\n"
                + "  To generate an average report, request /minute-report using GET\n";
    }

    @PostMapping(path = "/measures", consumes = "application/json", produces = "text/plain")
    public ResponseEntity<String> addMeasurement(@RequestBody Map<String, Object> measurement) {
        try {
            service.save(measurement);
            return ResponseEntity.ok("OK");

        } catch (MeasurementService.ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving measurement: " + e.getMessage());
        }
    }

    @GetMapping(path = "/minute-report", produces = "text/plain")
    public String getReport() {
        List<MeasurementService.ReportItem> reportItems = service.getMinuteAvgReport();
        return reportFormatter.format(reportItems);
    }
}
