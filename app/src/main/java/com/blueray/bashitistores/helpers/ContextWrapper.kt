package com.blueray.bashitistores.helpers

import android.content.Context
import android.os.Build
import android.os.LocaleList
import java.util.*

/** Documentation
 *
 * ContextWrapper class is a singleton that have one methode
 * the purpose the methode wrap is to make a new context of the activity
 * the new context is a localized context meaning that it CHANGES the Language of the following
 * - The activity attached to it
 * - All the resources that belongs to the activity attached to it
 *
 *this class is recommended to ensure that the activity and its fragment,resources,etc are correctly localized
 */

class ContextWrapper(base: Context?) : android.content.ContextWrapper(base) {
    companion object {
        fun wrap(context: Context, newLocale: Locale?): ContextWrapper {
            var mContext = context
            val res = context.resources
            val configuration = res.configuration
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                mContext = context.createConfigurationContext(configuration)
            } else {
                configuration.locale = newLocale
                res.updateConfiguration(configuration, res.displayMetrics)
            }
            return ContextWrapper(mContext)
        }
    }
}

/**
 * the code bellow is how to use it in your activity
 **/
/*
override fun attachBaseContext(newBase: Context?) {
    val lang = HelperUtils.getLang(newBase!!)
    val local = Locale(lang)
    val newContext = ContextWrapper.wrap(newBase, local)
    super.attachBaseContext(newContext)
}
*/
