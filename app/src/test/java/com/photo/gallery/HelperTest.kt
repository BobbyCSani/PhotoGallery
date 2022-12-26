package com.photo.gallery

import com.photo.gallery.data.model.ImageUrl
import com.photo.gallery.data.model.PhotoResponseModel
import com.photo.gallery.data.model.UserModel

fun generatePhotoResponseModel() =
    PhotoResponseModel(
        id = "1234",
        created_at = "2022-08-31T14:36:55Z",
        description = "Lorem ipsum sit dolor amet",
        alt_description = "",
        urls = ImageUrl(
            small = "https://images.unsplash.com/photo-1661956603025-8310b2e3036d?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzOTI3MTN8MXwxfGFsbHwxfHx8fHx8Mnx8MTY3MjAyMzI5MA&ixlib=rb-4.0.3&q=80&w=400",
            thumb = "https://images.unsplash.com/photo-1661956603025-8310b2e3036d?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwzOTI3MTN8MXwxfGFsbHwxfHx8fHx8Mnx8MTY3MjAyMzI5MA&ixlib=rb-4.0.3&q=80&w=200"
        ),
        user = UserModel(name = "John Doe")

    )