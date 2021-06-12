package dao;

import java.util.List;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import dto.Account;
import dto.AccountBook;
import dto.Asset;
import dto.Category;
import dto.ConversionAccount;
/**
 * AccountBookDao is a Data Access Object that fetches Account Book object from DB or stores new AccountBook object in DB.
 * @version 1.0
 * @author robinjoon */
public class AccountBookDao {
private JdbcTemplate jdbcTemplate;
	
	public AccountBookDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	/**
	 * @param yearMonth	Identifier for AccountBook. A string in the form of yy-mm. 
	 * @return Returns AccountBook with  yearMonth as an identifier.
	 * @throws DataAccessException - If there is no Accountbook with yearMonth as an identifier 
	 * */
	public AccountBook selectByYearMonth(String yearMonth) throws DataAccessException{
		String sql = "select * from book where yearMonth = ?";
		AccountBook book = jdbcTemplate.query(sql, new AccountBookRowMapper<AccountBook>(), yearMonth).get(0);
		sql = "select * from category where type = ?";
		List<Category> incomeCategoryList = jdbcTemplate.query(sql, new CategoryRowMapper<Category>(),"Income");
		List<Category> expenditureCategoryList = jdbcTemplate.query(sql, new CategoryRowMapper<Category>(),"Expenditure");
		sql = "select * from asset";
		List<Asset> assetList = jdbcTemplate.query(sql, new AssetRowMapper<Asset>());
		sql = "select * from account where yearMonth = ? and accountType = ?";
		List<Account> incomeAccountList = jdbcTemplate.query(sql, new AccountRowMapper<Account>(), yearMonth,"Income");
		List<Account> expenditureAccountList = jdbcTemplate.query(sql, new AccountRowMapper<Account>(), yearMonth,"Expenditure");
		sql = "select * from conversionaccount where yearmonth = ?";
		List<ConversionAccount> conversionAccountList = jdbcTemplate.query(sql, new ConversionAccountRowMapper<ConversionAccount>(),yearMonth);
		book = new AccountBook.Builder()
		.setYearMonth(book.getYearMonth())
		.setTotalIncome(book.getTotalIncome())
		.setTotlaExpenditure(book.getTotalIncome())
		.setIncomeCategoryList(incomeCategoryList)
		.setExpenditureAccountList(expenditureAccountList)
		.setAssetList(assetList)
		.setIncomeAccountList(incomeAccountList)
		.setExpenditureCategoryList(expenditureCategoryList)
		.setConversionAccountList(conversionAccountList)
		.build();
		return book;
	}
	/**
	 * @param yearMonth	Identifier for AccountBook. A string in the form of yy-mm. 
	 * @return Returns the newly created AccountBook.
	 * */
	public AccountBook newAccountBook(String yearMonth) throws DataAccessException{
		String sql = "insert into book(yearMonth) values(?) ";
		try {
			jdbcTemplate.update(sql,yearMonth);
		}catch(Exception e){
			return new AccountBook();
		}
		return selectByYearMonth(yearMonth);
	}
}
