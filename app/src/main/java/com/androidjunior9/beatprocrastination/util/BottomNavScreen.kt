package com.androidjunior9.beatprocrastination.util

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavScreen(
    navController: NavController,
    items:List<BottomNavItem>,
    onItemClick:(BottomNavItem) -> Unit,
    modifier:Modifier = Modifier
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.DarkGray,
        elevation = 5.dp
    ) {
        items.forEach { item->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = false,
                onClick = {
                    onItemClick(item)
                },
                selectedContentColor = Color.Red,
                unselectedContentColor =    Color.Gray,
                icon = {
                    Column() {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "Navigation Icon"
                        )
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp

                            )
                        }
                    }
                }
            )
        }
    }
}