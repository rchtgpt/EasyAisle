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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun MapRouteScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        // Grey box at the top -- Rachit --------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
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
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 48.dp, top = 12.dp, end = 48.dp, bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { /* Handle left button click */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.big_arrow_left),
                            contentDescription = "Previous"
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 8.dp)
                        ) {
                            Text(
                                text = "Corn Flakes",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Image(
                                painter = painterResource(id = R.drawable.uber_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(125.dp)
                                    .align(Alignment.CenterHorizontally),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    IconButton(onClick = { /* Handle right button click */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.big_arrow_right),
                            contentDescription = "Next"
                        )
                    }
                }
            }
        }

        // Black bar at the bottom
        Text(
            text = "Amy (x1)  Paulette (x1)  Matt (x1)",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(8.dp),
            color = Color.White
        )
    }
}

@Composable
fun IconButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .padding(0.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}


