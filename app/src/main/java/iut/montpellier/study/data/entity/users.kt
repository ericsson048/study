package iut.montpellier.study.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(
    @PrimaryKey(autoGenerate = true)
    var id :Int =0,
    var first_name:String,
    var last_nane:String,
    var email:String,
    var password:String
)
