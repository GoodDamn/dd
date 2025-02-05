package good.damn.editor.dd.prompt

import android.provider.CalendarContract.Calendars
import java.util.Calendar

fun Long.toDate(): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this

    val day = calendar.get(
        Calendar.DAY_OF_MONTH
    )

    val month = calendar.get(
        Calendar.MONTH
    ) + 1

    val year = calendar.get(
        Calendar.YEAR
    )

    return "$day.$month.$year"
}