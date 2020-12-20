package com.salmanqureshi.eusa;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;


public class SessionManager {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context c;

    private static final String KEY_STATUS = "isLoggedIn";
    private static final String KEY_OK = "Is Logged In";

    private static final String KEY_UID = "UID";

    public SessionManager(Context _c) {
        c = _c;
        userSession = c.getSharedPreferences("userLogin", Context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createSession(String uid) {

        editor.putString(KEY_STATUS, KEY_OK);

        editor.putString(KEY_UID, uid);

        editor.commit();
    }

    public HashMap<String, String> getUserDetailsfromSesh() {
        HashMap<String, String> data = new HashMap<String, String>();

        data.put(KEY_UID, userSession.getString(KEY_UID, null));

        return data;
    }

    public boolean checkLogin() {
        Log.d("BCASD", userSession.getString(KEY_STATUS, null));

        if (userSession.getString(KEY_STATUS, null) == "Is Logged In") {
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}
