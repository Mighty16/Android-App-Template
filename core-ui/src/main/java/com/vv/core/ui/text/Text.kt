package com.vv.core.ui.text

import android.content.Context
import android.widget.TextView
import androidx.annotation.StringRes

sealed interface Text {

    data class SimpleText(val text: String) : Text
    data class ResText(@StringRes val res: Int) : Text
    data class ResParamsText(@StringRes val res: Int, val params: List<Any>) : Text
}

fun TextView.setText(textObject: Text) {
    this.text = textObject.resolve(context)
}

fun Text.resolve(context: Context): String {
    return when (this) {
        is Text.ResText -> context.getString(res)
        is Text.SimpleText -> text
        is Text.ResParamsText -> context.getString(res).format(params)
    }
}