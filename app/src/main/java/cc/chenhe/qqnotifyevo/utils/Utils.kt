package cc.chenhe.qqnotifyevo.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Environment
import cc.chenhe.qqnotifyevo.R
import java.io.File

//-----------------------------------------------------------
// Intent Action
//-----------------------------------------------------------

const val ACTION_DELETE_NEVO_CHANNEL = "deleteNevoChannel"

// Android O+ 通知渠道 id
const val NOTIFY_FRIEND_CHANNEL_ID = "QQ_Friend"
const val NOTIFY_FRIEND_SPECIAL_CHANNEL_ID = "QQ_Friend_Special"
const val NOTIFY_GROUP_CHANNEL_ID = "QQ_Group"
const val NOTIFY_QZONE_CHANNEL_ID = "QQ_Zone"

// 自身默认的通知类别
const val NOTIFY_GROUP_ID = "base"

const val GITHUB_URL = "https://github.com/liangchenhe55/QQ-Notify-Evolution/releases"
const val MANUAL_URL = "https://github.com/liangchenhe55/QQ-Notify-Evolution/wiki"

const val ALIPAY =
        "alipayqr://platformapi/startapp?saId=10000007&qrcode=https://qr.alipay.com/tsx12672qtk37hufsxfkub7"

/**
 * 适配的应用包名列表。
 */
val packageNameList: List<String>
    get() = listOf(
            "com.tencent.mobileqq",
            "com.tencent.tim",
            "com.tencent.qqlite"
    )

/**
 * 通知渠道 id 列表。
 */
val notificationChannelIdList: List<String>
    get() = listOf(
            NOTIFY_FRIEND_CHANNEL_ID,
            NOTIFY_FRIEND_SPECIAL_CHANNEL_ID,
            NOTIFY_GROUP_CHANNEL_ID,
            NOTIFY_QZONE_CHANNEL_ID
    )

fun getChannelId(channel: NotifyChannel): String = when (channel) {
    NotifyChannel.FRIEND -> NOTIFY_FRIEND_CHANNEL_ID
    NotifyChannel.FRIEND_SPECIAL -> NOTIFY_FRIEND_SPECIAL_CHANNEL_ID
    NotifyChannel.GROUP -> NOTIFY_GROUP_CHANNEL_ID
    NotifyChannel.QZONE -> NOTIFY_QZONE_CHANNEL_ID
}

/**
 * 创建通知渠道。仅创建渠道实例，未注册到系统。
 */
fun getNotificationChannels(context: Context, nevo: Boolean): List<NotificationChannel> {
    val prefix = if (nevo) context.getString(R.string.notify_nevo_prefix) else ""

    val att = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION_COMMUNICATION_INSTANT)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

    val friendChannel = NotificationChannel(NOTIFY_FRIEND_CHANNEL_ID,
            prefix + context.getString(R.string.notify_friend_channel_name),
            NotificationManager.IMPORTANCE_HIGH).apply {
        description = context.getString(R.string.notify_friend_channel_des)
        setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att)
        enableVibration(true)
        enableLights(true)
    }

    val friendSpecialChannel = NotificationChannel(NOTIFY_FRIEND_SPECIAL_CHANNEL_ID,
            prefix + context.getString(R.string.notify_friend_special_channel_name),
            NotificationManager.IMPORTANCE_HIGH).apply {
        description = context.getString(R.string.notify_friend_special_channel_des)
        setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att)
        enableVibration(true)
        enableLights(true)
    }

    val groupChannel = NotificationChannel(NOTIFY_GROUP_CHANNEL_ID,
            prefix + context.getString(R.string.notify_group_channel_name),
            NotificationManager.IMPORTANCE_HIGH).apply {
        description = context.getString(R.string.notify_group_channel_des)
        setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att)
        enableVibration(true)
        enableLights(true)
    }

    val qzoneChannel = NotificationChannel(NOTIFY_QZONE_CHANNEL_ID,
            prefix + context.getString(R.string.notify_qzone_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT).apply {
        description = context.getString(R.string.notify_qzone_channel_des)
        setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), att)
        enableLights(true)
    }

    return listOf(friendChannel, friendSpecialChannel, groupChannel, qzoneChannel)
}


fun getAvatarDiskCacheDir(context: Context): File {
    val cachePath: File = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
            || !Environment.isExternalStorageRemovable()) {
        context.externalCacheDir!!
    } else {
        context.cacheDir
    }
    return File(cachePath, "conversion_icon")
}
