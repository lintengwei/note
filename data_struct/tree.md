# 树

## 二叉树遍历

起始就是把树形结构转换成线性结构来实现遍历。前序，中序，后序就是输出节点的位置不同。

### 前序遍历

依次访问节点的左子树，如果节点没有左子树，则访问节点的右子树，重复操作完成前序遍历

```javascript
function traverse(node){
  if(node===null){
    return 
  }
  console.log(node) //  打印当前节点
  traverse(node.leftChild)  //  递归遍历左子树
  traverse(node.rightChild) //  递归遍历右子树
}
```

### 中序遍历

找到最左边的叶子节点，开始遍历，回溯到父节点，如果父节点有右子树，则遍历父节点的右子树，重复操作

```javascript
function traverse(node){
  if(node===null){
    return 
  }
  traverse(node.leftChild)  //  递归遍历左子树
  console.log(node) //  打印当前节点
  traverse(node.rightChild) //  递归遍历右子树
}
```

### 后序遍历

找到最左边的叶子节点，然后访问同层的叶子节点，回溯到父节点，如果父节点有右子树，最后访问根节点

```javascript
function traverse(node){
  if(node===null){
    return 
  }
  traverse(node.leftChild)  //  递归遍历左子树
  traverse(node.rightChild) //  递归遍历右子树
  console.log(node) //  打印当前节点
}
```

### 层序遍历

先访问根节点，然后一层层从上到下、从左往右遍历

```javascript
function traverse(node){
  if(node===null){
    return 
  }
  console.log(node) //  打印当前节点
  traverse(node.leftChild)  //  递归遍历左子树
  traverse(node.rightChild) //  递归遍历右子树
}
```

## B-树

## B+树