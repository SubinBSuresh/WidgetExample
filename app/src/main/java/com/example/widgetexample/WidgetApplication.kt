package com.example.widgetexample

import android.app.Application

class WidgetApplication:Application() {
    companion object {
        const val ACTION_WIDGET_PLUS_CLICKED = "com.example.widgetexample.plus.clicked"
        const val ACTION_WIDGET_MINUS_CLICKED = "com.example.widgetexample.minus.clicked"
        const val TAG = "Dutchman"
    }
}