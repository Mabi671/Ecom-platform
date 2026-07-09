package org.example.controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RootController {

	@GetMapping("/")
	public RepresentationModel<?> index() {
		RepresentationModel<?> rootModel = new RepresentationModel<>();
		rootModel.add(linkTo(methodOn(ProductController.class).all()).withRel("products"));
		return rootModel;
	}
}
