/*
 * Copyright (c) 2018 Voonder
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.voonapp.boilerplate.ui.view.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.AsyncTask
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import com.voonapp.boilerplate.R
import com.voonapp.boilerplate.di.picture.GlideApp
import timber.log.Timber
import java.lang.ref.WeakReference

abstract class BaseNotification(private val context: Context) {

    protected abstract val channel: NotificationInformation.Channel
    protected abstract val content: NotificationInformation.Content
    protected abstract val data: NotificationInformation.Data
    protected abstract val intentToOpen: Intent
    protected abstract val style: NotificationCompat.Style?

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels()
        }

        createNotification()
    }

    private fun createNotification() {
        intentToOpen.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

        val taskStackBuilder = TaskStackBuilder.create(context)
            .addNextIntent(intentToOpen)

        val pendingIntent = taskStackBuilder.getPendingIntent(content.id.toInt(), PendingIntent.FLAG_ONE_SHOT)

        @Suppress("DEPRECATION")
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(context, channel.id)
        } else {
            NotificationCompat.Builder(context)
        }

        builder.setAutoCancel(true)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
            .setCategory(data.category)
            .setColor(ContextCompat.getColor(context, data.color))
            .setContentIntent(pendingIntent)
            .setContentText(content.message)
            .setContentTitle(content.title)
            .setDefaults(Notification.DEFAULT_ALL)
            .setGroup(data.groupKey)
            .setGroupSummary(true)
            .setLights(
                ContextCompat.getColor(context, data.color),
                context.resources.getInteger(R.integer.notification_light_duration),
                context.resources.getInteger(R.integer.notification_light_sleep)
            )
            .setSmallIcon(data.icon)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(
                longArrayOf(
                    0,
                    context.resources.getInteger(R.integer.notification_vibrate_duration).toLong(),
                    context.resources.getInteger(R.integer.notification_vibrate_sleep).toLong(),
                    context.resources.getInteger(R.integer.notification_vibrate_duration).toLong()
                )
            )
            .setVisibility(Notification.VISIBILITY_PUBLIC)

        if (content.withBigIcon && content.image != null) {
            builder.setLargeIcon(content.image)
        }

        if (style != null) {
            builder.setStyle(style)
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(content.id.toInt(), builder.build())
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createChannels() {
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()

        val notificationChannel =
            NotificationChannel(channel.id, channel.name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                enableLights(true)
                enableVibration(true)
                description = channel.descrition
                lightColor = ContextCompat.getColor(context, data.color)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                setShowBadge(true)
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), audioAttributes)
                vibrationPattern = longArrayOf(
                    0,
                    context.resources.getInteger(R.integer.notification_vibrate_duration).toLong(),
                    context.resources.getInteger(R.integer.notification_vibrate_sleep).toLong(),
                    context.resources.getInteger(R.integer.notification_vibrate_duration).toLong()
                )
            }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(notificationChannel)
    }

    class ImageAsync(private val context: WeakReference<Context>, private val url: String) :
        AsyncTask<Unit, Unit, Bitmap?>() {
        override fun doInBackground(vararg params: Unit?): Bitmap? {
            if (url.isEmpty()) return null

            return try {
                GlideApp.with(context.get()!!)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get()
            } catch (e: Exception) {
                Timber.e(e, "ImageAsync download error")
                null
            }
        }
    }
}
