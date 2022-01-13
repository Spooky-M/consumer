package consumer

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Consumer
import com.rabbitmq.client.Envelope
import com.rabbitmq.client.ShutdownSignalException

// Trenutno se ne koristi al ostavljam kod for future reference
class ConsumerImpl : Consumer {

    override fun handleConsumeOk(consumerTag: String?) {
        println("Consumer: consume OK")
    }

    override fun handleCancelOk(consumerTag: String?) {
        println("Consumer: cancel OK")
    }

    override fun handleCancel(consumerTag: String?) {
        println("Consumer: cancel")
    }

    override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) {
        println("Consumer: shutdown signal")
    }

    override fun handleRecoverOk(consumerTag: String?) {
        println("Consumer: recover OK")
    }

    override fun handleDelivery(
        consumerTag: String?,
        envelope: Envelope?,
        properties: AMQP.BasicProperties?,
        body: ByteArray?
    ) {
        val message = body?.toString(charset("UTF-8"))
        println(" [x] Received '$message'")
    }

}