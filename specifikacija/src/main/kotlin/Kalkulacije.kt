/**
 * An interface that defines a set of methods for performing various calculations
 * on tabular data based on column indices.
 */
interface Kalkulacije {
    /**
     * Calculates the sum of values in a specified column.
     *
     * @param columnIndex The index of the column whose values should be summed.
     */
    fun izracunajSum(columnIndex: Int)

    /**
     * Calculates the average value of a specified column.
     *
     * @param columnIndex The index of the column whose average value should be calculated.
     */
    fun izracunajAverage(columnIndex: Int)

    /**
     * Counts the total number of values in a specified column.
     *
     * @param columnIndex The index of the column where the count will be performed.
     */
    fun izracunajCount(columnIndex: Int)

    /**
     * Counts the number of values in a specified column that match a given value.
     *
     * @param columnIndex The index of the column to be searched.
     * @param value The value to match in the column.
     */
    fun izracunajCountIf(columnIndex: Int, value: Any)

    /**
     * Executes a specified operation on one or more columns.
     *
     * @param operacija The name of the operation to perform.
     * @param kolone A list of columns that are part of the operation.
     */
    fun operacija(operacija: String, kolone: List<Int>)

}