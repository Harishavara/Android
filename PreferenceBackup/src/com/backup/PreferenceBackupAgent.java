package com.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;


public class PreferenceBackupAgent extends BackupAgentHelper {

    // An arbitrary string used within the BackupAgentHelper implementation to
    // identify the SharedPreferenceBackupHelper's data.
	static final String BACKUP_KEY = "shared_pref_backup";

    // Simply allocate a helper and install it
	@Override
    public void onCreate() {
        SharedPreferencesBackupHelper helper =
                new SharedPreferencesBackupHelper(this, MainActivity.DATA_FILE_NAME);
        addHelper(BACKUP_KEY, helper);
    }
}
