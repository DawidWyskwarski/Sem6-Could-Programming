package com.example.playlist_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main application class for Playlist Service.
 *
 * The Playlist Service is responsible for managing global playlists.
 * It automatically updates playlists by reacting to specific messaging events:
 * when a track is successfully tagged (e.g., via TrackTaggedEvent), this
 * service discovers eligible playlists based on common tags and appends
 * the track.
 */
@SpringBootApplication(scanBasePackages = ["com.example.playlist_service", "com.example.common"])
class PlaylistServiceApplication

fun main(args: Array<String>) {
	runApplication<PlaylistServiceApplication>(*args)
}
