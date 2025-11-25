package com.bau.merenda.repository;

import com.bau.merenda.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UsuarioRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Usuario usuario) {
        Query query = em.createNativeQuery("INSERT INTO usuario (usuario, senha, role) " +
                "VALUES (:usuario, :senha, :role)");
        query.setParameter("usuario", usuario.getUsuario());
        query.setParameter("senha", usuario.getSenha());
        query.setParameter("role", usuario.getRole());
        query.executeUpdate();
    }

    @Transactional
    public void update(Usuario usuario) {
        Query query = em.createNativeQuery("UPDATE usuario SET usuario = :usuario, senha = :senha, role = :role " +
                "WHERE id = :id");
        query.setParameter("usuario", usuario.getUsuario());
        query.setParameter("senha", usuario.getSenha());
        query.setParameter("role", usuario.getRole());
        query.setParameter("id", usuario.getId());
        query.executeUpdate();
    }

    public List<Usuario> findAll() {
        Query query = em.createNativeQuery("SELECT * FROM usuario", Usuario.class);
        return query.getResultList();
    }

    public Usuario findById(Long id) {
        Query query = em.createNativeQuery("SELECT * FROM usuario WHERE id = :id", Usuario.class);
        query.setParameter("id", id);
        return (Usuario) query.getSingleResult();
    }

    public Usuario findByUsuario(String usuario) {
        Query query = em.createNativeQuery("SELECT * FROM usuario WHERE usuario = :usuario", Usuario.class);
        query.setParameter("usuario", usuario);
        List<Usuario> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
