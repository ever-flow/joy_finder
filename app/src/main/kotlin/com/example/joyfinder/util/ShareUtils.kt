package com.example.joyfinder.util

import android.content.Context
import androidx.core.app.ShareCompat
import com.example.joyfinder.data.ActivityLog
import java.text.SimpleDateFormat
import java.util.Locale

object ShareUtils {
    fun shareLogs(context: Context, logs: List<ActivityLog>) {
        if (logs.isEmpty()) return
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val csv = buildString {
            append("timestamp,activity,joyScore\n")
            logs.forEach { log ->
                val time = formatter.format(log.timestamp)
                append("$time,${log.activity},${log.joyScore}\n")
            }
        }
        ShareCompat.IntentBuilder(context)
            .setType("text/csv")
            .setText(csv)
            .setChooserTitle("Share Logs")
            .startChooser()
    }
}
