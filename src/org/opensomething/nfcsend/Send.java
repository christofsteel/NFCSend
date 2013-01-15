package org.opensomething.nfcsend;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

public class Send extends Activity {	
	private static final int REQUEST_CODE = 6384; // onActivityResult request code
	private NfcAdapter nfcAdapter;
	private BeamCallback callback;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        setContentView(R.layout.activity_send);
        if (nfcAdapter == null) return;  // NFC not available on this device
        callback = new BeamCallback();
        nfcAdapter.setBeamPushUrisCallback(callback, this);
    }
    	
    public void openSelect(View view) {
    	Intent target = FileUtils.createGetContentIntent();
		// Create the chooser Intent
		Intent intent = Intent.createChooser(
				target, getString(R.string.chooser_title));
		try {
			startActivityForResult(intent, REQUEST_CODE);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(Send.this, "Kaputt", Toast.LENGTH_LONG).show();
			// The reason for the existence of aFileChooser
		}			
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE:	
			// If the file selection was successful
			if (resultCode == RESULT_OK) {		
				if (data != null) {
					// Get the URI of the selected file
					final Uri uri = data.getData();

					try {
						// Create a file instance from the URI
						final File file = FileUtils.getFile(uri);
						Toast.makeText(Send.this, 
								"File Selected: "+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
						final TextView tv_selected = (TextView) findViewById(R.id.textView_select);
						tv_selected.setText(file.getName());
						//nfcAdapter.setBeamPushUris(new Uri[] {Uri.fromFile(file)}, this);
						callback.setUri(Uri.fromFile(file));
					} catch (Exception e) {
						Log.e("FileSelectorTestActivity", "File select error", e);
					}
				}
			} 
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    
}
