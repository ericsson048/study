package iut.montpellier.study.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Questions",
    foreignKeys = [ForeignKey(
        entity = Chapter::class,
        parentColumns = ["id"],
        childColumns = ["chapterId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "chapterId")
    val chapterId: Int,

    @ColumnInfo(name = "questionText")
    val questionText: String,

    @ColumnInfo(name = "option1")
    val option1: String,

    @ColumnInfo(name = "option2")
    val option2: String,

    @ColumnInfo(name = "option3")
    val option3: String,

    @ColumnInfo(name = "option4")
    val option4: String,

    @ColumnInfo(name = "correctAnswer")
    val correctAnswer: String
)