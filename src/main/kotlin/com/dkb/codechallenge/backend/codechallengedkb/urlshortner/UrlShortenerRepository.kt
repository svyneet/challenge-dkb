package com.dkb.codechallenge.backend.codechallengedkb.urlshortner

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlShortenerRepository : JpaRepository<UrlMapperEntity, String> {

    fun findByUrl(url: String): List<UrlMapperEntity>?
    fun findByHash(hash: String): List<UrlMapperEntity>?
}