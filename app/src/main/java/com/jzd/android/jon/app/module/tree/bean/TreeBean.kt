package com.jzd.android.jon.app.module.tree.bean

import com.jzd.android.jon.widget.tree.NodeId
import com.jzd.android.jon.widget.tree.NodeName
import com.jzd.android.jon.widget.tree.NodeParentId

open class TreeBean(@NodeId var id: String = "", @NodeParentId var parentId: String = "", @NodeName var name: String = "")