package com.mvvm.compiler

/**
 * @author Yang Shihao
 */
object Utils {
    @JvmStatic
    fun isNotEmpty(s: String?, block: (String) -> Unit) {
        if (s != null && s.trim { it <= ' ' }.isNotEmpty()) {
            block(s)
        }
    }
}

fun MutableSet<String>.addFeature(feature: String?) {
    Utils.isNotEmpty(feature) {
        add(it.toLowerCase())
    }
}

fun MutableSet<String>.addFeatures(features: Array<String>?) {
    features?.forEach {
        add(it.toLowerCase())
    }
}

fun MutableSet<String>.hasFeature(qualifiedClassName: String): Boolean {
    if (qualifiedClassName.isEmpty()) {
        return false
    }
    val s = qualifiedClassName.toLowerCase()
    return null != find { s.contains(it) }
}