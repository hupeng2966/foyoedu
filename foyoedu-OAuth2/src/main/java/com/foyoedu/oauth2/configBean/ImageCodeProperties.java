package com.foyoedu.oauth2.configBean;

public class ImageCodeProperties extends SmsCodeProperties {
	
	public ImageCodeProperties() {
		super.setLength(4);
	}
	private int width = 67;
	private int height = 23;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
