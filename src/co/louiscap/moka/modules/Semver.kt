package co.louiscap.moka.modules

import co.louiscap.moka.modules.exceptions.InvalidSemverException

enum class SemverModifier {
	EXACT_MATCH,
	MAJOR_MATCH,
	MINOR_MATCH,
	PATCH_MATCH,
	EVERY_MATCH,
}

data class Semver(val major: Int = 0, val minor: Int = 0, val patch: Int = 0, val modifier: SemverModifier = SemverModifier.EXACT_MATCH) {
	fun matches(other: Semver) = when (this.modifier) {
		SemverModifier.EXACT_MATCH -> {
			major == other.major &&
			minor == other.minor &&
			patch == other.patch
		}
		SemverModifier.MAJOR_MATCH -> major == other.major
		SemverModifier.MINOR_MATCH -> {
			major == other.major &&
			minor == other.minor
		}
		SemverModifier.PATCH_MATCH -> {
			major == other.major &&
			minor == other.minor &&
			patch == other.patch
		}
		SemverModifier.EVERY_MATCH -> true
	}

	companion object {
		val regex = """^\*$|^(\^)?(\d+)(?:\.(\d+))?(?:\.(\d+))?$"""
		fun getModifier(symbol: String) = when (symbol) {
			"^" -> SemverModifier.MAJOR_MATCH
			else -> SemverModifier.EXACT_MATCH
		}

		fun parse(src: String): Semver? {
			val input = src.trim()
			val match = Regex(regex).find(input) ?: throw InvalidSemverException()

			val full = match.groups[0]?.value ?: ""
			if (full == "*") {
				return Semver(modifier = SemverModifier.EVERY_MATCH)
			}

			val modifier = match.groups[1]?.value ?: ""
			val major = match.groups[2]?.value?.toInt() ?: throw InvalidSemverException()
			val minor = match.groups[3]?.value?.toInt() ?: 0
			val patch = match.groups[4]?.value?.toInt() ?: 0

			return Semver(major, minor, patch, getModifier(modifier))
		}
	}
}
