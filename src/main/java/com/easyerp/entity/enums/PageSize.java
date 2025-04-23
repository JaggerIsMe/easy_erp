package com.easyerp.entity.enums;


public enum PageSize {
	SIZE15(15), SIZE20(20), SIZE30(30), SIZE40(40), SIZE50(50);
	long size;

	private PageSize(long size) {
		this.size = size;
	}

	public long getSize() {
		return this.size;
	}
}
