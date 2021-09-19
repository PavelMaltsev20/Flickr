package com.pavelmaltsev.flickrgallery.model

import java.io.Serializable

data class FlickrPhoto(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val url_s: String,
    val height_s: Int,
    val width_s: Int,
) : Serializable {
    constructor() : this(
        "",
        "",
        "",
        "",
        0,
        "",
        "",
        0,
        0,
    )
}

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: ArrayList<FlickrPhoto>
)

data class FlickrResponse(
    val stat: String,
    val photos: Photos
)
