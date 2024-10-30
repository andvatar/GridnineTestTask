package com.gridnine.testing;

import java.security.InvalidParameterException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;

public class Filtering {

    public Predicate<Flight> departureInPast() {
        return f -> f.getSegments()
                .get(0)
                .getDepartureDate()
                .isAfter(LocalDateTime.now());
    }

    public Predicate<Flight> arrivingDateBeforeDeparture() {
        return f -> f.getSegments()
                .stream()
                .allMatch(s -> s.getArrivalDate().isAfter(s.getDepartureDate()));
    }

    public Predicate<Flight> totalGroundTimeGreaterThanTwoHours() {
        return f -> {
            List<Segment> segments = f.getSegments();
            Duration totalTime = Duration.ZERO;
            for (int i = 0; i < segments.size() - 1; i++) {
                totalTime = totalTime.plus(Duration.between(segments.get(i).getArrivalDate(), segments.get(i + 1).getDepartureDate()));
            }
            return totalTime.compareTo(Duration.of(2, ChronoUnit.HOURS)) < 0;
        };
    }

    public List<Flight> performFilter(List<Flight> flights, Predicate<Flight> filter) {
        if(filter == null) {
            throw new InvalidParameterException("filter cannot be null");
        }
        return flights.stream().filter(filter).toList();
    }

    public List<Flight> performFilters(List<Flight> flights, List<Predicate<Flight>> filters) {
        Predicate<Flight> filter = filters.stream().reduce(f -> true, Predicate::and);
        return performFilter(flights, filter);
    }
}
