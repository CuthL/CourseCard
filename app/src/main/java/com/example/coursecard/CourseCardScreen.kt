package com.example.coursecard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coursecard.data.Datasource

// ------------------------------------------------------------
// Navigation file: everything about moving between screens lives here.
// Same idea as CookieScreen.kt from the Week 10 lesson.
// ------------------------------------------------------------

/**
 * The list of screens (the "routes").
 * Add Calendar here once there is a Calendar screen.
 */
enum class CourseCardScreen {
    Today,
    Courses
}

/**
 * The whole app. MainActivity calls this.
 * Scaffold = the frame: the bottom bar stays put, and the NavHost in the
 * middle swaps whichever screen is showing.
 */
@Composable
fun CourseCardApp(
    navController: NavHostController = rememberNavController()
) {
    // Which screen is showing right now, so the bottom bar knows which tab to make bold.
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CourseCardScreen.valueOf(
        backStackEntry?.destination?.route ?: CourseCardScreen.Today.name
    )

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            CourseCardBottomBar(
                currentScreen = currentScreen,
                onTodayClick = { goToTab(navController, CourseCardScreen.Today) },
                onCoursesClick = { goToTab(navController, CourseCardScreen.Courses) }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CourseCardScreen.Today.name,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable(route = CourseCardScreen.Today.name) {
                TodayScreen()
            }

            composable(route = CourseCardScreen.Courses.name) {
                CoursesScreen(courses = Datasource().loadCourses())
            }
        }
    }
}

/**
 * Switches to a tab.
 * popUpTo(Today) + launchSingleTop stop tapping tabs from stacking up
 * copies of screens, so the phone's Back button from Courses goes to Today.
 */
private fun goToTab(navController: NavHostController, screen: CourseCardScreen) {
    navController.navigate(screen.name) {
        popUpTo(CourseCardScreen.Today.name)
        launchSingleTop = true
    }
}

/**
 * The one shared bottom bar (moved here from TodayScreen.kt and CoursesScreen.kt).
 * Same look as before: divider line, then Today / Courses / Calendar.
 */
@Composable
fun CourseCardBottomBar(
    currentScreen: CourseCardScreen,
    onTodayClick: () -> Unit,
    onCoursesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        HorizontalDivider(color = Color.Black)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onTodayClick) {
                Text(
                    text = "Today",
                    color = Color.Black,
                    fontWeight = if (currentScreen == CourseCardScreen.Today) FontWeight.Bold else FontWeight.Normal
                )
            }

            TextButton(onClick = onCoursesClick) {
                Text(
                    text = "Courses",
                    color = Color.Black,
                    fontWeight = if (currentScreen == CourseCardScreen.Courses) FontWeight.Bold else FontWeight.Normal
                )
            }

            TextButton(onClick = { /* No Calendar screen yet. */ }) {
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
fun CourseCardAppPreview() {
    MaterialTheme {
        CourseCardApp()
    }
}
