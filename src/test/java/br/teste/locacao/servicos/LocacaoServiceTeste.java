package br.teste.locacao.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.teste.locacao.entidades.Filme;
import br.teste.locacao.entidades.Locacao;
import br.teste.locacao.entidades.Produto;
import br.teste.locacao.entidades.Usuario;
import br.teste.locacao.utils.DataUtils;

public class LocacaoServiceTeste {
	
	@Test
	public void testeLocacao() {
		
		//Cenario onde inicilizaremos as variaveis ou objetos
		Locacao locacao = new Locacao();
		Produto filme = new Filme("A Mumia", 1, 5.00);
		Usuario usuario = new Usuario("Carlos");
		LocacaoService service = new LocacaoService();
		
		//A��o onde invocaremos o metodo que queremos testar
		
		locacao = service.alugar(usuario, filme);
		
		//valida��o onde vamos verificar se o metodo da a��o esta de acordo com o cenario especificado 
		//ou resultado de acordo com esperado
		
		Assert.assertThat(locacao.getUsuario().getNome(), is("Carlos"));// verifique que o nome do usuario � Carlos
		Assert.assertThat(locacao.getValor(), is(equalTo(5.0)));// verifique se o valor � igual a 5.00
		Assert.assertThat(locacao.getValor(), is(not(8.0)));// verifique se o valor n�o � a 8.00
		
		Assert.assertEquals("usuario corresponde ao mesmo nome: ","Carlos", locacao.getUsuario().getNome());
		Assert.assertEquals("Produto corresponde ao mesmo nome: ","A Mumia", locacao.getProduto().getNome());
		Assert.assertTrue("Data de loca��o corresponde a mesma data: ", DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue("Data de retorno corresponde a mesma data: ", DataUtils.isMesmaData(locacao.getDataRetorno(),DataUtils.obterDataComDiferencaDias(1)));
		Assert.assertEquals("valor do Produto corresponde ao mesmo valor: ", 5.00, locacao.getValor(), 0.01);
		
		
		
		
	}

}
