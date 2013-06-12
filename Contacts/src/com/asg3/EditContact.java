package com.asg3;

import com.asg3.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditContact extends Activity implements OnClickListener {

	private EditText edtID, edtContactNumber;
	private Button btnEditContact, btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.editcontact);

		edtID = (EditText) findViewById(R.id.edtID);
		edtContactNumber = (EditText) findViewById(R.id.edtUpdateContactNumber);

		btnEditContact = (Button) findViewById(R.id.btnEditContact);
		btnCancel = (Button) findViewById(R.id.btnCancel);

		btnEditContact.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

	}

	public void onClick(View v) {

		Intent intent = new Intent(this, MainActivity.class);
		if (v.getId() == R.id.btnEditContact) {
				ContactHelper.updateContact(getContentResolver(),
						edtID.getText().toString(),
						edtContactNumber.getText().toString());
				edtID.setText("");
				edtContactNumber.setText("");

				startActivity(intent);

		} else if (v.getId() == R.id.btnCancel) {
			super.onBackPressed();
		}

	}
}
