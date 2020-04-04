package com.linebot.util

import java.sql.Timestamp

class Utils {
    companion object {
        @JvmField
        val LINE_SEPARATOR: String = System.getProperty("line.separator")

        @JvmStatic
        fun uncheck(runnable: () -> Void) {
            try {
                runnable()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        @JvmStatic
        fun <T> uncheck(supplier: () -> T): T {
            try {
                return supplier()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        @JvmStatic
        fun <T, R> uncheck(t: T, function: (T) -> R): R {
            try {
                return function(t)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        @JvmStatic
        fun <T> uncheck(t: T, consumer: (T) -> Void) {
            try {
                consumer(t)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        @JvmStatic
        fun now(): Timestamp {
            return Timestamp(System.currentTimeMillis())
        }
    }
}
