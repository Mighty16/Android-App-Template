package com.vv.core.ui.notifications


interface UINotification {
    val id: String
    val title: String
    val description: String
}

sealed interface BaseUINotification : UINotification {
    data class Error(
        override val id: String,
        override val title: String,
        override val description: String
    ) : BaseUINotification

    data class Success(
        override val id: String,
        override val title: String,
        override val description: String
    ) : BaseUINotification
}

interface UINotificationsHandler {
    fun showUINotification(notification: UINotification)
}

