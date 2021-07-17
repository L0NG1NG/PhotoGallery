package com.longing.photogallery

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

private const val TAG = "PollWorker"

class PollWorker(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        val query = QueryPreferences.getStoredQuery(context)
        val lastResultId = QueryPreferences.getLastResultId(context)

        val items: List<GalleryItem> = if (query.isEmpty()) {
            FlickrFetchr().fetchPhotoRequest().execute()
                .body()?.photos?.galleryItems
        } else {
            FlickrFetchr().searchPhotoRequest(query)
                .execute().body()?.photos?.galleryItems
        } ?: emptyList()

        if (items.isEmpty()) {
            return Result.success()
        }

        val resultId = items.first().id
        if (resultId == lastResultId) {
            Log.i(TAG, "doWork: -->Got a old resultId:${resultId}")
        } else {
            Log.i(TAG, "doWork: -->Got a new resultId:${resultId}")
            QueryPreferences.setLastResultId(context, resultId)

            val intent = PhotoGalleryActivity.newInstance(context)
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val resource = context.resources
            val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setTicker(resource.getString(R.string.new_pictures_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(resource.getString(R.string.new_pictures_title))
                .setContentText(resource.getString(R.string.new_pictures_text))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
            val notificationManage = NotificationManagerCompat.from(context)
            notificationManage.notify(0, notification)
        }
        return Result.success()
    }
}