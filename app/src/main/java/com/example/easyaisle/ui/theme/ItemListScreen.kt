package com.example.easyaisle.ui.theme

import OrdersViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easyaisle.CustomBottomNavBar
import com.example.easyaisle.Order
import com.example.easyaisle.R

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
fun ItemList(navController: NavController, viewModel: OrdersViewModel, persons: List<Person> = dummyData) {
    val selectedUsersFromAGroceryStore: Array<String> = arrayOf("Matt Argos", "Paulette Mirez")
    val esriFreshListOfOrders by viewModel.esriFreshListOfOrders.collectAsState()


    fun filterOrdersByName(names: Array<String> = selectedUsersFromAGroceryStore, orders: List<Order> = esriFreshListOfOrders): List<Order> {
        return orders.filter { it.name in names }
    }

    val filteredOrders = filterOrdersByName()

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
            CustomBottomNavBar(
                onHomeClick = { navController.navigate("homeScreen") },
                onHelpClick = { /* Handle Search click */ },
                onGoClick = { /* Handle Center Button click */ },
                onSettingsClick = { /* Handle Settings click */ },
                onProfileClick = { /* Handle Profile click */ }
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
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center, // Center align text horizontally
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "46 Items • 9 Stops",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold // Optional: for emphasis
                )
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
