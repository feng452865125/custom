package com.chunhe.custom.framework.datatables;

import com.chunhe.custom.utils.MyUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DataTablesRequest {
    /**
     * Draw counter. This is used by DataTables to ensure that the Ajax returns from server-side processing requests are drawn in sequence by DataTables
     * (Ajax requests are asynchronous and thus can return out of sequence). This is used as part of the draw return parameter (see below).
     */
    @NotNull
    @Min(0)
    private int draw = 1;

    /**
     * Paging first record indicator. This is the start point in the current data set (0 index based - i.e. 0 is the first record).
     */
    @NotNull
    @Min(0)
    private int start = 0;

    /**
     * Number of records that the table can display in the current draw. It is expected that the number of records returned will be equal to this number, unless
     * the server has fewer records to return. Note that this can be -1 to indicate that all records should be returned (although that negates any benefits of
     * server-side processing!)
     */
    @NotNull
    @Min(-1)
    private int length = 10;

    /**
     * @see Search
     */
    @NotNull
    private Search search = new Search();

    /**
     * @see Order
     */
    @JsonProperty("order")
    @NotEmpty
    private List<Order> orders = new ArrayList<>();

    /**
     * @see Column
     */
    @NotEmpty
    private List<Column> columns = new ArrayList<>();

    /**
     *
     * @return a {@link Map} of {@link Column} indexed by name
     */
    public Map<String, Column> getColumnsAsMap() {
        Map<String, Column> map = new HashMap<String, Column>();
        for (Column column : columns) {
            map.put(column.getData(), column);
        }
        return map;
    }

    /**
     * Find a column by its name
     *
     * @param columnName the name of the column
     * @return the given Column, or <code>null</code> if not found
     */
    public Column getColumn(String columnName) {
        if (columnName == null) {
            return null;
        }
        for (Column column : columns) {
            if (columnName.equals(column.getName())) {
                return column;
            }
        }
        return null;
    }

    /**
     * Add a new column
     *
     * @param columnName the name of the column
     * @param searchable whether the column is searchable or not
     * @param orderable whether the column is orderable or not
     * @param searchValue if any, the search value to apply
     */
    public void addColumn(String columnName, boolean searchable, boolean orderable,
                          String searchValue) {
        this.columns.add(new Column(columnName, "", searchable, orderable,
                new Search(searchValue, false)));
    }

    /**
     * Add an order on the given column
     *
     * @param columnName the name of the column
     * @param ascending whether the sorting is ascending or descending
     */
    public void addOrder(String columnName, boolean ascending) {
        if (columnName == null) {
            return;
        }
        for (int i = 0; i < columns.size(); i++) {
            if (!columnName.equals(columns.get(i).getData())) {
                continue;
            }
            orders.add(new Order(i, ascending ? "asc" : "desc"));
        }
    }

    /**
     * To string for orders
     *
     * @return
     */
    public String orders() {
        if (orders.size() == 0) {
            return null;
        }

        if(columns.size() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();

        for(Order order : orders){
            sb.append(String.format("%s %s,", MyUtils.underscoreName(columns.get(order.getColumn()).getName()), order.getDir()));
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * Created by xuqiang on 2017/6/14.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Search {
        /**
         * Global search value. To be applied to all columns which have searchable as true.
         */
        @NotNull
        private String value;

        /**
         * <code>true</code> if the global filter should be treated as a regular expression for advanced searching, false otherwise. Note that normally server-side
         * processing scripts will not perform regular expression searching for performance reasons on large data sets, but it is technically possible and at the
         * discretion of your script.
         */
        @NotNull
        private boolean regex;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Order {
        /**
         * Column to which ordering should be applied. This is an index reference to the columns array of information that is also submitted to the server.
         */
        @NotNull
        @Min(0)
        private int column;

        /**
         * Ordering direction for this column. It will be <code>asc</code> or <code>desc</code> to indicate ascending ordering or descending ordering,
         * respectively.
         */
        @NotNull
        @Pattern(regexp = "(desc|asc)")
        private String dir;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column {
        /**
         * Column's data source, as defined by columns.data.
         */
        @NotBlank
        private String data;

        /**
         * Column's name, as defined by columns.name.
         */
        private String name;

        /**
         * Flag to indicate if this column is searchable (true) or not (false). This is controlled by columns.searchable.
         */
        @NotNull
        private boolean searchable;


        /**
         * Flag to indicate if this column is orderable (true) or not (false). This is controlled by columns.orderable.
         */
        @NotNull
        private boolean orderable;

        /**
         * Search value to apply to this specific column.
         */
        @NotNull
        private Search search;

//        /**
//         * Flag to indicate if the search term for this column should be treated as regular expression (true) or not (false). As with global search, normally
//         * server-side processing scripts will not perform regular expression searching for performance reasons on large data sets, but it is technically possible
//         * and at the discretion of your script.
//         */
//        private boolean regex;

        /**
         * Set the search value to apply to this column
         *
         * @param searchValue if any, the search value to apply
         */
        public void setSearchValue(String searchValue) {
            this.search.setValue(searchValue);
        }
    }
}
