package com.example.coursecard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

// ------------------------------------------------------------
// Frame 2: "My Courses" screen
// Same layout recipe as Cuthbert's TodayScreen: header on top, a scrolling
// middle that takes up all the leftover space, bottom bar at the bottom.
// ------------------------------------------------------------

@Composable
fun CoursesScreen(courses: List<Course>) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = 24.dp)
        ) {
            ScreenHeader(title = "My Courses")

            // weight(1f) = "take all the space left between the header and the bottom bar"
            CourseList(
                courses = courses,
                modifier = Modifier.weight(1f)
            )

            CoursesBottomNavigation()
        }
    }
}

/**
 * The scrolling middle part: "Fall 2026", one card per course, then "+ ADD COURSE".
 * LazyColumn = a scrolling list that only draws the cards currently on screen
 * (same as TeamList in the Week 8 lesson).
 */
@Composable
fun CourseList(courses: List<Course>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Hardcoded for now. Later this can come from the CSI academic calendar.
            Text(
                text = "Fall 2026",
                fontSize = 18.sp
            )
        }

        // One card for every course in the list.
        items(courses) { course ->
            CourseSummaryCard(course = course)
        }

        item {
            TextButton(onClick = { /* Open the Add Course screen later. */ }) {
                Text(
                    text = "+ ADD COURSE",
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}

/**
 * One bordered card: course code, course name, days • time.
 * Styled to match Cuthbert's ScheduleCourseCard so both screens look the same.
 */
@Composable
fun CourseSummaryCard(course: Course, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = course.code,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = course.name,
                fontSize = 18.sp
            )

            Text(
                text = "${formatDays(course.meetingDays)} • ${formatTimeRange(course.startTime, course.endTime)}",
                fontSize = 18.sp
            )
        }
    }
}

// ------------------------------------------------------------
// Header and bottom bar.
// Same setup as Cuthbert's CourseHeader() and BottomNavigation() in TodayScreen.kt.
// The bottom bar will move into the navigation file once we make it.
// ------------------------------------------------------------

@Composable
fun ScreenHeader(title: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )

            IconButton(
                onClick = {
                    // Open the settings screen later.
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings",
                    tint = Color.Black
                )
            }
        }

        HorizontalDivider(color = Color.Black)
    }
}

@Composable
fun CoursesBottomNavigation() {
    Column {
        HorizontalDivider(color = Color.Black)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { /* Go to Today screen later. */ }) {
                Text(
                    text = "Today",
                    color = Color.Black
                )
            }

            TextButton(onClick = { /* Already on Courses. */ }) {
                Text(
                    text = "Courses",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            TextButton(onClick = { /* Add Calendar screen later. */ }) {
                Text(
                    text = "Calendar",
                    color = Color.Black
                )
            }
        }
    }
}

// ------------------------------------------------------------
// Helpers that turn the stored data into the short text this screen shows.
// "private" = only this file can use them.
// ------------------------------------------------------------

/** [MONDAY, WEDNESDAY] -> "M/W" */
private fun formatDays(days: List<DayOfWeek>): String {
    return days.sorted().joinToString("/") { day -> shortDayName(day) }
}

private fun shortDayName(day: DayOfWeek): String {
    return when (day) {
        DayOfWeek.MONDAY -> "M"
        DayOfWeek.TUESDAY -> "T"
        DayOfWeek.WEDNESDAY -> "W"
        DayOfWeek.THURSDAY -> "Th"
        DayOfWeek.FRIDAY -> "F"
        DayOfWeek.SATURDAY -> "Sa"
        DayOfWeek.SUNDAY -> "Su"
    }
}

/** 4:40 PM and 6:20 PM -> "4:40 – 6:20 PM". Shows AM/PM twice only when they differ ("11:00 AM – 12:15 PM"). */
private fun formatTimeRange(start: LocalTime, end: LocalTime): String {
    val withAmPm = DateTimeFormatter.ofPattern("h:mm a", Locale.US)
    val withoutAmPm = DateTimeFormatter.ofPattern("h:mm", Locale.US)
    val sameHalfOfDay = (start.hour < 12) == (end.hour < 12)

    return if (sameHalfOfDay) {
        "${start.format(withoutAmPm)} – ${end.format(withAmPm)}"
    } else {
        "${start.format(withAmPm)} – ${end.format(withAmPm)}"
    }
}

// ------------------------------------------------------------
// Preview: open this file in Split view to see the screen without running the app.
// Same phone size as Cuthbert's preview so the two screens line up.
// ------------------------------------------------------------

@Preview(showBackground = true, widthDp = 393, heightDp = 851)
@Composable
fun CoursesScreenPreview() {
    MaterialTheme {
        CoursesScreen(courses = Datasource().loadCourses())
    }
}
