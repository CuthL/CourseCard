package com.example.coursecard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coursecard.data.Datasource
import com.example.coursecard.ui.CoursesScreen
import com.example.coursecard.ui.TodayScreen

// Navigation for the whole app, like CookieScreen.kt in the Week 10 lesson:
// the list of screens, the shared header and bottom bar, and the NavHost that swaps screens.

/** The screens (routes) and the title each one shows in the header. Add Calendar when it exists. */
enum class CourseCardScreen(val title: String) {
    Today(title = "CourseCard"),
    Courses(title = "My Courses")
}

/** The whole app. MainActivity calls this. */
@Composable
fun CourseCardApp(navController: NavHostController = rememberNavController()) {
    // The screen showing right now: it sets the header title and which tab is bold.
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CourseCardScreen.valueOf(
        backStackEntry?.destination?.route ?: CourseCardScreen.Today.name
    )

    Scaffold(
        containerColor = Color.White,
        topBar = { CourseCardTopBar(title = currentScreen.title) },
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

/** Switches tabs without stacking up copies of screens, so Back from Courses goes to Today. */
private fun goToTab(navController: NavHostController, screen: CourseCardScreen) {
    navController.navigate(screen.name) {
        popUpTo(CourseCardScreen.Today.name)
        launchSingleTop = true
    }
}

/** Shared header: screen title, settings gear, then a line. Like CookieAppBar in Week 10. */
@Composable
private fun CourseCardTopBar(title: String) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Medium)

            IconButton(onClick = { /* TODO: open the Settings screen. */ }) {
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

/** Shared bottom bar: Today / Courses / Calendar, with the current screen in bold. */
@Composable
private fun CourseCardBottomBar(
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
            BottomBarTab(
                label = "Today",
                isSelected = currentScreen == CourseCardScreen.Today,
                onClick = onTodayClick
            )
            BottomBarTab(
                label = "Courses",
                isSelected = currentScreen == CourseCardScreen.Courses,
                onClick = onCoursesClick
            )
            BottomBarTab(
                label = "Calendar",
                isSelected = false,
                onClick = { /* TODO: add the Calendar screen. */ }
            )
        }
    }
}

/** One tab in the bottom bar. Bold when it's the screen you're on. */
@Composable
private fun BottomBarTab(label: String, isSelected: Boolean, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = label,
            color = Color.Black,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 851)
@Composable
private fun CourseCardAppPreview() {
    MaterialTheme {
        CourseCardApp()
    }
}
