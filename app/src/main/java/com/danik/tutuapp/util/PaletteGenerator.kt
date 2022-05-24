package com.danik.tutuapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult


object PaletteGenerator {

    suspend fun convertStringToBitmap(
        imageUrl: String,
        context: Context
    ): Bitmap? {
        val loader = ImageLoader(context = context)
        val request = ImageRequest.Builder(context = context)
            .data(imageUrl)
            .allowHardware(false)
            .build()
        val imageResult = loader.execute(request = request)

        return if (imageResult is SuccessResult) {
            (imageResult.drawable as BitmapDrawable).bitmap
        } else {
            null
        }
    }

    fun extractColorFromBitmap(
        bitmap: Bitmap
    ): Map<String, String> {
        return mapOf(
            "darkVibrant" to parsePaletteSwatch(
                paletteColor = Palette.from(bitmap).generate().darkVibrantSwatch
            ),
            "vibrant" to parsePaletteSwatch(
                paletteColor = Palette.from(bitmap).generate().vibrantSwatch
            ),
            "onDarkVibrant" to parseStringToColor(
                paletteColor = Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor
            ),

            )
    }

    private fun parsePaletteSwatch(
        paletteColor: Palette.Swatch?
    ): String {
        return if (paletteColor != null) {
            val parsedPalette = Integer.toHexString(paletteColor.rgb)
            "#$parsedPalette"
        } else {
            "#000000"
        }
    }

    private fun parseStringToColor(
        paletteColor: Int?
    ): String {
        return if (paletteColor != null) {
            val parsedPalette = Integer.toHexString(paletteColor)
            "#$parsedPalette"
        } else {
            "#FFFFFF"
        }
    }
}