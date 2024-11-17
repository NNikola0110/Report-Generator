package txt

import Specifikacija
import model.Report
import model.ReportFormat
import java.io.File
import java.io.FileWriter

class TXTreport : Specifikacija() {
    override val reportFormat: ReportFormat = ReportFormat.TXT
    override val supportsFormat: Boolean = false
    override val supportsTitle: Boolean = true
    override val supportsRezime: Boolean = true


    override fun generate(report: Report, filePath: String) {
        //val filePath = "output.txt"
        val file = File(filePath)
        val row : MutableList<Any> = mutableListOf()

        try {
            val writer = FileWriter(file)
            val size = report.columns.get(0).data.size

            val max:MutableList<Int> = MutableList(report.columns.size){0}

            for(column in 0 until report.columns.size){
                for(i in 0 until size){
                    max[column] = maxOf(max[column],report.columns[column].data[i].toString().length)
                }
            }
            if(report.columnNames!=null) {
                println(report.columnNames!!.data.size)
                for(columnName in 0 until report.columns.size){
                    max[columnName] = maxOf(max[columnName], report.columnNames!!.data[columnName].length)
                }
                for (columnName in report.columnNames!!.data) {
                    writer.append(columnName.padEnd(max[report.columnNames!!.data.indexOf(columnName)] + 1, ' '))
                }
                writer.write("\n")
                for(column in 0 until report.columns.size){
                    writer.append("-".repeat(max[column] + 1))
                }
                writer.write("\n")
            }

            for(i in 0 until size){
                for(column in report.columns){
                    val cell = column.data[i].toString()
                    row.add(cell.padEnd(max[report.columns.indexOf(column)],' '))
                }
                writer.append(row.joinToString(" "))
                writer.appendLine()
                row.clear()
            }
            writer.write("\n")

            if(report.rezime?.data?.isNotEmpty() == true){
                writer.write("Rezime\n")
                for(pair in report.rezime!!.data!!){
                    writer.write(pair.first)
                    writer.write(pair.second.toString())
                    writer.write("\n")
                }
            }
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}