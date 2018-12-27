package br.teste.locacao.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import br.teste.locacao.entidades.Carro;
import br.teste.locacao.entidades.Filme;
import br.teste.locacao.entidades.Locacao;
import br.teste.locacao.entidades.Produto;
import br.teste.locacao.entidades.Usuario;
import br.teste.locacao.utils.DataUtils;

public class LocacaoServiceTeste {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Test
	public void testeLocacao() throws Exception {
		
		//Cenario onde inicilizaremos as variaveis ou objetos
		Locacao locacao = new Locacao();
		Produto filme = new Filme("A Mumia", 1, 5.00);
		Usuario usuario = new Usuario("Carlos");
		LocacaoService service = new LocacaoService();
		
		//A��o onde invocaremos o metodo que queremos testar
		
		locacao = service.alugar(usuario, filme);
		
		//valida��o onde vamos verificar se o metodo da a��o esta de acordo com o cenario especificado 
		//ou resultado de acordo com esperado
		
		error.checkThat(locacao.getUsuario().getNome(), is("Carlos"));// verifique que o nome do usuario � Carlos
		assertThat(locacao.getValor(), is(equalTo(5.0)));// verifique se o valor � igual a 5.00
		error.checkThat(locacao.getValor(), is(not(8.0)));// verifique se o valor n�o � a 8.00
		
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
				
				//A��o onde invocaremos o metodo que queremos testar
				
				locacao = service.alugar(usuario, carro);
		
		
	}

}
