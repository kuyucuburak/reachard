package com.burakkuyucu.reachard.sample

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
import com.burakkuyucu.reachard.di.ReachardDI

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
    Button(
        onClick = {
            ReachardDI.get<MainViewModel>().increaseCount()
        },
    ) {
        Text(text = "Increase count")
    }
}
