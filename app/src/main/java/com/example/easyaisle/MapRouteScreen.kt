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
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.Viewpoint
import com.arcgismaps.portal.Portal
import com.arcgismaps.toolkit.geoviewcompose.MapView
import androidx.compose.runtime.remember
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.mapping.PortalItem

private fun setApiKey() {
    ArcGISEnvironment.apiKey = ApiKey.create("AAPTxy8BH1VEsoebNVZXo8HurARQG1ySTwuxT06quT27YX0dgRgL2iTC2RGhcTYanKpfuZhOI75rOh8AoqmEHbElaG53UKO9OPuDPj64JvJvXpO3kmzP-NQjXuG2VrRKdmSvzU7cXc0nF4NtqXLkxu9gqyAai3khLwDmVSHatvc1YXeCz04nVeYGCQu2lcT2GJ3XvkaYzKfZPPeS2vbEIqIfEP4nrDCTxdT-EWc8prmqsvw.AT1_r6BYalJ9")
}

fun createMap(): ArcGISMap {
    setApiKey()

    val portal = Portal(
        url = "https://www.arcgis.com",
        connection = Portal.Connection.Anonymous
    )

    val portalItem = PortalItem(
        portal = portal,
        itemId = "17169924253f44fca1870c65f3327066"
    )

    return ArcGISMap(portalItem)

}

@Composable
fun MapRouteScreen(navController: NavController) {
    val map = remember { createMap() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        // Grey box at the top -- Rachit --------
        MapView(
            modifier = Modifier.fillMaxWidth().height(500.dp).padding(10.dp),
            arcGISMap = map
        )
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
                    DirectionItem(direction)
                }
            }
        }
    }
}

@Composable
fun DirectionItem(direction: String) {
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
        Text(
            text = direction,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun getDirectionIcon(direction: String): ImageVector {
    return when {
        direction.startsWith("Go forward") -> Icons.Default.ArrowForward
        direction.startsWith("Turn right") -> Icons.Default.ArrowForward
        direction.startsWith("Turn left") -> Icons.Default.ArrowBack
        direction.startsWith("Arrive") -> Icons.Default.LocationOn
        direction.startsWith("Depart") -> Icons.Default.ExitToApp
        direction.startsWith("Start") -> Icons.Default.PlayArrow
        direction.startsWith("Finish") -> Icons.Default.CheckCircle
        else -> Icons.Default.ArrowForward
    }
}

val directions = listOf(
    "Start",
    "Go forward 73 ft ~ < 1 min",
    "Turn right 11 ft ~ < 1 min",
    "Arrive, on the left",
    "Depart",
    "Go forward 53 ft ~ < 1 min",
    "Turn right 23 ft ~ < 1 min",
    "Arrive, on the right",
    "Depart",
    "Go forward 46 ft ~ < 1 min",
    "Arrive, on the right",
    "Depart",
    "Go forward < 1 ft ~ < 1 min",
    "Turn right near bathroom 87 ft ~ < 1 min",
    "Turn left < 1 ft ~ < 1 min",
    "Arrive, on the right",
    "Depart",
    "Go forward 29 ft ~ < 1 min",
    "Arrive, on the left",
    "Depart",
    "Go forward 28 ft ~ < 1 min",
    "Turn left 28 ft ~ < 1 min",
    "Arrive, on the right",
    "Depart",
    "Go forward 6 ft ~ < 1 min",
    "Turn right 35 ft ~ < 1 min",
    "Arrive, on the right",
    "Depart",
    "Go forward 25 ft ~ < 1 min",
    "Finish, on the left"
)

// OLD CODE

//Box(
//modifier = Modifier.fillMaxSize()
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(start = 48.dp, top = 12.dp, end = 48.dp, bottom = 12.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        IconButton(onClick = { /* Handle left button click */ }) {
//            Icon(
//                painter = painterResource(id = R.drawable.big_arrow_left),
//                contentDescription = "Previous"
//            )
//        }
//
//        Box(
//            modifier = Modifier
//                .size(200.dp)
//                .background(
//                    color = Color.White,
//                    shape = RoundedCornerShape(16.dp)
//                )
//                .padding(16.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(top = 8.dp)
//            ) {
//                Text(
//                    text = "Corn Flakes",
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    modifier = Modifier.align(Alignment.CenterHorizontally)
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Image(
//                    painter = painterResource(id = R.drawable.uber_logo),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(125.dp)
//                        .align(Alignment.CenterHorizontally),
//                    contentScale = ContentScale.Crop
//                )
//            }
//        }
//
//        IconButton(onClick = { /* Handle right button click */ }) {
//            Icon(
//                painter = painterResource(id = R.drawable.big_arrow_right),
//                contentDescription = "Next"
//            )
//        }
//    }
//}

// Black bar at the bottom
//Text(
//    text = "Amy (x1)  Paulette (x1)  Matt (x1)",
//    fontSize = 20.sp,
//    textAlign = TextAlign.Center,
//    modifier = Modifier
//    .fillMaxWidth()
//    .background(Color.Black)
//    .padding(8.dp),
//    color = Color.White
//)

//@Composable
//fun IconButton(onClick: () -> Unit, content: @Composable () -> Unit) {
//    Box(
//        modifier = Modifier
//            .size(36.dp)
//            .padding(0.dp)
//            .clickable(onClick = onClick),
//        contentAlignment = Alignment.Center
//    ) {
//        content()
//    }
//}