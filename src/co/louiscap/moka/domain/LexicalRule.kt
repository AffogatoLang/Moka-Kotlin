package co.louiscap.moka.domain

data class LexicalRule(val priority: Int, val lhs: String, val rule: Regex)