package com.example.a653k

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var SMS = "android.provider.Telephony.SMS_RECEIVED"
    lateinit var textView: TextView

    private lateinit var messagesBroadcast: MessagesBroadcast

    var smsBroadcast: MessagesBroadcast = object : MessagesBroadcast() {
        @RequiresApi(Build.VERSION_CODES.M)
        override fun onReceive(context: Context?, intent: Intent?) {
            super.onReceive(context, intent)
            textView.text = msg
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initViews()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                Toast.makeText(this, "SMS accepted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() {
        messagesBroadcast = MessagesBroadcast()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECEIVE_SMS
            ) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECEIVE_SMS), 1)

        }
    }

    override fun onStart() {
        super.onStart()
        val intent = IntentFilter(SMS)
        registerReceiver(messagesBroadcast, intent)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(messagesBroadcast)

    }
}