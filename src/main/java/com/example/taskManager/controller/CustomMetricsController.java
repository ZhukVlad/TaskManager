package com.example.taskManager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/custom-metrics")
public class CustomMetricsController {

    @Autowired
    private MetricsEndpoint metricsEndpoint;

    @GetMapping("tasks-request-count")
    public Map<String, Double> getRequestCount() {
        MetricsEndpoint.MetricDescriptor metricDescriptor = metricsEndpoint.metric("http.server.requests", null);

        Map<String, Double> requestCounts = new HashMap<>();
        double totalCount = 0.0; // To track total counts for /tasks-related URIs

        if (metricDescriptor != null && metricDescriptor.getAvailableTags() != null) {
            // Extract available URIs and methods
            List<String> uris = metricDescriptor.getAvailableTags().stream()
                    .filter(tag -> tag.getTag().equals("uri"))
                    .flatMap(tag -> tag.getValues().stream())
                    .filter(uri -> uri.startsWith("/tasks")) // Filter only /tasks-related URIs
                    .toList();

            List<String> methods = metricDescriptor.getAvailableTags().stream()
                    .filter(tag -> tag.getTag().equals("method"))
                    .flatMap(tag -> tag.getValues().stream())
                    .toList();

            // Iterate over each combination of URI and method
            for (String uri : uris) {
                for (String method : methods) {
                    // Build the tag list in the correct format
                    List<String> tags = List.of("uri:" + uri, "method:" + method);

                    // Query metrics for each combination
                    MetricsEndpoint.MetricDescriptor filteredMetric = metricsEndpoint.metric("http.server.requests", tags);

                    if (filteredMetric != null && filteredMetric.getMeasurements() != null) {
                        double count = filteredMetric.getMeasurements().stream()
                                .filter(measurement -> measurement.getStatistic().name().equals("COUNT"))
                                .mapToDouble(measurement -> measurement.getValue())
                                .sum();

                        if (count > 0) {
                            requestCounts.put(method + " " + uri, count);
                            totalCount += count;
                        }
                    }
                }
            }
        }

        requestCounts.put("All /tasks Requests", totalCount);

        return requestCounts;
    }
}
