package com.foyoedu.common.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult implements Serializable {
	private Integer total;
	private Object data;

	public PageResult(Integer total) {
		this(total,null);
	}

	public PageResult(Integer total, Object data) {
		this.data = data;
		this.total = total;
	}
}
