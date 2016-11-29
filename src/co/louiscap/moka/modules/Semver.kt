package co.louiscap.moka.modules

enum class SemverModifier {
    EXACT_MATCH,
    MAJOR_MATCH,
    MINOR_MATCH,
    PATCH_MATCH
}

data class Semver (val major: Int = 0, val minor: Int = 0, val patch: Int = 0, val modifier: SemverModifier = SemverModifier.EXACT_MATCH) {
    fun matches(other: Semver) = when(other.modifier) {
        SemverModifier.EXACT_MATCH -> {
            major == other.major &&
            minor == other.minor &&
            patch == other.patch
        }
        else -> false
    }
}