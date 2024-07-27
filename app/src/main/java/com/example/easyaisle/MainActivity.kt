package com.example.easyaisle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.easyaisle.ui.theme.EasyAisleTheme
import kotlinx.coroutines.delay
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        actionBar?.hide()
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showSplash by remember { mutableStateOf(true) }

            if (showSplash) {
                SplashScreen()
                LaunchedEffect(Unit) {
                    delay(3000) // Show splash for 3 seconds
                    showSplash = false
                }
            } else {
                // Your main app content
                EasyAisleTheme {
                    MyApp()
//                    SignInScreen()
//                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFA500)) // Orange background
    ) {
        Image(
            painter = painterResource(id = R.drawable.spash),
            contentDescription = "Splash Screen",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "signInScreen") {
        composable("signInScreen") { SignInScreen(navController) }
        composable("homeScreen") { HomeScreen() }
    }
}

//Composable for link account buttons
@Composable
fun ImageButtonWithShadow(
    modifier: Modifier = Modifier,
    image: Painter,
    backgroundColor: Color,
) {
    Surface(
        modifier = modifier.shadow(elevation = 8 .dp, shape = RoundedCornerShape(16.dp), clip = false),
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center),
                contentScale = ContentScale.Fit // Maintain aspect ratio, fit inside bounds
            )
        }
    }
}

// Composable function for the sign-in screen

@Composable
fun SignInScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Image(
            painter = painterResource(id = R.drawable.sign_in_header),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // email and password input fields
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                colors = OutlinedTextFieldDefaults.colors(
                    // unfocusedBorderColor = Color(0xFFB4B4B4),
                    unfocusedLabelColor = Color(0xFFB4B4B4),
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                colors = OutlinedTextFieldDefaults.colors(
                    // unfocusedBorderColor = Color(0xFFB4B4B4),
                    unfocusedLabelColor = Color(0xFFB4B4B4),
                ),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "need help?",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.End,
                color = Color.Gray,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            // login and register buttons
            Button(
                onClick = { navController.navigate("homeScreen") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF7931E)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("LOG IN", color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { /* Handle register */ },
                border = BorderStroke(1.dp, Color(0xFFF7931E)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("REGISTER", color = Color(0xFFF7931E))
            }
            Spacer(modifier = Modifier.height(24.dp))

            // link account buttons
            Text("Link Your Accounts", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ImageButtonWithShadow(
                    modifier = Modifier.size(80.dp, 30.dp),
                    image = painterResource(id = R.drawable.uber_logo),
                    backgroundColor = Color(0xFF142328)
                )

                ImageButtonWithShadow(
                    modifier = Modifier.size(80.dp, 30.dp),
                    image = painterResource(id = R.drawable.instacart_logo),
                    backgroundColor = Color(0xFFFAF1E5)
                )

                ImageButtonWithShadow(
                    modifier = Modifier.size(80.dp, 30.dp),
                    image = painterResource(id = R.drawable.doordash_logo),
                    backgroundColor = Color(0xFFFF3008)
                )
            }
            Spacer(modifier = Modifier.height(36.dp))

            // create personal account text
            Row {
                Text(text = "Not a Delivery Driver? ", color = Color.Gray, fontSize = 12.sp)
                ClickableText(
                    text = AnnotatedString("Create a Personal Account"),
                    onClick = { /* Handle click */ },
                    style = LocalTextStyle.current.copy(color = Color(0xFFF7931E), fontSize = 12.sp)
                )
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CLAIMED ORDERS") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 20.dp,
                    top = innerPadding.calculateTopPadding() + 20.dp,
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 20.dp,
                    bottom = innerPadding.calculateBottomPadding() + 20.dp
                )        ) {
            items(storeOrders) { storeOrder ->
                StoreOrderCard(storeOrder)
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun StoreOrderCard(storeOrder: StoreOrder) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .border(
                BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
    ) {
        Column(
        ) {
            // First card for the store name
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .border(
                        BorderStroke(2.dp, Color.Black),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    ),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = storeOrder.storeColor
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.traderjoes),
                        contentDescription = "Trader Joe's Logo",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center) // Align the image to the center horizontally
                    )
                }
            }

            // Second card for the list of orders
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    storeOrder.orders.forEachIndexed { index, order ->
                        OrderItem(order)
                    }
                }
            }
        }
    }
}


@Composable
fun OrderItem(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp)), // Add rounded corners to the parent card
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Black card displaying time
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = order.time,
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                    )
                }
            }

            // Gray card displaying other details
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.gray_200)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${order.customerName}, ${order.customerAddress}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${order.itemCount} Items in Cart",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.DarkGray
                        )
                    }

                    if (order.iconResId != null) {
                        Image(
                            painter = painterResource(id = order.iconResId),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

data class StoreOrder(
    val storeName: String,
    val storeColor: Color,
    val orders: List<Order>
)

data class Order(
    val time: String,
    val customerName: String,
    val customerAddress: String,
    val itemCount: Int,
    val distance: String,
    val iconResId: Int? = null
)

// Sample data
val storeOrders = listOf(
    StoreOrder(
        "The Esri Grocery Store",
        Color(0xFF4285F4),
        listOf(
            Order("12:00 PM", "Amy Adams", "844 West Ave", 18, "3 Miles Away", R.drawable.instacart_icon),
            Order("12:15 PM", "Paulette Mires", "67 La Jolla", 12, "4 Miles Away", R.drawable.ubereats_icon),
            Order("1:00 PM", "Matt Argos", "312 Park Street", 7, "6.5 miles away", R.drawable.doordash_icon)
        )
    ),
    StoreOrder(
        "STATER BROS: COLTON AVE",
        Color(0xFFE74C3C),
        listOf(
            Order("3:15 PM", "Joe Albes", "34 Park Street", 11, "6.2 Miles Away", R.drawable.ubereats_icon),
            Order("3:20 PM", "Rachel Strife"," 899 Arlington", 23, "4 Miles Away", R.drawable.doordash_icon)
        )
    ),
    StoreOrder(
        "ALDI: ALABAMA STREET",
        Color(0xFFF39C12),
        listOf(
            Order("12:15 PM", "Someone", "Somewhere", 12, "4.2 Miles Away", R.drawable.instacart_icon)
        )
    )
)

@Preview
@Composable
fun SimpleComposablePreview() {
    StoreOrderCard(StoreOrder(
        "The Esri Grocery Store",
        Color(0xFF4285F4),
        listOf(
            Order("12:00 PM", "Amy Adams", "844 West Ave", 18, "3 Miles Away", R.drawable.instacart_icon),
            Order("12:15 PM", "Paulette Mires", "67 La Jolla", 12, "4 Miles Away", R.drawable.ubereats_icon),
            Order("1:00 PM", "Matt Argos", "312 Park Street", 7, "6.5 miles away", R.drawable.doordash_icon)
        )
    ))
}
