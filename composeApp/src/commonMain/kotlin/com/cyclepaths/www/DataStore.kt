package com.cyclepaths.www

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

fun createDataStore(producePath: () -> String): DataStore<Preferences> {
    return PreferenceDataStoreFactory.createWithPath(produceFile = { producePath().toPath() })
}

suspend fun setUser(
    prefs: DataStore<Preferences>,
    user: User
) {
    prefs.edit { dataStore ->
        val idKey = intPreferencesKey("user_id")
        dataStore[idKey] = user.id
        val usernameKey = stringPreferencesKey("username")
        dataStore[usernameKey] = user.username
        val emailKey = stringPreferencesKey("email")
        dataStore[emailKey] = user.email
        val firstNameKey = stringPreferencesKey("firstName")
        dataStore[firstNameKey] = user.first_name
        val lastNameKey = stringPreferencesKey("lastName")
        dataStore[lastNameKey] = user.last_name
        val passwordKey = stringPreferencesKey("password")
        dataStore[passwordKey] = user.password
    }
}

suspend fun logout(
    prefs: DataStore<Preferences>,
) {
    prefs.edit { dataStore ->
        val idKey = intPreferencesKey("user_id")
        dataStore[idKey] = -1
        val usernameKey = stringPreferencesKey("username")
        dataStore[usernameKey] = ""
        val emailKey = stringPreferencesKey("email")
        dataStore[emailKey] = ""
        val firstNameKey = stringPreferencesKey("firstName")
        dataStore[firstNameKey] = ""
        val lastNameKey = stringPreferencesKey("lastName")
        dataStore[lastNameKey] = ""
        val passwordKey = stringPreferencesKey("password")
        dataStore[passwordKey] = ""
    }
}

suspend fun getUser(prefs: DataStore<Preferences>): User? {

    var id: Int? = -1
    var username: String = ""
    var email: String = ""
    var password: String = ""
    var firstname: String = ""
    var lastname: String = ""

    val dataStore = prefs.data.getLastEmittedItem()

    if (dataStore != null) {
        val idKey = intPreferencesKey("user_id")
        id = dataStore[idKey]
        if (id != null) {
            val usernameKey = stringPreferencesKey("username")
            username = dataStore[usernameKey]!!
            val emailKey = stringPreferencesKey("email")
            email = dataStore[emailKey]!!
            val firstNameKey = stringPreferencesKey("firstName")
            firstname = dataStore[firstNameKey]!!
            val lastNameKey = stringPreferencesKey("lastName")
            lastname = dataStore[lastNameKey]!!
            val passwordKey = stringPreferencesKey("password")
            password = dataStore[passwordKey]!!
            return User(id, username, firstname, lastname, email, password)
        }
    }
    return null
}

internal const val DATA_STORE_FILENAME = "prefs.preferences_pb"