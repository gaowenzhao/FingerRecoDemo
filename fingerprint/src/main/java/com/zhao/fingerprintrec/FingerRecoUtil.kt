package com.zhao.fingerprintrec

import android.annotation.TargetApi
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
@TargetApi(23)
object FingerRecoUtil {
    //========================= =========================
    private val TAG = FingerRecoUtil::class.java.simpleName
    private const val DEFAULT_KEY_NAME = "default_key"
    private lateinit var keyStore: KeyStore

    //========================= =========================
    fun isAvailable(context: Context): Boolean {//指纹可用
        val fm = FingerprintManagerCompat.from(context)
        return fm.isHardwareDetected && fm.hasEnrolledFingerprints()
    }

    fun getCipher(): Cipher? {
        initKey()
        return initCipher()
    }

    //========================= =========================
    private fun initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
            keyStore.load(null)
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val builder = KeyGenParameterSpec.Builder(
                DEFAULT_KEY_NAME, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setUserAuthenticationRequired(true)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            keyGenerator.init(builder.build())
            keyGenerator.generateKey()
        }
        catch (e: Exception) {
//            Loger.show(e.toString())
        }
    }

    private fun initCipher(): Cipher? {
        var cipher: Cipher? = null
        try {
            val key = keyStore.getKey(DEFAULT_KEY_NAME, null) as SecretKey
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES + "/"
                        + KeyProperties.BLOCK_MODE_CBC + "/"
                        + KeyProperties.ENCRYPTION_PADDING_PKCS7)
            cipher.init(Cipher.ENCRYPT_MODE, key)
        }
        catch (e: Exception) {
//            Loger.show(e.toString())
        }
        return cipher
    }
}