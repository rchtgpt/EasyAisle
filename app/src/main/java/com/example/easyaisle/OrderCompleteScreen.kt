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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
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
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Text(
                text = "All Items Collected!",
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = buildAnnotatedString {
                    append("You have completed your order ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Yellow)) {
                        append("in the most efficient way possible")
                    }
                    append(". Let your customers know on the following apps!")
                },
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                ImageButtonWithShadow(
                    modifier = Modifier.size(80.dp, 40.dp),
                    image = painterResource(id = R.drawable.uber_logo),
                    backgroundColor = Color(0xFF142328)
                )
                ImageButtonWithShadow(
                    modifier = Modifier.size(80.dp, 40.dp),
                    image = painterResource(id = R.drawable.instacart_logo),
                    backgroundColor = Color(0xFFFAF1E5)
                )
                ImageButtonWithShadow(
                    modifier = Modifier.size(80.dp, 40.dp),
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
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}