package com.bau.merenda.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bau.merenda.model.Item;
import com.bau.merenda.repository.ItemRepository;
import com.bau.merenda.repository.EscolhaAlunoRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repo;

    @Autowired
    private EscolhaAlunoRepository escolhaRepo;

    public Item getById(Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new RuntimeException("Item não encontrado: " + id));
    }

    public List<Item> listarTudo() {
        return repo.findAll();
    }

    // ✔ Salvar item no banco
    public void salvar(Item item) {
        repo.save(item);
    }

    // ✔ Deletar item do banco
    public void deletar(Long id) {
        repo.deleteById(id);
    }

    // ✔ Limpa todo o cardápio corretamente (removendo dependências primeiro)
    @Transactional
    public void limparCardapio() {
        escolhaRepo.deleteAll();  // apaga escolhas dos alunos
        repo.deleteAll();         // apaga os itens do cardápio
    }

    // ✔ Buscar item por ID (igual ao getById)
    public Item buscarPorId(Long itemId) {
        return repo.findById(itemId)
                   .orElseThrow(() -> new RuntimeException("Item não encontrado: " + itemId));
    }
}
