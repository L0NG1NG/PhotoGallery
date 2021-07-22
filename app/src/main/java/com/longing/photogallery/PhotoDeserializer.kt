package com.longing.photogallery

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class PhotoDeserializer : JsonDeserializer<PhotoResponse> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PhotoResponse {
        val jsonBody = json.asJsonObject
        val photosJsonObject = jsonBody.get("photos").asJsonObject
        val photoJsonArray = photosJsonObject.get("photo").asJsonArray

        val galleryItems = mutableListOf<GalleryItem>()
        for (index in 0 until photoJsonArray.size()) {
            val photoJsonObject = photoJsonArray[index].asJsonObject

            val item = GalleryItem()
            item.id = photoJsonObject.get("id").asString
            item.title = photoJsonObject.get("title").asString
            item.url = photoJsonObject.get("url_s").asString
            galleryItems.add(item)
        }

        return PhotoResponse().apply {
            this.galleryItems = galleryItems
        }


    }
}