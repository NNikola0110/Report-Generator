package model

import Kalkulacije

class Report(data:List<List<Any>>, columnNames: MutableList<String>?, title: String?, rezime: MutableList<Pair<String, Any>>?) : Kalkulacije{
    val columns:MutableList<Column> = mutableListOf()
    var columnNames: ColumnNames? = null
    var title:Title? = null
    var rezime:Rezime? = Rezime(rezime ?: mutableListOf())

    init{
        for(i in data){
            var column = Column(i)
            columns.add(column)
        }
        this.title = Title(title)
        if(columnNames != null) this.columnNames = ColumnNames(columnNames)
        if(rezime != null)
            this.rezime = Rezime(rezime)
    }


    override fun operacija(operacija: String, kolone: List<Int>) {
        val columnResults = mutableListOf<Any>()
        val column = columns[0].data

        if(proveriUnos(operacija,kolone)){
            for (row in 0 until column.size) {
                var result = 0
                when (operacija) {
                    "+" -> {
                        for (columnIndex in kolone) {
                            val cellValue = columns[columnIndex].data[row] as Int
                            result += cellValue
                        }
                    }
                    "*" -> {
                        result = 1
                        for (columnIndex in kolone) {
                            val cellValue = columns[columnIndex].data[row] as Int
                            result *= cellValue
                        }
                    }
                    "-" -> {
                        var first = true
                        for (columnIndex in kolone) {
                            val cellValue = columns[columnIndex].data[row] as Int
                            if (first) {
                                result = cellValue
                                first = false
                            } else {
                                result -= cellValue
                            }
                        }
                    }
                    "/" -> {
                        var first = true
                        for (columnIndex in kolone) {
                            val cellValue = columns[columnIndex].data[row] as Int
                            if (first) {
                                result = cellValue
                                first = false
                            } else {
                                result /= cellValue
                            }
                        }
                    }
                }
                columnResults.add(result)
            }

            columns.add(Column(columnResults))
            columnNames?.data?.add(operacija)
        }
    }



    fun proveriUnos(op: String, k: List<Int>): Boolean {
        var flag:Boolean = false

        for (index in k) {
            for(data in columns[index].data) {
                if (data !is Number) {
                    flag = false
                    throw IllegalArgumentException("Kolona koju ste izabrali nije numericka kolona")
                }
            }
            flag = true
        }
        if (flag){
            when (op) {
                "+", "*" -> {
                    if (k.size < 2) {
                        flag = false
                        throw IllegalArgumentException("Operacija $op ozahteva najmanje 2 kolone")
                    }
                }
                "-", "/" -> {
                    if (k.size != 2) {
                        flag = false
                        throw IllegalArgumentException("Operacija $op zahteva tacno 2 kolone")
                    }
                }
            }
        }
        return flag
    }

    override fun izracunajSum(columnIndex:Int) {
        var sum = 0
        for(cell in columns[columnIndex].data){
            sum += (cell as Int)
        }
        rezime?.data?.add("SUM kolone $columnIndex : " to sum)

    }

    override fun izracunajAverage(columnIndex:Int) {
        var avg = 0
        var cnt = 0
        for(cell in columns[columnIndex].data){
            avg += cell.toString().toInt()
            cnt++
        }
        println("done")
        rezime?.data?.add("AVERAGE kolone $columnIndex : " to (avg/cnt))
    }

    override fun izracunajCount(columnIndex:Int) {     //uraditi  COUNT IF  !!!!!!!!!!!!!!!
        var cnt = 0
        for(cell in columns[columnIndex].data){
            cnt++
        }
        rezime?.data?.add("COUNT kolone $columnIndex : " to cnt)
    }

    override fun izracunajCountIf(columnIndex:Int, value:Any){
        var cnt = 0
        for(cell in columns[columnIndex].data) {
            if(cell.toString() == value.toString()) {
                cnt++
            }
        }
        rezime?.data?.add("COUNT IF kolone $columnIndex (vrednost: $value) : " to cnt)
    }
}