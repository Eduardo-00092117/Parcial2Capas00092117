package com.uca.capas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.uca.capas.dao.LibroDAO;
import com.uca.capas.domain.Libro;

@Service
public class LibroServiceImpl implements LibroService {

	@Autowired
	private LibroDAO libroDAO;
	
	@Override
	public List<Libro> findAll() throws DataAccessException {
		return libroDAO.findAll();
	}

	@Override
	public void insertLibro(Libro libro) throws DataAccessException {
		libroDAO.insertLibro(libro);
	}

}
