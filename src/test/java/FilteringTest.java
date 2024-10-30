import com.gridnine.testing.Flight;
import com.gridnine.testing.FlightBuilder;
import com.gridnine.testing.Filtering;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilteringTest {
    private final List<Flight> flights = FlightBuilder.createFlights();
    private final Filtering filter = new Filtering();

    @Test
    public void departureInPastTest() {
        List<Flight> flightsDepartureInFuture = new ArrayList<>(flights);
        flightsDepartureInFuture.remove(2);
        List<Flight> result = filter.performFilter(flights, filter.departureInPast());
        Assertions.assertIterableEquals(flightsDepartureInFuture, result);
    }

    @Test
    public void arrivingBeforeDepartureTest() {
        List<Flight> flightsDepartureBeforeArriving = new ArrayList<>(flights);
        flightsDepartureBeforeArriving.remove(3);
        List<Flight> result = filter.performFilter(flights, filter.arrivingDateBeforeDeparture());
        Assertions.assertIterableEquals(flightsDepartureBeforeArriving, result);
    }

    @Test
    public void totalTimeOnGroundTest() {
        List<Flight> flightsLessThanTwoHoursOnGround = new ArrayList<>(flights);
        flightsLessThanTwoHoursOnGround.remove(4);
        flightsLessThanTwoHoursOnGround.remove(4);
        List<Flight> result = filter.performFilter(flights, filter.totalGroundTimeGreaterThanTwoHours());
        Assertions.assertIterableEquals(flightsLessThanTwoHoursOnGround, result);
    }

    @Test
    public void emptyFilterTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> filter.performFilter(flights, null));
        assertEquals("filter cannot be null", exception.getMessage());
    }

    @Test
    public void emptyFiltersTest() {
        List<Flight> result = filter.performFilters(flights, List.of());
        Assertions.assertIterableEquals(flights, result);
    }

    @Test
    public void allFiltersTest() {
        List<Flight> flightsAllFilters = new ArrayList<>(flights);
        for (int i = 0; i < 4; i++) {
            flightsAllFilters.remove(2);
        }
        List<Flight> result = filter.performFilters(flights, List.of(filter.departureInPast(), filter.arrivingDateBeforeDeparture(), filter.totalGroundTimeGreaterThanTwoHours()));
        Assertions.assertIterableEquals(flightsAllFilters, result);
    }
}
