package com.linebot.client

import com.linebot.util.Utils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import java.net.URI

@Component
class WebClient(
        private val restTemplate: RestTemplate
) {

    val log: Logger = LoggerFactory.getLogger(WebClient::class.java)

    fun <V, P> post(uri: String, param: P, resultClazz: Class<V>): V? {
        return this.exchange(this.postRequestEntity(uri, param), resultClazz)
    }

    operator fun <V> get(uri: String, resultClazz: Class<V>): V? {
        return this.exchange(this.getRequestEntity(uri), resultClazz)
    }

    private fun getRequestEntity(uri: String): RequestEntity<*> {
        return Utils.uncheck<RequestEntity<*>> { RequestEntity.get(URI(uri)).build() }
    }

    private fun <P> postRequestEntity(uri: String, param: P): RequestEntity<P> {
        return Utils.uncheck<RequestEntity<P>> {
            RequestEntity
                    .post(URI(uri))
                    .body(param)
        }
    }

    private fun <V> exchange(requestEntity: RequestEntity<*>, clazz: Class<V>): V? {
        try {
            val responseEntity = restTemplate.exchange(requestEntity, clazz)
            if (responseEntity.statusCode == HttpStatus.OK) {
                return responseEntity.body
            } else {
                log.error("Response status code is not OK(200). responseEntity: {}", responseEntity)
                throw RuntimeException("Response status code is not OK(200)")
            }
        } catch (e: HttpServerErrorException) {
            log.error("HttpServerErrorException: {}", e)
            throw RuntimeException(e)
        }

    }

    /*
    IntelliJのkotlin自動変換では、コメントアウト部分まで変換してくれなかった

    基本的にCollectionでデータを取得する時はこちらを利用したいが、
    これを利用するとLinkedHashMapのListになってしまう。
    うまいこと解消できなかったので、これは利用せず配列をListへ変換するように各Clientで実装する
    public <V> V exchange(RequestEntity requestEntity) {
        try {
            ResponseEntity<V> responseEntity = restTemplate.exchange(requestEntity, new ParameterizedTypeReference<V>() {
            });
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            } else {
                log.error("Response status code is not OK(200). responseEntity: {}", responseEntity);
                throw new RuntimeException("Response status code is not OK(200)");
            }
        } catch (HttpServerErrorException e) {
            log.error("HttpServerErrorException: {}", e);
            throw new RuntimeException(e);
        }
    }
    */
}
