package com.example.easyaisle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easyaisle.CustomBottomNavBar
import com.example.easyaisle.ImageButtonWithShadow
import com.example.easyaisle.R

@Composable
fun OrderCompleteScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFFFA726), Color(0xFFFF9800))))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Route Complete!",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Complete Transaction using your Delivery App",
                color = Color.White,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                ImageButtonWithShadow(
                    modifier = Modifier.size(100.dp, 40.dp),
                    image = painterResource(id = R.drawable.uber_logo),
                    backgroundColor = Color(0xFF142328)
                )
                ImageButtonWithShadow(
                    modifier = Modifier.size(100.dp, 40.dp),
                    image = painterResource(id = R.drawable.instacart_logo),
                    backgroundColor = Color(0xFFFAF1E5)
                )
                ImageButtonWithShadow(
                    modifier = Modifier.size(100.dp, 40.dp),
                    image = painterResource(id = R.drawable.doordash_logo),
                    backgroundColor = Color(0xFFD32F2F)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("homeScreen") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "NEW ORDER",
                    color = Color(0xFFFF9800),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}