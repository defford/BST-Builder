import type { TreeRecord } from "./types";

const BASE = import.meta.env.VITE_API_BASE_URL as string;

export async function processNumbers(numbers: string, balanced: boolean): Promise<TreeRecord> {
  const res = await fetch(`${BASE}/api/process-numbers?balanced=${balanced}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ numbers }),
  });
  if (!res.ok) throw new Error(`Request failed: ${res.status}`);
  return res.json();
}

export async function fetchPrevious(limit: number): Promise<TreeRecord[]> {
  const res = await fetch(`${BASE}/api/previous-trees?limit=${limit}`);
  if (!res.ok) throw new Error(`Request failed: ${res.status}`);
  return res.json();
}

