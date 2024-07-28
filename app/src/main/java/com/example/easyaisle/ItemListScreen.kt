package com.example.easyaisle

import OrdersViewModel
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import coil.compose.AsyncImage
import androidx.compose.material.icons.filled.Check
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.scale

// LIST PAGE START -------------------------------
data class Person(val name: String, val items: List<String>, val orderStore: String)
val dummyData = listOf(
    Person(
        name = "Amy Adams",
        items = listOf("KELLOGG Corn Flakes - 1", "Eggs 18 Count - 1", "WonderBread - 3", "Apples - 6", "Frozen Chicken - 4 Pack - 2", "Diet Coke - 12 Pack - 2", "Huggies size 2 - 24 Pack - 2", "Starbucks Breakfast Blend Grounds - Medium Roast - 2"),
        orderStore = "Uber Eats"
    ),
    Person(
        name = "Paulette Mirez",
        items = listOf("Apples - 8", "KELLOGGS Corn Flakes - 1", "Frozen Chicken - 4 Pack - 1", "WonderBread - 1", "Jif Crunchy Peanut Butter - 1", "Starbucks Breakfast Blend Grounds - Medium Roast - 1", "Eggs - 18 Count - 1", "Diet Coke - 12 Pack - 1"),
        orderStore = "Instacart"
    ),
    Person(
        name = "Matt Argos",
        items = listOf("Eggs - 18 Count - 1", "WonderBread - 1", "Starbucks Breakfast Blend Grounds - Medium Roast - 1", "Apples - 8", "KELLOGGS Corn Flakes - 1"),
        orderStore = "DoorDash"
    ),
)

@Composable
fun ImageFromUrl(url: String) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .size(245.dp, 435.dp)
            .padding(8.dp)
    )
}

@Composable
fun ItemList(navController: NavController, persons: List<Person> = dummyData) {
    val selectedUsersFromAGroceryStore: Array<String> = arrayOf("Matt Argos", "Paulette Mirez")
    val viewModel: OrdersViewModel = viewModel(LocalContext.current as ComponentActivity)
    val selectedCustomerNames by viewModel.selectedCustomerNames.collectAsState()
    val esriFreshListOfOrders by viewModel.esriFreshListOfOrders.collectAsState()


    fun filterOrdersByName(names: List<String> = selectedCustomerNames, orders: List<Order> = esriFreshListOfOrders): List<Order> {
        return orders.filter { it.name in names }
    }

    val filteredOrders = filterOrdersByName()
    val totalItems = filteredOrders.sumOf { order -> order.food_ordered?.values?.sum() ?: 0 }

    // Calculate the total number of unique food items
        val uniqueItems = mutableSetOf<String>()
        filteredOrders.forEach { order ->
            order.food_ordered?.keys?.let { uniqueItems.addAll(it) }
        }
        val totalUniqueItems = uniqueItems.size


    Scaffold(
        topBar = {
            Image(
                painter = painterResource(id = R.drawable.list_header),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        },
        bottomBar = {
            val context = LocalContext.current
            val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://devrt00020.esri.com/portal/apps/indoors/index.html?appid=368ca23be82d45348caf1bfc628fae4c")) }

            CustomBottomNavBar(
                onHomeClick = { navController.navigate("homeScreen") },
                onHelpClick = { /* Handle Help click */ },
                onGoClick = {
                    Log.d("jai mata di", "i'm inside")
                    context.startActivity(intent)

                },
                onSettingsClick = { /* Handle Settings click */ },
                onProfileClick = { /* Handle Profile click */ },
                isGoEnabled = selectedCustomerNames.isNotEmpty()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Padding to avoid content overlap with top/bottom bars
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp) // Padding on both sides
                    .clip(RoundedCornerShape(8.dp)) // Rounded edges
                    .background(Color.Black)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${totalItems} Items • ${totalUniqueItems} Stops",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold // Optional: for emphasis
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1B5E20))
                        .clickable {
                            navController.navigate("homeScreen")
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Check Icon",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            LazyColumn {
                items(filteredOrders) { order ->
                    Card(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFECECEC))
                    ) {
                        Column {
                            // Header with logo and name
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black)
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                // Name
                                Text(
                                    text = order.name,
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                                // Logo
                                when (order.platform) {
                                    "Uber Eats" -> Image(
                                        painter = painterResource(id = R.drawable.uber_logo),
                                        contentDescription = "Uber Eats Logo",
                                        modifier = Modifier.size(24.dp)
                                    )
//                                    "Uber Eats" -> ImageFromUrl(url = "https://media.discordapp.net/attachments/766898740627767297/1266858124841582723/jif.png?ex=66a6ad12&is=66a55b92&hm=091651d688ab5c51dc091277134826b55e2e404c60be9104a36122351e4ceb1e&=&format=webp&quality=lossless&width=245&height=435")
                                    "Instacart" -> Image(
                                        painter = painterResource(id = R.drawable.instacart_logo),
                                        contentDescription = "Instacart Logo",
                                        modifier = Modifier.size(24.dp)
                                    )
                                    "DoorDash" -> Image(
                                        painter = painterResource(id = R.drawable.doordash_icon),
                                        contentDescription = "DoorDash Logo",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                        Column {
                            // Content
                            Column(modifier = Modifier.padding(12.dp)) {
                                Spacer(modifier = Modifier.height(0.dp))
                                order.food_ordered?.forEach { (name, quantity) ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 3.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        // Bulleted list
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "• $name",
                                                fontSize = 14.sp,
                                                color = Color.DarkGray,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(end = 40.dp),
                                                overflow = TextOverflow.Ellipsis,
                                                maxLines = Int.MAX_VALUE
                                            )
                                        }
                                        // Quantity
                                        Text(
                                            text = "$quantity",
                                            fontSize = 14.sp,
                                            color = Color.DarkGray,
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .widthIn(min = 0.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
