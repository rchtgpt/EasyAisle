package com.example.easyaisle

import OrdersViewModel
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: OrdersViewModel, navController: NavController) {
    val esriFreshListOfOrders by viewModel.esriFreshListOfOrders.collectAsState()
    val costcoListOfOrders by viewModel.costcoListOfOrders.collectAsState()
    val traderJoesListOfOrders by viewModel.traderJoesListOfOrders.collectAsState()

    // Define storeOrders dynamically, including the updated orders from ViewModel
    val storeOrders = listOf(
        StoreOrder(
            "EsriFresh",
            Color(0xFF4285F4),
            esriFreshListOfOrders
        ),
        StoreOrder(
            "Trader Joes",
            Color(0xFFE74C3C),
            costcoListOfOrders
        ),
        StoreOrder(
            "Costco",
            Color(0xFFF39C12),
            traderJoesListOfOrders
        )
    )

    Scaffold(
        topBar = {
            Image(
                painter = painterResource(id = R.drawable.orders_header),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
        },
        bottomBar = {
            CustomBottomNavBar(
                onHomeClick = { /* Handle Home click */ },
                onHelpClick = { /* Handle Search click */ },
                onGoClick =  { navController.navigate("listScreen") },
                onSettingsClick = { /* Handle Profile click */ },
                onProfileClick = { /* Handle Center Button click */ }
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
                    bottom = innerPadding.calculateBottomPadding() + 10.dp
                )
        ) {
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
        Column {
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
                        painter = painterResource(id = storeOrder.storeLogo),
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
                    storeOrder.orders.forEachIndexed { _, order ->
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
                    .height(45.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = order.time,
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                    )
                    Image(
                        painter = painterResource(id = order.iconResId),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp)
                    )
                }
            }

            // Gray card displaying other details
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
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
                            text = "${order.name}",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${order.address}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${order.item_count} Items in Cart",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.DarkGray
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
    val orders: List<Order>,
    var storeLogo: Int = 0 // Default to 0 or any placeholder resource ID
) {
    init {
        setStoreLogo()
    }

    private fun setStoreLogo() {
        storeLogo = when (storeName) {
            "Trader Joes" -> R.drawable.traderjoes
            "Costco" -> R.drawable.costco // Replace with actual resource ID
            "EsriFresh" -> R.drawable.esrifresh // Replace with actual resource ID
            else -> R.drawable.traderjoes // Default logo if none match
        }
    }
}

data class Order(
    val name: String,
    val address: String,
    val platform: String,
    val time: String,
    val food_ordered: Map<String, Int>?,
    var item_count: Int = 0,
    var iconResId: Int = 0
) {
    init {
        item_count = food_ordered?.size ?: 0
    }

    fun setIconResId() {
        iconResId = when (platform) {
            "Instacart" -> R.drawable.instacart_icon
            "DoorDash" -> R.drawable.doordash_icon
            "Uber Eats" -> R.drawable.ubereats_icon
            else -> R.drawable.instacart_icon // Default icon to avoid ResourceNotFoundException
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderItemPreview() {
    OrderItem(order = Order(
        name = "Joe Albes",
        address = "34 Park Street (6.2 Miles Away)",
        platform = "Uber Eats",
        time = "3:15 PM",
        food_ordered = mapOf(
            "Apples" to 6,
            "Bananas" to 12
        )
    ).apply { setIconResId() })

}
