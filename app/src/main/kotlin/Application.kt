package app

import Specifikacija
import model.*
import java.io.File
import java.util.*

class Application {
    private val scanner = Scanner(System.`in`)
    private lateinit var serviceLoader: ServiceLoader<Specifikacija>

    fun start() {
        println("Welcome to Report Generator!")
        println("============================")

        try {
            loadReportFormats()
            mainMenu()
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    private fun loadReportFormats() {
        serviceLoader = ServiceLoader.load(Specifikacija::class.java)
        println("\nAvailable Report Formats:")
        val exporterServices = mutableMapOf<ReportFormat, Specifikacija> ()

        serviceLoader.forEach{ service ->
            exporterServices[service.reportFormat] = service
        }
        println(exporterServices.keys)
    }

    private fun mainMenu() {
        while (true) {
            println("\nMain Menu:")
            println("1. Generate New Report")
            println("2. Generate New Report from List")
            println("3. Generate New Report from Map")
            println("4. Exit")

            when (scanner.nextLine()) {
                "1" -> generateReport()
                "2" -> generateFromList()
                "3" -> generateFromMap()
                "4" -> return
                else -> println("Invalid option. Please try again.")
            }
        }
    }

    private fun generateFromList() {
        val fileData = readTextFile() ?: return
        val data = fileData.second.map { row -> row.map { it.toString() } }
        val availableHeaders = fileData.first.toMutableList()

        println("\nEnter output file name:")
        val fileName = scanner.nextLine()

        println("Do you want to include column names? (y/n)")
        val useColumnNames = scanner.nextLine().equals("y", ignoreCase = true)

        println("Do you want to include a title? (y/n)")
        val useTitle = scanner.nextLine().equals("y", ignoreCase = true)

        val reportFormat = selectReportFormat() ?: return

        try {
            when {
                // no column names no title
                !useColumnNames && !useTitle -> {
                    reportFormat.generateReport(data,fileName)
                }
                // only column names
                useColumnNames && !useTitle -> {
                    reportFormat.generateReport(data, availableHeaders,fileName)
                }
                // both column names and title
                useColumnNames && useTitle -> {

                    println("Enter report title:")
                    val title = scanner.nextLine()

                    val rezime = mutableListOf<Pair<String, Any>>()
                    println("Do you want to add summary items? (y/n)")
                    while (scanner.nextLine().equals("y", ignoreCase = true)) {
                        println("Enter summary name:")
                        val key = scanner.nextLine()
                        println("Enter summary value:")
                        val value = scanner.nextLine()
                        rezime.add(Pair(key, value))
                        println("Add another summary item? (y/n)")
                    }
                    reportFormat.generateReport(data, availableHeaders, title, rezime,fileName)
                }
                else -> {
                    println("Cannot generate report with title but without column names")
                    return
                }
            }
            println("Report generated successfully!")
        } catch (e: Exception) {
            println("Error generating report: ${e.message}")
        }
    }

    private fun generateFromMap() {
        val fileData = readTextFile() ?: return
        val availableHeaders = fileData.first.toMutableList()

        val dataMap = mutableMapOf<String, List<String>>()
        for (header in availableHeaders) {
            val columnIndex = availableHeaders.indexOf(header)
            val columnData = fileData.second.map { it[columnIndex].toString() }
            dataMap[header] = columnData
        }
        println("\nEnter output file name:")
        val fileName = scanner.nextLine()

        println("Do you want to include column names? (y/n)")
        val useColumnNames = scanner.nextLine().equals("y", ignoreCase = true)

        println("Do you want to include a title? (y/n)")
        val useTitle = scanner.nextLine().equals("y", ignoreCase = true)

        val reportFormat = selectReportFormat() ?: return

        try {
            when {
                // no column names no title
                !useColumnNames && !useTitle -> {
                    reportFormat.generateReport(dataMap,fileName)
                }
                // only column names
                useColumnNames && !useTitle -> {
                    reportFormat.generateReport(dataMap, availableHeaders,fileName)
                }
                // both column names and title
                useColumnNames && useTitle -> {
                    println("Enter report title:")
                    val title = scanner.nextLine()

                    val rezime = mutableListOf<Pair<String, Any>>()
                    println("Do you want to add summary items? (y/n)")
                    while (scanner.nextLine().equals("y", ignoreCase = true)) {
                        println("Enter summary key:")
                        val key = scanner.nextLine()
                        println("Enter summary value:")
                        val value = scanner.nextLine()
                        rezime.add(Pair(key, value))
                        println("Add another summary item? (y/n)")
                    }

                    reportFormat.generateReport(dataMap, availableHeaders, title, rezime,fileName)
                }
                // only title
                else -> {
                    println("Cannot generate report with title but without column names")
                    return
                }
            }
            println("Report generated successfully!")
        } catch (e: Exception) {
            println("Error generating report: ${e.message}")
        }
    }

    private fun generateReport() {

        val fileData = readTextFile() ?: return
        var headers = fileData.first.toMutableList()
        var data = fileData.second
        var report = Report(data,null,null,null)

        //var report = Report(listOf(listOf("iva","nikola", "pavle","djordje","ivana","aja"), listOf("nikolic","zlatanovic","nikolic","raketic","timotic","djukic"), listOf(100,100,102,103,104,100),listOf(100,101,102,103,104,105)), mutableListOf("ime","prezime","indeks","godina"),"Naslov",
        //mutableListOf(Pair("Godina: ",2024),Pair("Autor: ", 10223)))

        val reportFormat = selectReportFormat() ?: return
        setUp(reportFormat,report,headers)
        try {
            println("\nEnter output file name:")
            val fileName = scanner.nextLine()

            reportFormat.generate(report,fileName)
            println("Report generated successfully!")
        } catch (e: Exception) {
            println("Error generating report: ${e.message}")
        }
    }

    private fun readTextFile(): Pair<List<String>, List<List<Any>>> {
        println("Enter text file path:")
        val filePath = readlnOrNull() ?: ""

        val allLines = File(filePath).readLines()
        val headers = allLines.firstOrNull()?.split(",") ?: emptyList()
        val data = allLines.drop(1).map { line -> line.split(",").map { value -> value.trim().toIntOrNull() ?: value}}
        return Pair(headers, data)
    }

    private fun selectReportFormat(): Specifikacija? {
        println("\nSelect report format:")
        val generators = serviceLoader.toList()
        generators.forEachIndexed { index, generator ->
            println("${index + 1}. ${generator.reportFormat}")
        }

        val choice = scanner.nextLine().toIntOrNull()
        return if (choice != null && choice in 1..generators.size) {
            generators[choice - 1]
        } else {
            println("Invalid selection")
            null
        }
    }

    private fun setUp(reportFormat: Specifikacija,report: Report,headers:MutableList<String>) {

        if (reportFormat.supportsTitle) {
            println("\nDo you want to add a title? (y/n)")
            if (scanner.nextLine().equals("y", ignoreCase = true)) {
                println("Enter title:")
                val t = scanner.nextLine()
                val title = Title(t)
                if (reportFormat.supportsFormat) {
                    titleFormatting(title)
                    report.title = title
                }
            }
        }

        println("\nDo you want to include column headers? (y/n)")
        if (scanner.nextLine().equals("y", ignoreCase = true)) {
            report.columnNames = ColumnNames(headers)
            if (reportFormat.supportsFormat) {
                headerFormatting(report.columnNames!!)
            }
        }

//        println("\nDo you want to include row numbers? (y/n)")
//        config.includeRowNumbers = scanner.nextLine().equals("y", ignoreCase = true)


        if (reportFormat.supportsRezime) {
            configureSummary(report)
        }
        configureCalculations(report)

        if (reportFormat.supportsFormat) {
            configureTableFormatting(report)
        }

    }

    private fun titleFormatting(title: Title) {
        println("Select title formatting options:")
        println("1. Bold")
        println("2. Italic")
        println("3. Underline")
        println("4. Center")
        println("5. Font size")
        println("6. Custom Color")
        println("7. None")

        when (scanner.nextLine()) {
            "1" -> title.bold = true
            "2" -> title.italic = true
            "3" -> title.underline = true
            "4" -> title.fontSize = true
            "5" -> title.underline = true
            "6" -> {
                title.color = "red"
            }
        }
    }

    private fun headerFormatting(cn:ColumnNames) {
        println("Do you want to format headers? (y/n)")
        if (scanner.nextLine().equals("y", ignoreCase = true)) {
            println("Select title formatting options:")
            println("1. Bold")
            println("2. Italic")
            println("3. Underline")
            println("4. Center")
            println("5. Font size")
            println("6. Custom Color")
            println("7. None")

            when (scanner.nextLine()) {
                "1" -> cn.bold = true
                "2" -> cn.italic = true
                "3" -> cn.underline = true
                "4" -> cn.fontSize = true
                "5" -> cn.underline = true
                "6" -> {
                    cn.color = "red"
                }
            }
        }
    }

    private fun configureSummary(report: Report) {
        println("\nDo you want to add summary calculations? (y/n)")
        if (scanner.nextLine().equals("y", ignoreCase = true)) {
            while (true) {
                println("\nSelect summary type:")
                println("1. Sum")
                println("2. Average")
                println("3. Count")
                println("4. Finish adding summaries")

                when (scanner.nextLine()) {
                    "1" -> addSummaryCalculation(report, SummaryType.SUM)
                    "2" -> addSummaryCalculation(report, SummaryType.AVERAGE)
                    "3" -> addSummaryCalculation(report, SummaryType.COUNT)
                    "4" -> break
                }
            }
        }
    }
    private fun addSummaryCalculation(report: Report, type: SummaryType) {
        println("Enter column index for $type calculation:")
        val columnIndex = try {
            readln().toInt()
        } catch (e: NumberFormatException) {
            println("Please enter a valid number")
            -1
        }
        if (columnIndex >= 0) {
            println("You entered: $columnIndex")
        } else {
            println("Invalid input")
        }
        if (type == SummaryType.COUNT) {
            println("Do you want to add a condition? (y/n)")
            if (scanner.nextLine().equals("y", ignoreCase = true)) {
                println("Enter value to count:")
                val value = scanner.nextLine()
                report.izracunajCountIf(columnIndex,value)
                return
            }
        }
        when (type) {
            SummaryType.SUM -> report.izracunajSum(columnIndex)
            SummaryType.AVERAGE -> report.izracunajAverage(columnIndex)
            SummaryType.COUNT -> report.izracunajCount(columnIndex)
            else ->{return}
        }
    }

    private fun configureCalculations(report: Report) {
        println("\nDo you want to add calculated columns? (y/n)")
        if (scanner.nextLine().equals("y", ignoreCase = true)) {
            while (true) {
                println("\nSelect calculation type:")
                println("1. Sum")
                println("2. Difference")
                println("3. Multiplication")
                println("4. Division")
                println("5. Finish adding calculations")

                when (scanner.nextLine()) {
                    "1" -> addColumnCalculation(report, SummaryType.PLUS)
                    "2" -> addColumnCalculation(report, SummaryType.MINUS)
                    "3" -> addColumnCalculation(report, SummaryType.MUL)
                    "4" -> addColumnCalculation(report, SummaryType.DIV)
                    "5" -> break
                }
            }
        }
    }

    private fun addColumnCalculation(report: Report, type: SummaryType) {
        println("Enter name for the calculated column:")
        val newColumn = scanner.nextLine()

        println("Enter columns to calculate (comma-separated):")
        val columns = scanner.nextLine().split(",").mapNotNull { it.trim().toIntOrNull() }

        report.operacija(type.toString(),columns)
    }

    private fun configureTableFormatting(report: Report) {
        println("\nSelect table content style:")
        println("1. Bold")
        println("2. Italic")
        println("3. Underline")
        println("4. Center")
        println("5. Font size")
        println("6. Custom Color")
        println("7. None")

        val data = report.columns
        when (scanner.nextLine()) {
            "1" -> for(column in data)column.bold = true
            "2" -> for(column in data)column.italic = true
            "3" -> for(column in data)column.underline = true
            "4" -> for(column in data)column.center = true
            "5" -> for(column in data)column.fontSize = true
            "6" -> {
                for(column in data)
                 column.color="red"
            }
        }
    }
}