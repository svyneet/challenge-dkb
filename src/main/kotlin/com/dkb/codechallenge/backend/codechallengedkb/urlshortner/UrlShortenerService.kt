package com.dkb.codechallenge.backend.codechallengedkb.urlshortner

import mu.KotlinLogging
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service
import kotlin.jvm.internal.Intrinsics.Kotlin

@Service
class UrlShortenerService(
    private val repository: UrlShortenerRepository,
    val urlMapperConfig: UrlMapperConfig
) {
    val logger = KotlinLogging.logger {}
    fun generateShortURL(url: String): String {
        val existingEntity = repository.findByUrl(url)
        if(!existingEntity.isNullOrEmpty())
            return existingEntity[0].hash
        for (retry in 1.. urlMapperConfig.totalHashGenerateRetries.toInt()) {
            val hash = getHash()
            if (repository.findByHash(hash).isNullOrEmpty()) {
                println(hash)
                return repository.save(UrlMapperEntity(hash, url)).hash
            }
            logger.warn("$hash already exists in the database.")
        }
         return  "The hash for URL couldn't be generated."
    }

    fun getURLFromHash(hash: String): String {
        val result = repository.findByHash(hash)
        if (!result.isNullOrEmpty() && !result[0].url.isNullOrEmpty()) {
            return result[0].url!!
        }
        return "The requested URL associated with $hash couldn't be found."
    }

    fun deleteURL(url: String): String {
        val result = repository.findByUrl(url)
        return if (!result.isNullOrEmpty()) {
            repository.delete(result[0])
            "$url has been deleted."
        } else
            "$url doesn't exist."
    }

    private fun getHash(): String {
        return RandomStringUtils.randomAlphabetic(urlMapperConfig.hashSize.toInt()).lowercase()
    }

}