package com.dkb.codechallenge.backend.codechallengedkb.urlshortner

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class UrlMapperEntity(

    @Id
    @Column(nullable = false)
    val hash: String,

    @Column(nullable = false)
    var url: String
)