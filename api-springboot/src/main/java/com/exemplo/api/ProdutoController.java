package com.exemplo.api;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private List<Produto> produtos = new ArrayList<>();

    public ProdutoController() {
        produtos.add(new Produto(1, "Mouse", 50.0));
        produtos.add(new Produto(2, "Teclado", 120.0));
    }

    @GetMapping
    public List<Produto> listar() {
        return produtos;
    }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public Produto adicionar(@RequestBody Produto produto) {
        produtos.add(produto);
        return produto;
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable int id, @RequestBody Produto produtoAtualizado) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                p.setNome(produtoAtualizado.getNome());
                p.setPreco(produtoAtualizado.getPreco());
                return p;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String remover(@PathVariable int id) {
        produtos.removeIf(p -> p.getId() == id);
        return "Removido!";
    }
}