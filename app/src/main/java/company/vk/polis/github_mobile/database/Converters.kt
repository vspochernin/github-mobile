package company.vk.polis.github_mobile.database

import androidx.room.TypeConverter
import company.vk.polis.github_mobile.model.Owner
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromOwnerToString(owner: Owner): String {
        return Json.encodeToString(owner)
    }

    @TypeConverter
    fun fromStringToOwner(string: String): Owner {
        return Json.decodeFromString(string)
    }
}
