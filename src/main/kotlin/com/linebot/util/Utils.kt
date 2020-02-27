package com.linebot.util

import java.sql.Timestamp

class Utils {
    companion object {
        @JvmField
        val LINE_SEPARATOR: String = System.getProperty("line.separator")

        @JvmStatic
        fun <E : Exception> uncheck(runnable: Runnable_WithExceptions<E>) {
            try {
                runnable.run()
            } catch (e: RuntimeException) {
                throw e
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        @JvmStatic
        fun <T, E : Exception> uncheck(supplier: Supplier_WithExceptions<T, E>): T {
            try {
                return supplier.get()
            } catch (e: Throwable) {
                throw RuntimeException(e)
            }

        }

        @JvmStatic
        fun <T, R, E : Exception> uncheck(t: T, function: Function_WithExceptions<T, R, E>): R {
            try {
                return function.apply(t)
            } catch (e: RuntimeException) {
                throw e
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        @JvmStatic
        fun <T, E : Exception> uncheck(t: T, consumer: Consumer_WithExceptions<T, E>) {
            try {
                consumer.accept(t)
            } catch (e: RuntimeException) {
                throw e
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

        // kotlin用のuncheck
        // kotlinではチェック例外がない(チェックさせるには@Throwが必要)ため、FunctionalInterfaceを必要としない
        // これらをJavaで呼び出そうとするとコンパイルエラーとなるため、元のメソッドを残しジェネリクスで明示的に↑を呼び出す

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

        @FunctionalInterface
        interface Consumer_WithExceptions<T, E : Exception> {
            @Throws(Exception::class)
            fun accept(t: T)
        }

        @FunctionalInterface
        interface BiConsumer_WithExceptions<T, U, E : Exception> {
            @Throws(Exception::class)
            fun accept(t: T, u: U)
        }

        @FunctionalInterface
        interface Function_WithExceptions<T, R, E : Exception> {
            @Throws(Exception::class)
            fun apply(t: T): R
        }

        @FunctionalInterface
        interface Supplier_WithExceptions<T, E : Exception> {
            @Throws(Exception::class)
            fun get(): T
        }

        @FunctionalInterface
        interface Runnable_WithExceptions<E : Exception> {
            @Throws(Exception::class)
            fun run()
        }
    }
}