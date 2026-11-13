package com.dumper.android.ui.wait

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dumper.android.ui.memory.MemoryViewModel

@Composable
fun WaitForProcessScreen(navController: NavController, viewModel: MemoryViewModel) {
    val context = LocalContext.current
    val target by viewModel.waitProcessName.collectAsState()
    val waiting by viewModel.isWaitingForProcess.collectAsState()
    val status by viewModel.waitingStatus.collectAsState()

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

        Button(
            onClick = { viewModel.startWaitingForProcess(context) },
            enabled = !waiting,
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

        Text(
            text = status,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
