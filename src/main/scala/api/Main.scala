package api

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import scala.io.StdIn


object main {

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
    KafkaProducerApp.produtosKafka(csvreader.lerProdutos("data/produtos.csv"))
    KafkaProducerApp.clientesKafka(csvreader.lerClientes("data/clientes.csv"))
    KafkaProducerApp.itemKafka(csvreader.lerItens("data/itens.csv"))
    KafkaProducerApp.pagamentoKafka(csvreader.lerPagamentos("data/pagamentos.csv"))
    KafkaProducerApp.pedidoKafka(csvreader.lerPedidos("data/pedidos.csv"))
    KafkaProducerApp.reviewKafka(csvreader.lerReviews("data/reviews.csv"))
    KafkaProducerApp.vendedorKafka(csvreader.lerVendedores("data/vendedores.csv"))
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate()) 
  }
}