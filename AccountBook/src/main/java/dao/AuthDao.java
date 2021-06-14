package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
/**
 * AuthDao is a Data Access Object that performs user authentication.
 * @version 1.0
 * @author robinjoon */
public class AuthDao {
	private JdbcTemplate jdbcTemplate;
	
	public AuthDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	/**
	 * @param token	A token for user authentication included in the HTTP header. 
	 * @return Returns true if it matches the token stored in DB, otherwise false.
	 * @throws DataAccessException - If there is no auth table in the database or the structure is different.  
	 * */
	public boolean checkToken(String token) throws DataAccessException{
		String sql = "select COUNT(*) from auth where token = ?";
		List<Integer> result = jdbcTemplate.query(sql, new RowMapper<Integer>() {

			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Integer integer = rs.getInt(1);
				return integer;
			}
			
		},token) ;
		if(result!=null&&result.get(0)==1) {
			return true;
		}else {
			return false;
		}
	}
}
