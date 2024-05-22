package com.wuzhu.ncrash.monitor

import android.os.Bundle
import android.os.Process
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.toptop.nativecrash.NativeCrashMonitor
import com.wuzhu.ncrash.monitor.ui.theme.AndroidNativeCrashMonitorTheme

private const val TAG = "MainActivity"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidNativeCrashMonitorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(onClickCreateCrash = {
                        Log.e(TAG, "---nativeCrash---: nativeCrashCreate" )
                        NativeCrashMonitor.nativeCrashCreate()
                    })
                }
            }
        }

        init()
    }

    private fun init() {
        NativeCrashMonitor.init { threadName: String?, error: Error? ->
            Log.e(TAG, "---nativeCrash---onCrash: \nthreadName=$threadName,\nerror=$error")
        }
        Log.e(TAG, "---nativeCrash---: init success" )
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier, onClickCreateCrash: (() -> Unit)? = null) {
    Row {
        Button(onClick = {
            onClickCreateCrash?.invoke()
        }) {
            Text(
                text = "create crash",
                modifier = modifier
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidNativeCrashMonitorTheme {
        Greeting()
    }
}