package com.example.coursecard.data

import com.example.coursecard.model.Course
import java.time.DayOfWeek
import java.time.LocalTime

/**
 * Fake sample courses so the screen has something to show.
 * Same idea as Datasource.loadTeams() from the Week 8 lesson.
 * Later, this gets replaced by the courses the student actually adds.
 */
class Datasource {
    fun loadCourses(): List<Course> {
        return listOf(
            Course(
                code = "CSC 438",
                name = "Mobile Application Dev.",
                meetingDays = listOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY),
                startTime = LocalTime.of(16, 40),   // 4:40 PM (24-hour clock: 16 = 4 PM)
                endTime = LocalTime.of(18, 20)      // 6:20 PM
            ),
            Course(
                code = "MTH 230",
                name = "Calculus III",
                meetingDays = listOf(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY),
                startTime = LocalTime.of(19, 0),    // 7:00 PM
                endTime = LocalTime.of(20, 40)      // 8:40 PM
            )
        )
    }
}
