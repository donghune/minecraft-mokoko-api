package io.github.donghune.api.extensions

import java.text.DecimalFormat

private val df = DecimalFormat("#,##0")

fun Long.moneyFormat(colorCode: String = "&6"): String {
    return "$colorCode${df.format(this)}&f".translateColor()
}

fun Int.moneyFormat(colorCode: String = "&6"): String {
    return "$colorCode${df.format(this)}&f".translateColor()
}