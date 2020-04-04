package com.linebot.model.qiita

import com.linebot.model.common.RequestParameter
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Component
data class QiitaRequestParameter(
    var page: Int?,
    var perPage: Int?
) : RequestParameter {
    override fun convert(): MultiValueMap<String, String> {
        val params = LinkedMultiValueMap<String, String>()
        add(params, "page", this.page)
        add(params, "per_page", this.perPage)
        return params
    }
}
