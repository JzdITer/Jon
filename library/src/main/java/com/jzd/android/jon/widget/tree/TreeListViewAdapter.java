package com.jzd.android.jon.widget.tree;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class TreeListViewAdapter<T> extends BaseAdapter
{

	/**
	 * 存储所有可见的Node
	 */
	private List<Node> mNodes;
	/**
	 * 存储所有的Node
	 */
	protected List<Node> mAllNodes;
	private int mExpandLevel;
	/**
	 * 点击的回调接口
	 */
	private OnNodeClickListener onNodeClickListener;

	public interface OnNodeClickListener
	{
		void onClick(Object object, int position, boolean isExpand);
	}

	public void setOnNodeClickListener(OnNodeClickListener onNodeClickListener)
	{
		this.onNodeClickListener = onNodeClickListener;
	}

	public void setExpandLevel(int level)
	{
		this.mExpandLevel = level;
	}

	public TreeListViewAdapter(Context context, ListView mTree, int defaultExpandLevel)
			throws IllegalArgumentException, IllegalAccessException
	{
		this(context, mTree, null, defaultExpandLevel);
	}

	public void clear()
	{
		this.mAllNodes.clear();
		this.mNodes.clear();
		this.notifyDataSetChanged();
	}

	public void setDatas(List<T> datas) throws IllegalArgumentException, IllegalAccessException
	{
		this.mAllNodes = TreeHelper.getSortedNodes(datas, this.mExpandLevel, 0);
		this.mNodes = TreeHelper.getVisibleNode(this.mAllNodes);
		this.notifyDataSetChanged();
	}

	/**
	 * 
	 * @param mTree
	 * @param context
	 * @param datas
	 * @param defaultExpandLevel
	 *            默认展开几级树
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public TreeListViewAdapter(Context context, ListView mTree, List<T> datas, int defaultExpandLevel)
			throws IllegalArgumentException, IllegalAccessException
	{
		this.mNodes = new ArrayList<Node>();
		this.mAllNodes = new ArrayList<Node>();
		this.mExpandLevel = 0;
		this.mExpandLevel = defaultExpandLevel;
		if (datas != null)
		{
			this.setDatas(datas);
		}

		mTree.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				TreeListViewAdapter.this.expandOrCollapse(position);
				if (TreeListViewAdapter.this.onNodeClickListener != null)
				{
					TreeListViewAdapter.this.onNodeClickListener.onClick(
							((Node) TreeListViewAdapter.this.mNodes.get(position)).getObject(), position,
							((Node) TreeListViewAdapter.this.mNodes.get(position)).isExpand());
				}
				
			}
		});

	}

	/**
	 * 相应ListView的点击事件 展开或关闭某节点
	 * 
	 * @param position
	 */
	private void expandOrCollapse(int position)
	{
		Node n = (Node) this.mNodes.get(position);
		if (n != null)
		{
			n.setExpand(!n.isExpand());
			if (!n.isLeaf())
			{
				this.mNodes = TreeHelper.getVisibleNode(this.mAllNodes);
			}

			this.notifyDataSetChanged();
		}
	}

	protected void addDatas(List<T> datas, int addPosition, int parentPosition)
			throws IllegalArgumentException, IllegalAccessException
	{
//		List<Node> nodes = TreeHelper.getSortedNodes(datas, 0, 0);
//		mAllNodes.addAll(nodes);
//		this.mNodes = TreeHelper.getVisibleNode(this.mAllNodes);
//		this.notifyDataSetChanged();
		
		Node parentNode = (Node) this.mNodes.get(parentPosition);
		// 将自定义数据排序 1：默认加入的数据的第一层不展开
		List<Node> sortedNodes = TreeHelper.getSortedNodes(datas, parentNode.getLevel(), parentNode.getLevel() + 1);
		// 得到动态数据的根节点
		List<Node> rootNodes = TreeHelper.getRootNodes(sortedNodes);
		// 将动态数据的跟节点加入到总的数据中 因为默认不展开 所以只加根节点
		int addIndex = mAllNodes.indexOf(mNodes.get(addPosition)) + 1;
		mAllNodes.addAll(addIndex,sortedNodes);
		// 为动态数据添加父子关系
		for (Node node : rootNodes)
		{
			parentNode.getChildren().add(node);
			node.setParent(parentNode);
		}
		mNodes = TreeHelper.getVisibleNode(mAllNodes);
		notifyDataSetChanged();
	}

	@Override
	public int getCount()
	{
		return mNodes.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mNodes.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Node node = mNodes.get(position);
		convertView = getConvertView(node.getObject(), position, node.isExpand(), convertView, parent);
		// 设置内边距
		if (convertView != null)
		{
			convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
		}
		return convertView;
	}

	public abstract View getConvertView(Object object, int position, boolean isExpand, View convertView,
			ViewGroup parent);

}
