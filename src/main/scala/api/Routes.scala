package api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import io.circe.syntax._

object Routes {
    def getRoutes: Route = concat( 
        path("produtos") {
            get {
                val produtos= CsvReader.lerProdutos("data/produtos.csv")
                complete(produtos.asJson.noSpaces)
            }
        },
        path("reviews") {
            get {
                val reviews = CsvReader.lerReviews ("data/reviews.csv")
                complete(reviews.asJson.noSpaces)
            }
        },
        path("clientes") {
            get {
                val clientes = CsvReader.lerClientes ("data/clientes.csv")
                complete(clientes.asJson.noSpaces)
            }
        },
        path("itens") {
            get{
                val itens = CsvReader.lerItens ("data/itens.csv")
                complete(itens.asJson.noSpaces)
            }
        },
        path ("pagamentos") {
            get {
                val pagamentos = CsvReader.lerPagamentos ("data/pagamentos.csv")
                complete(pagamentos.asJson.noSpaces)
            }
        },
        path ("pedidos") {
            get {
                val pedidos = CsvReader.lerPedidos ("data/pedidos.csv")
                complete(pedidos.asJson.noSpaces)
            }
        },
        path ("vendedores") {
            get {
                val vendedores = CsvReader.lerVendedores ("data/vendedores.csv")
                complete(vendedores.asJson.noSpaces)
            }
        }
    )
}