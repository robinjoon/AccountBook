package dao;

import java.sql.ResultSet;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import dto.Category;

public class CategoryDao {
	private JdbcTemplate jdbcTemplate;

	public CategoryDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Category> selectIncomeCategories() throws DataAccessException {
		String sql = "select name from category where type = 'Income'";
		List<Category> categoryList = jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
			Category category = new Category();
			category.setType("Income");
			category.setName(rs.getString("name"));
			return category;
		});
		return categoryList;
	}

	public List<Category> selectExpenditureCategories() throws DataAccessException {
		String sql = "select name from category where type = 'Expenditure'";
		List<Category> categoryList = jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
			Category category = new Category();
			category.setType("Expenditure");
			category.setName(rs.getString("name"));
			return category;
		});
		return categoryList;
	}

	public Category select(String name, String type) throws DataAccessException {
		String sql = "select name from category where name = ? and type =?";
		Category category = jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
			Category c = new Category();
			c.setType(rs.getString("type"));
			c.setName(rs.getString("name"));
			return c;
		}, name,type).get(0);
		return category;
	}

	@Transactional
	public Category insert(String name, String type) {
		String sql = "insert into category(name,type) values(?,?)";
		jdbcTemplate.update(sql, name, type);
		sql = "select * from category where name = ? and type = ?";
		Category category = jdbcTemplate.query(sql, (ResultSet rs, int rowNum) -> {
			Category c = new Category();
			c.setType(rs.getString("type"));
			c.setName(rs.getString("name"));
			return c;
		}, name, type).get(0);
		return category;
	}
}
