package co.louiscap.moka.parsing.exceptions

class InvalidSequenceException(
        val index: Int,
        val line: Int = 0,
        val indexInLine: Int = 0,
        val restOfLine: String
) : Exception("An invalid sequence was encountered at position $index [l$line, c$indexInLine] : '$restOfLine'")