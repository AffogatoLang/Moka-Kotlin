package co.louiscap.moka.domain

data class LexicalRule(val priority: Int, val lhs: String, val rule: Regex) {
    companion object {
        fun fromString(src: String): LexicalRule {
            var (priority, lhs, regex) = src.split(Regex("""(?<!\\):"""))
            regex = if (regex.startsWith("^")) regex.trim() else "^${regex.trim()}"
            return LexicalRule(priority.trim().toInt(), lhs.trim(), Regex(regex))
        }
    }
}