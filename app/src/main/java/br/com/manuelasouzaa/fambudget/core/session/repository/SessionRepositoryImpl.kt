package br.com.manuelasouzaa.fambudget.core.session.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.manuelasouzaa.fambudget.core.session.model.TokenSession
import br.com.manuelasouzaa.fambudget.core.session.model.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SessionRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : SessionRepository {
    private val userIdKey = intPreferencesKey(USER_ID)
    private val userNameKey = stringPreferencesKey(USER_NAME)
    private val userEmailKey = stringPreferencesKey(USER_EMAIL)
    private val userPhoneNumberKey = stringPreferencesKey(USER_PHONE_NUMBER)
    private val isUserLoggedInKey = booleanPreferencesKey(IS_USER_LOGGED_IN)
    private val accessTokenKey = stringPreferencesKey(ACCESS_TOKEN)
    private val refreshTokenKey = stringPreferencesKey(REFRESH_TOKEN)

    private val _sessionExpired = MutableSharedFlow<Unit>()
    override val sessionExpired: SharedFlow<Unit> = _sessionExpired

    override val userSession: Flow<UserSession>
        get() = dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            UserSession(
                id = preferences[userIdKey] ?: -1,
                name = preferences[userNameKey] ?: "",
                email = preferences[userEmailKey] ?: "",
                phoneNumber = preferences[userPhoneNumberKey] ?: "",
                isLoggedIn = preferences[isUserLoggedInKey] ?: false
            )
        }

    override suspend fun saveUser(user: UserSession) {
        dataStore.edit { preferences ->
            preferences[userIdKey] = user.id
            preferences[userNameKey] = user.name
            preferences[userEmailKey] = user.email
            preferences[userPhoneNumberKey] = user.phoneNumber
            preferences[isUserLoggedInKey] = true
        }
    }

    override suspend fun expireSession() {
        _sessionExpired.emit(Unit)
        finishSession()
    }

    private suspend fun finishSession() {
        dataStore.edit { preferences ->
            preferences[isUserLoggedInKey] = false
        }
    }

    override suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[userIdKey] = -1
            preferences[userNameKey] = ""
            preferences[userEmailKey] = ""
            preferences[userPhoneNumberKey] = ""
            preferences[isUserLoggedInKey] = false
            preferences[accessTokenKey] = ""
            preferences[refreshTokenKey] = ""
        }
    }

    override suspend fun getAccessToken(): String {
        return dataStore.data.first()[accessTokenKey] ?: ""
    }

    override suspend fun getRefreshToken(): String {
        return dataStore.data.first()[refreshTokenKey] ?: ""
    }

    override suspend fun saveToken(tokenSession: TokenSession) {
        dataStore.edit { preferences ->
            preferences[accessTokenKey] = tokenSession.accessToken
            preferences[refreshTokenKey] = tokenSession.refreshToken
        }
    }

    companion object {
        const val USER_ID = "user_id"
        const val USER_NAME = "user_name"
        const val USER_EMAIL = "user_email"
        const val USER_PHONE_NUMBER = "user_phone_number"
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }
}
