package com.example.coursecard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import com.example.coursecard.ui.theme.CourseCardTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                TodayScreen()
            }
        }
    }
}

@Composable
fun TodayScreen() {
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
            CourseHeader()

            // Takes the available space between the header and navigation.
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Today's Schedule",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Tuesday, October 13",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "⚠ Monday Schedule Today",
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "NEXT CLASS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                ScheduleCourseCard(
                    courseCode = "CSC 438",
                    courseTitle = "Mobile Application Dev.",
                    time = "4:40 PM – 6:20 PM",
                    location = "Building 1N • Room 115"
                )

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "LATER TODAY",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(12.dp))

                ScheduleCourseCard(
                    courseCode = "MTH 230",
                    time = "7:00 PM – 8:40 PM",
                    location = "Building 2N • Room 001"
                )
            }

            BottomNavigation()
        }
    }
}

@Composable
fun CourseHeader() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "CourseCard",
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
fun ScheduleCourseCard(
    courseCode: String,
    time: String,
    location: String,
    courseTitle: String? = null,
    modifier: Modifier = Modifier
) {
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
                text = courseCode,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            // The later-today card can omit the course title.
            if (courseTitle != null) {
                Text(
                    text = courseTitle,
                    fontSize = 18.sp
                )
            }

            Text(
                text = time,
                fontSize = 18.sp
            )

            Text(
                text = location,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun BottomNavigation() {
    Column {
        HorizontalDivider(color = Color.Black)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = { /* Already on Today. */ }) {
                Text(
                    text = "Today",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            TextButton(onClick = { /* Add Courses screen later. */ }) {
                Text(
                    text = "Courses",
                    color = Color.Black
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

@Preview(showBackground = true, widthDp = 393, heightDp = 851)
@Composable
fun TodayScreenPreview() {
    MaterialTheme {
        TodayScreen()
    }
}