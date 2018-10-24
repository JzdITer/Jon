package com.jzd.android.jon.widget.tree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TreeHelper<T>
{
	/**
	 * 传入我们的普通bean，转化为我们排序后的Node
	 * 
	 * @param datas
	 * @param defaultExpandLevel
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel, int currentLevel)
			throws IllegalArgumentException, IllegalAccessException

	{
		List<Node> result = new ArrayList<Node>();
		// 将用户数据转化为List<Node>
		List<Node> nodes = convetDataToNode(datas);
		// 拿到根节点
		List<Node> rootNodes = getRootNodes(nodes);
		// 向根节点上一次加入子节点
		for (Node node : rootNodes)
		{
			addNode(result, node, defaultExpandLevel, currentLevel);
		}
		return result;
	}
	
	/**
	 * 过滤出所有可见的Node
	 * 
	 * @param nodes
	 * @return
	 */
	public static <T> List<Node> getVisibleNode(List<Node> nodes)
	{
		List<Node> result = new ArrayList<Node>();

		for (Node node : nodes)
		{
			// 如果为跟节点，或者上层目录为展开状态
			if (node.isParentExpand())
			{
				result.add(node);
			}
		}
		return result;
	}

	/**
	 * 将我们的数据转化为树的节点
	 * 
	 * @param datas
	 * @return
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T> List<Node> convetDataToNode(List<T> datas)
			throws IllegalArgumentException, IllegalAccessException

	{
		List<Node> nodes = new ArrayList<Node>();
		Node node = null;

		for (T t : datas)
		{
			String id = null;
			String parentId = null;
			String name = null;
			Class<? extends Object> clazz = t.getClass();
			Field[] declaredFields = clazz.getDeclaredFields();
			for (Field f : declaredFields)
			{
				if(id != null && parentId != null && name != null)
				{
					break;
				}
				if (f.getAnnotation(NodeId.class) != null)
				{
					f.setAccessible(true);
					id = (String) f.get(t);
					continue;
				}
				if (f.getAnnotation(NodeParentId.class) != null)
				{
					f.setAccessible(true);
					parentId = (String) f.get(t);
					continue;
				}
				if (f.getAnnotation(NodeName.class) != null)
				{
					f.setAccessible(true);
					name = (String) f.get(t);
					continue;
				}
			}
			if (id != null && parentId != null && name != null)
			{
				node = new Node(id, parentId, name,t);
				nodes.add(node);
			}

		}

		/**
		 * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
		 */
		for (int i = 0; i < nodes.size(); i++)
		{
			Node n = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++)
			{
				Node m = nodes.get(j);
				// n是m的父
				if (m.getParentId().equals(n.getId()))
				{
					n.getChildren().add(m);
					m.setParent(n);
				} else if (m.getId().equals(n.getParentId()))
				{
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}

		return nodes;
	}

	/**
	 * 获取根节点
	 * 
	 * @param nodes
	 * @return
	 */
	public static <T> List<Node> getRootNodes(List<Node> nodes)
	{
		List<Node> root = new ArrayList<Node>();
		for (Node node : nodes)
		{
			if (node.isRoot())
			{
				root.add(node);
			}
		}
		return root;
	}

	/**
	 * 把一个节点上的所有的内容都挂上去
	 */
	private static <T> void addNode(List<Node> nodes, Node node, int expandLeval, int currentLevel)
	{
		nodes.add(node);
		if (expandLeval >= currentLevel)
		{
			node.setExpand(true);
		}

		if (node.isLeaf())
		{
			return;
		}
		for (int i = 0; i < node.getChildren().size(); i++)
		{
			addNode(nodes, node.getChildren().get(i), expandLeval, currentLevel + 1);
		}
	}

}
