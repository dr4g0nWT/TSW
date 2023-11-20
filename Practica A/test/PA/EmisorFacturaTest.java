package PA;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmisorFacturaTest {

	Factura factura;
	Cliente cliente;
	EmisorFactura emisor;
	PrinterService print;
	EmailService email;
	
	@BeforeEach
	public void init()
	{
		factura = mock(Factura.class);

		cliente = mock(Cliente.class);
		
		print = mock(PrinterService.class);
		
		email = mock(EmailService.class);
		
		emisor = new EmisorFactura(print, email);
	}
	@Test
	public void emailTest() {
		
		when(cliente.prefiereEmails()).thenReturn(true);
		when(cliente.getEmail()).thenReturn("samuerod@ucm.es");
		
		emisor.emitirFactura(factura, cliente);
		
		verify(email).sendFactura(factura, cliente.getEmail());	
		verify(print, never()).printFactura(factura);	
	}
	
	@Test
	public void facturaTest() {
		
		when(cliente.prefiereEmails()).thenReturn(false);
		
		emisor.emitirFactura(factura, cliente);
		
		verify(print).printFactura(factura);	
		verify(email, never()).sendFactura(factura, cliente.getEmail());	
	}

}
