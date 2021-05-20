package com.android.buildinstorageform.instorageform;

public class InfoContentUtil {
	private int TotalCount;
	private String data;

	public int getTotalCount() {
		return TotalCount;
	}

	public void setTotalCount(int totalCount) {
		TotalCount = totalCount;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "InfoContentUtil [TotalCount=" + TotalCount + ", data=" + data + "]";
	}

}