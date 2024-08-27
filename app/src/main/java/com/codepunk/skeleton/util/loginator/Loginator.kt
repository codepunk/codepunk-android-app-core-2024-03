package com.codepunk.skeleton.util.loginator

import java.util.logging.Handler
import java.util.logging.Level
import java.util.logging.Logger
import java.util.logging.LogManager

/**
 * Utility class that makes logging simpler and more uniform, and allows for automatic
 * formatting that adds metadata (method name, line number etc.) to every log statement.
 */
@Suppress("Unused")
object Loginator {

    // region Variables

    /**
     * The root [Logger].
     */
    private val rootLogger: Logger by lazy {
        LogManager.getLogManager().getLogger("")
    }

    // endregion Variables

    // region Properties

    /**
     * The logging level for the root [Logger].
     */
    var level: Level
        get() = rootLogger.level ?: Level.ALL
        set(value) {
            rootLogger.level = value
        }

    // endregion Properties

    // region Methods

    /**
     * Clears all handlers from the root logger.
     */
    fun removeHandlers(): Loginator {
        rootLogger.handlers.forEach { handler ->
            rootLogger.removeHandler(handler)
        }
        return this
    }

    /**
     * Adds a handler to the root logger.
     */
    fun addHandler(handler: Handler): Loginator {
        rootLogger.addHandler(handler)
        return this
    }

    /**
     * Logs a message to the logger, using the supplied [level], [tag], [msg] and [throwable].
     */
    @SuppressWarnings("WeakerAccess")
    fun log(
        level: Level,
        tag: String = "",
        throwable: Throwable? = null,
        msg: (() -> String?)? = null
    ) {
        with(Logger.getLogger(tag)) {
            if (isLoggable(level)) {
                log(level, msg?.invoke() ?: "", throwable)
            }
        }
    }

    /**
     * Logs a DEBUG message to the logger, using the supplied [tag], [msg] and [throwable].
     */
    fun d(
        tag: String = "",
        throwable: Throwable? = null,
        msg: (() -> String?)? = null
    ) {
        log(Level.CONFIG, tag, throwable, msg)
    }

    /**
     * Logs an ERROR message to the logger, using the supplied [tag], [msg] and [throwable].
     */
    fun e(
        tag: String = "",
        throwable: Throwable? = null,
        msg: (() -> String?)? = null
    ) {
        log(Level.SEVERE, tag, throwable, msg)
    }

    /**
     * Logs an INFO message to the logger, using the supplied [tag], [msg] and [throwable].
     */
    fun i(
        tag: String = "",
        throwable: Throwable? = null,
        msg: (() -> String?)? = null
    ) {
        log(Level.INFO, tag, throwable, msg)
    }

    /**
     * Logs a VERBOSE message to the logger, using the supplied [tag], [msg] and [throwable].
     */
    fun v(
        tag: String = "",
        throwable: Throwable? = null,
        msg: (() -> String?)? = null
    ) {
        log(Level.FINER, tag, throwable, msg)
    }

    /**
     * Logs a WARNING message to the logger, using the supplied [tag], [msg] and [throwable].
     */
    fun w(
        tag: String = "",
        throwable: Throwable? = null,
        msg: (() -> String?)? = null
    ) {
        log(Level.WARNING, tag, throwable, msg)
    }

    // endregion Methods

}