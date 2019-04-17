package com.zhao.fingerrecodemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zhao.fingerprintrec.FingerRecoUtil
import com.zhao.fingerprintrec.FingerprintDialogFragment
import kotlinx.android.synthetic.main.content_main.*
class FingerPrintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (FingerRecoUtil.supportFingerprint(this)) {
            showFingerPrintDialog()
        }
       tv_click.setOnClickListener{
           if (FingerRecoUtil.supportFingerprint(this)) {
               showFingerPrintDialog()
           }
       }
    }

    private fun showFingerPrintDialog() {
       var dialogFragment = FingerprintDialogFragment<FingerPrintActivity>()
       dialogFragment.isCancelable = false
       dialogFragment.show(supportFragmentManager, "fingerprint")
    }
}
