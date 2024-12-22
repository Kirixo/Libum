package core.reader

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.util.TypedValue

object TextUtils {

    fun pxToDp(context: Context, px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    fun getTextWidth(context: Context, text: String, textSizeSp: Float): Int {
        val paint = Paint()
        paint.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            textSizeSp,
            context.resources.displayMetrics
        )
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        return pxToDp(context, bounds.width().toFloat()).toInt()
    }

    fun getTextHeight(context: Context, text: String, textSizeSp: Float): Int {
        val paint = Paint()
        paint.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            textSizeSp,
            context.resources.displayMetrics
        )
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        return pxToDp(context, bounds.height().toFloat()).toInt()
    }

    fun getTextHeight(context: Context, textSizeSp: Float): Float {
        val paint = Paint()
        paint.textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            textSizeSp,
            context.resources.displayMetrics
        )
        return pxToDp(context, (paint.fontMetrics.descent - paint.fontMetrics.ascent))
    }
}
