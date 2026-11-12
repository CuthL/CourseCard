package com.example.coursecard.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coursecard.data.Datasource
import com.example.coursecard.model.Course
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/** Frame 2: "My Courses". The header and bottom bar come from CourseCardNavigation.kt. */
@Composable
fun CoursesScreen(courses: List<Course>) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        CourseList(
            courses = courses,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

/**
 * "Fall 2026", one card per course, then "+ ADD COURSE".
 * Like LazyColumn (Week 8), but as a grid: one column when the phone is upright,
 * cards side by side when it's sideways, so landscape doesn't feel cramped.
 */
@Composable
private fun CourseList(courses: List<Course>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 280.dp),
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            // TODO: get the term from the CSI academic calendar.
            Text(text = "Fall 2026", fontSize = 18.sp)
        }

        items(courses) { course ->
            CourseSummaryCard(course = course)
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            // Grids stretch items to full width; this keeps the button on the left.
            TextButton(
                onClick = { /* TODO: open the Add Course screen. */ },
                modifier = Modifier.wrapContentWidth(Alignment.Start)
            ) {
                Text(text = "+ ADD COURSE", color = Color.Black, fontSize = 16.sp)
            }
        }
    }
}

/** One bordered card: code, name, and "M/W • 4:40 – 6:20 PM". Same style as ScheduleCourseCard. */
@Composable
private fun CourseSummaryCard(course: Course, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val days = formatDays(course.meetingDays)
            val time = formatTimeRange(course.startTime, course.endTime)

            Text(text = course.code, fontSize = 20.sp, fontWeight = FontWeight.Medium)
            Text(text = course.name, fontSize = 18.sp)
            Text(text = "$days • $time", fontSize = 18.sp)
        }
    }
}

private val timeWithAmPm = DateTimeFormatter.ofPattern("h:mm a", Locale.US)
private val timeWithoutAmPm = DateTimeFormatter.ofPattern("h:mm", Locale.US)

/** [MONDAY, WEDNESDAY] -> "M/W" */
private fun formatDays(days: List<DayOfWeek>): String =
    days.sorted().joinToString("/") { shortDayName(it) }

private fun shortDayName(day: DayOfWeek): String = when (day) {
    DayOfWeek.MONDAY -> "M"
    DayOfWeek.TUESDAY -> "T"
    DayOfWeek.WEDNESDAY -> "W"
    DayOfWeek.THURSDAY -> "Th"
    DayOfWeek.FRIDAY -> "F"
    DayOfWeek.SATURDAY -> "Sa"
    DayOfWeek.SUNDAY -> "Su"
}

/** 4:40 PM to 6:20 PM -> "4:40 – 6:20 PM". Shows AM/PM twice only when they differ. */
private fun formatTimeRange(start: LocalTime, end: LocalTime): String {
    val sameHalfOfDay = (start.hour < 12) == (end.hour < 12)
    val startText = start.format(if (sameHalfOfDay) timeWithoutAmPm else timeWithAmPm)
    return "$startText – ${end.format(timeWithAmPm)}"
}

@Preview(showBackground = true, widthDp = 393, heightDp = 851)
@Composable
private fun CoursesScreenPreview() {
    MaterialTheme {
        CoursesScreen(courses = Datasource().loadCourses())
    }
}
