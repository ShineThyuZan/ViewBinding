package com.galaxy_techno.buyer.common

object Constant {
    const val DB_NAME = "buyer_db"
    const val DS_NAME = "buyer_ds"
    const val USER_TABLE = "buyer_user_table"

    const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.+)(\\.)(.{1,})"

    const val EMAIL_REGEX_ADDRESS = ("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
            + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
            + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$")

    const val STATUS_FAIL = "FAIL"
    const val STATUS_SUCCESS = "SUCCESS"
    const val AUTH_LOGGED_IN = true
    const val AUTH_LOGGED_OUT = false
}