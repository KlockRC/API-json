package api

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import scala.io.StdIn


object Main {
def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem[Any] = ActorSystem(Behaviors.empty, "my-system")
    
    implicit val executionContext = system.executionContext
    val route = Routes.getRoutes

    val bindingFuture = Http().newServerAt("localhost", 9090).bind(route)

    println(s"Server now online. Please navigate to\nhttp://localhost:9090/produtos\n" +
      s"http://localhost:9090/reviews\n" +
      s"http://localhost:9090/clientes\n" +
      s"http://localhost:9090/itens\n" +
      s"http://localhost:9090/pagamentos\n" +
      s"http://localhost:9090/pedidos\n" +
      s"http://localhost:9090/vendedores\n" +
      s"Press RETURN to stop...")
    KafkaProducerApp.produtosKafka(CsvReader.lerProdutos("data/produtos.csv"))
    KafkaProducerApp.clientesKafka(CsvReader.lerClientes("data/clientes.csv"))
    KafkaProducerApp.itemKafka(CsvReader.lerItens("data/itens.csv"))
    KafkaProducerApp.pagamentoKafka(CsvReader.lerPagamentos("data/pagamentos.csv"))
    KafkaProducerApp.pedidoKafka(CsvReader.lerPedidos("data/pedidos.csv"))
    KafkaProducerApp.reviewKafka(CsvReader.lerReviews("data/reviews.csv"))
    KafkaProducerApp.vendedorKafka(CsvReader.lerVendedores("data/vendedores.csv"))
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate()) 
  }
}