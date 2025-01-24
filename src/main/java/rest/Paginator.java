package rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Paginator<T> {
    private final int currentPage;
    private final int lastPage;
    private final int from;
    private final int to;
    private final int total;
    private final List<T> items;

    @JsonCreator
    public Paginator(
            @JsonProperty("currentPage") int currentPage,
            @JsonProperty("lastPage") int lastPage,
            @JsonProperty("from") int from,
            @JsonProperty("to") int to,
            @JsonProperty("total") int total,
            @JsonProperty("items") List<T> items
    ) {
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.from = from;
        this.to = to;
        this.total = total;
        this.items = items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public List<T> getItems() {
        return items;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int getTotal() {
        return total;
    }
}
