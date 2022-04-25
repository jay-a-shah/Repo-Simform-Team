package com.example.simformcafeteria.Utils

import android.content.Context

import android.content.SharedPreferences
import com.example.simformcafeteria.model.User
import com.google.gson.GsonBuilder

class PreferenceHelper {

    private fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)
    }

    fun <T> put(appContext: Context, `object`: T, key: String) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        getPreference(appContext).edit().putString(key, jsonString).apply()
    }

    fun get(appContext: Context, key: String): User? {
        val value = getPreference(appContext).getString(key, null)
        return GsonBuilder().create().fromJson(value, User::class.java)
    }
}