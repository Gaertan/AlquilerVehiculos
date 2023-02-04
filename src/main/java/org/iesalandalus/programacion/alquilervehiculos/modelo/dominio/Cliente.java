package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

import java.util.Locale;
import java.util.Objects;

public class Cliente {
	
	private String ER_DNI = "\\d{8}[A-Za-z]";
	private String ER_TELEFONO = "\\d{9}";
	private String ER_NOMBRE = "(^(?=.{1,40}$)[a-zA-ZáéíóúüñÁÉÍÓÚÑ]+(?:[\\s][a-zA-ZáéíóúüñÁÉÍÓÚÑ]+)*$\r\n)[1,3]";
	
	private String nombre;
	private String dni;
	private String telefono;
	
	
	public Cliente(String nombre, String dni, String telefono) {

	setNombre(nombre);
	setDni(dni);
	setTelefono(telefono);
	
	}
	
	public Cliente(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No es posible copiar un cliente nulo.");
		}	
		setNombre(cliente.getNombre());
		setDni(cliente.getDni());
		setTelefono(cliente.getTelefono());
	}
	
	public String getNombre() { String nombreR = this.nombre; return nombreR;};
	public void setNombre(String nombre){
		if (nombre == null) {throw new NullPointerException("ERROR: El nombre no puede ser nulo.");}
		if (nombre.trim().isEmpty()||nombre.matches("\\s+")) {throw new IllegalArgumentException("ERROR: El nombre de un cliente no puede estar vacío.");}
		if (!nombre.matches(ER_NOMBRE)) {throw new IllegalArgumentException("ERROR: El nombre no tiene un formato válido.");}
		
		nombre = nombre.toLowerCase(Locale.ROOT);
		//separa la string cuando encuentra cualquier tipo de espacio/separador

		String nombreRoto[] = nombre.split("\\s+");		
		  for(int i = 0;i<nombreRoto.length;i++) {
			  nombreRoto[i].replaceAll("\\s\\W","");
			  try {
				  nombreRoto[i].trim();
		//deshace la string en el primer caracter y el resto,lo pasa a mayus y la vuelve a juntar
				  String palabra = nombreRoto[i].substring(nombreRoto[i].indexOf(" ") + 1);
				  palabra = Character.toUpperCase(palabra.charAt(0)) + palabra.substring(1);
				  if(palabra!=" ")nombreRoto[i] = palabra;}		  
			  catch(Exception e) {}
		  }		
		  //crea una nueva String uniendo los elementos de la arraay separados por un espacio
		 String resultado = String.join(" ", nombreRoto);
		 
		 this.nombre = resultado.trim();
	};
	
	public String getDni(){String dniR = this.dni; return dniR;};
	private void setDni(String dni){		
		if (dni == null) {throw new NullPointerException("ERROR: El DNI no puede ser nulo.");}
		String dni1 = dni;
		dni1.replaceAll("\\W","");
		dni1.toUpperCase();
		if (!dni1.matches(ER_DNI)) {throw new IllegalArgumentException("ERROR: El DNI no tiene un formato válido.");}		
		if(!comprobarLetraDni(dni1)) {throw new IllegalArgumentException("ERROR: La letra del DNI no es correcta.");}		
		this.dni = dni1;
	}
	
	private boolean comprobarLetraDni(String DNI){
		char tempLetraDNI = DNI.charAt(DNI.length()-1);
		//elimina todo lo que sea un numero y convierte la caadena en uno.
		String dniFixed = DNI.replaceAll("\\D","");
		int dniNumber = Integer.parseInt(dniFixed);
		char[] LETRAS_DNI = {'T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'};
		if (LETRAS_DNI[dniNumber%23]==tempLetraDNI) {return true;}
		else return false;
		
	}
	
	public String getTelefono(){String telefonoR = this.telefono;return telefonoR;};
	public void setTelefono(String telefono){
	if (telefono == null) {throw new NullPointerException("ERROR: El teléfono no puede ser nulo.");}
	if (telefono.trim().isEmpty()) {throw new IllegalArgumentException("ERROR: El telefono de un cliente no puede estar vacío.");}
	if (!telefono.matches(ER_TELEFONO)) {throw new IllegalArgumentException("ERROR: El teléfono no tiene un formato válido.");}
	this.telefono = telefono;
	}
	
	public Cliente getClienteConDni(String dni){
		if (dni == null) {throw new NullPointerException("ERROR: El dni de un cliente no puede ser nulo.");}
		if (!dni.matches(ER_DNI)) {throw new IllegalArgumentException("ERROR: El dni del cliente no tiene un formato válido.");}		
		if(!comprobarLetraDni(dni)) {throw new IllegalArgumentException("ERROR: La letra del dni del cliente no es correcta.");}
		//no sabia si poner bob esponja como en los tests la verdad
		Cliente clienteR =new Cliente("Andres Garcia Perez",dni,"666666666");
		return	clienteR;	
		}
		
		
	

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(dni, other.dni);
	};
	@Override
	public String toString() {return (String.format("%s - %s (%s)", nombre, dni, telefono) ) ;}
	
	
}