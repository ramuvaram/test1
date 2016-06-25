package com.demoorg.demo.action;

import com.adobe.cq.sightly.WCMUse;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.jcr.JsonItemWriter;
import org.apache.sling.commons.json.JSONArray;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.resource.details.AssetDetails;
import org.apache.sling.api.resource.ValueMap;
import ramu.ramu.MultiFieldPanelFunctions;
import org.apache.sling.api.resource.Resource;
import java.util.ArrayList;

public class BoxBackgroundService extends WCMUse {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoxBackgroundService.class);
	public List<Map<String, String>> valueListMap;
	List<Map<String, Integer>> listWithInt;
	int insertRowForLarge = 0;
	int insertRowForMedium = 0;
	int insertRowForSmall = 0;
	int insertRowForExtraSmall = 0;

	int lgColZero = 0;
	int mdColZero = 0;
	int smColZero = 0;
	int xsColZero = 0;

	public List<Map<String, Integer>> getListWithInt() {
		listWithInt = new ArrayList<Map<String, Integer>>();
		int lgCount = 0;
		int mdCount = 0;
		int smCount = 0;
		int xsCount = 0;
		int lgvalue = 0;
		int mdvalue = 0;
		int smallvalue = 0;
		int extrasmallvalue = 0;
		Resource resource = getResource();
		valueListMap = MultiFieldPanelFunctions.getMultiFieldPanelValues(resource, "columns");
		for (int i = 0; i < valueListMap.size(); i++) {
			insertRowForLarge = 0;
			insertRowForSmall = 0;
			insertRowForExtraSmall = 0;
			insertRowForMedium = 0;
			lgColZero = 0;
			mdColZero = 0;
			smColZero = 0;
			xsColZero = 0;
			if (null != valueListMap.get(i).get("large") && !valueListMap.get(i).get("large").isEmpty())

			{

				lgvalue = Integer.parseInt(valueListMap.get(i).get("large"));

			}
			if (null != valueListMap.get(i).get("medium") && !valueListMap.get(i).get("medium").isEmpty())

			{

				mdvalue = Integer.parseInt(valueListMap.get(i).get("medium"));

			}
			if (null != valueListMap.get(i).get("small") && !valueListMap.get(i).get("small").isEmpty())

			{

				smallvalue = Integer.parseInt(valueListMap.get(i).get("small"));

			}
			if (null != valueListMap.get(i).get("extrasmall") && !valueListMap.get(i).get("extrasmall").isEmpty())

			{

				extrasmallvalue = Integer.parseInt(valueListMap.get(i).get("extrasmall"));

			}

			HashMap<String, Integer> hmap = new HashMap<String, Integer>();

			mdCount = mdCount + mdvalue;

			smCount = smCount + smallvalue;

			xsCount = xsCount + extrasmallvalue;

			lgCount = lgCount + lgvalue;

			if (lgCount > 12) {
				insertRowForLarge = 1;
				lgCount = lgvalue;

			}
			if (mdCount > 12) {
				insertRowForMedium = 1;

				mdCount = mdvalue;

			}
			if (smCount > 12) {
				insertRowForSmall = 1;

				smCount = smallvalue;

			}
			lgColZero = (lgvalue == 0) ? 1 : lgColZero;
			mdColZero = (mdvalue == 0) ? 1 : mdColZero;
			smColZero = (smallvalue == 0) ? 1 : smColZero;
			xsColZero = (extrasmallvalue == 0) ? 1 : xsColZero;

			if (xsCount > 12) {
				insertRowForExtraSmall = 1;
				xsCount = extrasmallvalue;

			}
			String colIdString = valueListMap.get(i).get("colid");
			String[] colIdArray = colIdString.split("-");
			int colId = Integer.parseInt(colIdArray[1]);
			hmap.put("insertRowforlarge", insertRowForLarge);
			hmap.put("insertRowformedium", insertRowForMedium);
			hmap.put("insertRowforsmall", insertRowForSmall);
			hmap.put("insertRowforextraSmall", insertRowForExtraSmall);
			hmap.put("colid", colId);
			hmap.put("large", lgvalue);
			hmap.put("medium", mdvalue);
			hmap.put("small", smallvalue);
			hmap.put("extrasmall", extrasmallvalue);
			hmap.put("lgColZero", lgColZero);
			hmap.put("mdColZero", mdColZero);
			hmap.put("smColZero", smColZero);
			hmap.put("xsColZero", xsColZero);

			listWithInt.add(hmap);

		}
		return listWithInt;
	}

	public List<Map<String, String>> getValueListMap() {
		Resource resource = getResource();
		valueListMap = MultiFieldPanelFunctions.getMultiFieldPanelValues(resource, "columns");

		return valueListMap;

	}

	@Override
	public void activate() throws Exception {
		// TODO Auto-generated method stub

	}

	String desktopPath = null;
	String mobPath = null;
	String tabPath = null;

	long desktopHeight1 = 0;
	long mobtopHeight1 = 0;
	long tabtopHeight1 = 0;

	public void getBackGroundType() {

		String bkgrndType = getProperties().get("backgroundType", "");

		if (bkgrndType.equals("image")) {

			desktopPath = getProperties().get("desktopimage", String.class);
			mobPath = getProperties().get("smartphoneimage", String.class);
			tabPath = getProperties().get("landscapetabletimage", String.class);

		}

	}

	public String getDesktopPath() {
		desktopPath = getProperties().get("desktopimage", String.class);
		return desktopPath;
	}

	public String getMobPath() {
		mobPath = getProperties().get("smartphoneimage", String.class);
		return mobPath;
	}

	public String getTabPath() {
		tabPath = getProperties().get("landscapetabletimage", String.class);
		return tabPath;
	}

	public String getDesktopHeight() {
		String desktopHeight = null;
		try {

			desktopPath = getProperties().get("desktopimage", String.class);
			if (desktopPath != null) {
				Resource res = getResourceResolver().getResource(desktopPath);
				AssetDetails assetDetails = new AssetDetails(res);
				desktopHeight1 = assetDetails.getHeight();
				desktopHeight = String.valueOf(desktopHeight1);
			}
		} catch (Exception RepositoryException) {
			LOGGER.error(RepositoryException.getMessage());
		}
		return desktopHeight;
	}

	public String getMobtopHeight() {
		String mobtopHeight = null;
		try {
			mobPath = getProperties().get("smartphoneimage", String.class);
			if (mobPath != null) {
				Resource res = getResourceResolver().getResource(mobPath);
				AssetDetails assetDetails = new AssetDetails(res);
				mobtopHeight1 = assetDetails.getHeight();
				mobtopHeight = String.valueOf(mobtopHeight1);
			}
		} catch (Exception RepositoryException) {
			LOGGER.error(RepositoryException.getMessage());
		}
		return mobtopHeight;
	}

	public String getTabtopHeight() {
		String tabtopHeight = null;
		tabPath = getProperties().get("landscapetabletimage", String.class);
		try {
			if (tabPath != null) {
				Resource res = getResourceResolver().getResource(tabPath);
				AssetDetails assetDetails = new AssetDetails(res);
				tabtopHeight1 = assetDetails.getHeight();
				tabtopHeight = String.valueOf(tabtopHeight1);
			}
		} catch (Exception RepositoryException) {
			LOGGER.error(RepositoryException.getMessage());
		}
		return tabtopHeight;
	}

}
