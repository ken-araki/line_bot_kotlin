package com.linebot.model.response

data class RestApiResponse(
        var status: String? = null
) {
    companion object {
        @JvmStatic
        fun builder(): RestApiResponseBuilder = RestApiResponseBuilder()
    }

    class RestApiResponseBuilder {
        var status: String? = null

        fun build(): RestApiResponse {
            return RestApiResponse(status)
        }

        fun status(status: String?) = apply { this.status = status }
    }
}
