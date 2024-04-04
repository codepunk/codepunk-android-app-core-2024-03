package com.codepunk.skeleton.core.loginator

import android.util.Log
import java.util.logging.*

/**
 * A class that handles log messages from a [Logger] by writing to the Android Logger.
 */
@Suppress("unused")
class Handleinator(formatter: Formatter? = Formatinator()) : Handler() {

    // region Companion object

    companion object {

        // region Methods

        /**
         * Converts a [Logger] logging level into an Android logging priority value.
         *
         * @param level The [Logger] logging level.
         *
         * @return The resulting Android logging priority value.
         */
        private fun getPriority(level: Level): Int {
            val value = level.intValue()
            return when {
                value >= 1000 -> Log.ERROR
                value >= 900 -> Log.WARN
                value >= 800 -> Log.INFO
                value >= 700 -> Log.DEBUG
                else -> Log.VERBOSE
            }
        }

        // endregion Methods

    }

    // endregion Companion object

    // region Constructors

    init {
        formatter?.also {
            this.formatter = it
        }
    }

    // endregion Constructors

    // region Overridden methods

    /**
     * Sets a [Formatter]. This formatter will be used to format [LogRecord]s for this [Handler].
     * If [newFormatter] is a [Formatinator], then this handler's class name will be added to the
     * formatinator's class blacklist.
     */
    override fun setFormatter(newFormatter: Formatter?) {
        (newFormatter as? Formatinator)?.also { formatinator ->
            formatinator.addToClassBlacklist(javaClass.name)
        }
        super.setFormatter(newFormatter)
    }

    // endregion Overridden methods

    // region Implemented methods

    /**
     * Publishes the supplied [record].
     */
    override fun publish(record: LogRecord) {
        val priority = getPriority(record.level)
        val tag = (formatter as? Formatinator)?.formatTag(record) ?: record.loggerName
        val message = formatter?.format(record) ?: record.message
        Log.println(priority, tag, message)
    }

    /**
     * Flushes the Handler.
     */
    override fun flush() {
        // No need to flush, but must implement abstract method.
    }

    /**
     * Closes the Handler and frees all associated resources.
     */
    override fun close() {
        // No need to close, but must implement abstract method.
    }

    // endregion Implemented methods

}