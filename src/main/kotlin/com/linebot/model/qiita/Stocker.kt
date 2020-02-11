package com.linebot.model.qiita

import com.fasterxml.jackson.annotation.JsonProperty

data class Stocker(
        // https://qiita.com/api/v2/docs#get-apiv2itemsitem_idstockers

        var description: String? = null,
        var id: String? = null,
        var name: String? = null,
        var organization: String? = null,
        @JsonProperty("profile_image_url")
        var profileImageUrl: String? = null,
        @JsonProperty("website_url")
        var websiteUrl: String? = null
)
