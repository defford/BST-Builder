import type { Tree } from "../types";

type Props = {
  tree: Tree | null;
  width?: number;
  height?: number;
  variant?: 'default' | 'preview' | 'detail';
  fitToView?: boolean;
  padding?: number;
};

export default function TreeSvg({ tree, width = 640, height = 400, variant = 'default', fitToView = false, padding = 12 }: Props) {
  if (!tree) return null;
  const edgeColor = variant === 'preview' ? '#64748b' : '#cbd5e1';
  const edgeWidth = variant === 'preview' ? 2.5 : 2;
  const nodeRadius = variant === 'preview' ? 16 : 18;
  const fontSize = variant === 'preview' ? 13 : 14;

  // Compute transform to fit tree into the given width/height if requested
  let transform = '';
  if (fitToView && tree.nodes.length > 0) {
    const minX = Math.min(...tree.nodes.map((n) => n.x));
    const maxX = Math.max(...tree.nodes.map((n) => n.x));
    const minY = Math.min(...tree.nodes.map((n) => n.y));
    const maxY = Math.max(...tree.nodes.map((n) => n.y));
    const contentW = Math.max(1, maxX - minX);
    const contentH = Math.max(1, maxY - minY);
    const targetW = Math.max(1, width - padding * 2);
    const targetH = Math.max(1, height - padding * 2);
    const scale = Math.min(targetW / contentW, targetH / contentH);
    const extraX = (targetW - contentW * scale) / 2;
    const extraY = (targetH - contentH * scale) / 2;
    const tx = padding + extraX - minX * scale;
    const ty = padding + extraY - minY * scale;
    transform = `translate(${tx}, ${ty}) scale(${scale})`;
  }
  return (
    <svg width={width} height={height} viewBox={`0 0 ${width} ${height}`}>
      <defs>
        <filter id="nodeShadow" x="-50%" y="-50%" width="200%" height="200%">
          <feDropShadow dx="0" dy="1" stdDeviation="1.5" floodColor="#0f172a" floodOpacity="0.15" />
        </filter>
      </defs>
      <g stroke={edgeColor} strokeWidth={edgeWidth} transform={transform || undefined}>
        {tree.edges.map((e) => {
          const from = tree.nodes.find((n) => n.id === e.from)!;
          const to = tree.nodes.find((n) => n.id === e.to)!;
          return <line key={`${e.from}-${e.to}`} x1={from.x} y1={from.y} x2={to.x} y2={to.y} />;
        })}
      </g>
      <g transform={transform || undefined}>
        {tree.nodes.map((n) => (
          <g key={n.id}>
            <circle cx={n.x} cy={n.y} r={nodeRadius} fill="#ffffff" stroke="#1f2937" filter="url(#nodeShadow)" />
            <text x={n.x} y={n.y + 5} textAnchor="middle" fontSize={fontSize} fill="#0f172a" fontWeight={600}>
              {n.value}
            </text>
          </g>
        ))}
      </g>
    </svg>
  );
}


