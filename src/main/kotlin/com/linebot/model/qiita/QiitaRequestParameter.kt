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
    companion object {
        @JvmStatic
        fun builder(): QiitaRequestParameterBuilder = QiitaRequestParameterBuilder()
    }

    override fun convert(): MultiValueMap<String, String> {
        val params = LinkedMultiValueMap<String, String>()
        add(params, "page", this.page)
        add(params, "per_page", this.perPage)
        return params
    }

    class QiitaRequestParameterBuilder {
        var page: Int? = null
        var perPage: Int? = null

        fun build(): QiitaRequestParameter {
            return QiitaRequestParameter(
                    page = page,
                    perPage = perPage
            )
        }

        fun page(page: Int?) = apply { this.page = page }
        fun perPage(perPage: Int?) = apply { this.perPage = perPage }
    }
}
