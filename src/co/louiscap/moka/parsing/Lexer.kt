package co.louiscap.moka.parsing

import co.louiscap.moka.domain.LexicalRule
import co.louiscap.moka.domain.LexicalToken
import co.louiscap.moka.parsing.exceptions.InvalidSequenceException

class Lexer(val rules: Collection<LexicalRule>, val settings: Settings = Settings()) {

    data class Settings(val eatWhitespace: Boolean = true)

    data class Position(
        val index: Int,
        val line: Int,
        val column: Int,
        val startOfLine: Int? = null,
        val endOfLine: Int? = null
    )

    private data class NewLine(val pos: Int) {
        fun isBefore(idx: Int) = idx > pos
        fun isBefore(t: LexicalToken) = t.index > pos
    }

    private fun getAllNewlines(src: String): List<NewLine> {
        val newlines = mutableListOf<NewLine>()
        var last = src.indexOf('\n')

        while (last >= 0) {
            newlines.add(NewLine(last))
            last = src.indexOf('\n', last + 1)
        }

        return newlines
    }

    private fun positionOf(index: Int, src: String, newlines: List<NewLine>): Position {
        var startOfLine = 0
        val prevLines = newlines.filter { it.isBefore(index) }.sortedByDescending { it.pos }

        if (prevLines.isNotEmpty()) {
            startOfLine = prevLines.first().pos
        }

        var endOfLine = src.length
        val nextLines = newlines.filter { ! it.isBefore(index) }.sortedBy { it.pos }

        if (nextLines.isNotEmpty()) {
            endOfLine = nextLines.first().pos
        }

        val posInLine = index - (prevLines.lastOrNull()?.pos?: 0);

        return Position(
                index,
                prevLines.size,
                posInLine,
                startOfLine,
                endOfLine
        )
    }

    private fun eatWhitespace(src: String): Int {
        val res = Regex("""^\s+""").find(src) ?: return 0
        return res.value.length
    }

    public fun lex(src: String): List<LexicalToken> {
        val sorted = rules.sortedBy { it.priority }
        val tokens = mutableListOf<LexicalToken>()
        val newlines = getAllNewlines(src)

        var index = 0
        var prevIndex: Int
        var slice = src
        var match: MatchResult?

        while (index < src.length) {
            prevIndex = index
            slice = src.substring(index)

            for (rule in sorted) {
                match = rule.rule.find(slice)
                if (match != null) {
                    val matchGroup = match.value
                    val pos = positionOf(index, src, newlines)
                    tokens.add(LexicalToken(index, pos.line, pos.column, matchGroup, lhs = rule.lhs))
                    index += matchGroup.length
                    break
                }
            }
            if (prevIndex == index) {
                val increment = eatWhitespace(slice)
                if (increment > 0) {
                    index += increment
                } else {
                    val pos = positionOf(index, src, newlines)
                    val restOfLine = src.substring(pos.startOfLine?: 0, pos.endOfLine?: src.length).trim()
                    throw InvalidSequenceException(index, pos.line, pos.column, restOfLine)
                }
            }
        }
        return tokens
    }
}