package org.opensomething.nfcsend;

import android.net.Uri;
import android.nfc.NfcAdapter.CreateBeamUrisCallback;
import android.nfc.NfcEvent;

public class BeamCallback implements CreateBeamUrisCallback {
	
	private Uri uri;
	
	@Override
	public Uri[] createBeamUris(NfcEvent arg0) {
		// TODO Auto-generated method stub
		
		return new Uri[] {uri};
	}

	public void setUri(Uri uri) {
		this.uri = uri;
	}

}
