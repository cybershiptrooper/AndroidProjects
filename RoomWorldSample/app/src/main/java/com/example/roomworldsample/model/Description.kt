package com.example.roomworldsample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity (
    tableName = "Word_desc",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Word::class,
            parentColumns = arrayOf("word"),
            childColumns = arrayOf("word"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ),
    primaryKeys = arrayOf("word", "Definition")
)
class Description(
    @ColumnInfo(index = true)
    val word: String,
    @ColumnInfo(name = "Definition")
    val definition: String
)