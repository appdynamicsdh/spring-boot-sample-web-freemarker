/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.freemarker;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;

import java.util.List;
import java.util.logging.Logger;


@Controller
public class WelcomeController {

	/*@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@GetMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("time", new Date());
		model.put("message", this.message);
		return "welcome";
	}*/

	@GetMapping("/hello")
	public ResponseEntity hello() throws Exception {
		final String uri = "http://localhost:7073/testmicroservice1/hello/test";

		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);

		System.out.println(result);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}


	@GetMapping("/java_error")
	public ResponseEntity throwException() throws Exception {
		try {
			throw new Exception("Forced Exception");
		} catch (Exception e) {
		}
		return new ResponseEntity<>("Threw Java Exception", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/slow/{delay}")
	public ResponseEntity slowRequest(@PathParam("delay") int delay) throws Exception {

		try {
			for(int i = 0; i < delay; i++) Thread.sleep(1000);
		} catch(InterruptedException ex) {
		}

		return new ResponseEntity<>("Completed transaction with " + delay + " seconds delay", HttpStatus.OK);


	}

	@GetMapping("/sql_error")
	public ResponseEntity throwSqlException() throws Exception {
		try {
		} catch (Exception e) {
			//Ignore the Exception
		}
		return new ResponseEntity<>("Threw SQL Exception", HttpStatus.INTERNAL_SERVER_ERROR);

	}


	@PutMapping("/products/{id}")
	public Product update(@PathVariable("id") int id, Product source) {
		Product target = new Product();
		target.setName(source.getName());
		target.setStock(source.getStock());
		return target;
	}

	@PostMapping("/products")
	public ResponseEntity create(Product product) {
		return new ResponseEntity<>(URI.create(product.getId()+""), HttpStatus.CREATED);
	}

	@GetMapping("/products/{id}")
	public Product get(@PathVariable("id") int id) {
		Product target = new Product();
		target.setName("Bo");
		target.setStock(1);
		return target;
	}


	@DeleteMapping("/products/{id}")
	public ResponseEntity delete(@PathVariable("id") int id) {
		return new ResponseEntity<>("OK", HttpStatus.OK);

	}

	@GetMapping("/products")
	public List<Product> list() {
		Product target = new Product();
		target.setName("Bo");
		target.setStock(1);
		ArrayList x = new ArrayList<Product>();
		x.add(target);
		return x;
	}

}
