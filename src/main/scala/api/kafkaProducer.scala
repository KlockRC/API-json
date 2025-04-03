package api
import api.csvreader
import models.Produto
import io.circe.syntax._
import scala.util.Random
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Kafkaproducer {
    def produtos(Produto: List[Produto] ): Future[Unit] = Future {
    for (i <- Produto) {
        var tempo = Random.between(1000, 3500)
        var teste = i.asJson.noSpaces
        println("teste: " + teste)
        Thread.sleep(tempo)
        }
     }
}


