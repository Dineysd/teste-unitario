package br.teste.locacao.servicos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.teste.locacao.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void test() {
		
		assertTrue(true);// para verdadeiro
		
		assertFalse(false);// para falso
		
		assertEquals(1, 1);// se for igual
		
		assertEquals(0.322, 0.3225, 0.001); //verificando double com 0.01 *delta de comparação 
		assertEquals(Math.PI, 3.14, 0.01);  // delta é uma margen de erro de comparação
		
		int i = 5;
		Integer i2 = 5;
		
		//assertEquals(i, i2); //não funciona
		assertEquals(Integer.valueOf(i), i2); // tenho que converter tipo primitivo para objeto
		assertEquals(i, i2.intValue()); // ou objeto para tipo primitivo
		
		assertEquals("carlos", "carlos"); // so funciona se for iguais sem letras maiusculda ou minusculas
		assertNotEquals("carlos", "carlo");
		assertTrue("carlos".equalsIgnoreCase("Carlos"));// caso queira iguinorar as letra maiuscula
		assertTrue("carlos".startsWith("ca"));
		
		Usuario u1 = new Usuario("Sandra");
		Usuario u2 = new Usuario("Sandra");
		Usuario u3 = null;
		
		assertEquals(u1, u2);// esse assert só funcionara se estiver o metodo equals hashCode
		                     // na Classe Usuario
		
		assertSame(u2, u2); // compara objetos iguais da mesma instancia
		assertNotSame(u1, u2);
		
		assertNull(u3);
		assertNotNull(u2);
		
				
	}

}
