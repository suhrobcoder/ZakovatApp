package uz.suhrob.zakovat.data.pref

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppPrefs @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val prefs = context.getSharedPreferences("app-prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_TYPE = "user_type"
        private const val KEY_GROUP_NAME = "group_name"
    }

    fun getUserType(): String? {
        return prefs.getString(KEY_USER_TYPE, null)
    }

    fun setUserType(type: String) {
        prefs.edit(true) {
            putString(KEY_USER_TYPE, type)
        }
    }

    fun getGroupName(): String? {
        return prefs.getString(KEY_GROUP_NAME, null)
    }

    fun setGroupName(name: String) {
        prefs.edit(true) {
            putString(KEY_GROUP_NAME, name)
        }
    }
}