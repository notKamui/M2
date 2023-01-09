package com.notkamui.exam.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/** A country with its flag */
class Country(val code: String, val name: String) {
    @Transient var cachedFlag: Bitmap? = null

    fun getFlag(context: Context): Bitmap {
        val cf = cachedFlag
        return if (cf != null) cf
        else {
            val f = context.assets.open("$DATA_LOCATION/flags/$code.png").use { BitmapFactory.decodeStream(it) }
            cachedFlag = f
            f
        }
    }

    // Two countries are equal if they have the same code

    override fun hashCode() = code.hashCode()

    override fun equals(other: Any?) = (other as? Country)?.code == code

    override fun toString() = "$name ($code)"

    companion object {
        const val DATA_LOCATION = "data"

        fun loadList(context: Context): List<Country> {
            val text = context.assets.open("$DATA_LOCATION/countries.json").use { it.readBytes().toString(Charsets.UTF_8) }
            return Gson().fromJson(text, object: TypeToken<List<Country>>() {}.type)
        }
    }
}