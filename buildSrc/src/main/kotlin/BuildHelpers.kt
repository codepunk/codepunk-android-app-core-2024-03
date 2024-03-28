import com.android.build.gradle.api.ApplicationVariant

/**
 * Version 1 of keys/intents/etc. logic:
 * https://github.com/codepunk/codepunk-legacy-android-app-core-codepunk/blob/master/app/build.gradle
 *
 * Version 2 of keys/intents/etc. logic:
 * https://github.com/codepunk/codepunk-android-app-core-2021/blob/main/app/helpers.gradle
 */

fun makeKeys(variant: ApplicationVariant) {
    // TODO Make some useful methods here (see comments above)
    println("**************************************************")
    println("  Yerrrrrr: variant=${variant.name}")
    println("**************************************************")
}