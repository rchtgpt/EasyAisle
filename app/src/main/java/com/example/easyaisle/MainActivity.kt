package com.example.easyaisle

import OrdersViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.easyaisle.ui.theme.EasyAisleTheme
import kotlinx.coroutines.delay
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.FirebaseDatabase

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
                    delay(3000)
                    showSplash = false
                }
            } else {
                // Your main app content
                EasyAisleTheme {
                    FirebaseDatabase.getInstance().setPersistenceEnabled(true)
                    MyApp()
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
        composable("homeScreen") { HomeScreen(navController) }
        composable("listScreen") { ItemList(navController) }
        composable("mapScreen") { MapRouteScreen(navController) }
    }
}

@Composable
fun bottomBarShape(cutoutRadius: Float, cutoutHeightPx: Float): Shape {
    return GenericShape { size: Size, _ ->
        val width = size.width
        val height = size.height
        val centerX = width / 2

        moveTo(0f, 0f)
        lineTo(centerX - cutoutRadius - 20f, 0f)
        cubicTo(
            centerX - cutoutRadius, 0f,
            centerX - cutoutRadius, cutoutHeightPx,
            centerX, cutoutHeightPx
        )
        cubicTo(
            centerX + cutoutRadius, cutoutHeightPx,
            centerX + cutoutRadius, 0f,
            centerX + cutoutRadius + 20f, 0f
        )
        lineTo(width, 0f)
        lineTo(width, height)
        lineTo(0f, height)
        close()
    }
}

@Composable
fun CustomBottomNavBar(
    onHomeClick: () -> Unit,
    onHelpClick: () -> Unit,
    onGoClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit,
    isGoEnabled: Boolean
) {
    val cutoutRadius = with(LocalDensity.current) { 45.dp.toPx() }
    val cutoutHeightPx = with(LocalDensity.current) { 28.dp.toPx() }

    val shape = bottomBarShape(cutoutRadius, cutoutHeightPx)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        // Main bottom bar with cutout
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.Black,  // Adjust the color to match your design
                    shape = shape
                )
                .height(56.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    icon = painterResource(id = R.drawable.ic_home),
                    onClick = onHomeClick
                )
                IconButton(
                    icon = painterResource(id = R.drawable.ic_help),
                    onClick = onHelpClick
                )
                Spacer(modifier = Modifier.width(56.dp)) // Space for FAB
                IconButton(
                    icon = painterResource(id = R.drawable.ic_settings),
                    onClick = onSettingsClick
                )
                IconButton(
                    icon = painterResource(id = R.drawable.ic_profile),
                    onClick = onProfileClick
                )
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = {
                if (isGoEnabled) {
                    onGoClick()
                }
            },
            shape = CircleShape,
            containerColor = if (isGoEnabled) Color(0xFFFF9800) else Color.Gray,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = -30.dp)
                .size(70.dp)
                .shadow(elevation = 8.dp, shape = CircleShape)
        ) {
            Text(
                text = "GO!",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun IconButton(icon: Painter, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .padding(8.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .clickable(onClick = onClick),
            contentScale = ContentScale.Fit
        )
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
        modifier = modifier.shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp), clip = false),
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
