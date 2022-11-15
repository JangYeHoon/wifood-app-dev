package com.yogo.wifood.presentation.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

class SMSReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        Log.d("인증번호 추출 ", "리시브")
        if (p1?.action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            val bundle = p1?.extras
            val messages = smsMessageParse(bundle!!)
            Log.d("인증번호 추출 ", "액션 일치")

            if (messages.isNotEmpty()) {
                val content = messages[0]?.messageBody.toString()
                val certNumber = content.replace("[^0-9]".toRegex(), "")
                Log.d("인증번호 추출 ",certNumber)
            }
        }
    }

    private fun smsMessageParse(bundle: Bundle): Array<SmsMessage?> {
        val objs = bundle["pdus"] as Array<*>?
        val messages: Array<SmsMessage?> = arrayOfNulls<SmsMessage>(objs!!.size)
        for (i in objs.indices) {
            messages[i] = SmsMessage.createFromPdu(objs[i] as ByteArray)
        }
        return messages
    }
}