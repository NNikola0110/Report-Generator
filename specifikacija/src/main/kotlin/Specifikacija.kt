import model.*
/**
 * An abstract class that is used for generating reports in various formats.
 *
 * The Specifikacija class serves as a base for implementing different report generation specifications.
 * It supports various data input formats and configurations for report generation.
 *
 * @property reportFormat The format specification for the report output
 * @property supportsFormat Flag that indicates whether the implementation supports custom formatting
 * @property supportsTitle Flag that indicates whether the implementation supports report titles
 * @property supportsRezime Flag that indicates whether the implementation supports report summaries
 */

abstract class Specifikacija() {

    abstract val reportFormat: ReportFormat
    abstract val supportsFormat:Boolean
    abstract val supportsTitle:Boolean
    abstract val supportsRezime:Boolean

    /**
     * Generates a report based on the provided Report object and saves it to the specified output file.
     *
     * @param report The Report object containing the data and configuration for report generation
     * @param outputFile The path where the generated report will be saved
     */
    abstract fun generate(report: Report,outputFile:String)


    /**
     * Generates a basic report from a list of data without column names or additional formatting.
     *
     * @param data A list of lists containing the report data
     * @param outputFile The path where the generated report should be saved
     */
    fun generateReport(data:List<List<Any>>, outputFile: String){
        var report = Report(data, null,null,null)
        generate(report,outputFile)
    }
    /**
     * Generates a report from a list of data with column names.
     *
     * @param data A list of lists containing the report data
     * @param columnNames The names of the columns in the report
     * @param outputFile The path where the generated report should be saved
     */
    fun generateReport(data:List<List<String>>, columnNames: MutableList<String>,outputFile: String){
        var report = Report(data, columnNames,null,null)
        generate(report,outputFile)
    }
    /**
     * Generates a complete report with column names, title, and summary.
     *
     * @param data A list of lists containing the report data
     * @param columnNames The names of the columns in the report
     * @param title The title of the report
     * @param rezime A list of pairs containing summary information
     * @param outputFile The path where the generated report should be saved
     */
    fun generateReport(data:List<List<String>>, columnNames: MutableList<String>, title: String, rezime: MutableList<Pair<String,Any>>,outputFile: String){
        var report = Report(data, columnNames,title,rezime)
        generate(report,outputFile)
    }
    /**
     * Generates a basic report from a map of data.
     *
     * @param data A map where keys are column names and values are lists of data
     * @param outputFile The path where the generated report should be saved
     */
    fun generateReport(data:Map<String,List<String>>,outputFile: String){
        var reportData = converteMapToString(data)
        var report = Report(reportData.second, null,null,null)
        generate(report,outputFile)
    }
    /**
     * Generates a report from a map of data with column names.
     *
     * @param data A map where keys are column names and values are lists of data
     * @param columnNames The names of the columns in the report
     * @param outputFile The path where the generated report should be saved
     */
    fun generateReport(data:Map<String,List<String>>,columnNames: MutableList<String>,outputFile: String){
        val (columnNames,reportData) = converteMapToString(data)
        val report = Report(reportData, columnNames,null,null)
        generate(report,outputFile)
    }
    /**
     * Generates a complete report from a map of data with column names, title, and summary.
     *
     * @param data A map where keys are column names and values are lists of data
     * @param columnNames The names of the columns in the report
     * @param title The title of the report
     * @param rezime A list of pairs containing summary information
     * @param outputFile The path where the generated report will be saved
     */
    fun generateReport(data:Map<String,List<String>>,columnNames: MutableList<String>, title: String, rezime: MutableList<Pair<String,Any>>,outputFile: String){
        val (columnNames,reportData) = converteMapToString(data)
        val report = Report(reportData, columnNames,title,rezime)
        generate(report,outputFile)
    }


    /**
     * Converts a map of string lists to a pair of column names and data lists.
     *
     * @param map The input map where keys are column names and values are lists of data
     * @return A Pair where the first element is a list of column names and the second is the data as a list of lists
     */
    fun converteMapToString(map: Map<String, List<String>>): Pair<MutableList<String>, List<List<Any>>> {

        val columnNames = map.keys.toMutableList()
        val columns = columnNames.map { key ->
            map[key] ?: listOf()
        }
        return Pair(columnNames, columns)
    }
}