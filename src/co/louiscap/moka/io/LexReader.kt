package co.louiscap.moka.io

import co.louiscap.moka.domain.LexicalRule
import co.louiscap.moka.io.files.LexicalFile
import java.io.File

class LexReader(path: String) : FileReader<LexicalFile>(path) {
    override fun load(): LexicalFile {
        val rules = mutableListOf<LexicalRule>()
        val str = File(this.path)
                .bufferedReader()
                .use { it.lines().forEach { if (it.isNullOrBlank()) return@forEach else rules.add(LexicalRule.fromString(it)) } }
        return LexicalFile(rules)
    }
}