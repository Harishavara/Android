package com.filebackup;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.os.Bundle;
import android.app.Activity;
import android.app.backup.BackupManager;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MainActivity extends Activity {
	static final String TAG = "FileBackup";
	
	// Object for intrinsic lock
	static final Object sDataLock = new Object();
	
	// Global standard file name for everyone to use
    static final String DATA_FILE_NAME = "saved_data";

    // The various bits of UI that the user can manipulate 
    CheckBox chkNotification, chkSound;

    // Cache a reference to our persistent data file
    File mDataFile;

    // Cache a reference to the Backup Manager
    BackupManager mBackupManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		/** Once the UI has been inflated, cache the controls for later */
		chkNotification = (CheckBox) findViewById(R.id.chkNotification);
		chkSound = (CheckBox) findViewById(R.id.chkSound);
		
		/** Set up our file bookkeeping */
        mDataFile = new File(getFilesDir(), MainActivity.DATA_FILE_NAME);
        
        /** It is handy to keep a BackupManager cached */
        mBackupManager = new BackupManager(this);

        // Finally, build the UI from the persistent store
        populateUI();
	}

	void populateUI() {
        RandomAccessFile file;

        // Default values in case there's no data file yet
        boolean addNotification = false;
        boolean addSound = false;

        /** Hold the data-access lock around access to the file */
        synchronized (MainActivity.sDataLock) {
            boolean exists = mDataFile.exists();
            try {
                file = new RandomAccessFile(mDataFile, "rw");
                if (exists) {
                    Log.v(TAG, "Datafile exists.");
                    addNotification = file.readBoolean();
                    addSound = file.readBoolean();
                    Log.v(TAG, "Notification=" + addNotification
                            + "\nSound=" + addSound);
                } else {
                    // The default values were configured above: write them
                    // to the newly-created file.
                    Log.v(TAG, "Creating default datafile.");
                    writeDataToFileLocked(file, addNotification, addSound);

                    // We also need to perform an initial backup; ask for one
                    mBackupManager.dataChanged();
                }
            } catch (IOException ioe) {
                
            }
        }

        /** Now that we've processed the file, build the UI outside the lock */
        chkNotification.setChecked(addNotification);
        chkSound.setChecked(addSound);

        CompoundButton.OnCheckedChangeListener checkListener
                = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked) {
                // Whichever one is altered, we rewrite the entire UI state
                Log.v(TAG, "Checkbox toggled: " + buttonView);
                recordNewUIState();
            }
        };
        chkNotification.setOnCheckedChangeListener(checkListener);
        chkSound.setOnCheckedChangeListener(checkListener);
    }
	
	/**
     * Handy helper routine to write the UI data to a file.
     */
    void writeDataToFileLocked(RandomAccessFile file,
            boolean addNotification, boolean addSound)
        throws IOException {
            file.setLength(0L);
            file.writeBoolean(addNotification);
            file.writeBoolean(addSound);
            Log.v(TAG, "NEW STATE: Notification=" + addNotification
                    + "\nSound=" + addSound);
    }
    
    /**
     * Another helper; this one reads the current UI state and writes that
     * to the persistent store, then tells the backup manager that we need
     * a backup.
     */
    void recordNewUIState() {
        boolean addNotification = chkNotification.isChecked();
        boolean addSound = chkSound.isChecked();
        try {
            synchronized (MainActivity.sDataLock) {
                RandomAccessFile file = new RandomAccessFile(mDataFile, "rw");
                writeDataToFileLocked(file, addNotification, addSound);
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to record new UI state");
        }

        mBackupManager.dataChanged();
    }
}
