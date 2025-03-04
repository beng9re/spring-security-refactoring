package nextstep.security.builder;

import jakarta.servlet.Filter;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SecurityFilterOrderRegistration {
    private final List<OrderedFilter> filters = new ArrayList<>();

    private int currentFilterOrder = SecurityFilterOrder.values().length + 1;


    public void addFilter(Filter filter) {
        if (SecurityFilterOrder.isSecurityFilter(filter.getClass())) {
            filters.add(OrderedFilter.baseFilter(SecurityFilterOrder.findOrder(filter.getClass()), filter));
            return;
        }

        Order orderAnnotation = filter.getClass().getAnnotation(Order.class);
        if (orderAnnotation != null) {
            filters.add(OrderedFilter.baseFilter(orderAnnotation.value(), filter));
            return;
        }

        filters.add(OrderedFilter.baseFilter(currentFilterOrder, filter));
        currentFilterOrder++;

    }

    public void afterFilter(Class<? extends Filter> filterClass, Filter filter) {

    }

    public List<Filter> getFilters() {
        return filters.stream()
                .sorted(Comparator.comparingInt(OrderedFilter::getOrder))
                .map(OrderedFilter::getFilter)
                .toList();
    }
}
