package com.example.geofancing;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static final String COUNTRY_CODE = "COUNTRY_CODE";
    private static final String PHONE_NUMBER = "PHONE_NUMBER";
    private static final String wallet_balance = "wallet_balance";
    private static final String isHomeLocationSet = "isHomeLocationSet";
    private static final String LATITUDE = "LATITUDE";
    private static final String LONGITUDE = "LONGITUDE";
    private static final String USER_NAME = "USER_NAME";
    private static final String AUTH_TOKEN = "AUTH_TOKEN";
    private static final String SESSION_TYPE = "SESSION_TYPE";
    private static final String SESSION_STATUS = "SESSION_STATUS";
    private static final String SESSION_END_TIME = "SESSION_END_TIME";
    private static final String SESSION_REWARDS = "SESSION_REWARDS";
    //For Firebase Token generated
    private static final String ID_TOKEN = "ID_TOKEN";
    private static SharedPref instance = null;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private SharedPref(Context context) {
        pref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public static synchronized SharedPref getInstance(Context context) {
        if (null == instance) instance = new SharedPref(context);
        return instance;
    }

    public String getCountryCode() {
        return pref.getString(COUNTRY_CODE, null);
    }

    public void setCountryCode(String code) {
        editor.putString(COUNTRY_CODE, code);
        editor.commit();
    }

    public String getPhoneNumber() {
        return pref.getString(PHONE_NUMBER, null);
    }

    public void setPhoneNumber(String number) {
        editor.putString(PHONE_NUMBER, number);
        editor.commit();
    }

    public float getWallet_balance() {
        return pref.getFloat(wallet_balance, 0);
    }

    public void setWallet_balance(float balance) {
        editor.putFloat(wallet_balance, balance);
        editor.commit();
    }

    public boolean getIsHomeLocationSet() {
        return pref.getBoolean(isHomeLocationSet, false);
    }

    public void setIsHomeLocationSet(boolean isHomeLocationSetOrNot) {
        editor.putBoolean(isHomeLocationSet, isHomeLocationSetOrNot);
        editor.commit();
    }

    public float getLatitude() {
        return pref.getFloat(LATITUDE, 0);
    }

    public void setLatitude(float latitude) {
        editor.putFloat(LATITUDE, latitude);
        editor.commit();
    }

    public float getLongitude() {
        return pref.getFloat(LONGITUDE, 0);
    }

    public void setLongitude(float longitude) {
        editor.putFloat(LONGITUDE, longitude);
        editor.commit();
    }

    public String getUsername() {
        return pref.getString(USER_NAME, null);
    }

    public void setUsername(String userName) {
        editor.putString(USER_NAME, userName);
        editor.commit();
    }

    public String getAuthToken() {
        return pref.getString(AUTH_TOKEN, null);
    }

    public void setAuthToken(String authToken) {
        editor.putString(AUTH_TOKEN, authToken);
        editor.commit();
    }

    public String getSessionType() {
        return pref.getString(SESSION_TYPE, null);
    }

    public void setSessionType(String type) {
        editor.putString(SESSION_TYPE, type);
        editor.commit();
    }

    public String getSessionStatus() {
        return pref.getString(SESSION_STATUS, null);
    }

    public void setSessionStatus(String status) {
        editor.putString(SESSION_STATUS, status);
        editor.commit();
    }

    public void setSessionEndTime(String endTime) {
        editor.putString(SESSION_END_TIME, endTime);
        editor.commit();
    }

    public String getSessionEndPoint() {
        return pref.getString(SESSION_END_TIME, null);
    }

    public String getSessionRewards() {
        return pref.getString(SESSION_REWARDS, null);
    }

    public void setSessionRewards(String rewards) {
        editor.putString(SESSION_REWARDS, rewards);
        editor.commit();
    }

    public String getIdToken() {
        return pref.getString(ID_TOKEN, null);
    }

    public void setIdToken(String idToken) {
        editor.putString(ID_TOKEN, idToken);
        editor.commit();
    }

    /**
     * Method call when user log-out of application
     */
    public void clearSession() {
        editor.clear();
        editor.commit();
    }
}
