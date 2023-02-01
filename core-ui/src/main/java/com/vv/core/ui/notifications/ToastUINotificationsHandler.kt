package com.vv.core.ui.notifications

import android.content.Context
import android.widget.Toast

class ToastUINotificationsHandler(
    private val contextProvider: () -> Context?
) : UINotificationsHandler {

    override fun showUINotification(notification: UINotification) {
        val ctx = contextProvider.invoke() ?: return
        val text = getTextForNotification(notification)
        Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show()
    }

    private fun getTextForNotification(notification: UINotification): String? {
        return if (notification.description.isNotEmpty()) {
            "${notification.title}\n${notification.description}"
        }else {
            notification.title
        }
    }
}