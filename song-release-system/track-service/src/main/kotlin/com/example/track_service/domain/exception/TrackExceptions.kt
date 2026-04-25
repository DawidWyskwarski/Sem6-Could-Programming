package com.example.track_service.domain.exception

open class TrackDomainException(message: String) : RuntimeException(message)

class InvalidTrackTitleException(message: String) : TrackDomainException(message)
class InvalidTrackKeywordsException(message: String) : TrackDomainException(message)

