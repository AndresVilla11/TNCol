package com.mycompany.main.crud;

import java.util.List;

public interface IDatosArchivo {
	public boolean registrar(datoArchivo cliente);
	public List<datoArchivo> obtener();
	public boolean actualizar(datoArchivo cliente);
	public boolean eliminar(datoArchivo cliente);
}
