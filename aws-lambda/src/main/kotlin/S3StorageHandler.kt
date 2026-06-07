import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest

class S3StorageHandler(
    private val s3Client: S3Client,
    private val bucket: String
) {

    fun save(name: String, qrBytes: ByteArray): String {
        val key = if (name.endsWith(".png")) name else "$name.png"

        val request = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .contentType("image/png")
            .build()

        s3Client.putObject(request, RequestBody.fromBytes(qrBytes))

        return getS3Url(key)
    }

    fun getS3Url(resourceName: String): String {
        return "https://$bucket.s3.amazonaws.com/$resourceName"
    }
}