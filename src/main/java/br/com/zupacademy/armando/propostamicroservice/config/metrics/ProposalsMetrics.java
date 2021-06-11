package br.com.zupacademy.armando.propostamicroservice.config.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class ProposalsMetrics {

    private final MeterRegistry meterRegistry;
    private Counter counterProposalsCreated;
    private Timer timerProposalDetailsGet;

    public ProposalsMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.initializeCounters();
    }

    private void initializeCounters() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "armando-proposal-api"));
        tags.add(Tag.of("banco", "zupacademy-bank"));

        this.counterProposalsCreated = this.meterRegistry.counter("proposal_create", tags);
        this.timerProposalDetailsGet = this.meterRegistry.timer("proposal_details_get", tags);
    }

    public void incrementProposalsCreated() {
        this.counterProposalsCreated.increment();
    }

    public void timerProposalDetailsGetRecord(Duration duration) {
        this.timerProposalDetailsGet.record(duration);
    }

}
