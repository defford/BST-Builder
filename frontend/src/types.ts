export type TreeNode = { id: number; value: number; x: number; y: number };
export type TreeEdge = { from: number; to: number };
export type TreeLayout = { hSpacing: number; vSpacing: number };
export type Tree = { nodes: TreeNode[]; edges: TreeEdge[]; layout: TreeLayout };

export type TreeRecord = {
  id: number;
  inputNumbers: string;
  balanced: boolean;
  createdAt: string;
  tree: Tree;
};

