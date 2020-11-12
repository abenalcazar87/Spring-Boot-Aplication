package ec.com.galaxy.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ec.com.galaxy.service.GalaxyService;

/**
 * @author abenalcazar
 *
 */
@Controller
public class GalaxyController {
	
	 @Autowired
	 private GalaxyService galaxyService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		String a = "";
		if (file.isEmpty()) {
			return new ResponseEntity<Object>("Select to file", HttpStatus.OK);
		}

		try {
			a = galaxyService.readFile(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Object>(a, HttpStatus.OK);
	}

}
