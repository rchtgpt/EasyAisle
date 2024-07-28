package com.example.easyaisle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MapRouteScreen(navController: NavController) {

    var departClickCount by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        // Grey box at the top -- Rachit --------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .background(Color.Gray)
        ) {

        }
        // Grey box end -----------------

        // Column for yellow card with no padding
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = Color(0xFFFFA500),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(directions) { direction ->
                    DirectionItem(
                        direction = direction,
                        onStartClick = { /* Handle Start click */ },
                        onDepartClick = {
                            when (departClickCount) {
                                0 -> { /* Handle Depart 1 click */ }
                                1 -> { /* Handle Depart 2 click */ }
                                2 -> { /* Handle Depart 3 click */ }
                                3 -> { /* Handle Depart 4 click */ }
                                4 -> { /* Handle Depart 5 click */ }
                                5 -> { /* Handle Depart 6 click */ }
                                6 -> { /* Handle Depart 7 click */ }
                            }
                            departClickCount++
                        },
                        onFinishClick = { /* Handle Finish click */ }
                    )
                }
            }
        }
    }
}

@Composable
fun DirectionItem(
    direction: String,
    onStartClick: () -> Unit,
    onDepartClick: () -> Unit,
    onFinishClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = getDirectionIcon(direction),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(16.dp))
        when {
            direction == "START" -> {
                Button(
                    onClick = onStartClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        text = direction,
                        color = Color(0xFFFFA500),
                        modifier = Modifier
                    )
                }
            }
            direction == "DEPART" -> {
                Button(
                    onClick = onDepartClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        text = "NEXT",
                        color = Color(0xFFFFA500),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            direction == "FINISH" -> {
                Button(
                    onClick = onFinishClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        text = "FINISH",
                        color = Color(0xFFFFA500),
                        modifier = Modifier
                    )
                }
            }
            else -> {
                Text(
                    text = direction,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun getDirectionIcon(direction: String): ImageVector {
    return when {
        direction.startsWith("Go forward") -> Icons.Default.ArrowForward
        direction.startsWith("Turn right") -> Icons.Default.ArrowForward
        direction.startsWith("Turn left") -> Icons.Default.ArrowBack
        direction.startsWith("Arrive") -> Icons.Default.LocationOn
        direction.startsWith("DEPART") -> Icons.Default.ExitToApp
        direction.startsWith("START") -> Icons.Default.PlayArrow
        direction.startsWith("FINISH") -> Icons.Default.CheckCircle
        else -> Icons.Default.ArrowForward
    }
}

val directions = listOf(
    "START",
    "Go forward 73 ft ~ < 1 min",
    "Turn right 11 ft ~ < 1 min",
    "Arrive, on the left",
    "DEPART",
    "Go forward 53 ft ~ < 1 min",
    "Turn right 23 ft ~ < 1 min",
    "Arrive, on the right",
    "DEPART",
    "Go forward 46 ft ~ < 1 min",
    "Arrive, on the right",
    "DEPART",
    "Go forward < 1 ft ~ < 1 min",
    "Turn right near bathroom 87 ft ~ < 1 min",
    "Turn left < 1 ft ~ < 1 min",
    "Arrive, on the right",
    "DEPART",
    "Go forward 29 ft ~ < 1 min",
    "Arrive, on the left",
    "DEPART",
    "Go forward 28 ft ~ < 1 min",
    "Turn left 28 ft ~ < 1 min",
    "Arrive, on the right",
    "DEPART",
    "Go forward 6 ft ~ < 1 min",
    "Turn right 35 ft ~ < 1 min",
    "Arrive, on the right",
    "DEPART",
    "Go forward 25 ft ~ < 1 min",
    "FINISH"
)