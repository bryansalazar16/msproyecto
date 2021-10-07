package com.project.web.ms.controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.web.ms.modelo.Cliente;
import com.project.web.ms.modelo.DocVenta;
import com.project.web.ms.servicio.DocVentaServicio;

@RestController
@RequestMapping(value = "/docventa")
public class DocVentaController {

	@Autowired
	DocVentaServicio docventaServicio;
	
	//@GetMapping("/")
		@RequestMapping(value = "/", method = RequestMethod.GET)
		public ResponseEntity<List<DocVenta>> ListarDocVenta(@RequestParam(name = "docventaId",
		required = false) Long docventaId) {
			
			List<DocVenta> docventa = new ArrayList<>();
			
			if(docventaId == null) {
				docventa = docventaServicio.ListAllDocVenta();
				if(docventa.isEmpty()) {
					return ResponseEntity.noContent().build();
				}
			}else {
				docventa = docventaServicio.findByCliente(Cliente.builder().idcliente(docventaId).build());
				//
				if(docventa.isEmpty()) {
					return ResponseEntity.noContent().build();
				}
			}
			return ResponseEntity.ok(docventa);
		}
		
		@RequestMapping(value = "/{id}",method = RequestMethod.GET)
		public ResponseEntity<DocVenta> getDocVenta(@PathVariable("id") Long id){
			
			DocVenta docventa = docventaServicio.getDocVenta(id);
			if(docventa== null) {
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.ok(docventa);
		}
		
		//@RequestMapping(value = "/",method = RequestMethod.POST)
		@PostMapping
		public ResponseEntity<DocVenta> CrearDocVenta(@Valid @RequestBody DocVenta docventa,BindingResult result){
			
			if(result.hasErrors()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
			}

			DocVenta docventaCreado = docventaServicio.createDocVenta(docventa);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(docventaCreado);
		}
		
		@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
		public ResponseEntity<DocVenta> actualizarDocVenta(@PathVariable("id") Long id,
				@RequestBody DocVenta docventa){
			
			docventa.setIddoc(id);
			DocVenta docventaEncontrado = docventaServicio.updateDocVenta(docventa);
			
			if(docventaEncontrado == null) {
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.ok(docventaEncontrado);
		}
		
		@DeleteMapping(value = "/eliminar/{id}")
		public ResponseEntity<DocVenta> deleteDocVenta(@PathVariable("id") Long id){
			DocVenta docventaDelete = docventaServicio.deleteDocVenta(id);
			
			if(docventaDelete == null) {
				return ResponseEntity.notFound().build();
			}
			
			return ResponseEntity.ok(docventaDelete);
		}
		
		 private String formatMessage( BindingResult result){
		        List<Map<String,String>> errors = result.getFieldErrors().stream()
		                .map(err ->{
		                    Map<String,String>  error =  new HashMap<>();
		                    error.put(err.getField(), err.getDefaultMessage());
		                    return error;

		                }).collect(Collectors.toList());
		        ErrorMessage errorMessage = ErrorMessage.builder()
		                .code("01")
		                .messages(errors).build();
		        ObjectMapper mapper = new ObjectMapper();
		        String jsonString="";
		        try {
		            jsonString = mapper.writeValueAsString(errorMessage);
		        } catch (JsonProcessingException e) {
		            e.printStackTrace();
		        }
		        return jsonString;
		    }	
	
}
