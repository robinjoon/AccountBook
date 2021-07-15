package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class AuthDao {
	private JdbcTemplate jdbcTemplate;
	
	public AuthDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public boolean checkToken(String token) {
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
