package com.yimiao.app.ext

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by Vector
 * on 2017/6/7 0007.
 */
fun Double.toCurrency(numberZero: Int = 2): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA)
    if (numberZero >= 0) {
        numberFormat.minimumFractionDigits = numberZero
    }
    return numberFormat.format(this)
}

fun Double.format(numberZero: Int = 2): String {
    val buffer = StringBuffer()
    buffer.append("#0.")
    if (numberZero >= 1) {
        for (i in 1..numberZero) {
            buffer.append("0")
        }
    }
    return DecimalFormat(buffer.toString()).format(this)
}

fun Double.toFloorAndCurrency(numberZero: Int = 2): String {
    return toFloor().toCurrency(numberZero)
}

fun Double.toCeilAndCurrency(numberZero: Int = 2): String {
    return toCeil().toCurrency(numberZero)
}

fun Double.toFloor(): Double {
    return Math.floor(this)
}

fun Double.toCeil(): Double {
    return Math.ceil(this)
}