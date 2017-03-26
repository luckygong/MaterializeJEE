package com.materialize.jee.platform.base;

import java.io.Serializable;

public class ZTreeNode<ID> implements Serializable {
	private static final long serialVersionUID = 1L;

	private ID id;

	private ID pId;

    private String name;

    private String icon;

    private boolean checked;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public ID getpId() {
		return pId;
	}

	public void setpId(ID pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
