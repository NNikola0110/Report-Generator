package app

import Specifikacija
import app.Application
import model.Report
import model.ReportFormat
import java.util.*


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.



//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    Application().start()

//    var iz = Report(listOf(listOf("iva","nikola", "pavle","djordje","ivana","aja"), listOf("nikolic","zlatanovic","nikolic","raketic","timotic","djukic"), listOf(100,101,102,103,104,105),listOf(100,101,102,103,104,105)), mutableListOf("ime","prezime","indeks","godina"),"Naslov",
//        mutableListOf(Pair("Godina: ",2024),Pair("Autor: ", 10223))
//    )
//    var iz2 = Report(listOf(listOf("iva","nikola", "pavle","djordje","ivana","aja"), listOf("nikolic","zlatanovic","nikolic","raketic","timotic","djukic"), listOf(100,101,102,103,104,105),listOf(100,101,102,103,104,105),
//        listOf(1,2,3,4,5,6)), mutableListOf("ime","prezime","indeks","godina","nagrade"),"Naslov",
//        mutableListOf(Pair("Godina: ",2024),Pair("Autor: ", 10223))
//    )
//    var iz3 = Report(listOf(listOf("iva","nikola", "pavle","djordje","ivana","aja"), listOf("nikolic","zlatanovic","nikolic","raketic","timotic","djukic"), listOf(100,101,102,103,104,105),listOf(100,101,102,103,104,105)), null,"Naslov",
//        mutableListOf(Pair("Godina: ",2024),Pair("Autor: ", 10223))
//    )
//    var iz4 = Report(listOf(listOf("iva","nikola", "pavle","djordje","ivana","aja"), listOf("nikolic","zlatanovic","nikolic","raketic","timotic","djukic"), listOf(100,101,102,103,104,105),listOf(100,101,102,103,104,105)), mutableListOf("ime","prezime","indeks","godina"),null,null)
//
//    val serviceLoader = ServiceLoader.load(Specifikacija::class.java)
//
//
//    val exporterServices = mutableMapOf<ReportFormat, Specifikacija> ()
//
//    serviceLoader.forEach{ service ->
//
//        exporterServices.put(service.reportFormat, service)
//    }
//
//    iz2.operacija("+", listOf(2,3,4))
//    iz2.operacija("*", listOf(2,3,4))
//    iz2.operacija("-", listOf(2,3))
//    iz2.operacija("/", listOf(2,3))
//    iz2.izracunajSum(2)
//    iz.izracunajAverage(2)
//    iz.izracunajCount(2)
//    exporterServices[ReportFormat.CSV]?.generate(iz2)
//    exporterServices[ReportFormat.TXT]?.generate(iz4)
//    exporterServices[ReportFormat.PDF]?.generate(iz4)
//    exporterServices[ReportFormat.EXCEL]?.generate(iz4)

}