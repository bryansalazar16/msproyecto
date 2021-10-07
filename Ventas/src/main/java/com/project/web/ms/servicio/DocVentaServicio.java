package com.project.web.ms.servicio;

import java.util.List;

import com.project.web.ms.modelo.Cliente;
import com.project.web.ms.modelo.Deuda;
import com.project.web.ms.modelo.DocVenta;
import com.project.web.ms.modelo.Empleado;

public interface DocVentaServicio {
	
	public List<DocVenta>ListAllDocVenta();
	public DocVenta getDocVenta(Long id);
	
	public DocVenta createDocVenta ( DocVenta docventa);
	public DocVenta updateDocVenta( DocVenta docventa);
	public DocVenta deleteDocVenta( Long id);
	
	public List<DocVenta> findByCliente(Cliente cliente);
	public List<DocVenta> findByEmpleado(Empleado empleado);
	public List<DocVenta> findByDeuda(Deuda deuda);

}
