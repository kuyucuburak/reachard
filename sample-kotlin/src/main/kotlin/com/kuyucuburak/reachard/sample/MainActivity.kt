package com.kuyucuburak.reachard.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuyucuburak.reachard.di.ReachardDI
import com.kuyucuburak.reachard.namifier.ReachardNamifier
import com.kuyucuburak.reachard.namifier.enums.CaseTypeEnums.SENTENCE
import com.kuyucuburak.reachard.namifier.enums.CaseTypeEnums.TITLE

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel = MainViewModel()
        ReachardDI.put(mainViewModel)

        setContent {
            MainUI()
        }
    }
}

@Composable
fun MainUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CounterShower()
        Spacer(modifier = Modifier.height(24.dp))
        CounterIncreaser()
    }
}

@Composable
fun CounterShower() {
    val value by ReachardDI.get<MainViewModel>().counter.observeAsState()

    Text(
        text = value.toString(),
        color = Color(0xFFFFA500),
        fontSize = 36.sp,
    )
}

@Composable
fun CounterIncreaser() {
    val text = ReachardNamifier.convert(SENTENCE, TITLE, "increase count")

    Button(
        onClick = {
            ReachardDI.get<MainViewModel>().increaseCount()
        },
    ) {
        Text(text = text)
    }
}
