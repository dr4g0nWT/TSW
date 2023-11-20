package Bolsa;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class StockBrokerTest {
	
	private AnalistaMercado analista = mock(AnalistaMercado.class);
	private Portfolio portfolio = mock(Portfolio.class);
	
	private Stock stock = new Stock("A", "Apple", new BigDecimal(13.4));	
	private StockBroker broker = new StockBroker(analista);
	
	
	@Test
	void comprarTest() {
		
		//1		
		Stock liveStock = new Stock("A", "Apple", new BigDecimal(12.4));		
		when(analista.getCotizacion(stock.getSimbolo())).thenReturn(liveStock);
		//2
		when(portfolio.getAvgPrecio(stock)).thenReturn(new BigDecimal(13.0));
		//3		
		
		broker.perform(portfolio, stock);
		verify(portfolio).comprar(stock);
		verify(portfolio, never()).vender(stock, 10);
		
	}
	
	@Test
	void venderTest() {
		
		//1		
		Stock liveStock = new Stock("A", "Apple", new BigDecimal(15.4));		
		when(analista.getCotizacion(stock.getSimbolo())).thenReturn(liveStock);
		//2
		when(portfolio.getAvgPrecio(stock)).thenReturn(new BigDecimal(10.6));
		//3		
		
		broker.perform(portfolio, stock);
		verify(portfolio, never()).comprar(stock);
		verify(portfolio).vender(stock, 10);
		
	}
	
	@Test
	void noMercadoTest() throws Exception{
		
		//1		
		Stock liveStock = new Stock("A", "Apple", new BigDecimal(15.4));		
		when(analista.getCotizacion(stock.getSimbolo())).thenReturn(liveStock);
		//2
		when(portfolio.getAvgPrecio(stock)).thenReturn(new BigDecimal(10.6));
		//3		
		when(portfolio.vender(stock,10)).thenReturn(new BigDecimal(10.6));
		
		broker.perform(portfolio, stock);
		verify(portfolio, never()).comprar(stock);
		verify(portfolio).vender(stock, 10);
		
	}

}
