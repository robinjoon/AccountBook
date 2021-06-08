package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.*;

import dto.Asset;

class AssetRowMapper<T extends Asset> implements RowMapper<T> {

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		@SuppressWarnings("unchecked")
		T asset = (T) new Asset();
		asset.setAssetName(rs.getString("assetName"));
		asset.setValue(rs.getLong("value"));
		return asset;
	}
	
}
