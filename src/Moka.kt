import co.louiscap.moka.io.LexReader
import co.louiscap.moka.modules.Semver
import co.louiscap.moka.parsing.Lexer

fun main(args: Array<String>) {
    val reader = LexReader("examples/json.lex")
    val rules = reader.load().rules

    val lexer = Lexer(rules)

    println(Semver.parse("*"))
    println(Semver.parse("7.0.1"))
    println(Semver.parse("^5.4"))

    lexer.lex("""
{
    "user": "root",
    "password": "password",
    "development": true,
    "parts": ["a", 59, "c"]
}
""".trim()).forEach(::println)
}