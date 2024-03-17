package controller;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import model.Persona;
import service.AccesoService;


@RestController
public class PersonasController {
	
	@Autowired
	AccesoService service;
	
	
	@GetMapping(value="/personas/{nombre}/{email}/{edad}", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Persona> altaPersona(
			@PathVariable("nombre") String nombre,
			@PathVariable("email") String email,
			@PathVariable("edad") int edad			
		){
		Persona persona = new Persona(nombre, email, edad);
		
		//Llamado asincrono
		CompletableFuture<List<Persona>> resultado = service.llamadaServicio(persona);
		
		//tarea adicional mientras se espera respuesta dle servicio asincrono
		for(int i = 0; i < 50; i++) {
			System.out.println("esperando");
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//Se retorna datos del proceso asincrono
		try {
			return resultado.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}