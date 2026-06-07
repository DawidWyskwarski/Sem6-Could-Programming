import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import qrcode.QRCode
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.s3.S3Client
import kotlin.time.Clock
import kotlin.time.DurationUnit
import kotlin.time.Instant
import kotlin.time.toDuration

private const val MAX_URL_LENGTH = 150
private const val EXPIRY_MINUTES = 15L

class QrCodeHandler : RequestHandler<String, String> {

    private val region = Region.of(System.getenv("AWS_REGION"))

    private val s3Client = S3Client.builder()
        .region(region)
        .build()

    private val dynamoDbClient = DynamoDbClient.builder()
        .region(region)
        .build()

    private val s3Handler = S3StorageHandler(
        s3Client,
        System.getenv("S3_BUCKET_NAME")
    )

    private val dynamoDbRepository = DynamoDbRepository(
        dynamoDbClient,
        System.getenv("DYNAMODB_TABLE_NAME"),
    )

    override fun handleRequest(url: String, ctx: Context): String {

        validateUrl(url)

        val hashedUrl = url.hashCode().toString()
        val now: Instant = Clock.System.now()

        val qrCodeMetadata = dynamoDbRepository.getQrCodeMetadata(url)

        if (qrCodeMetadata != null && qrCodeMetadata.expiresAt > now) {
            return s3Handler.getS3Url(qrCodeMetadata.fileName + "." + qrCodeMetadata.fileExtension)
        }

        val qrCode = QRCode
            .ofSquares()
            .build(url)
            .renderToBytes()

        val s3Url = s3Handler.save(
            name = hashedUrl,
            qrBytes = qrCode
        )

        dynamoDbRepository.saveQrCodeMetadata(
            QrCodeMetadata(
                url = url,
                fileName = hashedUrl,
                fileExtension = "png",
                fileSize = qrCode.size.toLong(),
                createdAt = now,
                expiresAt = now.plus(EXPIRY_MINUTES.toDuration(DurationUnit.MINUTES) ),
            )
        )

        return s3Url
    }

    fun validateUrl(url: String) {
        require(url.isNotBlank()) { "URL must not be blank" }

        require(url.startsWith("http://") || url.startsWith("https://")) {
            "URL has to start with http:// or https://"
        }

        require(url.length <= MAX_URL_LENGTH) {
            "URL must be shorter than $MAX_URL_LENGTH characters"
        }

    }
}