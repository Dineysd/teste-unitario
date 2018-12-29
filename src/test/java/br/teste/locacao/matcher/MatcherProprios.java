package br.teste.locacao.matcher;

import java.util.Calendar;

public class MatcherProprios {

	public static DiaSemanaMatcher sejaEm(Integer diaDaSemana) {
		return new DiaSemanaMatcher(diaDaSemana);
		}
	
	public static DiaSemanaMatcher sejaNaSegunda() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
		}
	
	
}
