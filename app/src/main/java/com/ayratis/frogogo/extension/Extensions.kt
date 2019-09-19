package com.ayratis.frogogo.extension

fun Any.objectScopeName() = "${javaClass.simpleName}_${hashCode()}"