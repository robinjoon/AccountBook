package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import dto.ConversionAccount;
/**
 * ConversionAccountDao is a Data Access Object that fetches ConversionAccount object from DB or stores ConversionAccount object in DB.
 * @throws DataAccessException
 * All methods in this class throw a DataAccessException if the parameter is an invalid value or there is a problem with the DB connection.
 * @version 1.0
 * @author robinjoon */
public class ConversionAccountDao {
	private JdbcTemplate jdbcTemplate;
	
	public ConversionAccountDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	/**
	 * @param yearMonth	Identifier for AccountBook. A string in the form of yy-mm.
	 * @return Returns the List of ConversionAccount.
	 * */
	public List<ConversionAccount> selectByYearMonth(String yearMonth) throws DataAccessException{
		String sql = "select * from conversionaccount where yearmonth = ?";
		List<ConversionAccount> results = jdbcTemplate.query(sql,new ConversionAccountRowMapper<ConversionAccount>(),yearMonth);
		return results;
	}
	@Transactional
	/**
	 * @param account ConversionAccount object to store in DB.
	 * @return Returns the ConversionAccount stored in the DB.
	 * @throws IllegalArgumentException if accounts.getValue() is not positive.
	 * */
	public ConversionAccount insert(ConversionAccount account) throws DataAccessException{
		if(account.getValue()<=0)
			throw new IllegalArgumentException("value must be positive");
		String sql = "insert into conversionaccount(yearMonth,value,`from`,`to`,memo) values(?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql,new String[] {"aid"});
				pstmt.setString(1,account.getYearMonth());
				pstmt.setLong(2, account.getValue());
				pstmt.setString(3, account.getFrom());
				pstmt.setString(4, account.getTo());
				pstmt.setString(5, account.getMemo());
				return pstmt;
			}
		},keyHolder);
		int addAssetRet = addAsset(account.getValue(), account.getTo());
		int minusAssetRet = minusAsset(account.getValue(), account.getFrom());
		if(addAssetRet==1 && minusAssetRet ==1) {
			return selectByAid(keyHolder.getKey().longValue());
		}else {
			return new ConversionAccount();
		}
	}
	/**
	 * @param aid Identifier for ConversionAccount. A string in the form of yy-mm.
	 * @return Returns ConversionAccount with aid as the identifier.
	 * */
	public ConversionAccount selectByAid(long aid) throws DataAccessException{
		String sql = "select * from conversionaccount where aid = ?";
		List<ConversionAccount> results = jdbcTemplate.query(sql,new ConversionAccountRowMapper<ConversionAccount>(),aid);
		return results.get(0);
	}
	
	private int addAsset(long value,String assetName) throws DataAccessException{
		String sql = "update asset set value = value + ? where assetName = ?";
		return jdbcTemplate.update(sql,value,assetName);
	}
	
	private int minusAsset(long value,String assetName) throws DataAccessException{
		String sql = "update asset set value = value - ? where assetName = ?";
		return jdbcTemplate.update(sql,value,assetName);
	}
}
