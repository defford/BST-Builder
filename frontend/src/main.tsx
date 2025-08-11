import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import './index.css'
import App from './App.tsx'
import PreviousTreesPage from './pages/PreviousTreesPage.tsx'

// Ensure the app's first page is available at "/enter-numbers"
if (window.location.pathname === '/') {
  window.history.replaceState(null, '', '/enter-numbers')
}

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/enter-numbers" element={<App />} />
        <Route path="/previous-trees" element={<PreviousTreesPage />} />
      </Routes>
    </BrowserRouter>
  </StrictMode>,
)
