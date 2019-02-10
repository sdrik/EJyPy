package fr.gabriello.ejypy

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class SelectionDecorator(context: Activity) : DayViewDecorator {

    private val mDrawable: Drawable = ContextCompat.getDrawable(context, R.drawable.selector_selected_range)!!

    override fun shouldDecorate(day: CalendarDay?) = when (day) {
        null -> false
        else -> true
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setSelectionDrawable(mDrawable)
    }
}