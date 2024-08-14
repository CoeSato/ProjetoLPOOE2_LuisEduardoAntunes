/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsul.cc.lpoo.projetolpooe2_luiseduardoantunes.test;

import br.edu.ifsul.cc.lpoo.projetolpooe2_luiseduardoantunes.dao.PersistenciaJPA;
import br.edu.ifsul.cc.lpoo.projetolpooe2_luiseduardoantunes.model.Bibliotecario;
import br.edu.ifsul.cc.lpoo.projetolpooe2_luiseduardoantunes.model.Cliente;
import br.edu.ifsul.cc.lpoo.projetolpooe2_luiseduardoantunes.model.Reserva;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestePersistenciaObjetos {
    PersistenciaJPA jpa = new PersistenciaJPA();
    
    public TestePersistenciaObjetos() {
    }
    
    @Before
    public void setUp() {
        jpa.conexaoAberta();
    }
    
    @After
    public void tearDown() {
        jpa.fecharConexao();
    }
    
    @Test
    public void test() throws Exception{
        Calendar currentCalendar = Calendar.getInstance();
        Calendar newCalendar = Calendar.getInstance();
        
        Cliente c = new Cliente();
        c.setNome("Robisvaldo Franco");
        c.setEndereco("São José, 308");
        c.setEmail("robisvaldo.franco@gmail.com");
        c.setTelefone("9999999999");
         
        Bibliotecario b = new Bibliotecario();
        b.setNome("Marilurde Pierre");
        b.setSalario(2500.00);
        b.setTelefone("8888888888");
        
        int dia = 01;
        int mes = 02;
        int ano = 2003;
        newCalendar.set(ano, mes, dia);
        b.setDataAdmissao(Calendar.getInstance());
        
        Reserva r = new Reserva();
        r.setLivroReservado("Apostila Java I");
        r.setBibliotecario(b);
        r.setCliente(c);
        
        dia = currentCalendar.get(Calendar.DAY_OF_MONTH);
        mes = currentCalendar.get(Calendar.MONTH);
        ano = currentCalendar.get(Calendar.YEAR);
        newCalendar.set(ano, mes, dia);
        r.setDataInicio(newCalendar);
        
        dia = currentCalendar.get(Calendar.DAY_OF_MONTH+7);
        mes = currentCalendar.get(Calendar.MONTH);
        ano = currentCalendar.get(Calendar.YEAR);
        newCalendar.set(ano, mes, dia);
        r.setDataFim(newCalendar);
        
        jpa.persist(c);
        jpa.persist(b);
        jpa.persist(r);  
    }
    
}
