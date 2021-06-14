package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.AssetDao;
import dto.Asset;
import dto.DtoWithHttpCode;

@RestController
@RequestMapping("/assets")
public class AssetController {
	@Autowired
	private AssetDao dao;
	
	@PostMapping("/{name}")
	private DtoWithHttpCode<Asset> newAsset(@PathVariable("name")String name){
		DtoWithHttpCode<Asset> dto;
		Asset asset;
		try {
			asset = dao.insert(name);
			dto = new DtoWithHttpCode<Asset>(200, asset, new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<Asset>(500, new Asset(), new String[1]);
		}
		return dto;
	}
	@GetMapping("/{name}")
	private DtoWithHttpCode<Asset> selectAsset(@PathVariable("name")String name){
		DtoWithHttpCode<Asset> dto;
		Asset asset;
		try {
			asset = dao.selectByName(name);
			dto = new DtoWithHttpCode<Asset>(200, asset, new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<Asset>(500, new Asset(), new String[1]);
		}
		return dto;
	}
	@GetMapping("")
	private DtoWithHttpCode<List<Asset>> selectAllAsset(){
		DtoWithHttpCode<List<Asset>> dto;
		List<Asset> assetList;
		try {
			assetList = dao.selectAll();
			dto = new DtoWithHttpCode<List<Asset>>(200, assetList, new String[1]);
		}catch(Exception e) {
			dto = new DtoWithHttpCode<List<Asset>>(500, new ArrayList<Asset>(), new String[1]);
		}
		return dto;
	}
}
