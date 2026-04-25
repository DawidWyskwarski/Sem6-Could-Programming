package com.example.file_service.domain.exception

open class FileDomainException(message: String) : RuntimeException(message)

class EmptyFileException(message: String) : FileDomainException(message)
class InvalidFileExtensionException(message: String) : FileDomainException(message)
class InvalidFileSizeException(message: String) : FileDomainException(message)

