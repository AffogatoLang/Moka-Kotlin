package co.louiscap.moka.io

import co.louiscap.moka.domain.LexicalRule
import co.louiscap.moka.io.files.LexicalFile

class MockReader : FileReader<LexicalFile>("No Path Required") {

	override fun load(): LexicalFile {
		return LexicalFile(
			listOf(
				LexicalRule(50, "T_IDENT", Regex("^\\w+")),
				LexicalRule(10, "T_DEC", Regex("^let")),
				LexicalRule(10, "T_ASSIGN", Regex("^eq")),
				LexicalRule(45, "T_NUM", Regex("^\\d+"))
			)
		)
	}

}
