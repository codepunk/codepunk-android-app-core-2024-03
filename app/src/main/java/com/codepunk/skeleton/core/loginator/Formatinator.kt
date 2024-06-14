package com.codepunk.skeleton.core.loginator

import java.lang.StringBuilder
import java.util.logging.Formatter
import java.util.logging.LogRecord

/**
 * An implementation of [Formatter] that applies automatic formatting to log requests.
 */
open class Formatinator(
    private val appPackageName: String = ""
) : Formatter() {

    // region Variables

    private val packageBlacklist: MutableList<String> = listOfNotNull(
        this::class.java.`package`?.name,
    ).toMutableList()

    private val classBlacklist: MutableList<String> = mutableListOf()

    // endregion Variables

    // region Implemented methods

    /**
     * Formats the given log record and returns the formatted string.
     */
    override fun format(record: LogRecord): String {
        val message = formatMessage(record)
        val builder = StringBuilder()
        findSource(record)?.run {
            builder.append(methodName)
                .append("(")
                .append(fileName)
                .append(":")
                .append(lineNumber)
                .append(")")
        }
        if (builder.isNotEmpty() && message.isNotEmpty()) {
            builder.append(" ")
        }
        builder.append(message)
        if (builder.isNotEmpty() && record.thrown != null) {
            builder.append(System.lineSeparator())
                .append(record.thrown.stackTraceToString())
        }
        return builder.toString()
    }

    // endregion Implemented methods

    // region Methods

    /**
     * Searches for the [StackTraceElement] associated with the source (i.e. cause)
     * of the supplied [record].
     */
    @SuppressWarnings("WeakerAccess")
    protected fun findSource(record: LogRecord): StackTraceElement? =
        (record.thrown ?: Throwable()).stackTrace.find { element ->
            element.packageName.orEmpty().startsWith(appPackageName) &&
            element.packageName !in packageBlacklist &&
                    element.className !in classBlacklist
        }

    /**
     * Constructs a log tag from the given log record.
     */
    @Suppress("UNUSED")
    fun formatTag(record: LogRecord): String = record.loggerName.ifEmpty {
        findSource(record)?.topLevelClass?.simpleName ?: record.loggerName
    }

    /**
     * Adds a package name to the package blacklist; that is, the list of packages that will be
     * skipped when determining the "source" stack trace element (the element at which a log
     * request was made).
     */
    @Suppress("UNUSED")
    fun addToPackageBlacklist(packageName: String) {
        packageBlacklist.add(packageName)
    }

    /**
     * Adds a class name to the class blacklist; that is, the list of classes that will be
     * skipped when determining the "source" stack trace element (the element at which a log
     * request was made).
     */
    @Suppress("UNUSED")
    fun addToClassBlacklist(className: String) {
        classBlacklist.add(className)
    }

    // endregion Methods

    // region Companion object

    companion object {

        // region Extension properties

        /**
         * Extension property to extract a package name from a [StackTraceElement].
         */
        protected val StackTraceElement.packageName: String?
            get() = try {
                Class.forName(className).`package`?.name
            } catch (e: Exception) {
                null
            }

        /**
         * Extension property that gets the top-level class from a [StackTraceElement]
         */
        protected val StackTraceElement.topLevelClass: Class<*>?
            get() = try {
                var cls = Class.forName(className)
                var enclosingClass = cls.enclosingClass
                while (enclosingClass != null) {
                    cls = enclosingClass
                    enclosingClass = cls.enclosingClass
                }
                cls
            } catch (e: Exception) {
                null
            }

        // endregion Extension properties

    }

    // endregion Companion object

}