package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.*;

import dto.Category;

class CategoryRowMapper<T extends Category> implements RowMapper<T> {

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		@SuppressWarnings("unchecked")
		T category = (T) new Category();
		category.setName(rs.getString("name"));
		category.setType(rs.getString("type"));
		return category;
	}
	
}
