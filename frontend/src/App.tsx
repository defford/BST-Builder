import { useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import './App.css'
import { processNumbers } from './api'
import type { Tree, TreeRecord } from './types'
import TreeSvg from './components/TreeSvg'

function App() {
  const [numbers, setNumbers] = useState('')
  const [balanced, setBalanced] = useState(false)
  const [latest, setLatest] = useState<Tree | null>(null)
  const [latestRecord, setLatestRecord] = useState<TreeRecord | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)
  const isNumbersEmpty = useMemo(() => numbers.trim().length === 0, [numbers])

  const onSubmit = async () => {
    setLoading(true)
    setError(null)
    try {
      const rec = await processNumbers(numbers, balanced)
      setLatest(rec.tree)
      setLatestRecord(rec)
    } catch (e: any) {
      setError(e.message || 'Request failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="container">
      <header className="topbar">
        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
          <h2 style={{ margin: 0 }}>Binary Search Tree Builder</h2>
        </div>
        <nav style={{ display: 'flex', gap: 12 }}>
          <Link to="/previous-trees">View All Trees</Link>
        </nav>
      </header>

      <div className="card" style={{ marginTop: 16 }}>
        <div className="toolbar">
          <input
            value={numbers}
            onChange={(e) => setNumbers(e.target.value)}
            placeholder="Enter numbers e.g. 7, 3 10 1 5"
            type="text"
          />

          <div className="segmented" role="tablist" aria-label="Tree Type">
            <button
              className={!balanced ? 'active' : ''}
              role="tab"
              aria-selected={!balanced}
              onClick={() => setBalanced(false)}
            >
              BST
            </button>
            <button
              className={balanced ? 'active' : ''}
              role="tab"
              aria-selected={balanced}
              onClick={() => setBalanced(true)}
            >
              AVL
            </button>
          </div>

          <button className="btn-primary" onClick={onSubmit} disabled={loading || isNumbersEmpty}>
            {loading ? 'Processing…' : 'Build Tree'}
          </button>
        </div>
        <div style={{ marginTop: 6 }}>
          <span className="muted">Use commas or spaces to separate numbers</span>
        </div>

        {error && <div style={{ color: 'crimson', marginTop: 8 }}>{error}</div>}

        <div style={{ marginTop: 16, display: 'flex', justifyContent: 'center' }}>
          <TreeSvg tree={latest} width={800} height={480} />
        </div>

        {latestRecord && (
          <div className="actions">
            <button className="btn-ghost" onClick={() => navigator.clipboard.writeText(JSON.stringify(latestRecord, null, 2))}>Copy JSON</button>
            <button className="btn-ghost" onClick={() => { setNumbers(''); setLatest(null); setLatestRecord(null); setError(null); }}>Clear</button>
          </div>
        )}

        {latestRecord && (
          <details style={{ marginTop: 12 }}>
            <summary>Latest JSON</summary>
            <pre style={{ whiteSpace: 'pre-wrap' }}>{JSON.stringify(latestRecord, null, 2)}</pre>
          </details>
        )}
      </div>
    </div>
  )
}

export default App
