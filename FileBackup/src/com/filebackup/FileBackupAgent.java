package com.filebackup;

import java.io.IOException;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.os.ParcelFileDescriptor;

public class FileBackupAgent extends BackupAgentHelper {
	
	/**
     * The "key" string passed when adding a helper is a token used to
     * disambiguate between entities supplied by multiple different helper
     * objects.  They only need to be unique among the helpers within this
     * one agent class, not globally unique.
     */
    static final String FILE_HELPER_KEY = "the_file";

    @Override
    public void onCreate() {
        // All we need to do when working within the BackupAgentHelper mechanism
        // is to install the helper that will process and back up the files we
        // care about.  In this case, it's just one file.
        FileBackupHelper helper = new FileBackupHelper(this, MainActivity.DATA_FILE_NAME);
        addHelper(FILE_HELPER_KEY, helper);
    }

    /**
     * We want to ensure that the UI is not trying to rewrite the data file
     * while we're reading it for backup, so we override this method to
     * supply the necessary locking.
     */
    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data,
             ParcelFileDescriptor newState) throws IOException {
        // Hold the lock while the FileBackupHelper performs the backup operation
        synchronized (MainActivity.sDataLock) {
            super.onBackup(oldState, data, newState);
        }
    }

    /**
     * Adding locking around the file rewrite that happens during restore is
     * similarly straightforward.
     */
    @Override
    public void onRestore(BackupDataInput data, int appVersionCode,
            ParcelFileDescriptor newState) throws IOException {
        // Hold the lock while the FileBackupHelper restores the file from
        // the data provided here.
        synchronized (MainActivity.sDataLock) {
            super.onRestore(data, appVersionCode, newState);
        }
    }
}
