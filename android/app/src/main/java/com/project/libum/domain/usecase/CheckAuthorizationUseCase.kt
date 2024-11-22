package com.project.libum.domain.usecase

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.project.libum.LibumApp
import com.project.libum.core.exeption.IncorrectPasswordException
import com.project.libum.core.exeption.NoCachedUserException
import com.project.libum.presentation.view.activity.AuthorizationActivity
import com.project.libum.presentation.view.activity.MainActivity

class CheckAuthorizationUseCase {

    suspend fun invoke(context: Context): Intent {
        val app = context.applicationContext as LibumApp
        return try {
            app.logInCachedUserUseCase.invoke()
            Intent(context, MainActivity::class.java)
        } catch (e: IncorrectPasswordException) {
            Intent(context, AuthorizationActivity::class.java).apply {
                putExtra(LibumApp.ERROR_MSG, e.message)
            }
        } catch (e: NoCachedUserException) {
            Intent(context, AuthorizationActivity::class.java)
        }
    }
}