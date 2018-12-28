package br.teste.locacao.servicos;

import static br.teste.locacao.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.teste.locacao.entidades.Locacao;
import br.teste.locacao.entidades.Produto;
import br.teste.locacao.entidades.Usuario;
import br.teste.locacao.exceptions.LocadoraException;
import br.teste.locacao.exceptions.ProdutoSemEstoqueException;

public class LocacaoService {
	
	@SuppressWarnings("unused")
	public Locacao alugar(Usuario usuario, Produto produto) throws ProdutoSemEstoqueException, LocadoraException  {
		
		
		if(usuario == null) {throw new LocadoraException("Usuario vazio!");}
		if(produto == null) {throw new LocadoraException("Produto vazio!");}
		if(produto.getEstoque() == 0) {throw new ProdutoSemEstoqueException();}
		
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

}