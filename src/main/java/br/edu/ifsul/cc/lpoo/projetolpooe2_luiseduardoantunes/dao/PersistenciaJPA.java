/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.cc.lpoo.projetolpooe2_luiseduardoantunes.dao;

import br.edu.ifsul.cc.lpoo.projetolpooe2_luiseduardoantunes.model.Bibliotecario;
import br.edu.ifsul.cc.lpoo.projetolpooe2_luiseduardoantunes.model.Cliente;
import br.edu.ifsul.cc.lpoo.projetolpooe2_luiseduardoantunes.model.Reserva;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class PersistenciaJPA implements InterfacePersistencia {

    public EntityManagerFactory factory;    
    public EntityManager entity;            

    public PersistenciaJPA() {
        factory = Persistence.createEntityManagerFactory("ProjetoLPOOE2_LuisEduardoAntunes");
        entity = factory.createEntityManager();
    }

    @Override
    public Boolean conexaoAberta() {
        if (entity == null || !entity.isOpen()) {
            entity = factory.createEntityManager();
        }
        return entity.isOpen();
    }

    @Override
    public void fecharConexao() {
        if (entity != null && entity.isOpen()) {
            entity.close();
        }
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        EntityManager em = getEntityManager();
        return em.find(c, id);//encontra um determinado registro 
    }

    public void persist(Object o) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(o);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    /*
    Todos os métodos agora chamam getEntityManager() para garantir que o EntityManager esteja sempre aberto e pronto para uso.
     */
    public EntityManager getEntityManager() {
        if (entity == null || !entity.isOpen()) {
            entity = factory.createEntityManager();
        }
        return entity;
    }

    @Override
    public void remover(Object o) throws Exception {
//        No método remover, antes de chamar remove, usamos merge se o objeto não estiver gerenciado.
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(o)) {
                o = em.merge(o); // Anexa o objeto ao contexto de persistência, se necessário
            }
            em.remove(o);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
    
    public void merge(Object o) throws Exception {
    entity.getTransaction().begin();
    entity.merge(o);
    entity.getTransaction().commit();
}

    public List<Cliente> getClientes(String texto) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Cliente> query = em.createQuery("SELECT m FROM Cliente m WHERE LOWER(m.nome) Like :nome", Cliente.class);
            query.setParameter("nome", "%"+texto.toLowerCase()+"%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Bibliotecario> getBibliotecarios(String texto) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Bibliotecario> query = em.createQuery("SELECT m FROM Bibliotecario m WHERE LOWER(m.nome) Like :nome", Bibliotecario.class);
            query.setParameter("nome", "%"+texto.toLowerCase()+"%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<Reserva> getReservas(String texto) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Reserva> query = em.createQuery("SELECT m FROM Reserva m WHERE LOWER(m.livroReservado) Like :livroReservado", Reserva.class);
            query.setParameter("livroReservado", "%"+texto.toLowerCase()+"%");
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
