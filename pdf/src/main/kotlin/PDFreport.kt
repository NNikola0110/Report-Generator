package pdf

import Specifikacija
import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import model.Report
import model.ReportFormat
import java.io.File
import java.io.FileOutputStream


class PDFreport() : Specifikacija() {
    override val reportFormat: ReportFormat = ReportFormat.PDF
    override val supportsFormat: Boolean = true
    override val supportsTitle: Boolean = true
    override val supportsRezime: Boolean = true

    override fun generate(report: Report, filePath: String) {

        val html = buildHtml(report)
        try {
            //val filePath = "output.pdf"
            val writer: PdfWriter = PdfWriter(FileOutputStream(filePath))

            val pdf: PdfDocument = PdfDocument(writer)
            val properties = ConverterProperties()
            properties.setFontProvider(DefaultFontProvider(true, true, true))
            HtmlConverter.convertToPdf(html, pdf, properties);

        } catch (e: Exception) {
            e.printStackTrace()
            println("Error generating PDF: ${e.message}")
        }
    }

    fun buildHtml(report: Report): String {

        var htmlTitle = ""
        val styles = mutableListOf<String>()
        val title = report.title
        if(title?.name != null){
            if(title.bold){
                styles.add("font-weight: bold;")
            }
            if(title.color != "black"){
                styles.add("color: blue;")
            }
            if(title.italic){
                styles.add("font-style: italic;")
            }
            if(title.underline){
                styles.add("text-decoration: underline;")
            }
            if(title.fontSize){
                styles.add("font-size: 20px;")
            }
            if (title.center){
                styles.add("text-align: center;")
            }
            val styleString = if (styles.isNotEmpty()) "style=\"${styles.joinToString(";")}\"" else ""
            htmlTitle = "<h1 $styleString>${title.name}</h1>"
        }

        var htmlTableHeader = ""
        val columnStyles = mutableListOf<String>()
        val columnNames = report.columnNames
        if (columnNames?.data != null) {
            if (columnNames.bold) {
                columnStyles.add("font-weight: bold;")
            }
            if (columnNames.italic) {
                columnStyles.add("font-style: italic;")
            }
            if (columnNames.underline) {
                columnStyles.add("text-decoration: underline;")
            }
            if (columnNames.color != "black") {
                columnStyles.add("color: blue;")
            }
            if (columnNames.fontSize) {
                columnStyles.add("font-size: 20px;")
            }
            if (columnNames.center) {
                columnStyles.add("text-align: center;")
            }

            val styleString = if (columnStyles.isNotEmpty()) "style=\"${columnStyles.joinToString(" ")}\"" else ""
             val htmlColumnNames = columnNames.data.joinToString(""){ columnName ->
                 "<th $styleString>$columnName</th>"
             }
            htmlTableHeader = "<tr border: 1px solid black;>$htmlColumnNames</tr>"

        }

        val columns = report.columns
        val col = report.columns[0].data
        var htmlTableCells = ""
        var htmlTableRow = ""

        for (i in 0 until col.size) {
            // Start new row at beginning of each iteration
            htmlTableCells += "<tr>"

            for (column in columns) {
                val columnStyle = mutableListOf<String>()

                // Add all style properties
                if (column.bold) {
                    columnStyle.add("font-weight: bold")
                }
                if (column.italic) {
                    columnStyle.add("font-style: italic")
                }
                if (column.underline) {
                    columnStyle.add("text-decoration: underline")
                }
                if (column.color != "black") {
                    columnStyle.add("color: red")
                }
                if (column.fontSize) {
                    columnStyle.add("font-size: 20px")
                }
                if (column.center) {
                    columnStyle.add("text-align: center")
                }

                // Always add border style and combine all styles properly
                columnStyle.add("border: 1px solid black")

                // Fix the style attribute syntax
                val styleString = if (columnStyle.isNotEmpty())
                    "style=\"${columnStyle.joinToString("; ")}\""
                else
                    ""

                val cellValue = column.data[i].toString()
                htmlTableCells += "<td $styleString>$cellValue</td>"
            }

            htmlTableCells += "</tr>"
        }
        var htmlRezime = ""
        if (report.rezime?.data?.isNotEmpty() == true) {
            htmlRezime = """
                <div class="rezime" style="margin-top: 20px;">
                    <h2>Rezime</h2>
                    ${report.rezime!!.data?.joinToString("<br>") { pair -> "${pair.first} ${pair.second}" }}
                </div>
            """.trimIndent()
        }



        val htmlTemplate = """
<!DOCTYPE html>
<html>
<head>
    <style>
        body { font-family: Arial, sans-serif; }
        table { border-collapse: collapse; width: 100%; }
        td, th { padding: 8px; }
    </style>
</head>
<body>
    ${if (htmlTitle.isNotEmpty()) htmlTitle else ""}
        <table border="1">
            ${if (htmlTableHeader.isNotEmpty()) "<thead><tr>$htmlTableHeader</tr></thead>" else ""}
            <tbody>
                $htmlTableCells
            </tbody>
        </table>
        ${if (htmlRezime.isNotEmpty()) htmlRezime else ""}
</body>
</html>
""".trimIndent()

        return htmlTemplate
    }
}