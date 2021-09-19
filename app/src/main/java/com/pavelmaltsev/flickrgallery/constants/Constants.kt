package com.pavelmaltsev.flickrgallery.constants

class Constants {
    companion object {
        const val BASE_URL = "https://api.flickr.com"
        const val PHOTOS_PATH = "/services/rest/?method=flickr.photos.getRecent"
        const val API_KEY = "&api_key=aabca25d8cd75f676d3a74a72dcebf21"
        const val JSON_FORMAT = "&format=json&nojsoncallback=1"
        const val EXTRA_URL = "&extras=url_s&size=o"
    }
}

class Flickr {
    companion object {
        const val PAGE = "page"
        const val PER_PAGE = "per_page"
        const val PAGE_SIZE = 50
        const val POSTED_USER_ADDRESS = "https://www.flickr.com/photos/"
    }
}

class AppValues {
    companion object {
        const val KEY_FLICKR = "key_flickr"
        const val DEFAULT_FLICKR = "default_flickr"
        const val EXTRA_PHOTO = "extra_photo"
        const val PHOTO_FORMAT = ".jpg"


    }
}

