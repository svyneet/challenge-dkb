package com.dkb.codechallenge.backend.codechallengedkb.urlshortner

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UrlMapperConfig {
    @Value("\${urlmapper.retry-hash-generate}")
    lateinit var totalHashGenerateRetries: String

    @Value("\${urlmapper.hash-size}")
    lateinit var hashSize: String

}