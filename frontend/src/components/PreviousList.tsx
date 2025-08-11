import type { TreeRecord } from "../types";
import TreeSvg from "./TreeSvg";

type Props = { items: TreeRecord[] };

export default function PreviousList({ items }: Props) {
  if (!items.length) return null;
  return (
    <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill, minmax(280px, 1fr))", gap: 16 }}>
      {items.map((r) => (
        <div key={r.id} className="card" style={{ padding: 12 }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 8 }}>
            <div className="muted">{new Date(r.createdAt).toLocaleString()}</div>
            <span style={{ fontSize: 12, padding: '2px 8px', borderRadius: 999, background: '#eef2ff', color: '#3730a3', border: '1px solid #e0e7ff' }}>
              {r.balanced ? 'AVL' : 'BST'}
            </span>
          </div>
          <TreeSvg tree={r.tree} width={268} height={180} variant="preview" fitToView padding={10} />
          <details style={{ marginTop: 8 }}>
            <summary style={{ cursor: "pointer" }}>JSON</summary>
            <pre style={{ whiteSpace: "pre-wrap" }}>{JSON.stringify(r, null, 2)}</pre>
          </details>
        </div>
      ))}
    </div>
  );
}


