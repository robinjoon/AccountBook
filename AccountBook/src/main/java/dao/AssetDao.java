package dao;

import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import dto.Asset;

public class AssetDao {
	private JdbcTemplate jdbcTemplate;
	
	public AssetDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Transactional
	public Asset insert(String name) throws DataAccessException{
		String sql = "insert into asset(assetname,value) values(?,0)";
		jdbcTemplate.update(sql, name);
		sql = "select * from asset where assetName = ?";
		Asset asset = jdbcTemplate.query(sql, new AssetRowMapper<Asset>(), name).get(0);
		return asset;
	}
	
	public Asset selectByName(String name) throws DataAccessException{
		String sql = "select * from asset where assetName = ?";
		Asset asset = jdbcTemplate.query(sql, new AssetRowMapper<Asset>(), name).get(0);
		return asset;
	}
	
	public List<Asset> selectAll() throws DataAccessException{
		String sql = "select * from asset";
		List<Asset> assetList = jdbcTemplate.query(sql, new AssetRowMapper<Asset>());
		return assetList;
	}
	
}
