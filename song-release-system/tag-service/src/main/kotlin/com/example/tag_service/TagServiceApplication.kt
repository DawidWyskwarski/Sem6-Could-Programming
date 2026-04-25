package com.example.tag_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.tag_service", "com.example.common"])
class TagServiceApplication

/**
 * Tag Service: Responsible for managing and assigning categorization tags to uploaded music tracks.
 * - Listens for TrackUploadedEvent to initiate the tagging process as part of the track's lifecycle.
 * - Through AssignTagsUseCase, it matches track keywords against predefined or existing domain Tags.
 * - Persists the relationships (tags assigned to a track) via its local TagRepository.
 * - Emits a TrackTaggedEvent upon successful assignment, enabling subsequent features (like playlist curation)
 *   to consume categorized tracks.
 */
fun main(args: Array<String>) {
	runApplication<TagServiceApplication>(*args)
}
