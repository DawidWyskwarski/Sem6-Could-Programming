package com.example.file_service.infrastructure.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class AwsConfig {

    @Bean
    fun s3Client(
        @Value("\${aws.accessKeyId:#{null}}") accessKeyId: String?,
        @Value("\${aws.secretAccessKey:#{null}}") secretAccessKey: String?,
        @Value("\${aws.sessionToken:#{null}}") sessionToken: String?,
        @Value("\${aws.region}") region: String
    ): S3Client {
        val clientBuilder = S3Client.builder().region(Region.of(region))

        if (!accessKeyId.isNullOrBlank() && !secretAccessKey.isNullOrBlank() && !sessionToken.isNullOrBlank()) {
            val credentials = AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken)
            clientBuilder.credentialsProvider(StaticCredentialsProvider.create(credentials))
        } else {
            clientBuilder.credentialsProvider(DefaultCredentialsProvider.create())
        }

        return clientBuilder.build()
    }
}