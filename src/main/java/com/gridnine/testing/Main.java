package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Filtering filter = new Filtering();

        List<Flight> flights = FlightBuilder.createFlights();
        System.out.println(flights);
        System.out.println("=======================================================================");
        System.out.println(filter.performFilter(flights, filter.departureInPast()));
        System.out.println("=======================================================================");
        System.out.println(filter.performFilter(flights, filter.arrivingDateBeforeDeparture()));
        System.out.println("=======================================================================");
        System.out.println(filter.performFilter(flights, filter.totalGroundTimeGreaterThanTwoHours()));
        System.out.println("All filters together");
        System.out.println(filter.performFilters(flights, List.of(filter.departureInPast(), filter.arrivingDateBeforeDeparture(), filter.totalGroundTimeGreaterThanTwoHours())));
    }
}