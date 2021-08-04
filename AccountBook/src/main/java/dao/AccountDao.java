package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import dto.Account;
/**
 * AccountDao is a Data Access Object that fetches Account object from DB or stores Account object in DB.
 * @throws DataAccessException
 * All methods in this class throw a DataAccessException if the parameter is an invalid value or there is a problem with the DB connection.
 * @version 1.0
 * @author robinjoon */
public class AccountDao {
	private JdbcTemplate jdbcTemplate;
	
	public AccountDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	/**
	 * @param aid Account's identifier. natural number.
	 * @return Returns Account with aid as the identifier.
	 */
	public List<Account> selectByDate(String date){
		String sql = "select * from account where DATE(time) = ?";
		List<Account> results = jdbcTemplate.query(sql, new AccountRowMapper<Account>(),date);
		return results;
	}
	public Account selectByAid(long aid) throws DataAccessException{
		String sql = "select * from account where aid = ?";
		List<Account> results = jdbcTemplate.query(sql, new AccountRowMapper<Account>(),aid);
		return results.get(0);
	}
	/**
	 * @param yearMonth	Identifier for AccountBook. A string in the form of yy-mm. 
	 * @param type A string indicating the type of Account. Expenditure or Income
	 * @return Returns the List of Accounts.
	 */
	public List<Account> selectByYearMonth(String yearMonth, String type)throws DataAccessException{
		String sql = "select * from account where yearMonth = ? and accountType = ?";
		List<Account> results = jdbcTemplate.query(sql, new AccountRowMapper<Account>(),yearMonth ,type);
		return results;
	}
	/**
	 * @param account Account object to store in DB. 
	 * @return Returns the ID of Account stored in DB.
	 * @throws IllegalArgumentException if accounts.getValue() is not positive.
	 * */
	@Transactional
	public long insert(Account account) throws DataAccessException{
		if(account.getValue()<=0) {
			throw new IllegalArgumentException("value must be positive");
		}
		String sql = "insert into account(accountType,value,assetName,category,memo,yearMonth,time) values(?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql,new String[] {"aid"});
				pstmt.setString(1, account.getAccountType());
				pstmt.setLong(2, account.getValue());
				pstmt.setString(3, account.getAssetName());
				pstmt.setString(4, account.getCategory());
				pstmt.setString(5, account.getMemo());
				pstmt.setString(6, account.getYearMonth());
				pstmt.setTimestamp(7, Timestamp.valueOf(account.getTime()));
				return pstmt;
			}
		},keyHolder);
		if(account.getAccountType().equals("Expenditure")) {
			addTotalExpenditure(account);
			minusAsset(account);
		}else {
			addTotalIncome(account);
			addAsset(account);
		}
		return (long) keyHolder.getKey();
	}
	/**
	 * @param account Account object to store in DB. 
	 * @return Returns the Account stored in the DB.
	 * @throws IllegalArgumentException if accounts.getValue() is not positive.
	 * */
	public Account insertWithAid(Account account) throws DataAccessException{
		if(account.getValue()<=0) {
			throw new IllegalArgumentException("value must be positive");
		}
		String sql = "insert into account values(?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql,account.getAid(),account.getTime(),account.getAccountType(),account.getValue(),
				account.getAssetName(),account.getCategory(),account.getMemo(),account.getYearMonth());
		if(account.getAccountType().equals("Expenditure")) {
			addTotalExpenditure(account);
			minusAsset(account);
		}else {
			addTotalIncome(account);
			addAsset(account);
		}
		return selectByAid(account.getAid());
	}
	/**
	 * @param aid Account's identifier. natural number.
	 * @return Returns the deleted Account.
	 * */
	public Account deleteAccount(long aid) throws DataAccessException {
		String sql = "delete from account where aid = ?";
		Account account = selectByAid(aid);
		jdbcTemplate.update(sql, aid);
		if(account.getAccountType().equalsIgnoreCase("Income")) {
			minusTotalIncome(account);
			minusAsset(account); // 수입이 줄어든 것이므로 자산을 감소시킴
		}else if(account.getAccountType().equalsIgnoreCase("Expenditure")){
			minusTotalExpenditure(account);
			addAsset(account); // 지출이 없어진 것이므로 자산을 증가시킴
		}
		return account;
	}
	
	private void minusTotalIncome(Account account) throws DataAccessException{
		String sql = "update book set totalIncome = totalIncome - ? where yearMonth = ?";
		jdbcTemplate.update(sql,account.getValue(),account.getYearMonth());
	}
	
	private void addTotalIncome(Account account) throws DataAccessException{
		String sql = "update book set totalIncome = totalIncome + ? where yearMonth = ?";
		jdbcTemplate.update(sql,account.getValue(),account.getYearMonth());
	}
	
	private void addTotalExpenditure(Account account) throws DataAccessException{
		String sql = "update book set totalExpenditure = totalExpenditure + ? where yearMonth = ?";
		jdbcTemplate.update(sql,account.getValue(),account.getYearMonth());
	}
	
	private void minusTotalExpenditure(Account account) throws DataAccessException{
		String sql = "update book set totalExpenditure = totalExpenditure - ? where yearMonth = ?";
		jdbcTemplate.update(sql,account.getValue(),account.getYearMonth());
	}
	
	private int addAsset(Account account) throws DataAccessException{
		String sql = "update asset set value = value + ? where assetName = ?";
		return jdbcTemplate.update(sql,account.getValue(),account.getAssetName());
	}
	
	private int minusAsset(Account account) throws DataAccessException{
		String sql = "update asset set value = value - ? where assetName = ?";
		return jdbcTemplate.update(sql,account.getValue(),account.getAssetName());
	}
}
