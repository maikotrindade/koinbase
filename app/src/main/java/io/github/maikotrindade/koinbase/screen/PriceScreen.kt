package io.github.maikotrindade.koinbase.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
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

@Composable
fun PriceScreen() {
    Scaffold(
        containerColor = DarkBlueBg, bottomBar = {
            BottomNavigationBar(GrayText)
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(DarkBlueBg)
        ) {
            TopBar()
            PriceSection()
            Spacer(modifier = Modifier.height(16.dp))
            ChartSection(GrayText)
            Spacer(modifier = Modifier.height(16.dp))
            ActionButtons(AccentBlue, SecondaryBlue)
            Spacer(modifier = Modifier.height(8.dp))
            TransferButton(SecondaryBlue)
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.price_screen_title),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(24.dp))
    }
}

@Composable
fun PriceSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = stringResource(R.string.price_screen_price_value),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )
        Text(
            text = stringResource(R.string.price_screen_price_change),
            color = Color(0xFF8d9bce),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.price_screen_currency_pair),
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Text(
            text = stringResource(R.string.price_screen_price_value),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("1D", color = GrayText, fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                "+0.42%",
                color = Color(0xFF0bda62),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ChartSection(grayText: Color) {
    Column(modifier = Modifier.padding(16.dp)) {
        val mockChartData = listOf(30f, 60f, 40f, 70f, 20f, 80f, 50f)
        LineChart(
            data = mockChartData,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(horizontal = 16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("Jul 1", "Jul 2", "Jul 3", "Jul 4", "Jul 5", "Jul 6", "Jul 7").forEach {
                Text(it, fontSize = 12.sp, color = grayText, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun LineChart(
    data: List<Float>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color(0xFFABB4FF),
    backgroundColor: Color = Color(0xFF0E1324)
) {
    Canvas(
        modifier = modifier.background(backgroundColor)
    ) {
        if (data.isEmpty()) return@Canvas

        val spacing = size.width / (data.size - 1)
        val maxValue = data.maxOrNull() ?: 0f
        val minValue = data.minOrNull() ?: 0f
        val chartHeight = size.height

        val points = data.mapIndexed { index, value ->
            val x = index * spacing
            val y = chartHeight - (value - minValue) / (maxValue - minValue) * chartHeight
            Offset(x, y)
        }

        val path = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (i in 1 until points.size) {
                val prev = points[i - 1]
                val current = points[i]
                val midX = (prev.x + current.x) / 2
                val midY = (prev.y + current.y) / 2
                quadraticBezierTo(prev.x, prev.y, midX, midY)
            }
            lineTo(points.last().x, points.last().y)
        }

        drawPath(
            path = path, color = lineColor, style = Stroke(
                width = 4.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round
            )
        )
    }
}

@Composable
fun ActionButtons(buyColor: Color, sellColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = buyColor),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
        ) {
            Text(stringResource(R.string.price_screen_buy_button))
        }
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = sellColor),
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .weight(1f)
                .height(40.dp)
        ) {
            Text(stringResource(R.string.price_screen_sell_button))
        }
    }
}

@Composable
fun TransferButton(bgColor: Color) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(containerColor = bgColor),
        shape = RoundedCornerShape(50),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Text(stringResource(R.string.price_screen_transfer_button))
    }
}

@Composable
fun BottomNavigationBar(grayText: Color) {
    NavigationBar(containerColor = Color(0xFF171e36)) {
        NavigationBarItem(icon = {
            Icon(
                Icons.Default.Home, contentDescription = null, tint = grayText
            )
        }, label = {
            Text(
                stringResource(R.string.price_screen_tab_home),
                color = grayText,
                fontSize = 12.sp
            )
        }, selected = false, onClick = {})
        NavigationBarItem(icon = {
            Icon(
                Icons.Default.CurrencyExchange, contentDescription = null, tint = grayText
            )
        }, label = {
            Text(
                stringResource(R.string.price_screen_tab_trade),
                color = grayText,
                fontSize = 12.sp
            )
        }, selected = false, onClick = {})
        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Default.Wallet,
                    contentDescription = null,
                    tint = grayText
                )
            },
            label = {
                Text(
                    stringResource(R.string.price_screen_tab_wallet),
                    color = grayText,
                    fontSize = 12.sp
                )
            },
            selected = false,
            onClick = {})
        NavigationBarItem(
            icon = { Icon(Icons.Default.AccountBox, contentDescription = null, tint = grayText) },
            label = {
                Text(
                    stringResource(R.string.price_screen_tab_about),
                    color = grayText,
                    fontSize = 12.sp
                )
            },
            selected = false,
            onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PriceScreenPreview() {
    PriceScreen()
}