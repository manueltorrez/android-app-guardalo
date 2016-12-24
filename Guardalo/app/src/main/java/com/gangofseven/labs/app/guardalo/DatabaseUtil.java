package com.gangofseven.labs.app.guardalo;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by jlou trill on 12/22/2016.
 */

public class DatabaseUtil {
    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }

        return mDatabase;
    }
}
