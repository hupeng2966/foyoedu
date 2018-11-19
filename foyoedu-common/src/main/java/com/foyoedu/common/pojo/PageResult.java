package com.foyoedu.common.pojo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

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

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
