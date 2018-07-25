package br.com.rads.gistnator.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import br.com.rads.gistnator.R

class LanguageCustomView : TextView {

    private var _language: String? = null
    private val langMap = mapOf(
            "C" to Pair(R.color.c, R.drawable.ic_c_plain),
            "C++" to Pair(R.color.cpluplus, R.drawable.ic_cplusplus_plain),
            "CSS" to Pair(R.color.css, R.drawable.ic_css3_plain),
            "HTML" to Pair(R.color.html, R.drawable.ic_html5_plain),
            "Java" to Pair(R.color.java, R.drawable.ic_java_plain),
            "JavaScript" to Pair(R.color.javascript, R.drawable.ic_javascript_plain),
            "Python" to Pair(R.color.python, R.drawable.ic_python_plain),
            "Ruby" to Pair(R.color.ruby, R.drawable.ic_ruby_plain),
            "Shell" to Pair(R.color.shell, R.drawable.ic_linux_plain)
    )

    var language: String?
        get() = _language
        set(value) {
            _language = value
            updateViewForLang()
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }


    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.LanguageCustomView, defStyle, 0)

        _language = a.getString(R.styleable.LanguageCustomView_language)
        updateViewForLang()

        a.recycle()

    }

    private fun updateViewForLang() {
        if (langMap.containsKey(_language)) {
            val pair = langMap[_language]!!
            setTextColor(ContextCompat.getColor(context, pair.first))
            setCompoundDrawablesWithIntrinsicBounds(0, 0, pair.second, 0)
        }
    }
}
