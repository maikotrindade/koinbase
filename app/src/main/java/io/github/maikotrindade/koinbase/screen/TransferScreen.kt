package io.github.maikotrindade.koinbase.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.maikotrindade.koinbase.R
import io.github.maikotrindade.koinbase.ui.theme.AccentBlue
import io.github.maikotrindade.koinbase.ui.theme.DarkBlueBg
import io.github.maikotrindade.koinbase.ui.theme.GrayText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen() {
    var amount by remember { mutableStateOf("") }
    var toAddress by remember { mutableStateOf("") }

    val mockedTransactions = listOf(
        stringResource(R.string.transfer_screen_received) to "0.00000001 BTC",
        stringResource(R.string.transfer_screen_sent) to "0.00000002 BTC",
        stringResource(R.string.transfer_screen_received) to "0.00000003 BTC",
        stringResource(R.string.transfer_screen_sent) to "0.00000004 BTC",
        stringResource(R.string.transfer_screen_received) to "0.00000005 BTC",
        stringResource(R.string.transfer_screen_sent) to "0.00000004 BTC",
        stringResource(R.string.transfer_screen_received) to "0.00000001 BTC",
        stringResource(R.string.transfer_screen_sent) to "0.00000324 BTC",
        stringResource(R.string.transfer_screen_received) to "0.00000025 BTC",
        stringResource(R.string.transfer_screen_sent) to "0.00000032 BTC",
        stringResource(R.string.transfer_screen_received) to "0.00000012 BTC",
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DarkBlueBg
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 70.dp) // Leave space for button
                    .verticalScroll(rememberScrollState())
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.transfer_screen_send_bitcoin),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(R.string.transfer_screen_back),
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBlueBg)
                )

                SectionTitle(stringResource(R.string.transfer_screen_amount))
                InputField(
                    value = amount,
                    onValueChange = { amount = it },
                    background = GrayText,
                    placeholder = "" // Could be made stringResource if needed
                )

                SectionTitle(stringResource(R.string.transfer_screen_to))
                InputField(
                    value = toAddress,
                    onValueChange = { toAddress = it },
                    background = GrayText,
                    placeholder = stringResource(R.string.transfer_screen_paste_wallet_address)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(R.string.transfer_screen_available), color = Color.White)
                    Text("2.003455441 BTC", color = Color.White)
                }

                SectionTitle(stringResource(R.string.transfer_screen_transactions))

                mockedTransactions.forEach { (type, amount) ->
                    TransactionItem(type = type, amount = amount, background = GrayText)
                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(16.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
                shape = RoundedCornerShape(50),
            ) {
                Text(stringResource(R.string.transfer_screen_confirm))
            }
        }
    }
}


@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}

@Composable
fun InputField(
    value: String, onValueChange: (String) -> Unit, background: Color, placeholder: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color(0xFFABABAB)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp),
        shape = RoundedCornerShape(12.dp)
    )
}

@Composable
fun TransactionItem(type: String, amount: String, background: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(background, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = if (type == "Received") android.R.drawable.arrow_down_float else android.R.drawable.arrow_up_float),
                contentDescription = type,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(amount, color = Color.White, fontWeight = FontWeight.Medium)
            Text(type, color = Color(0xFFABABAB), fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransferScreenPreview() {
    TransferScreen()
}