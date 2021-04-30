package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ScaffoldScreen() {
    val users = (18..100).map { User("User $it", it) }
    val scope = rememberCoroutineScope()
    var selectedTab: Int by mutableStateOf(0)
    var menuExpanded by mutableStateOf(false)
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.Yellow,
                title = { Text(text = "Compose") },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Delete, "Delete")
                    }
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(Icons.Filled.Edit, "Edit")
                    }
                    IconButton(onClick = { menuExpanded = !menuExpanded }) {
                        Icon(Icons.Filled.MoreVert, "Edit")
                        DropdownMenu(
                            expanded = menuExpanded, onDismissRequest = {
                                menuExpanded = false
                            }, content = {
                                DropdownMenuItem(onClick = {
                                    menuExpanded = false
                                }, content = { Text("Option 1") })
                                DropdownMenuItem(onClick = {
                                    menuExpanded = false
                                }, content = { Text("Option 2") })
                            }
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) { Icon(Icons.Default.Menu, "Menu") }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                content = {
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Home, "Home") },
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        selectedContentColor = Color.Magenta,
                        unselectedContentColor = Color.LightGray,
                        label = { Text(text = "Home") }
                    )
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Settings, "Setting") },
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        selectedContentColor = Color.Magenta,
                        unselectedContentColor = Color.LightGray,
                        label = { Text(text = "Settings") }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                backgroundColor = Color.Red,
                contentColor = Color.White,
                shape = CutCornerShape(
                    topStart = 20.dp, bottomEnd = 20.dp
                )
            ) { Icon(Icons.Filled.Add, "Add") }
        },
        drawerContent = {
            Text(text = "My Drawer")
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        bottom = it.calculateBottomPadding()
                    )
            ) {
                if (selectedTab == 0) {
                    UserListScreen(users)
                } else {
                    Text(
                        text = "Tab 2!",
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
    )
}

data class User(
    val name: String,
    val age: Int
)

@Composable
fun UserListScreen(users: List<User>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                "Header",
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        itemsIndexed(users) { index, user ->
            Text(
                "${user.name} - ${user.age}",
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}