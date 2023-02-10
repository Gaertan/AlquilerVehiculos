package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.naming.OperationNotSupportedException;

public class Alquiler {


	private Cliente cliente;
	private Turismo turismo;

	protected static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/LL/yyyy");
	private final int PRECIO_DIA = 20;
	private int precio = 0;
	LocalDate fechaAlquiler;
	LocalDate fechaDevolucion;


	public Alquiler(Cliente cliente, Turismo turismo, LocalDate fechaAlquiler) {


		setCliente(cliente);
		setTurismo(turismo);
		setFechaAlquiler(fechaAlquiler);
	}


	public Alquiler(Alquiler alquiler) {
		if (alquiler == null) {throw new NullPointerException("ERROR: No es posible copiar un alquiler nulo.");}
		setCliente( new Cliente (alquiler.getCliente() )  );
		setTurismo( new Turismo (alquiler.getTurismo() )  );
		setFechaAlquiler(alquiler.getFechaAlquiler());
		setFechaDevolucion(alquiler.getFechaDevolucion());

	}


	public Cliente getCliente() {
		return cliente;
	}


	private void setCliente(Cliente cliente) {
		if (cliente == null) {throw new NullPointerException("ERROR: El cliente no puede ser nulo.");}
	this.cliente=cliente;
	}


	public Turismo getTurismo() {
		return turismo;
	}


	private void setTurismo(Turismo turismo) {
		if (turismo == null) {throw new NullPointerException("ERROR: El turismo no puede ser nulo.");}
	this.turismo=turismo;
	}


	public LocalDate getFechaAlquiler() {
		return fechaAlquiler;
	}


	private void setFechaAlquiler(LocalDate fechaAlquiler) {
		if (fechaAlquiler == null) {throw new NullPointerException("ERROR: La fecha de alquiler no puede ser nula.");}
		if(fechaAlquiler.isAfter(LocalDate.now())) {throw new IllegalArgumentException("ERROR: La fecha de alquiler no puede ser futura.");}
		//if(fechaAlquiler.isAfter( LocalDate.now().minusDays(1) )) {throw new IllegalArgumentException("ERROR: La fecha de alquiler no puede ser futura.");}
		this.fechaAlquiler = fechaAlquiler;
	}


	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}


	private void setFechaDevolucion(LocalDate fechaDevolucion) {
		if (fechaDevolucion == null) {throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");}
		if(fechaDevolucion.isAfter(LocalDate.now())) {throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");}
		if(fechaDevolucion.isBefore(this.fechaAlquiler)||fechaDevolucion==fechaAlquiler) {throw new IllegalArgumentException("ERROR: La fecha de devolución debe ser posterior a la fecha de alquiler.");}

		this.fechaDevolucion = fechaDevolucion;
	}

	public void devolver(LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if(this.fechaDevolucion!=null) {throw new OperationNotSupportedException("ERROR: La devolución ya estaba registrada.");}
		setFechaDevolucion(fechaDevolucion);
	}

	public int getPrecio() {
		int factorCilindrada =  (this.turismo.getCilindrada()/10);
		int numDias = 0;
		try{numDias= Period.between(fechaAlquiler,fechaDevolucion).getDays(); }catch(Exception e) {}
		int precio=(PRECIO_DIA+factorCilindrada)*numDias;
		return precio;
	}


	@Override
	public int hashCode() {
		return Objects.hash(cliente, fechaAlquiler, turismo);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Alquiler other = (Alquiler) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(fechaAlquiler, other.fechaAlquiler)
				&& Objects.equals(turismo, other.turismo);
	}

	@Override
	public String toString() {
		if(fechaDevolucion==null) {return String.format("%s <---> %s, %s - %s (%d€)", cliente, turismo,
				FORMATO_FECHA.format(fechaAlquiler), "Aún no devuelto", 0);}
		else {
		return	String.format("%s <---> %s, %s - %s (%d€)", cliente, turismo,
					FORMATO_FECHA.format(fechaAlquiler), FORMATO_FECHA.format(fechaDevolucion), 29);
		}

	}


}
