package br.com.nglauber.jetpackcomposeplayground.crud

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.nglauber.jetpackcomposeplayground.R
import br.com.nglauber.jetpacksample.repository.LocalRepository

enum class SocialNetwork(val title: String, @DrawableRes val icon: Int) {
    FACEBOOK("Facebook", R.drawable.icon_facebook),
    INSTAGRAM("Instagram", R.drawable.icon_insta),
    TWITTER("Twitter", R.drawable.icon_twitter)
}

val socialNetworks = listOf(
    SocialNetwork.FACEBOOK,
    SocialNetwork.INSTAGRAM,
    SocialNetwork.TWITTER
)

@Composable
fun SocialNetworkScreen() {
    val context = LocalContext.current
    val viewModel: AppViewModel =
        viewModel("AppViewModel", AppViewModelFactory(LocalRepository(context)))
    SocialNetwork(
        usersLiveData = viewModel.allUsers,
        onSaveUser = { user ->
            viewModel.saveUser(user)
        },
        onDeleteUser = { user ->
            viewModel.deleteUser(user)
        }
    )
}

@Composable
fun SocialNetwork(
    usersLiveData: LiveData<List<UserBinding>>,
    onSaveUser: (UserBinding) -> Unit,
    onDeleteUser: (UserBinding) -> Unit
) {
    val defaultSocialNetwork = socialNetworks.first()
    val currentUser = mutableStateOf(
        UserBinding(0, "", true, defaultSocialNetwork)
    )
    val users by usersLiveData.observeAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        InputPanel(currentUser.value, onInsertUser = { user ->
            currentUser.value = UserBinding(0, "", true, defaultSocialNetwork)
            onSaveUser(user)
        })
        UserList(
            users = users ?: emptyList(),
            onDeleteUser = onDeleteUser,
            onUpdate = { user ->
                currentUser.value =
                    UserBinding(user.id, user.name, user.isActive, user.socialNetwork)
            }
        )
    }
}

@Composable
fun InputPanel(currentUser: UserBinding, onInsertUser: (UserBinding) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            label = { Text("Digite seu nome") },
            value = currentUser.name,
            onValueChange = { s: String ->
                currentUser.name = s
            }
        )
        Row {
            Checkbox(
                checked = currentUser.isActive,
                onCheckedChange = { currentUser.isActive = it })
            Text("Ativo")
        }
        val onSelectedChange: (SocialNetwork) -> Unit = { s -> currentUser.socialNetwork = s }
        Column {
            socialNetworks.forEach { socialNetwork ->
                Row(Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (socialNetwork == currentUser.socialNetwork),
                        onClick = { onSelectedChange(socialNetwork) }
                    )
                    .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = (socialNetwork == currentUser.socialNetwork),
                        onClick = { onSelectedChange(socialNetwork) }
                    )
                    Text(
                        text = socialNetwork.title,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        Button(onClick = {
            //formModel.users.add(formModel.currentUser)
            onInsertUser(currentUser)
        }, content = { Text("Save") })
    }
}

@Composable
fun UserList(
    users: List<UserBinding>,
    onDeleteUser: (UserBinding) -> Unit,
    onUpdate: (UserBinding) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(users, key = { user -> user.id }) {
            UserItem(user = it, onDeleteUser = onDeleteUser, onUpdate = onUpdate)
        }
    }
}

@Composable
fun UserItem(
    user: UserBinding,
    onDeleteUser: (UserBinding) -> Unit,
    onUpdate: (UserBinding) -> Unit
) {
    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                onClick = {
                    onUpdate(user)
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(user.socialNetwork.icon),
                contentDescription = "Social Network",
                modifier = Modifier.size(72.dp),
                contentScale = ContentScale.FillHeight
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = user.name)
                Text(text = if (user.isActive) "Ativo" else "Inativo")
            }
            OutlinedButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = {
                    onDeleteUser(user)
                },
                content = { Icon(Icons.Filled.Delete, "Delete") }
            )
        }
    }
}

