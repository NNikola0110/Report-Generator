package excel

import Specifikacija
import model.Report
import model.ReportFormat
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream


class EXCELreport : Specifikacija(){
    override val reportFormat: ReportFormat = ReportFormat.EXCEL
    override val supportsFormat: Boolean = true
    override val supportsTitle: Boolean = true
    override val supportsRezime: Boolean = true

    override fun generate(report: Report, destination: String) {
        //var destination : String ="output.xlsx"
        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("Report")
        var font:Font = workbook.createFont()
        var font1:Font = workbook.createFont()
        var font2:Font = workbook.createFont()
        report.title?.let{
            val titleRow: Row = sheet.createRow(0)
            val titleCell: Cell = titleRow.createCell(0)
            if(report.title!!.name != null){
                titleCell.setCellValue(report.title!!.name.toString())

                val titleStyle = workbook.createCellStyle().apply {
                    if (report.title!!.center) {
                        alignment = HorizontalAlignment.CENTER
                    }
                    if (report.title!!.bold) {
                        font.apply {
                            bold = true
                        }
                    }
                    if (report.title!!.fontSize) {
                        font.apply {
                            fontHeightInPoints = 16
                        }
                    }
                    if (report.title!!.color != "black") {
                        font.apply {
                            color = IndexedColors.BLUE.index
                        }
                    }
                    if (report.title!!.underline) {
                        font.apply {
                            underline = FontUnderline.SINGLE.byteValue
                        }
                    }
                    if (report.title!!.italic) {
                        font.apply {
                            italic=true
                        }
                    }
                    this.setFont(font)
                }
                titleCell.cellStyle = titleStyle
            }
            }

    report.columnNames.let{
        val titleRow: Row = sheet.createRow(1)
        if(report.columnNames?.data != null){
            for (ime in 0 until report.columnNames!!.data.size){
                val titleCell: Cell = titleRow.createCell(ime)
                titleCell.setCellValue(report.columnNames!!.data[ime])
                val titleStyle1 = workbook.createCellStyle().apply {
                    if (report.title!!.center) {
                        alignment = HorizontalAlignment.CENTER
                    }
                    if (report.columnNames!!.bold) {
                        font1.apply {
                            bold = true
                        }
                    }
                    if (report.columnNames!!.fontSize) {
                        font1.apply {
                            fontHeightInPoints = 16
                        }
                    }
                    if (report.columnNames!!.color != "black") {
                        font1.apply {
                            color = IndexedColors.ORANGE.index
                        }
                    }
                    if (report.columnNames!!.underline) {
                        font1.apply {
                            underline = FontUnderline.SINGLE.byteValue
                        }
                    }
                    if (report.columnNames!!.italic) {
                        font1.apply {
                            italic=true
                        }
                    }
                    this.setFont(font1)
                }
                titleCell.cellStyle = titleStyle1
            }
        }

    }

       report.columns.let{
           for (i in 2 until report.columns[0].data.size+2) {
               val titleRow: Row = sheet.createRow(i)
               for (j in 0 until report.columns.size){
                   val titleCell: Cell = titleRow.createCell(j)
                   titleCell.setCellValue(report.columns[j].data[i-2].toString())
                   val titleStyle2 = workbook.createCellStyle().apply {
                       if (report.title!!.center) {
                           alignment = HorizontalAlignment.CENTER
                       }
                       if (report.columns[j].bold) {
                           font2.apply {
                               bold = true
                           }
                       }
                       if (report.columns[j].fontSize) {
                           font2.apply {
                               fontHeightInPoints = 16
                           }
                       }
                       if (report.columns[j].color != "black") {
                           font2.apply {
                               color = IndexedColors.PINK.index
                           }
                       }
                       if (report.columns[j].underline) {
                           font2.apply {
                                underline = FontUnderline.SINGLE.byteValue
                           }
                       }
                       if (report.columns[j].italic) {
                           font2.apply {
                               italic=true
                           }
                       }
                       this.setFont(font2)
                   }
                   titleCell.cellStyle = titleStyle2
               }
           }
       }
        FileOutputStream(destination).use {outputStream -> workbook.write(outputStream)}
        workbook.close()
    }
}