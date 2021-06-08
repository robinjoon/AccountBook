package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.*;

import dto.Account;

class AccountRowMapper<T extends Account> implements RowMapper<T> {

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		@SuppressWarnings("unchecked")
		T account = (T) new Account.Builder()
		.setAid(rs.getLong("aid"))
		.setTime(rs.getTimestamp("time").toLocalDateTime())
		.setAccountType(rs.getString("accountType"))
		.setValue(rs.getLong("value"))
		.setAssetName(rs.getString("assetName"))
		.setCategory(rs.getString("category"))
		.setMemo(rs.getString("memo"))		
		.setYearMonth(rs.getString("yearMonth"))
		.build();
		return account;
		
	}
	
}
