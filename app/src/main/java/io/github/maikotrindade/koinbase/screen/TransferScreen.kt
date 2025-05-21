package io.github.maikotrindade.koinbase.screen

import android.R.attr.onClick
import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import io.github.maikotrindade.koinbase.ui.theme.SecondaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferScreen(onBack: () -> Unit, onConfirm: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var toAddress by remember { mutableStateOf("") }
    var showConfirmation by remember { mutableStateOf(false) }

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
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.systemBars.asPaddingValues()),
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
                    }, navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(R.string.transfer_screen_back),
                                tint = Color.White
                            )
                        }
                    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBlueBg)
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
            ConfirmFooter(onConfirmTransfer = { showConfirmation = true })

            if (showConfirmation) {
                ConfirmationBottomSheet(
                    amount = amount,
                    toAddress = toAddress,
                    onDismiss = { showConfirmation = false },
                    onConfirm = {
                        showConfirmation = false
                        onConfirm()
                    })
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

@Composable
fun BoxScope.ConfirmFooter(onConfirmTransfer: () -> Unit) {
    Button(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .height(80.dp)
            .padding(16.dp),
        onClick = onConfirmTransfer,
        colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
        shape = RoundedCornerShape(50),
    ) {
        Text(stringResource(R.string.transfer_screen_confirm))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationBottomSheet(
    amount: String, toAddress: String, onDismiss: () -> Unit, onConfirm: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = DarkBlueBg,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.transfer_screen_confirm_title),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.transfer_screen_sending),
                color = Color.White,
                fontSize = 17.sp,
            )
            Text(
                text = amount,
                color = Color.White,
                fontSize = 17.sp,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.transfer_screen_to_address),
                color = Color.White,
                fontSize = 17.sp,
            )
            Text(
                text = toAddress,
                color = Color.White,
                fontSize = 17.sp,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.transfer_screen_confirm_button))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = SecondaryBlue),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.transfer_screen_cancel), color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransferScreenPreview() {
    TransferScreen(onBack = {}, onConfirm = {})
}