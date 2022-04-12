package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.model.dto.VendaDiaria;
import com.algaworks.algafood.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi{

	@Autowired
	private VendaQueryService queryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter){
		return queryService.consultarVendasDiarias(filter);
	}
	
	@GetMapping(path = "/vendas-diarias/report", produces = MediaType.APPLICATION_PDF_VALUE)
	@Override
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro){
	
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	
}
