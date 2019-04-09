package com.zhao.fingerrecodemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.zhao.fingerprintrec.FingerRecoUtil;
import com.zhao.fingerprintrec.FingerprintDialogFragment;

import javax.crypto.Cipher;
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (FingerRecoUtil.INSTANCE.supportFingerprint(this)) {
            showFingerPrintDialog(FingerRecoUtil.INSTANCE.getCipher()) ;
        }
    }

    private void showFingerPrintDialog(Cipher cipher) {
        FingerprintDialogFragment fragment = new FingerprintDialogFragment<LoginActivity>();
        fragment.setCancelable(false);
        fragment.setCipher(cipher);
        fragment.show(getSupportFragmentManager(), "fingerprint");
    }

    public void onAuthenticated() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
