package com.bau.merenda.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bau.merenda.model.EscolhaAluno;
import com.bau.merenda.repository.EscolhaAlunoRepository;

@Service
public class EscolhaAlunoService {

    @Autowired
    private EscolhaAlunoRepository repo;

    public void salvar(EscolhaAluno escolha) {
        repo.save(escolha);
    }

    public List<EscolhaAluno> listarTodos() {
        return repo.findAll();
    }

    public void zerarDia() {
        repo.deleteAll();
    }

    // ========================================================
    // CONTAGEM POR ITEM (para o painel da merendeira)
    // ========================================================
    public Map<String, Integer> contarPorItem() {
        Map<String, Integer> mapa = new HashMap<>();

        List<EscolhaAluno> lista = repo.findAll();
        for (EscolhaAluno e : lista) {
            if (e.getItem() != null) {
                String nomeItem = e.getItem().getNome();
                mapa.put(nomeItem, mapa.getOrDefault(nomeItem, 0) + e.getQuantidade());
            }
        }
        return mapa;
    }

    // ========================================================
    // CONTAGEM POR PERÍODO / TURNO
    // ========================================================
    public Map<String, Integer> contarPorPeriodo() {
        Map<String, Integer> mapa = new HashMap<>();

        List<EscolhaAluno> lista = repo.findAll();
        for (EscolhaAluno e : lista) {
            String turno = e.getTurno();
            mapa.put(turno, mapa.getOrDefault(turno, 0) + e.getQuantidade());
        }
        return mapa;
    }

   
}
