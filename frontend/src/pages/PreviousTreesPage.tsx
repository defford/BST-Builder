import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { fetchPrevious } from '../api'
import type { TreeRecord } from '../types'
import PreviousList from '../components/PreviousList'

export default function PreviousTreesPage() {
  const [items, setItems] = useState<TreeRecord[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const run = async () => {
      setLoading(true)
      setError(null)
      try {
        const data = await fetchPrevious(200)
        setItems(data)
      } catch (e: any) {
        setError(e.message || 'Request failed')
      } finally {
        setLoading(false)
      }
    }
    run()
  }, [])

  return (
    <div style={{ maxWidth: 1000, margin: '0 auto', padding: 16 }}>
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', gap: 12 }}>
        <h1 style={{ margin: 0 }}>All Trees</h1>
        <nav style={{ display: 'flex', gap: 12 }}>
          <Link to="/enter-numbers">Enter Numbers</Link>
        </nav>
      </div>
      {error && <div style={{ color: 'red', marginTop: 8 }}>{error}</div>}
      {loading ? <div>Loading...</div> : <PreviousList items={items} />}
    </div>
  )
}


