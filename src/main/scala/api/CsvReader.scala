package api
import models.{Produto, Review, Cliente, Pagamento, Pedido, Item,Vendedor}
import scala.io.Source
import scala.util.{Try, Using}
import org.slf4j.LoggerFactory

val logger = LoggerFactory.getLogger(getClass)

object CsvReader {
  def lerProdutos(caminho: String): List[Produto] = {
    Using(Source.fromFile(caminho)) { file =>
      val linhas = file.getLines().drop(1).toList
      logger.info(s"Número de linhas lidas: ${linhas.size}")
      linhas.take(5).foreach(linha => logger.info(s"Linha: $linha"))

      linhas.flatMap { linha =>
        val colunas = linha.split(",").map(_.trim.replace("\"", ""))
        Try(Produto(colunas(0), colunas(1), colunas(2), colunas(3), colunas(4), colunas(5), colunas(6), colunas(7), colunas(8))).toOption
      }
    }.getOrElse {
      logger.error(s"Erro ao ler o arquivo: $caminho")
      List.empty
    }
  }
  def lerReviews(caminho: String): List[Review] = {
    Using(Source.fromFile(caminho)) { file =>
      val linhas = file.getLines().drop(1).toList
      logger.info(s"Número de linhas lidas: ${linhas.size}")
      linhas.take(5).foreach(linha => logger.info(s"Linha: $linha"))

      linhas.flatMap { linha => 
        val colunas = linha.split(",").map(_.trim.replace("\"", ""))
        Try(Review(colunas(0), colunas(1), colunas(2), colunas(3), colunas(4), colunas(5), colunas(6))).toOption
      }
    }.getOrElse {
      logger.error(s"Erro ao ler o arquivo: $caminho")
      List.empty
    }
  }
  def lerClientes(caminho: String): List[Cliente] = {
     Using(Source.fromFile(caminho)) { file =>
      val linhas = file.getLines().drop(1).toList
      logger.info(s"Número de linhas lidas: ${linhas.size}")
      linhas.take(5).foreach(linha => logger.info(s"Linha: $linha"))

      linhas.flatMap { linha =>
        val colunas = linha.split(",").map(_.trim.replace("\"", ""))
        Try(Cliente(colunas(0), colunas(1), colunas(2), colunas(3), colunas(4))).toOption
        }
      }.getOrElse{
        logger.error(s"Erro ao ler o arquivo: $caminho")
        List.empty
      }
    }
    def lerItens(caminho: String): List[Item] = {
      Using(Source.fromFile(caminho)) { file =>
       val linhas = file.getLines().drop(1).toList
       logger.info(s"Número de linhas lidas: ${linhas.size}")
       linhas.take(5).foreach(linha => logger.info(s"Linha: $linha"))
       linhas.flatMap { linha =>
        val colunas = linha.split(",").map(_.trim.replace("\"", ""))
        Try(Item(colunas(0), colunas(1), colunas(2), colunas(3), colunas(4), colunas(5), colunas(6))).toOption
        } 
      }.getOrElse{
        logger.error(s"Erro ao ler o arquivo: $caminho")
        List.empty
      }
    }
    def lerPagamentos(caminho: String): List[Pagamento] = {
      Using(Source.fromFile(caminho)) { file =>
        val linhas = file.getLines().drop(1).toList
        logger.info(s"Número de linhas lidas: ${linhas.size}")
        linhas.take(5).foreach(linha => logger.info(s"Linha: $linha"))
        linhas.flatMap { linha =>
          val colunas = linha.split(",").map(_.trim.replace("\"", ""))
          Try(Pagamento(colunas(0), colunas(1), colunas(2), colunas(3), colunas(4))).toOption
          }  
      }.getOrElse{
        logger.error(s"Erro ao ler o arquivo: $caminho")
        List.empty
      }
    }
    def lerPedidos(caminho: String): List[Pedido] = {
      Using(Source.fromFile(caminho)) { file =>
        val linhas = file.getLines().drop(1).toList
        logger.info(s"Número de linhas lidas: ${linhas.size}")
        linhas.take(5).foreach(linha => logger.info(s"Linha: $linha"))
        linhas.flatMap { linha =>
          val colunas = linha.split(",").map(_.trim.replace("\"", ""))
          Try(Pedido(colunas(0), colunas(1), colunas(2), colunas(3), colunas(4), colunas(5), colunas(6), colunas(7))).toOption
        }
      }.getOrElse {
        logger.error(s"Erro ao ler o arquivo: $caminho")
        List.empty
      }
    }
    def lerVendedores(caminho: String): List[Vendedor] = {
      Using(Source.fromFile(caminho)) { file =>
        val linhas = file.getLines().drop(1).toList
        logger.info(s"Número de linhas lidas: ${linhas.size}")
        linhas.take(5).foreach(linha => logger.info(s"Linha: $linha"))
        linhas.flatMap { linha =>
          val colunas = linha.split(",").map(_.trim.replace("\"", ""))
          Try(Vendedor(colunas(0), colunas(1), colunas(2), colunas(3))).toOption
        }
      }.getOrElse {
        logger.error(s"Erro ao ler o arquivo: $caminho")
        List.empty
      }
    }
  }