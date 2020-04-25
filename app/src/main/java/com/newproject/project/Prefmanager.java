package com.newproject.project;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefmanager {
     private  static final String KEY_FIRST_NAME="firstname";
    private  static final String KEY_LAST_NAME="lastname";
    private  static final String KEY_EMAIL="email";
    private  static final String KEY_PHONE="phone";
    private  static final String KEY_USER_NAME="username";
    private static final String KEY_SHARED_PREF="saveuser";
   private Context context;
    private SharedPreferences preferences;
    Prefmanager(Context context) {
        this.context=context;
    }
    public boolean UserLogin(){
     preferences=context.getSharedPreferences(KEY_SHARED_PREF,Context.MODE_PRIVATE);
        return preferences.getString(KEY_USER_NAME, null) != null;
    }
    public boolean UserLogout(){
        preferences=context.getSharedPreferences(KEY_SHARED_PREF,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }
    public void saveUserDetail(String firstname,String lastname,String email,String phone,String username){
       preferences=context.getSharedPreferences(KEY_SHARED_PREF,Context.MODE_PRIVATE);
    SharedPreferences.Editor editor=preferences.edit();
    editor.putString(KEY_FIRST_NAME,firstname);
    editor.putString(KEY_LAST_NAME,lastname);
    editor.putString(KEY_EMAIL,email);
    editor.putString(KEY_PHONE,phone);
    editor.putString(KEY_USER_NAME,username);
    editor.apply();
    }
     public String getFirstName(){
        preferences=context.getSharedPreferences(KEY_SHARED_PREF,Context.MODE_PRIVATE);
        return preferences.getString(KEY_USER_NAME,null);
     }

    public  String getLastName() {
        preferences=context.getSharedPreferences(KEY_SHARED_PREF,Context.MODE_PRIVATE);
        return preferences.getString(KEY_LAST_NAME,null);
    }
    public String getEmail(){
        preferences=context.getSharedPreferences(KEY_SHARED_PREF,Context.MODE_PRIVATE);
        return  preferences.getString(KEY_EMAIL,null);
    }
    public String getPhone(){
        preferences=context.getSharedPreferences(KEY_PHONE,Context.MODE_PRIVATE);
        return preferences.getString(KEY_PHONE,null);
    }
    public String getUserName(){
        preferences=context.getSharedPreferences(KEY_SHARED_PREF,Context.MODE_PRIVATE);
        return preferences.getString(KEY_USER_NAME,null);
    }
}
