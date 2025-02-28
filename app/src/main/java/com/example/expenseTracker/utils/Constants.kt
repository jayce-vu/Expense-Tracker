package com.example.expenseTracker.utils

import com.example.expenseTracker.BuildConfig

object Constants {
    const val IMAGE_ID = "image_iD"
    const val FAV_ID = "favourite_id"
    const val BASE_URL = BuildConfig.baseUrl
    const val SHARED_PREFERENCES = BuildConfig.prefName
    const val PREF_PASSWORD = BuildConfig.prefPassword
    const val URL = "image_url"
    const val DATABASE_NAME = "expense_tracker.db"
    const val SUB_ID = "my-user97"
}

object Routes {
    const val HOME_SCREEN = "home_screen"
    const val MY_FAVOURITES_SCREEN = "my_favourites"
    const val LOGIN_SCREEN = "login_screen"
    const val SIGN_UP_SCREEN = "sign_up_screen"
    const val GET_STARTED_SCREEN = "get_started_screen"
    const val PROFILE_SCREEN = "profile_screen"
    const val ADD_EXPENSE_SCREEN = "add_expense_screen"
    const val TRANSACTION_HISTORY_SCREEN = "transaction_history_screen"
}