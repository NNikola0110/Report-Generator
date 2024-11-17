plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "komponente_projejekat"
include("csv")
include("pdf")
include("exel")
include("txt")
include("specifikacija")
include("app")
