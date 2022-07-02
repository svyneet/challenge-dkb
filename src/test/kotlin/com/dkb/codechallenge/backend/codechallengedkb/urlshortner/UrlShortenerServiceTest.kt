package com.dkb.codechallenge.backend.codechallengedkb.urlshortner

import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class UrlShortenerServiceTest @Autowired constructor(
    private val service: UrlShortenerService,
) {

    val testURL = "www.dkb.com"

    @Test
    fun testGenerateShortUrl() {
        val hash = service.generateShortURL(testURL)
        assert(Regex("[a-z]{2}").matches(hash))
    }

    @Test
    fun testGenerateShortUrlHashClash() {
        var hashClash = false
        for (temp in 0..676) {
            if (service.generateShortURL(testURL + temp) == "The hash for URL couldn't be generated.") {
                hashClash = true
                break
            }
        }
        assert(hashClash)
    }

    @Test
    fun testGenerateShortUrlRepeat() {
        val result = service.generateShortURL("$testURL/test")
        val result2 = service.generateShortURL("$testURL/test")
        assert(result == result2)

    }

    @Test
    fun testGetURLFromHash() {
        val hash = service.generateShortURL(testURL)
        val result = service.getURLFromHash(hash)
        assert(result == testURL)

    }

    @Test
    fun testGetURLFromHashNotFound() {
        val result = service.getURLFromHash("test")
        assert(result == "The requested URL associated with test couldn't be found.")

    }

    @Test
    fun testDeleteUrl() {
        service.generateShortURL("$testURL/delete")
        val result = service.deleteURL("$testURL/delete")
        assert(result.equals("$testURL/delete has been deleted."))

    }

    @Test
    fun testDeleteUrlDoNotExist() {
        val result = service.deleteURL("$testURL/delete_false")
        assert(result.equals("$testURL/delete_false doesn't exist."))

    }
}