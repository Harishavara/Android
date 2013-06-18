package com.backup;

import android.app.Activity;
import android.app.backup.BackupManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {
	
	EditText edtTxtSharedPref;
	CheckBox chkNotification, chkSound;
	Button btnSave;
	
	/** Also supply a global standard file name for everyone to use */
    static final String DATA_FILE_NAME = "shared_pref";
	
	BackupManager myBackupManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
		edtTxtSharedPref = (EditText)findViewById(R.id.edtTxtSharedPref);
		chkNotification = (CheckBox)findViewById(R.id.chkNotification);
		chkSound = (CheckBox)findViewById(R.id.chkSound);
		btnSave = (Button)findViewById(R.id.btnSave);
		
		btnSave.setOnClickListener(this);
		
		myBackupManager = new BackupManager(this);
		
		LoadPreferences();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
        case R.id.btnSave:
        	SavePreferences("displayName", edtTxtSharedPref.getText().toString());
        	SavePreferences("notification", chkNotification.isChecked());
        	SavePreferences("sound", chkSound.isChecked());
        	myBackupManager.dataChanged();
        	LoadPreferences();
        	break;
    	default:
    		break;
		}
	}
	
	private void SavePreferences(String key, String value){
	    SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.DATA_FILE_NAME, MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putString(key, value);
	    editor.commit();
	}
	
	private void SavePreferences(String key, boolean value){
		SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.DATA_FILE_NAME, MODE_PRIVATE);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putBoolean(key, value);
	    editor.commit();
	}
	
	private void LoadPreferences(){
		SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.DATA_FILE_NAME, MODE_PRIVATE);
		
	    String name = sharedPreferences.getString("displayName", "");
	    boolean chk1 = sharedPreferences.getBoolean("notification", false);
	    boolean chk2 = sharedPreferences.getBoolean("sound", false);
	    
	    edtTxtSharedPref.setText(name);
	    chkNotification.setChecked(chk1);
	    chkSound.setChecked(chk2);
	}
}

