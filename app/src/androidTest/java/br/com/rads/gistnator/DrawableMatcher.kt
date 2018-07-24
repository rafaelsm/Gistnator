package br.com.rads.gistnator

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher


object DrawableMatcher {

    fun withTextViewDrawable(drawableId: Int): Matcher<View> {
        return object : BoundedMatcher<View, TextView>(TextView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("has image drawable resource $drawableId")
            }

            override fun matchesSafely(textView: TextView): Boolean {
                return sameBitmap(textView.compoundDrawables.first(), ContextCompat.getDrawable(textView.context, drawableId))
            }
        }
    }

    fun withDrawable(drawable: Drawable): Matcher<View> {
        return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("has image drawable resource $drawable")
            }

            override fun matchesSafely(imageView: ImageView): Boolean {
                return sameBitmap(imageView.drawable, drawable)
            }
        }
    }

    fun withDrawable(drawableId: Int): Matcher<View> {
        return object : BoundedMatcher<View, ImageView>(ImageView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("has image drawable resource $drawableId")
            }

            override fun matchesSafely(imageView: ImageView): Boolean {
                return sameBitmap(imageView.drawable, ContextCompat.getDrawable(imageView.context, drawableId))
            }
        }
    }

    private fun sameBitmap(drawable: Drawable?, otherDrawable: Drawable?): Boolean {
        var drawable = drawable
        var otherDrawable = otherDrawable

        if (drawable == null || otherDrawable == null) {
            return false
        }

        if (drawable is StateListDrawable && otherDrawable is StateListDrawable) {
            drawable = drawable.current
            otherDrawable = otherDrawable.current
        }

        if (drawable is VectorDrawable && otherDrawable is VectorDrawable) {
            drawable = drawable.current
            otherDrawable = otherDrawable.current

            return bitmapFromDrawable(drawable!!).sameAs(bitmapFromDrawable(otherDrawable!!))

        }

        if (drawable is BitmapDrawable && otherDrawable is BitmapDrawable) {
            val bitmap = drawable.bitmap
            val otherBitmap = otherDrawable.bitmap
            return bitmap.sameAs(otherBitmap)
        } else if (otherDrawable is VectorDrawable) {
            val bitmap = convertFromVectorDrawable(drawable!!)
            val otherBitmap = convertFromVectorDrawable(otherDrawable)
            return bitmap.sameAs(otherBitmap)
        }

        return false
    }

    private fun convertFromVectorDrawable(drawable: Drawable): Bitmap {
        var drawable = drawable
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable).mutate()
        }
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun bitmapFromDrawable(drawable: Drawable): Bitmap {
        val bitmap: Bitmap
        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

}
