package com.galaxy_techno.buyer.common

object Endpoint {
    //API URLs
    const val API_HOST = "http://192.168.100.241:8080/buyer/"
//    const val API_HOST = "http://18.139.50.179:8080/buyer/"

    private const val REGISTER_PATH = "register/"
    private const val LOGIN_PATH = "login/"
    private const val MASTER_DATA = "masterData/"

    const val GET_OTP = API_HOST + REGISTER_PATH + "getOTP"
    const val VALIDATE_OTP = API_HOST + REGISTER_PATH + "validateOTP"

    const val LOGIN = API_HOST + "doLogin"
    const val REGISTER = API_HOST + REGISTER_PATH + "customerRegister"
    const val FORGET_PASSWORD = API_HOST + "forget_password"

    /**  for AccessToken and Refresh Token (Authorization) */
    const val AUTHORIZATION = "Authorization"

    //get category list
    const val GET_CATEGORY_LIST = API_HOST + MASTER_DATA + "getCategoryList"
    const val REGISTER_FAVOURITE_CATEGORY = API_HOST + REGISTER_PATH + "customerCategoryRegister"

    // get country List
    const val GET_COUNTRY_LIST = API_HOST + MASTER_DATA + "getCountryList"

    // get state list
    const val GET_STATE_LIST = API_HOST + MASTER_DATA + "getStateList"

    //get township list
    const val GET_TOWNSHIP_LIST = API_HOST+ MASTER_DATA + "getTownshipList"

    // get Token
    const val GET_REFRESH_TOKEN = API_HOST + "token/refresh"

}