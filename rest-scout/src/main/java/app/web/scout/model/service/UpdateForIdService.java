package app.web.scout.model.service;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.web.scout.model.pojo.Scout;
import app.web.scout.model.repository.ScoutRepository;

import org.json.JSONArray;
import org.json.JSONObject;


@Service
@Transactional
public class UpdateForIdService {

	@Autowired private ScoutRepository estudianteRepository;

	@PersistenceContext
	private EntityManager em;
	
	public void borraEstudiante(Integer id) {
		Scout persona = estudianteRepository.findById(id).get();
		if (persona !=null) {
			persona.setEstado("X");
			estudianteRepository.save(persona);
			/* Codigo adicinal */
		}
	}

}
