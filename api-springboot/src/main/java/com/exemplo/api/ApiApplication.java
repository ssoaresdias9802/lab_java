package com.exemplo.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class ApiApplication implements CommandLineRunner {

    private final ProdutoController produtoController;

    public ApiApplication(ProdutoController produtoController) {
        this.produtoController = produtoController;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n===== MENU PRODUTOS =====");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Buscar produto por ID");
            System.out.println("3 - Cadastrar produto");
            System.out.println("4 - Atualizar produto");
            System.out.println("5 - Remover produto");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    if (produtoController.listar().isEmpty()) {
                        System.out.println("Nenhum produto cadastrado.");
                    } else {
                        for (Produto p : produtoController.listar()) {
                            System.out.println("ID: " + p.getId());
                            System.out.println("Nome: " + p.getNome());
                            System.out.println("Preço: R$ " + p.getPreco());
                            System.out.println("----------------------");
                        }
                    }
                    break;

                case 2:
                    System.out.print("Digite o ID: ");
                    int idBusca = scanner.nextInt();

                    Produto produtoEncontrado = produtoController.buscar(idBusca);
                    System.out.println(produtoEncontrado != null ? produtoEncontrado : "Produto não encontrado!");
                    break;

                case 3:
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();

                    System.out.print("Preço: ");
                    double preco = scanner.nextDouble();

                    Produto novoProduto = new Produto(id, nome, preco);
                    produtoController.adicionar(novoProduto);

                    System.out.println("Produto cadastrado com sucesso!");
                    break;

                case 4:
                    System.out.print("Digite o ID do produto que deseja atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();

                    System.out.print("Novo preço: ");
                    double novoPreco = scanner.nextDouble();

                    Produto produtoAtualizado = new Produto(idAtualizar, novoNome, novoPreco);
                    Produto resultado = produtoController.atualizar(idAtualizar, produtoAtualizado);

                    if (resultado != null) {
                        System.out.println("\n=== PRODUTO ATUALIZADO ===");
                        System.out.println("ID: " + resultado.getId());
                        System.out.println("Nome: " + resultado.getNome());
                        System.out.println("Preço: R$ " + resultado.getPreco());
                        System.out.println("==========================");
                    } else {
                        System.out.println("Produto não encontrado!");
                    }
                    break;

                case 5:
                    System.out.print("Digite o ID do produto que deseja remover: ");
                    int idRemover = scanner.nextInt();

                    String mensagem = produtoController.remover(idRemover);
                    System.out.println(mensagem);
                    break;

                case 0:
                    System.out.println("Encerrando o menu...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }
}