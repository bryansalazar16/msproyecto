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
import com.project.web.ms.modelo.PedidoInterno;
import com.project.web.ms.modelo.Producto;
import com.project.web.ms.servicio.PedidoInternoServicio;

@RestController
@RequestMapping(value = "/pedido")

public class PedidoInternoController {
	@Autowired
	PedidoInternoServicio pedidointernoServicio;
	
	//@GetMapping("/")
			@RequestMapping(value = "/", method = RequestMethod.GET)
			public ResponseEntity<List<PedidoInterno>> ListarPedidoInterno(@RequestParam(name = "idpedido",
			required = false) Long idpedido) {
				
				List<PedidoInterno> pedidointerno = new ArrayList<>();
				
				if(idpedido == null) {
					pedidointerno = pedidointernoServicio.ListAllPedidoInterno();
					if(pedidointerno.isEmpty()) {
						return ResponseEntity.noContent().build();
					}
				}else {
					pedidointerno = pedidointernoServicio.findByProducto(Producto.builder().idproducto(idpedido).build());
					if(pedidointerno.isEmpty()) {
						return ResponseEntity.noContent().build();
					}
				}
				return ResponseEntity.ok(pedidointerno);
			}
			
			@RequestMapping(value = "/{id}",method = RequestMethod.GET)
			public ResponseEntity<PedidoInterno> getPedidoInterno(@PathVariable("id") Long id){
				
				PedidoInterno pedidointerno = pedidointernoServicio.getPedidoInterno(id);
				if(pedidointerno== null) {
					return ResponseEntity.notFound().build();
				}
				
				return ResponseEntity.ok(pedidointerno);
			}
			
			//@RequestMapping(value = "/",method = RequestMethod.POST)
			@PostMapping
			public ResponseEntity<PedidoInterno> CrearPedidoInterno(@Valid @RequestBody PedidoInterno pedidointerno,BindingResult result){
				
				if(result.hasErrors()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
				}

				PedidoInterno pedidointernoCreado = pedidointernoServicio.createPedidoInterno(pedidointerno);
				
				return ResponseEntity.status(HttpStatus.CREATED).body(pedidointernoCreado);
			}
			
			@RequestMapping(value = "/{id}",method = RequestMethod.PUT)
			public ResponseEntity<PedidoInterno> actualizarPedidoInterno(@PathVariable("id") Long id,
					@RequestBody PedidoInterno pedidointerno){
				
				pedidointerno.setIdpedido(id);
				PedidoInterno pedidointernoEncontrado = pedidointernoServicio.updatePedidoInterno(pedidointerno);
				
				if(pedidointernoEncontrado == null) {
					return ResponseEntity.notFound().build();
				}
				
				return ResponseEntity.ok(pedidointernoEncontrado);
			}
			
			@DeleteMapping(value = "/eliminar/{id}")
			public ResponseEntity<PedidoInterno> deletePedidoInterno(@PathVariable("id") Long id){
				PedidoInterno pedidoInternoDelete = pedidointernoServicio.deletePedidoInterno(id);
				
				if(pedidoInternoDelete == null) {
					return ResponseEntity.notFound().build();
				}
				
				return ResponseEntity.ok(pedidoInternoDelete);
			}
			
			/*@DeleteMapping(value = "/eliminar/{id}")
			public ResponseEntity<PedidoInterno> deletePedidoInterno(@PathVariable("id") Long id){
				PedidoInterno pedidoInternoDelete = pedidointernoServicio.deletePedidoInterno(id);
				
				if(pedidoInternoDelete == null) {
					return ResponseEntity.notFound().build();
				}
				
				return ResponseEntity.ok(pedidoInternoDelete);
			}*/
			
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
