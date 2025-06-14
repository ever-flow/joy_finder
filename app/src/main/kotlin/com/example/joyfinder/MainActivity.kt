package com.example.joyfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.joyfinder.ui.LogViewModel
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
    var activity by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }
    val formatter = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = activity,
                    onValueChange = { activity = it },
                    label = { Text("Activity") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = score,
                    onValueChange = { score = it },
                    label = { Text("Joy Score (1-5)") },
                    keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    val s = score.toIntOrNull() ?: 0
                    if (activity.isNotBlank() && s in 1..5) {
                        viewModel.addLog(activity, s)
                        activity = ""
                        score = ""
                    }
                }) {
                    Text("Add")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                LazyColumn {
                    items(logs) { log ->
                        val time = formatter.format(Date(log.timestamp))
                        Text(text = "${'$'}time: ${'$'}{log.activity} - ${'$'}{log.joyScore}")
                        Divider()
                    }
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
