package com.example.wifood.presentation.view.map.component

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.wifood.presentation.view.component.SingleRatingStar
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun CustomMarker(
    context: Context,
    position: LatLng,
    title: String,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptorFromVector(
        context, iconResourceId
    )
    Marker(
        rememberMarkerState(position = position),
        title = title,
        icon = icon
    )
}

private fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

@Composable
fun MarkerCustomInfoWindow(
    name: String,
    review: String,
    score: Int
) {
    Column(
        modifier = Modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(10.dp),
                ambientColor = Color(0x0D000000)
            )
            .padding(
                horizontal = 18.dp,
                vertical = 18.dp
            )
    ) {
        Row {
            for (i in 1..5) {
                SingleRatingStar(
                    isClicked = i <= score.toInt(),
                    starSize = 20
                )
                Spacer(Modifier.width(2.dp))
            }
        }
        Text(text = name)
        Text(text = review)
    }
}