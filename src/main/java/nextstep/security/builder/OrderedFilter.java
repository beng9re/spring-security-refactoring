package nextstep.security.builder;

import jakarta.servlet.Filter;

public class OrderedFilter {
    private final int order;
    private final int subOrder;
    private final Filter filter;

    private OrderedFilter(int order, int subOrder, Filter filter) {
        this.order = order;
        this.subOrder = subOrder;
        this.filter = filter;
    }

    public int getOrder() {
        return order;
    }

    public Filter getFilter() {
        return filter;
    }

    public int getSubOrder() {
        return subOrder;
    }

    public static OrderedFilter baseFilter(int order, Filter filter) {
        return new OrderedFilter(order, 0, filter);
    }

    public static OrderedFilter generateBeforeOrderFilter(OrderedFilter targetFilter,  Filter filter) {
        return new OrderedFilter(targetFilter.getOrder(), targetFilter.getSubOrder() - 1, filter);
    }

    public static OrderedFilter generateNextOrderFilter(OrderedFilter targetFilter,  Filter filter) {
        return new OrderedFilter(targetFilter.getOrder(), targetFilter.getSubOrder() + 1, filter);
    }
}
