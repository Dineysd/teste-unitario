package br.teste.locacao.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
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
	public void setup() {// antes
		service = new LocacaoService();
		produtos = new ArrayList<Produto>();
	}

	@Test
	public void deveAlugarProdutoComSucesso() throws Exception {
		// adicionando o sabado para o teste funcionar, ele só funciona se for SABADO
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// Cenario onde inicilizaremos as variaveis ou objetos

		Produto filme = new Filme("A Mumia", 1, 5.00);
		Produto filme2 = new Filme("Sem Limites", 2, 5.00);
		Usuario usuario = new Usuario("Carlos");
		produtos = Arrays.asList(filme, filme2);

		// Ação onde invocaremos o metodo que queremos testar

		Locacao locacao = service.alugar(usuario, produtos);

		// validação onde vamos verificar se o metodo da ação esta de acordo com o
		// cenario especificado
		// ou resultado de acordo com esperado

		error.checkThat(locacao.getUsuario().getNome(), is("Carlos"));// verifique que o nome do usuario é Carlos
		assertThat(locacao.getValor(), is(equalTo(10.0)));// verifique se o valor é igual a 5.00
		error.checkThat(locacao.getValor(), is(not(8.0)));// verifique se o valor não é a 8.00

		Assert.assertEquals("usuario corresponde ao mesmo nome: ", "Carlos", locacao.getUsuario().getNome());
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));
		Assert.assertEquals("valor do Produto corresponde ao mesmo valor: ", 10.00, locacao.getValor(), 0.01);

	}

	// Metodo Teste Elegante
	@Test(expected = ProdutoSemEstoqueException.class)
	public void deveLançarExcecao_AoAlugarProdutoSemEstoque() throws Exception {

		// Cenario onde inicilizaremos as variaveis ou objetos
		Locacao locacao = new Locacao();
		Produto carro = new Carro("Cruze", 0, 75.00);
		Produto carro2 = new Carro("Civic", 0, 85.00);
		Usuario usuario = new Usuario("Carlos");
		produtos = Arrays.asList(carro, carro2);

		// Ação onde invocaremos o metodo que queremos testar

		locacao = service.alugar(usuario, produtos);
	}

	// forma robusta
	@Test
	public void naoDeveAlugarProdutosSemUsuario() throws ProdutoSemEstoqueException {
		// Cenario

		Produto filme = new Filme("A Mumia II", 1, 5.00);
		Produto filme2 = new Filme("Sem Limites", 2, 5.00);
		Produto filme3 = new Filme("tropa de elite", 3, 5.00);
		produtos = Arrays.asList(filme, filme2, filme3);
		// Ação
		try {
			Locacao locacao = service.alugar(null, produtos);
			fail("Deveria lançar uma exception!");
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio!"));
		}

	}

	// Metodo de teste com nova solução
	@Test
	public void naoDeveAlugarProdutoSemExistirProduto() throws ProdutoSemEstoqueException, LocadoraException {

		// Cenario onde inicilizaremos as variaveis ou objetos
		Usuario usuario = new Usuario("Carlos");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Produto vazio!");

		// Ação onde invocaremos o metodo que queremos testar
		Locacao locacao = service.alugar(usuario, null);
	}

	@Test
	public void DeveDevolverNaSegundaAoAlugarNoSabado() throws ProdutoSemEstoqueException, LocadoraException {
		// adicionando o sabado para o teste funcionar, ele só funciona se for SABADO
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenario
		Usuario usuario = new Usuario("Sandra");
		Produto carro = new Carro("Mustang cobra", 1, 145.0);
		produtos = Arrays.asList(carro);

		// Ação
		Locacao retorno = service.alugar(usuario, produtos);
		// verificação
		boolean heSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
		Assert.assertTrue(heSegunda);
	}

}
