package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.*;

import dto.AccountBook;

class AccountBookRowMapper<T extends AccountBook> implements RowMapper<T> {

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		@SuppressWarnings("unchecked")
		T book = (T) new AccountBook.Builder()
		.setYearMonth(rs.getString("yearMonth"))
		.setTotalIncome(rs.getLong("totalIncome"))
		.setTotlaExpenditure(rs.getLong("totalExpenditure"))
		.build();
		
		return book;
	}
	
}
