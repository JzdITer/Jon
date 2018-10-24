package com.jzd.android.jon.widget.tree;

import java.util.ArrayList;
import java.util.List;

public class Node
{

	private String id;
	private String parentId;

	private String name;

	/**
	 * 自定义的实体
	 */
	private Object object;

	/**
	 * 当前的级别
	 */
	@SuppressWarnings("unused")
	private int level;

	/**
	 * 是否展开
	 */
	private boolean isExpand = false;

	/**
	 * 孩子节点
	 */
	private List<Node> children = new ArrayList<Node>();

	/**
	 * 父节点
	 */
	private Node parent;

	public Node()
	{
	}

	public Node(String id, String parentId, String name)
	{
		this(id, parentId, name, null);
	}

	public Node(String id, String parentId, String name, Object object)
	{
		super();
		this.id = id;
		this.parentId = parentId;
		this.name = name;
		this.object = object;
	}

	public void setObject(Object object)
	{
		this.object = object;
	}

	public Object getObject()
	{
		return object;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getParentId()
	{
		return parentId;
	}

	public void setParentId(String parentId)
	{
		this.parentId = parentId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public boolean isExpand()
	{
		return isExpand;
	}

	public List<Node> getChildren()
	{
		return children;
	}

	public void setChildren(List<Node> children)
	{
		this.children = children;
	}

	public Node getParent()
	{
		return parent;
	}

	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	/**
	 * 是否为跟节点
	 * 
	 * @return
	 */
	public boolean isRoot()
	{
		return parent == null;
	}

	/**
	 * 判断父节点是否展开
	 * 
	 * @return
	 */
	public boolean isParentExpand()
	{
		return parent == null ? true : parent.isExpand();
	}

	/**
	 * 是否是叶子界点
	 * 
	 * @return
	 */
	public boolean isLeaf()
	{
		return children.size() == 0;
	}

	/**
	 * 获取level
	 */
	public int getLevel()
	{
		return parent == null ? 0 : parent.getLevel() + 1;
	}

	/**
	 * 设置展开
	 * 
	 * @param isExpand
	 */
	public void setExpand(boolean isExpand)
	{
		this.isExpand = isExpand;
		if (!isExpand)
		{
			for (Node node : children)
			{
				node.setExpand(isExpand);
			}
		}
	}

}
