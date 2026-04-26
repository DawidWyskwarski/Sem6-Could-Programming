package com.example.file_service.infrastructure.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client

@Configuration
class AwsConfig {

    @Bean
    fun s3Client(
        @Value("\${aws.accessKeyId}") accessKeyId: String,
        @Value("\${aws.secretAccessKey}") secretAccessKey: String,
        @Value("\${aws.sessionToken}") sessionToken: String,
        @Value("\${aws.region}") region: String
    ): S3Client {
        val credentials = AwsSessionCredentials.create(accessKeyId, secretAccessKey, sessionToken)
        return S3Client.builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build()
    }
}