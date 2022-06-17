package io.github.donghune.api.extensions

import org.bukkit.ChatColor

fun String.translateColor(code: Char = '&') = ChatColor.translateAlternateColorCodes(code, this)
fun String.reverseTranslateColor(code: Char = '&') = replace('§', code)