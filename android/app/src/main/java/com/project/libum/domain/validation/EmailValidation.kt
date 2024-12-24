package com.project.libum.domain.validation

class EmailValidation {
    companion object{
        fun validate(email: String): Boolean {
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            return email.matches(emailPattern.toRegex())
        }
    }

}