package com.twisha.streamfilereader.query;

import com.twisha.streamfilereader.model.CSVRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Query {

    private final List<String> selectColumns;
    private final Predicate<CSVRow> filter;

    private final String groupByColumn;

    private final String aggregateColumn;

    private final String aggregateFunction;

    public Query(Builder builder) {
        this.selectColumns = builder.selectColumns;
        this.filter = builder.filter;
        this.groupByColumn = builder.groupByColumn;
        this.aggregateColumn = builder.aggregateColumn;
        this.aggregateFunction = builder.aggregateFunction;
    }

    public List<String> getSelectColumns() {
        return selectColumns;
    }

    public Predicate<CSVRow> getFilter() {
        return filter;
    }

    public String getGroupByColumn() {
        return groupByColumn;
    }

    public String getAggregateColumn() {
        return aggregateColumn;
    }

    public String getAggregateFunction() {
        return aggregateFunction;
    }

    public static class Builder {
        private List<String> selectColumns = new ArrayList<>();
        private Predicate<CSVRow> filter = row -> true;
        private String groupByColumn;
        private String aggregateColumn;
        private String aggregateFunction;

        public Builder select(String... columns) {
            this.selectColumns.addAll(Arrays.asList(columns));
            return this;
        }

        public Builder where(Predicate<CSVRow> filter) {
            this.filter = filter;
            return this;
        }

        public Builder groupBy(String column) {
            this.groupByColumn = column;
            return this;
        }

        public Builder aggregate(String function, String column) {
            this.aggregateFunction = function;
            this.aggregateColumn = column;
            return this;
        }

        public Query build() {
            return new Query(this);
        }
    }


}
