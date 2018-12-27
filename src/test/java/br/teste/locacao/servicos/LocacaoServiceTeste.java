package br.teste.locacao.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.teste.locacao.entidades.Carro;
import br.teste.locacao.entidades.Filme;
import br.teste.locacao.entidades.Locacao;
import br.teste.locacao.entidades.Produto;
import br.teste.locacao.entidades.Usuario;
import br.teste.locacao.utils.DataUtils;

public class LocacaoServiceTeste {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testeLocacao() throws Exception {
		
		//Cenario onde inicilizaremos as variaveis ou objetos
		Locacao locacao = new Locacao();
		Produto filme = new Filme("A Mumia", 1, 5.00);
		Usuario usuario = new Usuario("Carlos");
		LocacaoService service = new LocacaoService();
		
		//Ação onde invocaremos o metodo que queremos testar
		
		locacao = service.alugar(usuario, filme);
		
		//validação onde vamos verificar se o metodo da ação esta de acordo com o cenario especificado 
		//ou resultado de acordo com esperado
		
		error.checkThat(locacao.getUsuario().getNome(), is("Carlos"));// verifique que o nome do usuario é Carlos
		assertThat(locacao.getValor(), is(equalTo(5.0)));// verifique se o valor é igual a 5.00
		error.checkThat(locacao.getValor(), is(not(8.0)));// verifique se o valor não é a 8.00
		
		Assert.assertEquals("usuario corresponde ao mesmo nome: ","Carlos", locacao.getUsuario().getNome());
		assertEquals("Produto corresponde ao mesmo nome: ","A Mumia", locacao.getProduto().getNome());
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()),is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(),DataUtils.obterDataComDiferencaDias(1)), is(true));
		Assert.assertEquals("valor do Produto corresponde ao mesmo valor: ", 5.00, locacao.getValor(), 0.01);
		
		
	}
	
	//Metodo Teste Elegante
	@Test(expected=Exception.class)
	public void testeLocacao_produtoSemEstoque() throws Exception {
		
		//Cenario onde inicilizaremos as variaveis ou objetos
				Locacao locacao = new Locacao();
				Produto carro = new Carro("cruze", 0, 75.00);
				Usuario usuario = new Usuario("Carlos");
				LocacaoService service = new LocacaoService();
				
				//Ação onde invocaremos o metodo que queremos testar
				
				locacao = service.alugar(usuario, carro);	
		
	}
	
	//Metodo Teste robusto
	@Test
	public void testeLocacao_produtoSemEstoque_2() {
		
		//Cenario onde inicilizaremos as variaveis ou objetos
				Locacao locacao = new Locacao();
				Produto filme = new Filme("A Mumia II", 0, 5.00);
				Usuario usuario = new Usuario("Carlos");
				LocacaoService service = new LocacaoService();
				
				//Ação onde invocaremos o metodo que queremos testar
				try {
					locacao = service.alugar(usuario, filme);
					fail("Deveria ter lançado uma exceção!");
				} catch (Exception e) {
					assertThat(e.getMessage(),is("Sem estoque"));
				}
		
		
	}
	
	//Metodo Teste com nova solução
		@Test
		public void testeLocacao_produtoSemEstoque_3() throws Exception {
			
			//Cenario onde inicilizaremos as variaveis ou objetos
					Locacao locacao = new Locacao();
					Produto carro = new Carro("Civic", 0, 85.00);
					Usuario usuario = new Usuario("Carlos");
					LocacaoService service = new LocacaoService();
					
					//Ação onde invocaremos o metodo que queremos testar
					
					exception.expect(Exception.class);// esperando exceção de teste
					exception.expectMessage("Sem estoque");
					
					locacao = service.alugar(usuario, carro);	
					
					
			
		}
	
	

}
