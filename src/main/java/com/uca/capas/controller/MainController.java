package com.uca.capas.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.domain.Categoria;
import com.uca.capas.domain.Libro;
import com.uca.capas.service.CategoriaService;
import com.uca.capas.service.LibroService;

@Controller
public class MainController {
	
	@Autowired
	private LibroService libroService;
	@Autowired
	private CategoriaService categoriaService;
	
	private List<Categoria> categoria = null;
	
	@RequestMapping("/index")
	public ModelAndView ini() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("mensaje", "");
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/Libro")
	public ModelAndView Libro() {
		ModelAndView mav = new ModelAndView();
		Libro lib = new Libro();
		try {
			categoria = categoriaService.findAll(); 
			mav.addObject("categoria", categoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("libro", lib);
		mav.setViewName("libro"); 
		return mav;
	}
	
	@RequestMapping("/Categoria")
	public ModelAndView Categoria() {
		ModelAndView mav = new ModelAndView();
		Categoria cat = new Categoria();
		mav.addObject("categoria", cat);
		mav.setViewName("categoria"); 
		return mav;
	}
	
	@RequestMapping("/guardarLibro")
	public ModelAndView insertar(@Valid @ModelAttribute Libro libro, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if(result.hasErrors()) {
			mav.setViewName("libro");
			mav.addObject("categoria", categoria);
		} else {
			try {
				Calendar c = Calendar.getInstance();
				String fecha = Integer.toString(c.get(Calendar.YEAR)) + "/" +
						Integer.toString(c.get(Calendar.MONTH)+1) + "/" +
						Integer.toString(c.get(Calendar.DATE)) + " " +
						Integer.toString(c.get(Calendar.HOUR_OF_DAY)) + ":" +
						Integer.toString(c.get(Calendar.MINUTE));
				
				Date date2 = new Date();
				date2 = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(fecha);
				
				libro.setF_ingreso(date2);
				if(libro.getB_estado()==null) {
	                   libro.setB_estado(false);
	               }
				libroService.insertLibro(libro);
				mav.addObject("mensaje", "Libro ingresada con exito!");
				mav.setViewName("index");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;
	}
	
	@RequestMapping("/guardarCategoria")
	public ModelAndView insertarCategoria(@Valid @ModelAttribute Categoria categoria, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if(result.hasErrors()) {
			mav.setViewName("categoria");
		} else {
			try {
				categoriaService.insert(categoria);
				mav.addObject("mensaje", "Categoría guardada con éxito");
				mav.setViewName("index");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;
	}
	
	@RequestMapping("/listado")
	public ModelAndView listado() {
		ModelAndView mav = new ModelAndView();
		List<Libro> list = null;
		try {
			list = libroService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("libro", list);
		mav.setViewName("listado");
		return mav;
	}
}


