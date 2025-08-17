@file:OptIn(ExperimentalMaterial3Api::class)

package com.kuyucuburak.reachard.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kuyucuburak.reachard.di.ReachardDI
import com.kuyucuburak.reachard.namifier.ReachardNamifier
import com.kuyucuburak.reachard.namifier.enums.CaseTypeEnums.SENTENCE
import com.kuyucuburak.reachard.namifier.enums.CaseTypeEnums.TITLE

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeVariables()

        setContent {
            MainUI()
        }
    }

    private fun initializeVariables() {
        val mainViewModel = MainViewModel()

        ReachardDI.put(mainViewModel)
    }

    @Composable
    private fun MainUI() {
        val containerColor: Color = MaterialTheme.colorScheme.background
        val topBarContainerColor: Color = MaterialTheme.colorScheme.primary
        val topBarContentColor: Color = MaterialTheme.colorScheme.onPrimary

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_name),
                            color = topBarContentColor,
                            fontSize = 24.sp,
                        )
                    },
                    colors = TopAppBarColors(
                        containerColor = topBarContainerColor,
                        scrolledContainerColor = topBarContainerColor,
                        navigationIconContentColor = topBarContentColor,
                        titleContentColor = topBarContentColor,
                        actionIconContentColor = topBarContentColor,
                    ),
                )
            },
        ) { innerPadding: PaddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(containerColor)
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CounterShower()
                Spacer(modifier = Modifier.height(24.dp))
                CounterIncreaser()
            }
        }
    }

    @Composable
    private fun CounterShower() {
        val value: Int? by ReachardDI.get<MainViewModel>().counter.observeAsState()

        val contentColor: Color = MaterialTheme.colorScheme.onBackground

        Text(
            text = value.toString(),
            color = contentColor,
            fontSize = 36.sp,
        )
    }

    @Composable
    private fun CounterIncreaser() {
        val text: String = ReachardNamifier.convert(SENTENCE, TITLE, "increase count")

        Button(
            onClick = {
                ReachardDI.get<MainViewModel>().increaseCount()
            },
        ) {
            Text(text = text)
        }
    }
}
