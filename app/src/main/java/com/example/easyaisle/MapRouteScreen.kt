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


