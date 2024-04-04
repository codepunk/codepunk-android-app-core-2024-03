import com.android.build.gradle.api.ApplicationVariant
import java.util.Properties
import org.gradle.api.Project

/**
 * Version 1 of keys/intents/etc. logic:
 * https://github.com/codepunk/codepunk-legacy-android-app-core-codepunk/blob/master/app/build.gradle
 *
 * Version 2 of keys/intents/etc. logic:
 * https://github.com/codepunk/codepunk-android-app-core-2021/blob/main/app/helpers.gradle
 */

fun ApplicationVariant.makeKeys() {
    // TODO Make some useful methods here (see comments above)
    println("**************************************************")
    println("  Yerrrrrr: variant=${this.name}")
    println("**************************************************")
}

fun ApplicationVariant.extractLocalProperty(
    project: Project,
    name: String,
    defaultValue: String = "[$name is missing from local.properties]",
    type: String = "String",
    formatValue: (String) -> String = { "\"$it\"" }
) {
    val properties = Properties()
    properties.load(project.file("local.properties").inputStream())
    val propertyValue = properties.getProperty(name) ?: defaultValue

    println("**************************************************")
    println("  Yerrrrrr: variant=${this.name}, propertyValue=$propertyValue")
    println("**************************************************")

    buildConfigField(type, name, formatValue(propertyValue))
}