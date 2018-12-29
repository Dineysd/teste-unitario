package br.teste.locacao.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.teste.locacao.entidades.Carro;
import br.teste.locacao.entidades.Filme;
import br.teste.locacao.entidades.Locacao;
import br.teste.locacao.entidades.Produto;
import br.teste.locacao.entidades.Usuario;
import br.teste.locacao.exceptions.LocadoraException;
import br.teste.locacao.exceptions.ProdutoSemEstoqueException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	@Parameter
	public List<Produto> produtos;
	@Parameter(value=1)
	public Double valorLocacao;
	@Parameter(value=2)
	public String cenario;
	
	private LocacaoService service;

	@Before
	public void setup() {// antes
		service = new LocacaoService();
	}
	
	private static Produto filme = new Filme("Vendedor de sonhos", 1, 6.00);
	private static Produto filme2 = new Filme("Superando Limites", 1, 6.00);
	private static Produto carro = new Carro("Porche Carrera", 1, 100.00);
	private static Produto carro2 = new Carro("Lamborguine", 1, 200.00);
	private static Produto filme3 = new Filme("Vendedor de sonhos II", 2, 6.00);
	private static Produto carro3 = new Carro("Civic", 2, 85.00);
	private static Produto filme4 = new Filme("O utimo desafio", 2, 6.00);
	
	@Parameters(name="{2}")
	public static Collection<Object[]> getParametos(){
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme, filme2), 12.0, "2 produtos: Sem desconto"},
			{Arrays.asList(filme, filme2, carro), 87.0, "3 produtos: 25%"},
			{Arrays.asList(filme, filme2, carro, carro2), 187.0, "4 produtos: 50%"},
			{Arrays.asList(filme, filme2, carro, carro2, filme3), 188.5, "5 produtos: 75%"},
			{Arrays.asList(filme, filme2, carro, carro2, filme3, carro3), 188.5, "6 produtos: 100%"},
			{Arrays.asList(filme, filme2, carro, carro2, filme3, carro3, filme4), 194.5, "7 produtos: Sem desconto"}
		});
	}

	@Test
	public void deveCalcularValorLocacaoConsiderandoDesconto() throws ProdutoSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario = new Usuario("Diney");

		// Ação
		Locacao locacao = service.alugar(usuario, produtos);

		// Verificação

		assertThat(locacao.getValor(), is(valorLocacao));
		;
	}

}
