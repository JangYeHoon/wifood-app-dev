package com.example.wifood.util

import timber.log.Timber

class TimberLogPrefix : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "${element.className}:${element.methodName}#${element.lineNumber}"
    }
}