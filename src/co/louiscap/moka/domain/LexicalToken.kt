package co.louiscap.moka.domain

data class LexicalToken(val index: Int, val line: Int = -1, val column: Int = -1, val src: String, val lhs: String)