package model

enum class SummaryType {
    SUM,AVERAGE,COUNT,PLUS,MINUS,MUL,DIV;

    override fun toString(): String {
        return when (this) {
            SUM -> "SUM"
            AVERAGE -> "AVERAGE"
            COUNT -> "COUNT"
            PLUS -> "+"
            MINUS -> "-"
            MUL -> "*"
            DIV -> "/"
        }
    }
}