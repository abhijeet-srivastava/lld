package com.twisha;

public class Cell {
    private final Character row;
    private final Integer column;


    public Cell(Character row, Integer column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (!row.equals(cell.row)) return false;
        return column.equals(cell.column);
    }

    @Override
    public int hashCode() {
        int result = row.hashCode();
        result = 31 * result + column.hashCode();
        return result;
    }
}
