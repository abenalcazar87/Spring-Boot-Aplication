package ec.com.galaxy.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.galaxy.core.process.Processor;

/**
 * @author abenalcazar
 *
 */
@Service
public class GalaxyService {

	// folder main
	private String upload_folder = ".//src//main//resources//data//";

	/**
	 * Save file
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void saveFile(MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(upload_folder + file.getOriginalFilename());
			Files.write(path, bytes);
		}
	}

	/**
	 * Read file txt 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String readFile(MultipartFile file) throws IOException {
		BufferedReader br;
		List<String> result = new ArrayList();
		if (!file.isEmpty()) {
			String line;
			InputStream is = file.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				result.add(line);
			}
		}

		// Initialize a new processor archive txt line to line
		Processor processor = new Processor();

		// Read the input from txt, validate and process
		ArrayList<String> output = processor.read(result);
		
		// print in window output file
		for (int i = 0; i < output.size(); i++) {
			System.out.println(output.get(i));
		}

		return output.toString();
	}

}
