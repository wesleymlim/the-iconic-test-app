package com.uesurei.theiconicapp.network.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val product_Id: String,
    val prevKey: Int?,
    val nextKey: Int?
)