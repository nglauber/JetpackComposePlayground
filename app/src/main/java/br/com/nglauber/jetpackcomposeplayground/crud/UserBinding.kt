package br.com.nglauber.jetpackcomposeplayground.crud

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.nglauber.jetpacksample.repository.User

class UserBinding(
    val id: Long,
    name: String,
    isActive: Boolean,
    socialNetwork: SocialNetwork
) {
    var name by mutableStateOf(name)
    var isActive by mutableStateOf(isActive)
    var socialNetwork by mutableStateOf(socialNetwork)

    fun toUser() = User(
        id, name, isActive, socialNetwork.name
    )

    companion object {
        fun fromUser(user: User) = UserBinding (
            user.uid, user.name, user.isActive, SocialNetwork.valueOf(user.socialNetwork)
        )
    }
}