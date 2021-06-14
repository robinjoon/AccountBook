package dao;

import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import dto.Asset;
/**
 * AssetDao is a Data Access Object that fetches Asset object from DB or stores new Asset object in DB.
 * @version 1.0
 * @author robinjoon */
public class AssetDao {
	private JdbcTemplate jdbcTemplate;
	
	public AssetDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	/**
	 * @param name	Identifier for Asset. A string. 
	 * @return Returns a newly created Asset object with name as an identifier and a value of 0.
	 * @throws DataAccessException - If there is no Asset table in the database or the structure is different. 
	 * */
	@Transactional
	public Asset insert(String name) throws DataAccessException{
		String sql = "insert into asset(assetname,value) values(?,0)";
		jdbcTemplate.update(sql, name);
		sql = "select * from asset where assetName = ?";
		Asset asset = jdbcTemplate.query(sql, new AssetRowMapper<Asset>(), name).get(0);
		return asset;
	}
	/**
	 * @param name	Identifier for Asset. A string. 
	 * @return Returns Asset object with name as an identifier.
	 * @throws DataAccessException - If there is no Asset table in the database or the structure is different. 
	 * */
	public Asset selectByName(String name) throws DataAccessException{
		String sql = "select * from asset where assetName = ?";
		Asset asset = jdbcTemplate.query(sql, new AssetRowMapper<Asset>(), name).get(0);
		return asset;
	}
	/**
	 * @return Returns all Asset objects stored in the DB in the form of List<Asset>.
	 * @throws DataAccessException - If there is no Asset table in the database or the structure is different. 
	 * */
	public List<Asset> selectAll() throws DataAccessException{
		String sql = "select * from asset";
		List<Asset> assetList = jdbcTemplate.query(sql, new AssetRowMapper<Asset>());
		return assetList;
	}
	
}
