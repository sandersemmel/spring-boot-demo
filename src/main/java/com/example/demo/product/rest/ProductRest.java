package com.example.demo.product.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.product.entity.Product;
import com.example.demo.product.service.ProductService;
import com.github.rkpunjal.sqlsafe.SqlSafeUtil;

@CrossOrigin
@RestController()
@RequestMapping("/product")
public class ProductRest {

	@Autowired
	ProductService productService;

	@GetMapping("/producttest")
	public String greeting() {
		return "hello from product";
	}

	@PostMapping("/createproduct")
	public String createProduct(@RequestBody Product product) {
		
		
		if( !(SqlSafeUtil.isSqlInjectionSafe(product.getName()) && SqlSafeUtil.isSqlInjectionSafe(product.getName()) )){
			return "Not created";
		}

		Product newProduct = new Product();
		newProduct.setName(product.getName());
		newProduct.setPrice(product.getPrice());
	
		productService.createProduct(product);
		return "Created";
	}

	@GetMapping("/getallproducts")
	public List<Product> getallproducts() {
		var products = productService.getAllProducts();
		return products;
	}

}
