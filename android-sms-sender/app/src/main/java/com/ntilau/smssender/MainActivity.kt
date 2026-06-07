package com.ntilau.smssender

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SMS_PERMISSION_REQUEST_CODE = 1001
        private const val DESTINATION = "1266"
        private const val MESSAGE = "NL2000 AAN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when {
            hasSmsPermission() -> sendSmsAndFinish()
            shouldShowPermissionRationale() -> showPermissionRationale()
            else -> requestSmsPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != SMS_PERMISSION_REQUEST_CODE) return

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            sendSmsAndFinish()
        } else {
            if (shouldShowPermissionRationale()) {
                showPermissionRationale()
            } else {
                showPermanentlyDeniedDialog()
            }
        }
    }

    private fun hasSmsPermission(): Boolean =
        ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) ==
            PackageManager.PERMISSION_GRANTED

    private fun shouldShowPermissionRationale(): Boolean =
        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)

    private fun requestSmsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS),
            SMS_PERMISSION_REQUEST_CODE
        )
    }

    private fun showPermissionRationale() {
        AlertDialog.Builder(this)
            .setTitle("SMS Permission Required")
            .setMessage("This app sends an SMS to 1266 when triggered by Samsung Routines. Please grant SMS permission.")
            .setPositiveButton("Grant") { _, _ -> requestSmsPermission() }
            .setNegativeButton("Cancel") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    private fun showPermanentlyDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("SMS permission has been permanently denied. Grant it in App Settings for this app to work.")
            .setPositiveButton("Settings") { _, _ -> openAppSettings(); finish() }
            .setNegativeButton("Cancel") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

    private fun sendSmsAndFinish() {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(DESTINATION, null, MESSAGE, null, null)
            Toast.makeText(this, "SMS sent to ${DESTINATION}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send SMS: ${e.message}", Toast.LENGTH_LONG).show()
        } finally {
            finish()
        }
    }
}
