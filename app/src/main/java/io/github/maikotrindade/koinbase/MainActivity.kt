package io.github.maikotrindade.koinbase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.maikotrindade.koinbase.screen.PriceScreen
import io.github.maikotrindade.koinbase.screen.TransferScreen
import io.github.maikotrindade.koinbase.ui.theme.KoinbaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinbaseTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController, startDestination = "price"
                ) {
                    composable("price") {
                        PriceScreen(
                            navigateToTransfer = {
                                navController.navigate("transfer")
                            })
                    }
                    composable("transfer") {
                        val context = LocalContext.current
                        val message =
                            stringResource(R.string.success_dialog_transfer_successs_description)
                        TransferScreen(onBack = {
                            navController.popBackStack()
                        }, onConfirm = {
                            navController.popBackStack()
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        })
                    }
                }
            }
        }
    }
}
