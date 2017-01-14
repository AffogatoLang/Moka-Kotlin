package co.louiscap.moka.modules

data class Module(
	val name: String = "Moka Package",
	val version: Semver,
	val dependencies: Collection<Dependency>,
	val root: String?
)

data class Dependency(
	val name: String,
	val version: Semver
)
