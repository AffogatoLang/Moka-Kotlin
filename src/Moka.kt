import co.louiscap.moka.domain.LexicalRule
import co.louiscap.moka.io.FileReader
import co.louiscap.moka.io.LexReader
import co.louiscap.moka.io.MockReader
import co.louiscap.moka.io.files.LexicalFile
import co.louiscap.moka.parsing.Lexer

fun main(args: Array<String>) {
    val reader = LexReader("examples/simple.lex")
    val rules = reader.load().rules

    val lexer = Lexer(rules)

    lexer.lex("""
let my_func eq 143
""".trim()).forEach(::println)
}