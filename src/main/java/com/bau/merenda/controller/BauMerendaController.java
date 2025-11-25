package com.bau.merenda.controller;

import com.bau.merenda.model.Usuario;
import com.bau.merenda.model.EscolhaAluno;
import com.bau.merenda.model.EscolhaDTO;
import com.bau.merenda.model.Item;
import com.bau.merenda.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bau.merenda.service.EscolhaAlunoService;
import com.bau.merenda.service.ItemService;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class BauMerendaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private EscolhaAlunoService escolhaAlunoService;

    // ================================
    // LOGIN / CADASTRO
    // ================================
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioService.login(usuario.getUsuario(), usuario.getSenha());
            return "redirect:/cardapio";
        } catch (RuntimeException e) {
            model.addAttribute("erro", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/cadastro")
    public String cadastroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String salvarUsuario(@ModelAttribute Usuario usuario, Model model) {

        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            model.addAttribute("erro", "O campo 'Senha' não pode ser vazio.");
            return "cadastro";
        }

        if (!Objects.equals(usuario.getSenha(), usuario.getConfirmaSenha())) {
            model.addAttribute("erro", "As senhas não coincidem!");
            return "cadastro";
        }

        usuarioService.cadastrar(usuario.getUsuario(), usuario.getSenha());
        return "redirect:/login";
    }

    // ================================
    // PAINEL MERENDEIRA (CARDÁPIO)
    // ================================
    @GetMapping("/cardapio")
    public String paginaCardapio(Model model) {
    
        // Itens cadastrados para o cardápio (já existia)
        List<Item> itens = itemService.listarTudo();
        model.addAttribute("itens", itens);
    
        // Demandas dos alunos (NOVO)
        List<EscolhaAluno> demandas = escolhaAlunoService.listarTodos();
        model.addAttribute("demandas", demandas);
    
  // Dados agrupados por item (para gráfico 1)
Map<String, Integer> demandaPorItem = escolhaAlunoService.contarPorItem();
model.addAttribute("demandaPorItem", demandaPorItem);

// Dados agrupados por período (para gráfico 2)
Map<String, Integer> demandaPorPeriodo = escolhaAlunoService.contarPorPeriodo();
model.addAttribute("demandaPorPeriodo", demandaPorPeriodo);
        // Mantém o objeto item para o formulário
        model.addAttribute("item", new Item());
    
        return "cardapio";
    }
    
    @PostMapping("/cardapio")
    public String adicionarItem(@ModelAttribute("item") Item item) {
        itemService.salvar(item);
        return "redirect:/cardapio";
    }

    @GetMapping("/cardapio/remover/{id}")
    public String removerItem(@PathVariable Long id) {
        itemService.deletar(id);
        return "redirect:/cardapio";
    }

    @PostMapping("/cardapio/zerar")
    public String zerarDia(RedirectAttributes redirectAttributes) {
    
        // Zera os itens do cardápio
        itemService.limparCardapio();
    
        // ZERA também as demandas dos alunos
        escolhaAlunoService.zerarDia();
    
        redirectAttributes.addFlashAttribute("mensagem", "Cardápio e demandas zerados com sucesso!");
        return "redirect:/cardapio";
    }
    

    @GetMapping("/logout")
    public String sair(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ================================
    // TELA DO ALUNO — MOSTRAR CARDÁPIO
    // ================================
   @GetMapping("/aluno/cardapio")
public String cardapioAluno(Model model) {
    List<Item> itens = itemService.listarTudo();

    ObjectMapper mapper = new ObjectMapper();
    try {
        model.addAttribute("itensJson", mapper.writeValueAsString(itens));
    } catch (Exception e) {
        model.addAttribute("itensJson", "[]");
    }

    model.addAttribute("escolhaAluno", new EscolhaAluno());
    return "aluno";
}

@PostMapping("/aluno/confirmar")
@ResponseBody
public String confirmarEscolhas(@RequestBody List<EscolhaDTO> escolhas) {

    for (EscolhaDTO dto : escolhas) {

        // Busca o item pelo ID enviado no JSON
        Item item = itemService.getById(dto.getItemId());
        if (item == null) {
            throw new RuntimeException("Item não encontrado: ID = " + dto.getItemId());
        }

        // Cria o objeto que será salvo no banco
        EscolhaAluno escolha = new EscolhaAluno();
        escolha.setTurno(dto.getTurno());
        escolha.setQuantidade(dto.getQuantidade());
        escolha.setItem(item);
        escolha.setDataHora(LocalDateTime.now());

        escolhaAlunoService.salvar(escolha);
    }

    return "OK";
}

    

    // ================================
    // MERENDEIRA VÊ DEMANDA
    // ================================
    @GetMapping("/demanda")
    public String mostrarDemanda(Model model) {
        model.addAttribute("demandas", escolhaAlunoService.listarTodos());
        return "demanda";
    }

}
