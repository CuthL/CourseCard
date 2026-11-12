package com.example.coursecard.model

import java.time.DayOfWeek
import java.time.LocalTime

/**
 * One class the student is taking.
 *
 * Think of this as a blank index card: every course gets the same labeled blanks.
 * For now it only has what the My Courses screen shows.
 */
data class Course(
    val code: String,                   // "CSC 438"
    val name: String,                   // "Mobile Application Dev."
    val meetingDays: List<DayOfWeek>,   // [MONDAY, WEDNESDAY]. The screen turns this into "M/W".
    val startTime: LocalTime,           // 4:40 PM. A real time, not text.
    val endTime: LocalTime              // 6:20 PM
)
