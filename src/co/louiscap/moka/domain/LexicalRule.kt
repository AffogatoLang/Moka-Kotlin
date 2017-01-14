package co.louiscap.moka.domain

data class LexicalRule(val priority: Int, val lhs: String, val rule: Regex) {
	companion object {
		fun fromString(src: String): LexicalRule {
			var pos = src.indexOfFirst { it == ':' }
			val priority = src.substring(0, pos).trim()
			val rest = src.substring(pos + 1)

			pos = rest.indexOfFirst { it == ':' }
			val lhs = rest.substring(0, pos).trim()
			var regex = rest.substring(pos + 1).trim()

			regex = if (regex.startsWith("^")) regex else "^${regex}"
			return LexicalRule(priority.trim().toInt(), lhs.trim(), Regex(regex))
		}
	}
}
