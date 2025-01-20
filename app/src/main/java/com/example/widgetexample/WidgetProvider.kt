package com.example.widgetexample

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.example.widgetexample.WidgetApplication.Companion.ACTION_WIDGET_MINUS_CLICKED
import com.example.widgetexample.WidgetApplication.Companion.ACTION_WIDGET_PLUS_CLICKED
import com.example.widgetexample.WidgetApplication.Companion.TAG

class WidgetProvider : AppWidgetProvider() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "onReceive()")
        super.onReceive(context, intent)
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val componentName = ComponentName(context, WidgetProvider::class.java)
        val widgetIds = appWidgetManager.getAppWidgetIds(componentName)
        when (intent.action) {
            ACTION_WIDGET_PLUS_CLICKED -> {
                Log.i(TAG, "plus clicked")
                updateWidgetText(context, appWidgetManager, widgetIds, "plus button")
            }

            ACTION_WIDGET_MINUS_CLICKED -> {
                Log.i( TAG, "minus clicked")
                updateWidgetText(context, appWidgetManager, widgetIds, "minus button")

            }
            // resize of widgets will be received here
            else -> Log.d(TAG, "Unknown action: ${intent.action}")
        }
    }


    override fun onUpdate(
        context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray
    ) {
        Log.i(TAG, "onUpdate()")
        appWidgetIds.forEach { widgetId ->
            updateAppWidget(context, appWidgetManager, widgetId)
        }
    }

    private fun updateAppWidget(
        context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int
    ) {
        Log.i(TAG, "updateWidget() :: updating widget")
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)
        val plusIntent = Intent(context, WidgetProvider::class.java).apply {
            action = ACTION_WIDGET_PLUS_CLICKED
        }
        val minusIntent = Intent(context, WidgetProvider::class.java).apply {
            action = ACTION_WIDGET_MINUS_CLICKED
        }
        val plusPendingIntent = PendingIntent.getBroadcast(
            context, appWidgetId,
            plusIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val minusPendingIntent = PendingIntent.getBroadcast(
            context, appWidgetId,
            minusIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        views.setOnClickPendingIntent(R.id.plus_button, plusPendingIntent)
        views.setOnClickPendingIntent(R.id.minus_button, minusPendingIntent)
        views.setTextViewText(R.id.tv_value, "Widget updated")
        appWidgetManager.updateAppWidget(appWidgetId, views)
        Log.i(TAG, "updateWidget() :: updating widget done")

    }

    private fun updateWidgetText(
        context: Context, appWidgetManager: AppWidgetManager, widgetIds: IntArray, message: String
    ) {
        Log.i(TAG, "updateWidgetTex() with string: $message")
        widgetIds.forEach { widgetId ->
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            views.setTextViewText(R.id.tv_value, message)
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}