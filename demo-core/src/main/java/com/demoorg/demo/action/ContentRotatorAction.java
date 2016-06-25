package com.demoorg.demo.action;

import org.apache.commons.lang.StringUtils;

import javax.jcr.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demoorg.demo.model.BaseBean;
import com.demoorg.demo.model.ContentRotatorBean;

/**
 * The Class ContentRotatorAction.
 */
public class ContentRotatorAction extends BaseAction {

	/**
	 * logger object for handling log messages.
	 */
	private static final Logger LOG = LoggerFactory
			.getLogger(ContentRotatorAction.class);

	@Override
	public <T extends BaseBean> T execute() {

		final ContentRotatorBean heroBean = new ContentRotatorBean();

		String title = null;
		String description = null;
		String link = null;
		String linktext = null;
		String format = null;

		String title2 = null;
		String description2 = null;
		String link2 = null;
		String linktext2 = null;

		String title3 = null;
		String description3 = null;
		String link3 = null;
		String linktext3 = null;

		final String host = "";

		String assetswf = null;
		String assetswf1 = null;
		String assetswf2 = null;

		String asset = null;
		String asset2 = null;
		String asset3 = null;

		final Value[] val = null;
		try {
			title = getCurrentNode().getProperty("./title").getValue()
					.getString();
			description = getCurrentNode().getProperty("./description")
					.getValue().getString();
			link = getCurrentNode().getProperty("./link").getValue()
					.getString();
			format = getCurrentNode().getProperty("./format").getValue()
					.getString();
			if (getCurrentNode().hasProperty("./linktext")) {
				linktext = getCurrentNode().getProperty("./linktext")
						.getValue().getString();
			}
			title2 = getCurrentNode().getProperty("./title2").getValue()
					.getString();
			description2 = getCurrentNode().getProperty("./description2")
					.getValue().getString();

			link2 = getCurrentNode().getProperty("./link2").getValue()
					.getString();
			format = getCurrentNode().getProperty("./format").getValue()
					.getString();
			linktext2 = getCurrentNode().getProperty("./linktext2").getValue()
					.getString();
			title3 = getCurrentNode().getProperty("./title3").getValue()
					.getString();
			description3 = getCurrentNode().getProperty("./description3")
					.getValue().getString();
			link3 = getCurrentNode().getProperty("./link3").getValue()
					.getString();
			format = getCurrentNode().getProperty("./format").getValue()
					.getString();
			linktext3 = getCurrentNode().getProperty("./linktext3").getValue()
					.getString();

			if (getCurrentNode().hasProperty("fileReference1")
					&& !(getCurrentNode().getProperty("./fileReference1")
							.getString().equalsIgnoreCase(""))) {
				heroBean.setImg1(getCurrentNode().getProperty(
						"./fileReference1").getString());

			}
			if (getCurrentNode().hasProperty("fileReference2")
					&& !(getCurrentNode().getProperty("./fileReference2")
							.getString().equalsIgnoreCase(""))) {
				heroBean.setImg2(getCurrentNode().getProperty(
						"./fileReference2").getString());
			}

			if (getCurrentNode().hasProperty("fileReference3")
					&& !(getCurrentNode().getProperty("./fileReference3")
							.getString().equalsIgnoreCase(""))) {
				heroBean.setImg3(getCurrentNode().getProperty(
						"./fileReference3").getString());
			}

			if (getCurrentNode().hasProperty("./asset")) {
				asset = getCurrentNode().getProperty("./asset").getValue()
						.getString();
			}
			if (getCurrentNode().hasProperty("./asset2")) {
				asset2 = getCurrentNode().getProperty("./asset2").getValue()
						.getString();
			}
			if (getCurrentNode().hasProperty("./asset3")) {
				asset3 = getCurrentNode().getProperty("./asset3").getValue()
						.getString();
			}

			assetswf = host + asset;
			assetswf1 = host + asset2;
			assetswf2 = host + asset3;
			heroBean.setTitle(getCurrentNode().getProperty("./title")
					.getValue().getString());
		} catch (Exception e) {
			LOG.error("Exception in Content Rotator ::::: " + e);
		}

		String text = description;
		if (!StringUtils.isEmpty(text)) {
			description = StringUtils.replace(text, "<p>", "");
			description = StringUtils.replace(description, "</p>", "");
			description = StringUtils.replace(description, "<br>", "");
			heroBean.setDescription(description);
		}

		heroBean.setLink(link);
		heroBean.setFormat(format);
		heroBean.setLinktext(linktext);

		heroBean.setTitle2(title2);
		text = description2;
		if (!StringUtils.isEmpty(text)) {
			description2 = StringUtils.replace(text, "<p>", "");
			description2 = StringUtils.replace(description2, "</p>", "");
			description2 = StringUtils.replace(description2, "<br>", "");
			heroBean.setDescription2(description2);
		}

		heroBean.setLink2(link2);
		heroBean.setFormat(format);
		heroBean.setLinktext2(linktext2);

		heroBean.setTitle3(title3);
		text = description3;
		if (!StringUtils.isEmpty(text)) {
			description3 = StringUtils.replace(text, "<p>", "");
			description3 = StringUtils.replace(description3, "</p>", "");
			description3 = StringUtils.replace(description3, "<br>", "");
			heroBean.setDescription3(description3);
		}

		heroBean.setLink3(link3);
		heroBean.setFormat(format);
		heroBean.setLinktext3(linktext3);

		heroBean.setAssetswf(assetswf);
		heroBean.setAssetswf1(assetswf1);
		heroBean.setAssetswf2(assetswf2);

		heroBean.setAsset(asset);
		heroBean.setAsset2(asset2);
		heroBean.setAsset3(asset3);

		return (T) heroBean;
	}

}