package com.dumper.android.ui.wait

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dumper.android.ui.memory.MemoryViewModel

private enum class ProcessListMethod { ANDROID, SHIZUKU }

@Composable
fun WaitForProcessScreen(navController: NavController, viewModel: MemoryViewModel) {
    val context = LocalContext.current
    val target by viewModel.waitProcessName.collectAsState()
    val waiting by viewModel.isWaitingForProcess.collectAsState()
    val status by viewModel.waitingStatus.collectAsState()
    var selectedMethod by rememberSaveable { mutableStateOf(ProcessListMethod.ANDROID) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Wait for process")

        OutlinedTextField(
            value = target,
            onValueChange = { viewModel.changeWaitProcessName(it) },
            label = { Text("Target process") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Process list method")
        MethodChoice(
            title = "Android (current implementation)",
            selected = selectedMethod == ProcessListMethod.ANDROID,
            onClick = { selectedMethod = ProcessListMethod.ANDROID }
        )
        MethodChoice(
            title = "Shizuku (integration placeholder)",
            selected = selectedMethod == ProcessListMethod.SHIZUKU,
            onClick = { selectedMethod = ProcessListMethod.SHIZUKU }
        )

        if (selectedMethod == ProcessListMethod.SHIZUKU) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Shizuku setup")
                    Text(text = "Not connected — Shizuku API integration is intentionally left as a TODO.")
                    Text(text = "Selecting this option does not change process discovery or start any privileged action.")
                    Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
                        Text("Check connection (not implemented)")
                    }
                    Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) {
                        Text("Request permission (not implemented)")
                    }
                }
            }
        }

        Button(
            onClick = { viewModel.startWaitingForProcess(context) },
            enabled = !waiting && selectedMethod == ProcessListMethod.ANDROID,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start wait")
        }

        Button(
            onClick = { viewModel.stopWaitingForProcess(context) },
            enabled = waiting,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Stop")
        }

        Text(text = status, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun MethodChoice(title: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = title, modifier = Modifier.padding(start = 8.dp))
    }
}
