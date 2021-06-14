package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.CategoryDao;
import dto.Category;
import dto.DtoWithHttpCode;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private CategoryDao dao;
	
	@GetMapping("/income")
	private DtoWithHttpCode<List<Category>> selectIncome(){
		DtoWithHttpCode<List<Category>> dto;
		List<Category> categoryList;
		try {
			categoryList = dao.selectIncomeCategories();
			dto = new DtoWithHttpCode<List<Category>>(200, categoryList, new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<List<Category>>(500, new ArrayList<Category>(), new String[1]);
		}
		return dto;
	}
	
	@GetMapping("/Expenditure")
	private DtoWithHttpCode<List<Category>> selectExpenditure(){
		DtoWithHttpCode<List<Category>> dto;
		List<Category> categoryList;
		try {
			categoryList = dao.selectExpenditureCategories();
			dto = new DtoWithHttpCode<List<Category>>(200, categoryList, new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<List<Category>>(500, new ArrayList<Category>(), new String[1]);
		}
		return dto;
	}
	
	@GetMapping("/{type}/{name}")
	private DtoWithHttpCode<Category> select(@PathVariable("type") String type, @PathVariable("name") String name){
		DtoWithHttpCode<Category> dto;
		Category category;
		try {
			category = dao.select(name, type);
			dto = new DtoWithHttpCode<Category>(200, category, new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<Category>(500, new Category(), new String[1]);
		}
		return dto;
	}
	
	@PostMapping("/{type}/{name}")
	private DtoWithHttpCode<Category> newCategory(@PathVariable("type") String type, @PathVariable("name") String name){
		DtoWithHttpCode<Category> dto;
		Category category;
		try {
			category = dao.insert(name, type);
			dto = new DtoWithHttpCode<Category>(200, category, new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<Category>(500, new Category(), new String[1]);
		}
		return dto;
	}
}
