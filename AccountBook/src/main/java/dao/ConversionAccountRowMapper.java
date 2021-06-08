package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.*;

import dto.ConversionAccount;

class ConversionAccountRowMapper<T extends ConversionAccount> implements RowMapper<T> {

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		@SuppressWarnings("unchecked")
		T account = (T) new ConversionAccount.Builder()
		.setYearMonth(rs.getString("yearMonth"))
		.setFrom(rs.getString("from"))
		.setTo(rs.getString("to"))
		.setValue(rs.getLong("value"))
		.setTime(rs.getTimestamp("time").toLocalDateTime())
		.setAid(rs.getLong("aid"))
		.setMemo(rs.getString("memo"))
		.build();
		return account;
		
	}
	
}
