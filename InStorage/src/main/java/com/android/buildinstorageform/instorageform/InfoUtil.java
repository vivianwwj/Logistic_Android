package com.android.buildinstorageform.instorageform;

public class InfoUtil {
	private int status;
	private String msg, content;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "InfoUtil [status=" + status + ", msg=" + msg + ", content=" + content + "]";
	}

}