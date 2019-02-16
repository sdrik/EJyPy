package fr.gabriello.ejypy

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewFacade
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SelectionDecoratorTest {

    private val context: Context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun selectionDecorator_AlwaysTrue() {
        val decorator = SelectionDecorator(context)
        val day = mock(CalendarDay::class.java)
        assertThat(decorator.shouldDecorate(day)).isTrue()
    }

    @Test
    fun selectionDecorator_FalseIfNull() {
        val decorator = SelectionDecorator(context)
        assertThat(decorator.shouldDecorate(null)).isFalse()
    }

    @Test
    fun selectionDecorator_DecorateWithProperDrawable() {
        val decorator = SelectionDecorator(context)
        val view = mock(DayViewFacade::class.java)
        decorator.decorate(view)
        verify(view).setSelectionDrawable(any(Drawable::class.java))
    }
}