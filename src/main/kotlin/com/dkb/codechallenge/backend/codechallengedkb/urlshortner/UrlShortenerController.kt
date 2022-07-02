package com.dkb.codechallenge.backend.codechallengedkb.urlshortner

import com.dkb.codechallenge.backend.codechallengedkb.utils.ResponseObject
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/urlgenerator/v1")
class UrlShortenerController(
    val service: UrlShortenerService,
    val objectMapper: ObjectMapper,
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getURLFromHash(@RequestParam(value = "hash", required = true) hash: String): ResponseEntity<String> {
        val result = service.getURLFromHash(hash)
        return if (result != "The requested URL associated with $hash couldn't be found.")
            ResponseEntity.ok().body(objectMapper.writeValueAsString(ResponseObject(result)))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.writeValueAsString(ResponseObject(result)))
    }

    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun postURL(@RequestParam(value = "url", required = true) url: String): ResponseEntity<String> {
        val result = service.generateShortURL(url)
        return if (result != "The hash for URL couldn't be generated.")
            ResponseEntity.ok().body(objectMapper.writeValueAsString(ResponseObject(result)))
        else
            ResponseEntity.status(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED).body(objectMapper.writeValueAsString(ResponseObject(result)))
    }

    @DeleteMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteURL(@RequestParam(value = "url", required = true) url: String): ResponseEntity<String> {
        val result = service.deleteURL(url)
        return if (result != "$url doesn't exist.")
            ResponseEntity.ok().body(objectMapper.writeValueAsString(ResponseObject(result)))
        else
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.writeValueAsString(ResponseObject(result)))
    }

}