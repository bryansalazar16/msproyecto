package com.project.web.ms.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.web.ms.modelo.PedidoInterno;
import com.project.web.ms.modelo.Producto;
import com.project.web.ms.repositorio.PedidoInternoRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoInternoServicioImplementa implements PedidoInternoServicio{
	public final PedidoInternoRepositorio pedidointernoRepositorio;
	
	@Override
	public List<PedidoInterno> ListAllPedidoInterno() {
		return pedidointernoRepositorio.findAll();
	}

	@Override
	public PedidoInterno getPedidoInterno(Long id) {
		return pedidointernoRepositorio.findById(id).orElse(null);
	}

	@Override
	public PedidoInterno createPedidoInterno(PedidoInterno pedidointerno) {
		return pedidointernoRepositorio.save(pedidointerno);
	}

	@Override
	public PedidoInterno updatePedidoInterno(PedidoInterno pedidointerno) {
		PedidoInterno pedidointernoUpdate = getPedidoInterno(pedidointerno.getIdpedido());
		
		if (pedidointernoUpdate == null) {
			return null;
		}
		
		pedidointernoUpdate.setCantidad(pedidointerno.getCantidad());
		pedidointernoUpdate.setProducto(pedidointerno.getProducto());
		
		
		return pedidointernoRepositorio.save(pedidointernoUpdate);
	}

	@Override
	public PedidoInterno deletePedidoInterno(Long id) {
		PedidoInterno pedidointernoDelete = getPedidoInterno(id);
		
		if (pedidointernoDelete == null) {
			return null;
		}		
		
		pedidointernoDelete.setCantidad(0);
		return pedidointernoRepositorio.save(pedidointernoDelete);
	}

	@Override
	public List<PedidoInterno> findByProducto(Producto producto) {
		return pedidointernoRepositorio.findByProducto(producto);
	}

}
