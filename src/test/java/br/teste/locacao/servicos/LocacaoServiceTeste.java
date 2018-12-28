package br.teste.locacao.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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
	List<Produto> produtos;
	@Rule
	public ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {//antes
		service = new LocacaoService();
		produtos = new ArrayList<Produto>();
	}

	@Test
	public void deveAlugarProdutoComSucesso() throws Exception {

		// Cenario onde inicilizaremos as variaveis ou objetos
		
		Produto filme = new Filme("A Mumia", 1, 5.00);
		Produto filme2 = new Filme("Sem Limites", 2, 5.00);
		Usuario usuario = new Usuario("Carlos");
		produtos = Arrays.asList(filme, filme2);

		// A��o onde invocaremos o metodo que queremos testar

		Locacao locacao = service.alugar(usuario, produtos);
	
		// valida��o onde vamos verificar se o metodo da a��o esta de acordo com o
		// cenario especificado
		// ou resultado de acordo com esperado

		error.checkThat(locacao.getUsuario().getNome(), is("Carlos"));// verifique que o nome do usuario � Carlos
		assertThat(locacao.getValor(), is(equalTo(10.0)));// verifique se o valor � igual a 5.00
		error.checkThat(locacao.getValor(), is(not(8.0)));// verifique se o valor n�o � a 8.00

		Assert.assertEquals("usuario corresponde ao mesmo nome: ", "Carlos", locacao.getUsuario().getNome());
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));
		Assert.assertEquals("valor do Produto corresponde ao mesmo valor: ", 10.00, locacao.getValor(), 0.01);

	}

	// Metodo Teste Elegante
	@Test(expected = ProdutoSemEstoqueException.class)
	public void deveLan�arExcecao_AoAlugarProdutoSemEstoque() throws Exception {

		// Cenario onde inicilizaremos as variaveis ou objetos
		Locacao locacao = new Locacao();
		Produto carro = new Carro("Cruze", 0, 75.00);
		Produto carro2 = new Carro("Civic", 0, 85.00);
		Usuario usuario = new Usuario("Carlos");
		produtos = Arrays.asList(carro, carro2);

		// A��o onde invocaremos o metodo que queremos testar

		locacao = service.alugar(usuario, produtos);
	}

	//forma robusta
	@Test
	public void naoDeveAlugarProdutosSemUsuario() throws ProdutoSemEstoqueException {
		// Cenario
		
		Produto filme = new Filme("A Mumia II", 1, 5.00);
		Produto filme2 = new Filme("Sem Limites", 2, 5.00);
		Produto filme3 = new Filme("tropa de elite", 3, 5.00);
		produtos = Arrays.asList(filme, filme2, filme3);
		// A��o 
		try {
			Locacao locacao = service.alugar(null, produtos);
			fail("Deveria lan�ar uma exception!");
		} catch (LocadoraException e) {
			assertThat(e.getMessage(),is("Usuario vazio!"));
		}

	}
	
	// Metodo de teste com nova solu��o
		@Test
		public void naoDeveAlugarProdutoSemExistirProduto() throws ProdutoSemEstoqueException, LocadoraException {

			// Cenario onde inicilizaremos as variaveis ou objetos
			Usuario usuario = new Usuario("Carlos");

			exception.expect(LocadoraException.class );
			exception.expectMessage("Produto vazio!");
			
			// A��o onde invocaremos o metodo que queremos testar
			Locacao locacao = service.alugar(usuario, null);
		}
		
		@Test
		public void devePagar75PcQuandoForOTerceiroProduto() throws ProdutoSemEstoqueException, LocadoraException {
			//cenario
			Usuario usuario = new Usuario("Diney");
			Produto filme = new Filme("Vendedor de sonhos", 1, 6.00);
			Produto filme2 = new Filme("Superando Limites", 1, 6.00);
			Produto carro = new Carro("Porche Carrera", 1, 100.00);
			produtos = Arrays.asList(filme, filme2, carro);
			
			
			//A��o
			Locacao locacao = service.alugar(usuario, produtos);
			
			//Verifica��o
			
			assertThat(locacao.getValor(), is(87.0));;
		}
		
		@Test
		public void devePagar50PcQuandoForOQuartoProduto() throws ProdutoSemEstoqueException, LocadoraException {
			//cenario
			Usuario usuario = new Usuario("Diney");
			Produto filme = new Filme("Vendedor de sonhos", 1, 6.00);
			Produto filme2 = new Filme("Superando Limites", 1, 6.00);
			Produto carro = new Carro("Porche Carrera", 1, 100.00);
			Produto carro2 = new Carro("Lamborguine", 1, 200.00);
			produtos = Arrays.asList(filme, filme2, carro, carro2);
			
			
			//A��o
			Locacao locacao = service.alugar(usuario, produtos);
			
			//Verifica��o
			
			assertThat(locacao.getValor(), is(187.0));;
		}
		
		@Test
		public void devePagar25PcQuandoForOQuintoProduto() throws ProdutoSemEstoqueException, LocadoraException {
			//cenario
			Usuario usuario = new Usuario("Diney");
			Produto filme = new Filme("Vendedor de sonhos", 1, 6.00);
			Produto filme2 = new Filme("Superando Limites", 1, 6.00);
			Produto carro = new Carro("Porche Carrera", 1, 100.00);
			Produto carro2 = new Carro("Lamborguine", 1, 200.00);
			Produto filme3 = new Filme("Vendedor de sonhos II", 2, 6.00);
			produtos = Arrays.asList(filme, filme2, carro, carro2, filme3);
			
			
			//A��o
			Locacao locacao = service.alugar(usuario, produtos);
			
			//Verifica��o
			
			assertThat(locacao.getValor(), is(188.5));;
		}

}	
