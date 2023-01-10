package br.com.nglauber.jetpackcomposeplayground.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ScaffoldScreen() {
    val users = (18..100).map { Person("User $it", it) }
    val scope = rememberCoroutineScope()
    var selectedTab: Int by remember { mutableStateOf(0) }
    var menuExpanded by remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val fabShape = CutCornerShape(
        topStart = 20.dp, bottomEnd = 20.dp
    )
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
                cutoutShape = fabShape,
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
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            var requestToOpen by remember {
                mutableStateOf(false)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DropdownMenu(
                    modifier = Modifier.wrapContentWidth(),
                    expanded = requestToOpen,
                    onDismissRequest = { requestToOpen = false },
                ) {
                    (1..2).forEach {
                        DropdownMenuItem(
                            onClick = {
                                requestToOpen = false
                            }
                        ) {
                            Text("Option $it")
                        }
                    }
                }
                FloatingActionButton(
                    onClick = {
                        requestToOpen = true
                    },
                    backgroundColor = Color.Red,
                    contentColor = Color.White,
                    shape = fabShape
                ) { Icon(Icons.Filled.Add, "Add") }
            }
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

data class Person(
    val name: String,
    val age: Int
)

@Composable
fun UserListScreen(users: List<Person>) {
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
        itemsIndexed(users) { _, user ->
            Text(
                "${user.name} - ${user.age}",
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}