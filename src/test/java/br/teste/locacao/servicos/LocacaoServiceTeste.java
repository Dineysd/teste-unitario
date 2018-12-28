package br.teste.locacao.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.teste.locacao.entidades.Carro;
import br.teste.locacao.entidades.Filme;
import br.teste.locacao.entidades.Locacao;
import br.teste.locacao.entidades.Produto;
import br.teste.locacao.entidades.Usuario;
import br.teste.locacao.exceptions.LocadoraException;
import br.teste.locacao.exceptions.ProdutoSemEstoqueException;
import br.teste.locacao.utils.DataUtils;

public class LocacaoServiceTeste {

	LocacaoService service;
	@Rule
	public ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {//antes
		service = new LocacaoService();
		System.out.println("Before");
	}
	
	@After
	public void tearDown() {//Depois
		System.out.println("After");
	}
	
	@BeforeClass
	public static void setupClass() {//antes de todas as classe ser instanciada
		System.out.println("BeforeClass");
	}
	
	@AfterClass
	public static void tearDownClass() {//Depois do utimo teste finalizado
		System.out.println("AfterClass");
	}

	@Test
	public void testeLocacao() throws Exception {

		// Cenario onde inicilizaremos as variaveis ou objetos
		System.out.println("teste 1");
		Locacao locacao = new Locacao();
		Produto filme = new Filme("A Mumia", 1, 5.00);
		Usuario usuario = new Usuario("Carlos");

		// Ação onde invocaremos o metodo que queremos testar

		locacao = service.alugar(usuario, filme);
	
		// validação onde vamos verificar se o metodo da ação esta de acordo com o
		// cenario especificado
		// ou resultado de acordo com esperado

		error.checkThat(locacao.getUsuario().getNome(), is("Carlos"));// verifique que o nome do usuario é Carlos
		assertThat(locacao.getValor(), is(equalTo(5.0)));// verifique se o valor é igual a 5.00
		error.checkThat(locacao.getValor(), is(not(8.0)));// verifique se o valor não é a 8.00

		Assert.assertEquals("usuario corresponde ao mesmo nome: ", "Carlos", locacao.getUsuario().getNome());
		assertEquals("Produto corresponde ao mesmo nome: ", "A Mumia", locacao.getProduto().getNome());
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));
		Assert.assertEquals("valor do Produto corresponde ao mesmo valor: ", 5.00, locacao.getValor(), 0.01);

	}

	// Metodo Teste Elegante
	@Test(expected = ProdutoSemEstoqueException.class)
	public void testeLocacao_produtoSemEstoque() throws Exception {

		// Cenario onde inicilizaremos as variaveis ou objetos
		Locacao locacao = new Locacao();
		Produto carro = new Carro("cruze", 0, 75.00);
		Usuario usuario = new Usuario("Carlos");

		// Ação onde invocaremos o metodo que queremos testar

		locacao = service.alugar(usuario, carro);
	}

	//forma robusta
	@Test
	public void testeLocacao_usuarioVazio() throws ProdutoSemEstoqueException {
		// Cenario
		Produto filme = new Filme("A Mumia II", 1, 5.00);
		// Ação 
		try {
			Locacao locacao = service.alugar(null, filme);
			fail("Deveria lançar uma exception!");
		} catch (LocadoraException e) {
			assertThat(e.getMessage(),is("Usuario vazio!"));
		}

	}
	
	// Metodo de teste com nova solução
		@Test
		public void testeLocacao_produtoVazio() throws ProdutoSemEstoqueException, LocadoraException {

			// Cenario onde inicilizaremos as variaveis ou objetos
			Usuario usuario = new Usuario("Carlos");

			exception.expect(LocadoraException.class );
			exception.expectMessage("Produto vazio!");
			
			// Ação onde invocaremos o metodo que queremos testar
			Locacao locacao = service.alugar(usuario, null);
		}

}	
