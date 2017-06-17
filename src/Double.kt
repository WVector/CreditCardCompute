package com.yimiao.app.ext

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * Created by Vector
 * on 2017/6/7 0007.
 */
fun Double.toCurrency(): String = NumberFormat.getCurrencyInstance(Locale.CHINA).format(this)

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