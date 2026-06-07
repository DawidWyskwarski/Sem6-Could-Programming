import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import kotlin.time.Clock
import kotlin.time.Instant

data class QrCodeMetadata(
    val url: String,
    val fileName: String,
    val fileExtension: String,
    val fileSize: Long,
    val createdAt: Instant = Clock.System.now(),
    val expiresAt: Instant,
)

class DynamoDbRepository(
    private val client: DynamoDbClient,
    private val tableName: String,
) {

    fun getQrCodeMetadata(url: String): QrCodeMetadata? {
        val request = GetItemRequest.builder()
            .tableName(tableName)
            .key(mapOf("url" to AttributeValue.builder().s(url).build()))
            .build()

        val result = client.getItem(request)

        if (!result.hasItem() || result.item().isEmpty()) return null

        val item = result.item()

        return QrCodeMetadata(
            url = item.str("url"),
            fileName = item.str("fileName"),
            fileExtension = item.str("fileExtension"),
            fileSize = item.str("fileSize").toLong(),
            createdAt = Instant.parse(item.str("createdAt")),
            expiresAt = Instant.parse(item.str("expiresAt")),
        )
    }

    fun saveQrCodeMetadata(metadata: QrCodeMetadata) {
        val request = PutItemRequest.builder()
            .tableName(tableName)
            .conditionExpression("attribute_not_exists(#u)")
            .expressionAttributeNames(mapOf("#u" to "url"))
            .item(mapOf(
                "url" to attr(metadata.url),
                "fileName" to attr(metadata.fileName),
                "fileExtension" to attr(metadata.fileExtension),
                "fileSize" to attr(metadata.fileSize.toString()),
                "createdAt" to attr(metadata.createdAt.toString()),
                "expiresAt" to attr(metadata.expiresAt.toString()),
            ))
            .build()

        client.putItem(request)
    }

    private fun attr(value: String) = AttributeValue.builder().s(value).build()
    private fun Map<String, AttributeValue>.str(key: String) =
        this[key]?.s() ?: error("Missing DynamoDB attribute: $key")
}