import com.rabbitmq.client.*
import java.nio.charset.StandardCharsets


const val EXCHANGE_NAME: String = "whatsupdoc"
const val CONNECTION: String = "amqp://guest:guest@localhost:5672/"
const val TAG: String = ""


fun main(args: Array<String>) {
    val factory = ConnectionFactory()
    val connection = factory.newConnection(CONNECTION)
    val channel = connection?.createChannel()
    channel?.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT)

    val queueName = channel?.queueDeclare()?.queue
    channel?.queueBind(queueName, EXCHANGE_NAME, "")

    println("Waiting for messages...")
    val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
        val message = String(delivery.body, StandardCharsets.UTF_8)
        println("[$consumerTag] Received message: '$message'")
    }
    val cancelCallback = CancelCallback { consumerTag: String? ->
        println("[$consumerTag] was canceled")
    }

    val tag: String? = channel?.basicConsume(queueName, true, TAG, deliverCallback, cancelCallback)
}