package api

import models.{Produto, Review, Cliente, Pagamento, Pedido, Item,Vendedor}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CsvReaderIntegrationSpec extends  AnyWordSpec with Matchers {
  "CsvReader-Produtos" should {
    "Ler Produtos de um arquivo CSV e Criar Objetos produtos" in {
      val caminho = "test_resources/test_produtos.csv"
      val produtos = CsvReader.lerProdutos(caminho)

      produtos should not be (empty)
      produtos.head shouldBe a [Produto]
      produtos.head.product_id should be ("1e9e8ef04dbcff4541ed26657ea517e5")
    }
    "retorna lista vazia e logar erro se o arquivo nao existir" in {
      val caminho = "test_resources/arquivo_inexistente.csv"
      val produtos = CsvReader.lerProdutos(caminho)
      produtos should be (empty)
    }
  }
  "CsvReader-Clientes" should {
    "Ler Clientes de um arquivo CSV e Criar Objetos clientes" in {
      val caminho = "test_resources/test_clientes.csv"
      val clientes = CsvReader.lerClientes(caminho)

      clientes should not be (empty)
      clientes.head shouldBe a [Cliente]
      clientes.head.customer_id should be("06b8999e2fba1a1fbc88172c00ba8bc7")
    }
    "retorna lista vazia e logar erro se o arquivo nao existir" in {
      val caminho = "test_resources/arquivo_inexistente.csv"
      val clientes = CsvReader.lerClientes(caminho)
      clientes should be(empty)
    }
  }
  "CsvReader-Items" should {
    "Ler Items de um arquivo CSV e Criar Objetos items" in {
      val caminho = "test_resources/test_items.csv"
      val items = CsvReader.lerItens(caminho)

      items should not be (empty)
      items.head shouldBe a [Item]
      items.head.order_id should be("00010242fe8c5a6d1ba2dd792cb16214")
    }
    "retorna lista vazia e logar erro se o arquivo nao existir" in {
      val caminho = "test_resources/arquivo_inexistente.csv"
      val items = CsvReader.lerItens(caminho)
      items should be(empty)
    }
  }
  "CsvReader-Pagamentos" should {
    "Ler Pagamentos de um arquivo CSV e Criar Objetos pagamentos" in {
      val caminho = "test_resources/test_pagamentos.csv"
      val pagamentos = CsvReader.lerPagamentos(caminho)

      pagamentos should not be (empty)
      pagamentos.head shouldBe a [Pagamento]
      pagamentos.head.order_id should be("b81ef226f3fe1789b1e8b2acac839d17")
    }
    "retorna lista vazia e logar erro se o arquivo nao existir" in {
      val caminho = "test_resources/arquivo_inexistente.csv"
      val pagamentos = CsvReader.lerPagamentos(caminho)
      pagamentos should be(empty)
    }
  }
  "CsvReader-Pedidos" should {
    "Ler Pedidos de um arquivo CSV e Criar Objetos pedidos" in {
      val caminho = "test_resources/test_pedidos.csv"
      val pedidos = CsvReader.lerPedidos(caminho)

      pedidos should not be (empty)
      pedidos.head shouldBe a [Pedido]
      pedidos.head.order_id should be("e481f51cbdc54678b7cc49136f2d6af7")
    }
    "retorna lista vazia e logar erro se o arquivo nao existir" in {
      val caminho = "test_resources/arquivo_inexistente.csv"
      val pedidos = CsvReader.lerPedidos(caminho)
      pedidos should be(empty)
    }
  }
  "CsvReader-Reviews" should {
    "Ler reviews de um arquivo CSV e Criar Objetos reviews" in {
      val caminho = "test_resources/test_reviews.csv"
      val reviews = CsvReader.lerReviews(caminho)

      reviews should not be (empty)
      reviews.head shouldBe a [Review]
      reviews.head.review_id should be("7bc2406110b926393aa56f80a40eba40")
    }
    "retorna lista vazia e logar erro se o arquivo nao existir" in {
      val caminho = "test_resources/arquivo_inexistente.csv"
      val reviews = CsvReader.lerReviews(caminho)
      reviews should be(empty)
    }
  }
  "CsvReader-Vendedores" should {
    "Ler vendedores de um arquivo CSV e Criar Objetos vendedores" in {
      val caminho = "test_resources/test_vendedores.csv"
      val vendedores = CsvReader.lerVendedores(caminho)

      vendedores should not be (empty)
      vendedores.head shouldBe a [Vendedor]
      vendedores.head.seller_id should be("3442f8959a84dea7ee197c632cb2df15")
    }
    "retorna lista vazia e logar erro se o arquivo nao existir" in {
      val caminho = "test_resources/arquivo_inexistente.csv"
      val vendedores = CsvReader.lerVendedores(caminho)
      vendedores should be(empty)
    }
  }
}