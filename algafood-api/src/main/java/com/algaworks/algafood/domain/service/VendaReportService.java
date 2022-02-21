package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro);
	
}
