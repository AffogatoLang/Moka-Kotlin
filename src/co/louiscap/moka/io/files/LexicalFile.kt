package co.louiscap.moka.io.files

import co.louiscap.moka.domain.LexicalRule

data class LexicalFile(val rules: Collection<LexicalRule>) {
	constructor() : this(listOf<LexicalRule>())
}
