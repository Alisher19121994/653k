package com.example.a653k

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.widget.Toast
import androidx.annotation.RequiresApi

open class MessagesBroadcast : BroadcastReceiver() {

    private var SMS = "android.provider.Telephony.SMS_RECEIVED"
    lateinit var msg: String

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent!!.action == SMS) {

            val bundle: Bundle? = intent.extras

            if (bundle != null) {
                val objects = bundle["sms"] as Array<*>?
                val messages = arrayOfNulls<SmsMessage>(objects!!.size)

                for (i in objects.indices) {
                    val format = bundle.getString("format")
                    messages[i] = SmsMessage.createFromPdu(objects[i] as ByteArray?, format)
                    msg = messages[i]?.messageBody.toString()

                }
            }
        }

        Toast.makeText(context, "Message: $msg", Toast.LENGTH_SHORT).show()
    }
}
