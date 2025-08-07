package com.example.joyfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.joyfinder.ui.LogViewModel
import com.example.joyfinder.util.ShareUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { JoyFinderApp() }
    }
}

@Composable
fun JoyFinderApp(viewModel: LogViewModel = viewModel()) {
    val logs by viewModel.logs.collectAsState()
    val averages by viewModel.activityAverages.collectAsState()
    var activity by remember { mutableStateOf("") }
    var score by remember { mutableStateOf(3f) }
    val formatter = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    var offlineAnalysis by remember { mutableStateOf("") }

    MaterialTheme {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Joy Finder") }) }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                OutlinedTextField(
                    value = activity,
                    onValueChange = { activity = it },
                    label = { Text("Activity") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Joy Score: ${'$'}{score.toInt()}")
                Slider(
                    value = score,
                    onValueChange = { score = it },
                    valueRange = 1f..5f,
                    steps = 3,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    Button(onClick = {
                        if (activity.isNotBlank()) {
                            viewModel.addLog(activity, score.toInt())
                            activity = ""
                            score = 3f
                        } else {
                            Toast.makeText(context, "Enter an activity", Toast.LENGTH_SHORT).show()
                        }
                    }) { Text("Add") }

                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { ShareUtils.shareLogs(context, logs) }) {
                        Text("Export")
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        val prompt = viewModel.generatePrompt(averages)
                        clipboard.setText(androidx.compose.ui.text.AnnotatedString(prompt))
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://gemini.google.com"))
                        context.startActivity(intent)
                    }) {
                        Text("Analyze Online")
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        offlineAnalysis = viewModel.offlineAnalysis(averages)
                    }) {
                        Text("Analyze Offline")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                if (logs.isEmpty()) {
                    Text("No logs yet. Add one above!")
                } else {
                    LazyColumn {
                        items(logs) { log ->
                            val time = formatter.format(Date(log.timestamp))
                            Text(text = "${'$'}time: ${'$'}{log.activity} - ${'$'}{log.joyScore}")
                            Divider()
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                averages.forEach { (act, avg) ->
                    Text(text = "$act: ${"%.1f".format(avg)}")
                }
                if (offlineAnalysis.isNotBlank()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = offlineAnalysis)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JoyFinderApp()
}

