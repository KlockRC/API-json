package models

import io.circe.{Encoder, Decoder}
import io.circe.generic.semiauto._

case class Pedido(
    order_id: String,
    customer_id: String,
    order_status: String,
    order_purchase_timestamp: String,
    order_approved_at: String,
    order_delivered_carrier_date: String,
    order_delivered_customer_date: String,
    order_estimated_delivery_date: String
)

object Pedido {
    implicit var pedidoEncoder: Encoder[Pedido] = deriveEncoder[Pedido]
    implicit var pedidoDecoder: Decoder[Pedido] = deriveDecoder[Pedido]
}