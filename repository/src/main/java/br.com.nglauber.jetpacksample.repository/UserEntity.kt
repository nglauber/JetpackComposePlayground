package br.com.nglauber.jetpacksample.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    var name: String,
    var isActive: Boolean,
    var socialNetwork: String
) {
    fun toUser() = User(
        this.uid,
        this.name,
        this.isActive,
        this.socialNetwork
    )

    companion object {
        fun fromUser(user: User) = UserEntity(
            user.uid,
            user.name,
            user.isActive,
            user.socialNetwork
        )
    }
}