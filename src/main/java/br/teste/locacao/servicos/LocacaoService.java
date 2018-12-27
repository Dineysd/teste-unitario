package br.teste.locacao.servicos;

import static br.teste.locacao.utils.DataUtils.adicionarDias;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import br.teste.locacao.entidades.Filme;
import br.teste.locacao.entidades.Locacao;
import br.teste.locacao.entidades.Produto;
import br.teste.locacao.entidades.Usuario;
import br.teste.locacao.utils.DataUtils;

public class LocacaoService {
	
	public Locacao alugar(Usuario usuario, Produto produto) {
		Locacao locacao = new Locacao();
		locacao.setProduto(produto);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(produto.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar metodo para salvar
		
		return locacao;
	}

	@Test
	public void testeLocacao() {
		
		//Cenario onde inicilizaremos as variaveis ou objetos
		Locacao locacao = new Locacao();
		Produto filme = new Filme("A Mumia", 1, 5.00);
		Usuario usuario = new Usuario("Carlos");
		LocacaoService service = new LocacaoService();
		
		//Ação onde invocaremos o metodo que queremos testar
		
		locacao = service.alugar(usuario, filme);
		
		//validação onde vamos verificar se o metodo da ação esta de acordo com o cenario especificado 
		//ou resultado de acordo com esperado
		
		Assert.assertTrue("usuario corresponde ao mesmo nome: ", locacao.getUsuario().getNome().equals("Carlos"));
		Assert.assertTrue("Produto corresponde ao mesmo nome: ", locacao.getProduto().getNome().equals("A Mumia"));
		Assert.assertTrue("Data de locação corresponde a mesma data: ", DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue("Data de retorno corresponde a mesma data: ", DataUtils.isMesmaData(locacao.getDataRetorno(),DataUtils.obterDataComDiferencaDias(1)));
		Assert.assertTrue("valor do Produto corresponde ao mesmo valor: ", locacao.getValor().equals(5.00));
		
		
		
		
	}
}