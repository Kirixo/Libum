package com.project.libum.presentation.view.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.project.libum.LibumApp
import com.project.libum.presentation.view.activity.AuthorizationActivity
import com.project.libum.presentation.view.activity.MainActivity

fun Activity.navigateToMainActivity(context: Context){
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}

fun Activity.navigateToAuthorization(context: Context, error: String?){
    val intent = Intent(context, AuthorizationActivity::class.java)
    intent.putExtra(LibumApp.ERROR_MSG, error)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
}

fun showErrorMessage(context: Context?, errorMsg: String){
    Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
}