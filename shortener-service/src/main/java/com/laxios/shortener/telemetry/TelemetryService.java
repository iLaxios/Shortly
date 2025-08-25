package com.laxios.shortener.telemetry;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TelemetryService {

    private final MeterRegistry meterRegistry;

    public void incrementUrlCreated(String createdByUser) {
        meterRegistry.counter("url.created.count",
                        "user", createdByUser == null ? "anonymous" : createdByUser)
                .increment();
    }

    public void recordCacheHit(boolean hit) {
        meterRegistry.counter("url.cache.hits", "hit", String.valueOf(hit)).increment();
    }

    public void recordLatency(long millis) {
        meterRegistry.timer("url.request.latency")
                .record(millis, TimeUnit.MILLISECONDS);
    }

    public void incrementValidationFailed() {
        meterRegistry.counter("url.validation.failed.count").increment();
    }
}
