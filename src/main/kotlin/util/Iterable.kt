package util

fun <T> Iterable<T>.replaced(old: T, new: T) = map { if(it == old) new else it }
