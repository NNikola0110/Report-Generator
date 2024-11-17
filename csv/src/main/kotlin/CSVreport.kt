package csv

import Specifikacija
import model.Report
import model.ReportFormat
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class CSVreport : Specifikacija() {

    override val reportFormat: ReportFormat = ReportFormat.CSV
    override val supportsFormat: Boolean = false
    override val supportsTitle: Boolean = false
    override val supportsRezime: Boolean = false

    override fun generate(report: Report, filePath: String) {
        //val filePath = "output.csv"
        val file = File(filePath)
        val row : MutableList<Any> = mutableListOf()

        try {
            val writer = FileWriter(file)
            val csvPrinter = CSVPrinter(writer, CSVFormat.DEFAULT)
            val size = report.columns.get(0).data.size

            if(report.columnNames!=null)
                csvPrinter.printRecord(report.columnNames!!.data)
            for(i in 0 until size){
                for(column in report.columns){
                    row.add(column.data[i])
                }
                csvPrinter.printRecord(row)
                row.clear()
                //csvPrinter.println()
            }
            writer.close()
            csvPrinter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
}
}