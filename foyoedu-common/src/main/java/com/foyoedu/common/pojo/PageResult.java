package com.foyoedu.common.pojo;

import com.foyoedu.common.utils.JsonUtils;
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
		return JsonUtils.objectToJson(this);
	}
}
