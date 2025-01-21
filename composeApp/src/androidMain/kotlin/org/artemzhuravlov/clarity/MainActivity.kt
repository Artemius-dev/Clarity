package org.artemzhuravlov.clarity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.defaultComponentContext
import com.google.firebase.FirebaseApp
import org.artemzhuravlov.clarity.di.koin
import org.artemzhuravlov.clarity.navigation.root.IRootComponent
import org.artemzhuravlov.clarity.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        installSplashScreen()

        val rootComponentFactory: IRootComponent.Factory by koin.inject()

        // Always create the root component outside Compose on the main thread
        val rootComponent = rootComponentFactory(defaultComponentContext())

        setContent {
            App(rootComponent = rootComponent)
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp", showBackground = true, showSystemUi = true)
@Composable
fun AppAndroidPreview() {
    AppTheme {
        Scaffold(modifier = Modifier.safeDrawingPadding().padding(16.dp)) { padding ->
//            ClinicDetailsScreen()
//            ContactsScreen()
        }
    }
}